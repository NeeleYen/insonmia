package com.test.Insomnia.controller.daoTesting;


import java.sql.Timestamp;
import java.time.YearMonth;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.Insomnia.dao.BulletinDao;
import com.test.Insomnia.dao.ChatDao;
import com.test.Insomnia.dao.CreditCardDao;
import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.dao.UserPictureDao;
import com.test.Insomnia.model.Bulletin;
import com.test.Insomnia.model.Chat;
import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.User;
import com.test.Insomnia.model.UserPicture;
import com.test.Insomnia.model.UserPicturePK;

@Controller
public class TestDaoController_user {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CreditCardDao creditCardDao;

	@Autowired
	private UserPictureDao userPictureDao;
	@Autowired
	private ChatDao chatDao;
	
	@Autowired
	private BulletinDao bulletinDao;
	
	@Autowired
	private ProductDao productDao;
	
	@GetMapping("/addUserBulletin")
	public String addUserBulletin() {
		Bulletin neeleBulletin = new Bulletin();
		neeleBulletin.setContent("小火龍買的到嗎");
		Timestamp datetimeBulletin1 = new Timestamp(System.currentTimeMillis());
		neeleBulletin.setAddedTime(datetimeBulletin1);
		Timestamp datetimeBulletin2 = new Timestamp(System.currentTimeMillis());
		neeleBulletin.setModifyTime(datetimeBulletin2);
		Optional<Product> findByIdBulletin1 = productDao.findById(2);
		if (findByIdBulletin1.isPresent()) {
			Product product = findByIdBulletin1.get();
			neeleBulletin.setProduct(product);	
		}
		Optional<User> findByIdBulletin2 = userDao.findById(1);
		if (findByIdBulletin2.isPresent()) {
			User user = findByIdBulletin2.get();
			neeleBulletin.setUserId(user);
		}
		bulletinDao.save(neeleBulletin);
		
		Bulletin lisaBulletin = new Bulletin();
		lisaBulletin.setContent("現在只要你儲值課金就可以擁有一隻小火龍喔!");
		Timestamp datetimeBulletin3 = new Timestamp(System.currentTimeMillis());
		lisaBulletin.setAddedTime(datetimeBulletin3);
		Timestamp datetimeBulletin4 = new Timestamp(System.currentTimeMillis());
		lisaBulletin.setModifyTime(datetimeBulletin4);
		Optional<Product> findByIdBulletin3 = productDao.findById(2);
		if (findByIdBulletin3.isPresent()) {
			Product product = findByIdBulletin3.get();
			lisaBulletin.setProduct(product);	
		}
		Optional<User> findByIdBulletin4 = userDao.findById(2);
		if (findByIdBulletin4.isPresent()) {
			User user = findByIdBulletin4.get();
			lisaBulletin.setUserId(user);		
		}
		bulletinDao.save(lisaBulletin);
		
		Bulletin xuanBulletin = new Bulletin();
		xuanBulletin.setContent("那哪裡有噴火龍?");
		Timestamp datetimeBulletin5 = new Timestamp(System.currentTimeMillis());
		xuanBulletin.setAddedTime(datetimeBulletin5);
		Timestamp datetimeBulletin6 = new Timestamp(System.currentTimeMillis());
		xuanBulletin.setModifyTime(datetimeBulletin6);
		Optional<Product> findByIdBulletin5 = productDao.findById(2);
		if (findByIdBulletin5.isPresent()) {
			Product product = findByIdBulletin5.get();
			xuanBulletin.setProduct(product);	
		}
		Optional<User> findByIdBulletin6 = userDao.findById(3);
		if (findByIdBulletin6.isPresent()) {
			User user = findByIdBulletin6.get();
			xuanBulletin.setUserId(user);		
		}
		bulletinDao.save(xuanBulletin);
		
		
		return "home";
	}
	
//	@GetMapping("/addUserBulletin")
//	public String addUserBulletin() {
//		return "home";
//	}
//	@GetMapping("/addUserBulletin")
//	public String addUserBulletin() {
//		return "home";
//	}
//	@GetMapping("/addUserBulletin")
//	public String addUserBulletin() {
//		return "home";
//	}
	
	
	

	@GetMapping("/addUserChat")
	public String addUserChat() {
		Chat neele = new Chat();
		neele.setContent("neele測試資料");
        Timestamp datetime = new Timestamp(System.currentTimeMillis());
        neele.setTime(datetime);		
		Optional<User> findId1 = userDao.findById(1);
		if (findId1.isPresent()) {
			User user = findId1.get();
			neele.setUserIdSender(user); // 發送者
		}
		Optional<User> findId2 = userDao.findById(2);
		if (findId2.isPresent()) {
			User user = findId2.get();
			neele.setUserIdReceiver(user); // 接收者
			chatDao.save(neele);
		}
		
		Chat lisa = new Chat();
		lisa.setContent("lisa測試資料");
		Timestamp datetime2 = new Timestamp(System.currentTimeMillis());
		lisa.setTime(datetime2);		
		Optional<User> findId3 = userDao.findById(2);
		if (findId3.isPresent()) {
			User user = findId3.get();
			lisa.setUserIdSender(user); // 發送者
		}
		Optional<User> findId4 = userDao.findById(1);
		if (findId4.isPresent()) {
			User user = findId4.get();
			lisa.setUserIdReceiver(user); // 接收者
			chatDao.save(lisa);
		}
		
		Chat xuan = new Chat();
		xuan.setContent("xuan測試資料");
		Timestamp datetime3 = new Timestamp(System.currentTimeMillis());
		xuan.setTime(datetime3);		
		Optional<User> findId5 = userDao.findById(3);
		if (findId5.isPresent()) {
			User user = findId5.get();
			xuan.setUserIdSender(user); // 發送者
		}
		Optional<User> findId6 = userDao.findById(1);
		if (findId6.isPresent()) {
			User user = findId6.get();
			xuan.setUserIdReceiver(user); // 接收者
			chatDao.save(xuan);
		}
	
		return "home";
	}
	
