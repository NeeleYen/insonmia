package com.test.Insomnia.Service;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.test.Insomnia.dao.BulletinDao;
import com.test.Insomnia.dao.CreditCardDao;
import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.dao.UserPictureDao;
import com.test.Insomnia.dao.UserResetPasswordDao;
import com.test.Insomnia.dao.UserVerificationDao;
import com.test.Insomnia.model.Bulletin;
import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.User;
import com.test.Insomnia.model.UserPicture;
import com.test.Insomnia.model.UserPicturePK;
import com.test.Insomnia.model.UserResetPassword;
import com.test.Insomnia.model.UserVerification;

@Service
@Transactional // 要用springframework
public class UserService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserResetPasswordDao userResetPasswordDao;

	@Autowired
	private UserVerificationDao userVerificationDao;

	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private UserPictureDao userPictureDao;

	@Autowired
	private CreditCardDao creditCardDao;

	@Autowired
	private BulletinDao bulletinDao;

	// 註冊user
	public void insert(User user) {
//		密碼可以有空白
		user.setAddress(user.getAddress().trim());
		user.setUsername(user.getUsername().trim());
		user.setEmail(user.getEmail().trim());
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword())); // 密碼經過雜湊保護了
		user.setPermissions("unchecked"); // 尚未驗證
		userDao.save(user);

//		這是註冊驗證用的
		String token = UUID.randomUUID().toString();
		UserVerification userVerification = new UserVerification();
		userVerification.setUserId(user);
		userVerification.setVerificationToken(token);
		LocalDateTime DateTime = LocalDateTime.now().plusMinutes(60);
		Date expiration = Date.from(DateTime.atZone(ZoneId.systemDefault()).toInstant());
		userVerification.setVerificationMaturity(expiration);
		userVerificationDao.save(userVerification);

//		註冊後就發送email
		SimpleMailMessage message = new SimpleMailMessage(); // 要寄送的email+url
		message.setFrom("your-Email"); // your-Email
		message.setTo(user.getEmail().trim());
		message.setSubject("會員驗證");
		message.setText("請點擊下面的連結驗證您的會員：http://localhost:8888/my-insomnia/public/user/Verification?token="
				+ userVerification.getVerificationToken());
		javaMailSender.send(message);

//		這是修改用的
		UserResetPassword userResetP = new UserResetPassword();
		userResetP.setUserId(user);
		userResetP.setResetPasswordToken(token);
		LocalDateTime expirationDateTime = LocalDateTime.now();
		Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
		userResetP.setMaturity(expirationDate);
		userResetPasswordDao.save(userResetP);
	}

	// 刪除user透過id
	public void deleteById(Integer id) {
		userDao.deleteById(id);
	}

	// 判斷有沒有重複註冊
	public User findName(String name) {
		User user = userDao.findUserName(name.trim());
		if (user != null) {
			// 代表有重複
			return user;
		}
		return null;
	}

	public User findEmail(String email) {
		User user = userDao.findUserEmail(email.trim());
		if (user != null) {
			return user;
		}
		return null;
	}

	// 登入判斷
	public User userLogIn(String email, String password) {
		User user = userDao.findUserLogin(email.trim(), password);
		if (user != null) {
			return user;
		}
		return null;
	}

	// 修改地址
	public void userUpdateAddress(String address, Integer id) {
		if (id != null) {
			userDao.updateUserAddress(address, id);
		}
	}
	
	// 修改密碼
	public void userUpdatePassword(String password, Integer id) {
		if (id != null) {
			userDao.updateUserPassword(new BCryptPasswordEncoder().encode(password), id);
		}
	}

//	重設密碼，寄信用的
	public void userSendEmail(String email) {
		User findEmail = userDao.findUserEmail(email.trim());

		if (findEmail != null) {
			UserResetPassword userResetPassword = findEmail.getUserResetPasswordId();

			SimpleMailMessage message = new SimpleMailMessage(); // 要寄送的email+url
			message.setFrom("your-Email"); // your-Email
			message.setTo(findEmail.getEmail());
			message.setSubject("重設密碼");
			message.setText("請點擊下面的連結重置您的密碼：http://localhost:8888/my-insomnia/public/user/forgetPassword?token="
					+ userResetPassword.getResetPasswordToken());
			javaMailSender.send(message);
		}
	}

//	重新發送修改密碼
	public void userUpdateToken(String email) {
		User findEmail = userDao.findUserEmail(email.trim());
		if (findEmail != null) {
			try {
				UserResetPassword userReset = findEmail.getUserResetPasswordId();
				if (userReset == null) {
					UserResetPassword userResetP = new UserResetPassword();
//					新建一個，還沒有重複的
					userResetP.setUserId(findEmail);

					String token = UUID.randomUUID().toString();
					userResetP.setResetPasswordToken(token);

					LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(30);
					Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
					userResetP.setMaturity(expirationDate);

					userResetPasswordDao.save(userResetP);
				}
				userReset.setUserId(findEmail);
				String token = UUID.randomUUID().toString();
				userReset.setResetPasswordToken(token);
				LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(30);
				Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
				userReset.setMaturity(expirationDate);
				userResetPasswordDao.save(userReset);

			} catch (Exception e) {

			}
		}
	}

//	修改密碼後要把token的時間砍掉 => 就不能點入後再重新修改
	public void deleteToken(String resetPasswordToken) {
		UserResetPassword findToken = userResetPasswordDao.findToken(resetPasswordToken);
		LocalDateTime expirationDateTime = LocalDateTime.now();
		Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
		findToken.setMaturity(expirationDate);
		userResetPasswordDao.save(findToken);
	}