	@GetMapping("/addUser")
	public String addUser() {
//		Area taiwan = new Area();
		User neele = new User();
//		taiwan.setArea("taiwan");
		neele.setAddress("高雄市前金區");
		neele.setEmail("neele@github.com");
		neele.setUsername("Neele");
		neele.setPassword("neele");
//		areaDao.save(taiwan);
		userDao.save(neele);

		User Lisa = new User();
		Lisa.setAddress("高雄市前金區");
		Lisa.setEmail("Lisa@github.com");
		Lisa.setUsername("Lisa");
		Lisa.setPassword("lisa");
		userDao.save(Lisa);

		User Xuan = new User();
		Xuan.setAddress("高雄市前金區");
		Xuan.setEmail("Xuan@github.com");
		Xuan.setUsername("Xuan");
		Xuan.setPassword("xuan");
		userDao.save(Xuan);
		return "home";
	}

	@GetMapping("/addCreditCard")
	public String addCreditCard() {
		CreditCard neeleCard = new CreditCard();
		neeleCard.setCancelStatus(1);
		neeleCard.setCardNumber(Long.valueOf("0009876598768765"));
		neeleCard.setCvc(111);
		neeleCard.setValidDate(YearMonth.of(2023, 01));
		Optional<User> userQuery1 = userDao.findById(1);
		if (userQuery1.isPresent()) {
			User user1 = userQuery1.get();
			neeleCard.setUserId(user1);
			creditCardDao.save(neeleCard);
		}

		CreditCard lisaCard = new CreditCard();
		lisaCard.setCancelStatus(1);
		lisaCard.setCardNumber(Long.valueOf("0009876598768765"));
		lisaCard.setCvc(222);
		lisaCard.setValidDate(YearMonth.of(2023, 02));
		Optional<User> userQuery2 = userDao.findById(2);
		if (userQuery2.isPresent()) {
			User user2 = userQuery2.get();
			lisaCard.setUserId(user2);
			creditCardDao.save(lisaCard);
		}

		CreditCard xuanCard = new CreditCard();
		xuanCard.setCancelStatus(1);
		xuanCard.setCardNumber(Long.valueOf("0009876598768765"));
		xuanCard.setCvc(333);
		xuanCard.setValidDate(YearMonth.of(2023, 03));
		Optional<User> userQuery3 = userDao.findById(3);
		if (userQuery3.isPresent()) {
			User user3 = userQuery3.get();
			xuanCard.setUserId(user3);
			creditCardDao.save(xuanCard);
		}
		return "home";

	}

	// 雙主鍵測試，沒放圖片...暫時用null
	@GetMapping("/addUserPicture")
	public String addUserPicture(UserPicture p) {
		UserPicture neelePicture = new UserPicture();
		neelePicture.setPicture(null); // 要放圖片檔案的...
		Optional<User> findById1 = userDao.findById(1); // 連接的user id
		if (findById1.isPresent()) {
			User user1 = findById1.get();
			neelePicture.setUserPicturePk(new UserPicturePK(user1, 1)); // 雙主鍵
			userPictureDao.save(neelePicture);
		}

		UserPicture neelePicture2 = new UserPicture();
		neelePicture2.setPicture(null);
		Optional<User> findById11 = userDao.findById(1);
		if (findById11.isPresent()) {
			User user1 = findById11.get();
			neelePicture2.setUserPicturePk(new UserPicturePK(user1, 2));
			userPictureDao.save(neelePicture2);
		}

		UserPicture lisaPicture = new UserPicture();
		lisaPicture.setPicture(null); 
		Optional<User> findById2 = userDao.findById(2); 
		if (findById2.isPresent()) {
			User user1 = findById2.get();
			lisaPicture.setUserPicturePk(new UserPicturePK(user1, 1));
			userPictureDao.save(lisaPicture);
		}

		UserPicture xuanPicture = new UserPicture();
		xuanPicture.setPicture(null);
		Optional<User> findById3 = userDao.findById(3);
		if (findById3.isPresent()) {
			User user1 = findById3.get();
			xuanPicture.setUserPicturePk(new UserPicturePK(user1, 2));
			userPictureDao.save(xuanPicture);
		}
		return "home";
	}

}