//	重新取得權限驗證
	public void UpdateVerificationToken(Principal principal) {
		User findEmail = userDao.findUserEmail(principal.getName());
		if (findEmail != null) {
			try {
				UserVerification userVerification = findEmail.getUserVerification();
				if (userVerification == null) {
					UserVerification userVerificatio = new UserVerification();
//					新建一個，還沒有重複的
					userVerificatio.setUserId(findEmail);

					String token = UUID.randomUUID().toString();
					userVerificatio.setVerificationToken(token);

					LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(60);
					Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
					userVerificatio.setVerificationMaturity(expirationDate);

					userVerificationDao.save(userVerificatio);
				}
				userVerification.setUserId(findEmail);
				String token = UUID.randomUUID().toString();
				userVerification.setVerificationToken(token);
				LocalDateTime expirationDateTime = LocalDateTime.now().plusMinutes(60);
				Date expirationDate = Date.from(expirationDateTime.atZone(ZoneId.systemDefault()).toInstant());
				userVerification.setVerificationMaturity(expirationDate);
				userVerificationDao.save(userVerification);

			} catch (Exception e) {

			}
		}
	}

//	驗證權限、寄信(重寄送用)
	public void verificationSendEmail(Principal principal) {
		User findEmail = userDao.findUserEmail(principal.getName());
		UserVerification userVerification = findEmail.getUserVerification();

		SimpleMailMessage message = new SimpleMailMessage(); // 要寄送的email+url
		message.setFrom("your-Email"); // your-Email
		message.setTo(principal.getName());
		message.setSubject("會員驗證");
		message.setText("請點擊下面的連結驗證您的會員：http://localhost:8888/my-insomnia/public/user/Verification?token="
				+ userVerification.getVerificationToken());
		javaMailSender.send(message);
	}

//	找token(修改密碼)
	public UserResetPassword findUserToken(String resetPasswordToken) {
		UserResetPassword findToken = userResetPasswordDao.findToken(resetPasswordToken);
		if (findToken != null) {
			return findToken;
		}
		return null;
	}

//	找token(修改權限)
	public UserVerification findVerificationToken(String verificationToken) {

		UserVerification findToken = userVerificationDao.findToken(verificationToken);
		if (findToken == null) {
			return null;
		}
		return findToken;
	}

//	修改密碼(信箱驗證)
	public void resetUser(String password, Integer id) {
		String newPassword = new BCryptPasswordEncoder().encode(password);
		userDao.resetUser(newPassword, id);
	}

//	修改會員權限，驗證信收到後(Token找到User)
	public void UserPermissions(String TokenverificationToken) {
		UserVerification findToken = userVerificationDao.findToken(TokenverificationToken);
		User userId = findToken.getUserId();
		userDao.updateUserPermissions("member", userId.getId());
	}

//	使用者圖片(add)轉成byte
	public byte[] addUserPicture(MultipartFile photo) {
		byte[] bytes = null;
		try {
			bytes = photo.getBytes();
			return bytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

//	儲存/更新圖片
	public void saveUserPicture(UserPicture picture) {
		userPictureDao.save(picture);
	}

	public UserPicture getUserPictureById(UserPicturePK picturePK) {
		Optional<UserPicture> findById = userPictureDao.findById(picturePK);
		if (findById.isEmpty()) {
			return null;
		}
		return findById.get();
	}

//	儲存卡號
	public CreditCard saveCard(CreditCard card) {
		CreditCard save = creditCardDao.save(card);
		return save;
	}

//	卡號處理、查詢信用卡
//	Map<卡片id, xxx>
//	Map<Integer, String>
//	並且只放入狀態碼不等於-1的(就不會印出信用卡卡號了)
	public Map<Integer, String> findCardById(User user) {
		Set<CreditCard> creditCards = user.getCreditCards();

		Map<Integer, String> cardMap = new HashMap<>();
		for (CreditCard creditCard : creditCards) {
			if (creditCard.getCancelStatus() == -1) {
				continue;
			}
			String replace = "-xxxx-xxxx-";
			Long cradNum = creditCard.getCardNumber();
			String strCardNumFull = String.format("%016d", cradNum);
			StringBuffer stringBuffer = new StringBuffer(strCardNumFull);
			stringBuffer.replace(4, 12, replace);
			cardMap.put(creditCard.getCreditCardId(), stringBuffer.toString());
		}
		return cardMap;
	}

	public CreditCard updateCardCancelStatus(CreditCard cards) {
		cards.setCancelStatus(-1);
		CreditCard save = creditCardDao.save(cards);
		return save;
	}

//	使用者新增留言
	public void bulletinSave(Bulletin bulletin) {
		bulletinDao.save(bulletin);
	}
	
	
	

//	由user、product找到留言板，單獨給會員自己看用
//	public Bulletin bulletinfindById(User user, Product product) {
//		Bulletin findByProductIdAndUserId = bulletinDao.findByProductIdAndUserId(product, user);
//		if (findByProductIdAndUserId==null) {
//			return null;
//		}
//		return findByProductIdAndUserId;
//	}

//	-----james added: start-----
//	get user by id (for: 測試使用postman輸入product)
	public User findById(Integer id) {
		Optional<User> optional = userDao.findById(id);

		if (optional.isEmpty()) {
			return null;
		}

		return optional.get();
	}

	public User principleToUser(Principal principal) {
		if (principal == null) {
			return null;
		}
		User findEmail = this.findEmail(principal.getName()); // 找出登入的email
		Integer id = findEmail.getId(); // 找到id

		User user = this.findById(id); // Lisa is here
		return user;
	}
//	-----james added: end-----
}
