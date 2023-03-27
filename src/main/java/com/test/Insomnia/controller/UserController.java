package com.test.Insomnia.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.Insomnia.Service.CreditCardService;
import com.test.Insomnia.Service.ProductService;
import com.test.Insomnia.Service.UserService;
import com.test.Insomnia.dto.AlterUserPassword;
import com.test.Insomnia.dto.BulletinDTO;
import com.test.Insomnia.dto.CreditCardDTO;
import com.test.Insomnia.dto.UserDTO;
import com.test.Insomnia.model.Bulletin;
import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.User;
import com.test.Insomnia.model.UserPicture;
import com.test.Insomnia.model.UserPicturePK;
import com.test.Insomnia.model.UserResetPassword;
import com.test.Insomnia.model.UserVerification;
import com.test.Insomnia.utils.FormatValidAndTransform;

@Controller
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private ServletContext context;

	@Autowired
	private CreditCardService creditCardService;

	@Autowired
	private FormatValidAndTransform formatValidAndTransform;

	@Autowired
	private ProductService productService;

	// 沒有權限用的網頁
	@GetMapping("/public/unchecked")
	public String fail() {
		return "user/unchecked";
	}

	// 給登入用的
	@GetMapping("/public/signIn")
	public String test(RedirectAttributes redirectAttributes) {
		return "signIn";
	}
	
//	登出回到首頁
	@GetMapping("/logout")
	public String test2() {
		return "redirect:/";
	}

	// 保留，第一版
	@GetMapping("/public/user/add")
	public String saveUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/signUp";
	}

	// 註冊的信箱、使用名稱有重複要回應重複(不能讓他註冊)
	// 1. 先檢查有無重複使用者名
	// 2. email有無重複
	// 3. 沒有重複再輸入
	@PostMapping("/public/user/post")
	public ResponseEntity<Map<String, Object>> addUserPost(@ModelAttribute User users, Model model) {
		User findName = userService.findName(users.getUsername().trim());
		User findEmail = userService.findEmail(users.getEmail().trim());
		Map<String, Object> response = new HashMap<>();
		if (findEmail == null && findName == null) {
			userService.insert(users);
			response.put("status", "success");

			return ResponseEntity.ok(response);
		} else {
			if (findEmail != null && findName != null) {
				response.put("status", "errorEmailName");
			} else if (findName != null) {
				response.put("status", "errorName");
			} else {
				response.put("status", "errorEmail");
			}
			return ResponseEntity.ok(response);
		}
	}

	// postman測試 可以刪除
	@ResponseBody
	@DeleteMapping("/user/delete")
	public String deleteUser(@RequestParam("id") Integer id) {
		try {
			userService.deleteById(id);
			return "刪除成功";
		} catch (Exception e) {
			return "刪除失敗";
		}
	}

	// 轉到登入頁面而已
	@GetMapping("/public/user/sign")
	public String signInUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "user/signIn";
	}

//	登入還沒弄好: 
//	檢查帳號是否存在
//	比對密碼是否相等
//	餅乾
//	加密
	@PostMapping("/public/user/logIn")
	public ResponseEntity<Map<String, Object>> logIn(@RequestParam String email,
			@RequestParam("password") String password) { // email, password 從前端輸入進來的
		// 資料庫的資料
		User user = userService.userLogIn(email, password); // 到資料庫查詢(用email,password)
		if (user != null) {
			Map<String, Object> response = new HashMap<>();
			response.put("status", "success");
			return ResponseEntity.ok(response);
		} else {
			Map<String, Object> response = new HashMap<>();
			response.put("status", "error");
			return ResponseEntity.badRequest().body(response);
		}
	}

//	登入失敗跳轉頁面
	@GetMapping("/public/user/signInError")
	public String signInErrorUser(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "NO";
	}

//	修改使用者資料- User查詢，查出自己登入是誰?
	@GetMapping("/users/replace")
	public String replace(Principal principal, Model model) {
		User user = new User();
		User userP = userService.principleToUser(principal);
		String address = userP.getAddress();
		if (address != null) {
			user.setAddress(address);
		}
		model.addAttribute("user", user);
		return "user/alter";
	}

	// 強制要求要先登入才能修改
	@PostMapping("/users/alter")
	public ResponseEntity<Map<String, Object>> alter(@RequestParam String address, Principal principal) {
		Map<String, Object> response = new HashMap<>();
		try {
			User user = userService.principleToUser(principal);
			Integer userId = user.getId();
			if (userId != null) {
				userService.userUpdateAddress(address, userId);
				response.put("status", "update");
				return ResponseEntity.ok(response);
			}
		} catch (Exception e) {
			response.put("status", "error");
			return ResponseEntity.badRequest().body(response);
		}
		response.put("status", "error");
		return ResponseEntity.badRequest().body(response);
	}
	
//	TODO 密碼
	@GetMapping("/users/replacePassword")
	public String replacePassword(Principal principal, @ModelAttribute("dto") AlterUserPassword alterUserPassword, Model model) {
		if (principal==null) {
			return "redirect:/public/user/sign";
		}
		model.addAttribute("dto", alterUserPassword);
		return "user/alterPassword";
	}
	
		// 強制要求要先登入才能修改，密碼
	@PostMapping("/users/alterPassword")
	public ResponseEntity<Map<String, Object>> alterPassword(@ModelAttribute("dto") AlterUserPassword alterUserPassword, Principal principal) {
		Map<String, Object> response = new HashMap<>();
		try {
			User user = userService.principleToUser(principal);
			String password = user.getPassword();
			BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
			if (!bCryptPasswordEncoder.matches(alterUserPassword.getPasswordNow(), password)) {
				response.put("status", "error");
				return ResponseEntity.badRequest().body(response);				
			}
			Integer userId = user.getId();
			String passwordCheck = alterUserPassword.getPasswordCheck();
			String passwordAlter = alterUserPassword.getPasswordAlter();
			if (userId != null && passwordCheck.equals(passwordAlter)) {	
				userService.userUpdatePassword(passwordAlter, userId);
				response.put("status", "update");
				return ResponseEntity.ok(response);
			}
		} catch (Exception e) {
			response.put("status", "error");
			return ResponseEntity.badRequest().body(response);
		}
		response.put("status", "error");
		return ResponseEntity.badRequest().body(response);
	}
	
	
	

//	從送會員驗證信件(60分鐘內驗證)，發送後跳轉回首頁
	@PostMapping("/user/Verification")
	public ResponseEntity<Map<String, Object>> verificationAgain(Principal principal) {
		User user = userService.principleToUser(principal);
		Map<String, Object> response = new HashMap<>();
		if (user.getPermissions().equals("unchecked")) {
			userService.UpdateVerificationToken(principal);
			userService.verificationSendEmail(principal);
			response.put("success", "已送出會員驗證，請於60分鐘內至email進行驗證");
			return ResponseEntity.ok(response);
		} else {
			response.put("error", "輸入錯誤");
			return ResponseEntity.badRequest().body(response);
		}
	}

//	重設密碼，跳轉頁面
	@GetMapping("/public/user/forget")
	public String replaceInUser(@ModelAttribute("dto") UserDTO dto, Model model) {
		model.addAttribute("dto", dto);
		return "user/forgetPassword";
	}

	@PostMapping("/public/forgotPassword")
	public ResponseEntity<Map<String, Object>> forgotPassword(@ModelAttribute("dto") UserDTO dto) {
		Map<String, Object> response = new HashMap<>();
		User findEmail = userService.findEmail(dto.getStringEmail());
		if (findEmail != null) {
//			有搜尋到，要發送email
			userService.userUpdateToken(dto.getStringEmail());
			userService.userSendEmail(dto.getStringEmail());
			response.put("success", "請至email修改密碼");
			return ResponseEntity.ok(response);
		}
		response.put("error", "輸入錯誤");
		return ResponseEntity.badRequest().body(response);
	}

//	http://localhost:8888/my-insomnia/public/user/forgetPassword?token=........
//	檢查時間，設定30分鐘內驗證
	@GetMapping("/public/user/forgetPassword")
	public String reset(@RequestParam("token") String resetPasswordToken, Model model) {

		UserResetPassword findUserToken = userService.findUserToken(resetPasswordToken);
		Date maturity = findUserToken.getMaturity();
		if (findUserToken != null && maturity.after(new Date())) {
			UserResetPassword user = new UserResetPassword();
			user.setResetPasswordToken(findUserToken.getResetPasswordToken());
			model.addAttribute("user", user);
			return "user/reset";
		}
		return "NO";
	}

	@GetMapping("/public/user/reset")
	public String reset() {
		return "user/OK"; //
	}

//	email修改密碼
	@PostMapping("/public/user/resetPassword")
	public String resetPassword(@RequestParam("password") String password,
			@RequestParam("token") String resetPasswordToken) {
		UserResetPassword findUserToken = userService.findUserToken(resetPasswordToken);
		if (findUserToken != null) {
			User userId = findUserToken.getUserId();
			Integer id = userId.getId();
			userService.resetUser(password, id);
			userService.deleteToken(resetPasswordToken);
			return "redirect:/public/user/reset";
		}
		return "NO";
	}

	@GetMapping("/public/user/member")
	public String member() {
		return "user/Verification";
	}

	@GetMapping("/public/user/checked")
	public String checked() {
		return "user/checked";
	}

	@GetMapping("/public/user/unchecked")
	public String unchecked() {
		return "unchecked";
	}

//	有連進來就會修改、驗證成功(會員權限)
	@GetMapping("/public/user/Verification")
	public String verification(@RequestParam("token") String verificationToken) {
		UserVerification findVerificationToken = userService.findVerificationToken(verificationToken);
		if (findVerificationToken == null) {
			return "redirect:/public/user/unchecked";
		}
		Date maturity = findVerificationToken.getVerificationMaturity();
		if (findVerificationToken != null && maturity.after(new Date())) {
			User userId = findVerificationToken.getUserId();
			if (userId.getPermissions().equals("unchecked")) {
//			修改user權限
				userService.UserPermissions(verificationToken);
				return "redirect:/public/user/member";
			} else {
				return "redirect:/public/user/checked";
			}
		}
		return "redirect:/public/user/unchecked";
	}

	@GetMapping("/user/icon")
	public String userIcon(Principal principal) {
		User user = userService.principleToUser(principal);
		if (user == null) {
			return "redirect:/public/user/sign";
		}
		return "user/userIcon";
	}

//	上傳後立即顯示圖片
	@ResponseBody
	@PostMapping("/user/priture")
	public ResponseEntity<byte[]> addUserPicture(@RequestParam("picture") MultipartFile photo, Principal principal) {
//		確定會員有無登入
		if (principal == null) {
			return null;
		}
//		找到User
		User user = userService.principleToUser(principal);
//		要增加的會員圖片PK，放User、圖片編號
		UserPicturePK userPicturePK = new UserPicturePK(user, 1);
//		使用者傳的檔案轉成byte
		byte[] addUserPicture = userService.addUserPicture(photo);
//		設定好使用者圖片(PK、圖片的byte)
		UserPicture userPicture = new UserPicture(userPicturePK, addUserPicture);
//		儲存
		userService.saveUserPicture(userPicture);

		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(addUserPicture);
	}

//	首頁圖
//	沒登入前不顯示
//	未上傳前用預設圖片
//	上傳後用使用者上傳的圖

	@GetMapping("/public/user/avatar")
	public ResponseEntity<byte[]> getAvatar(Principal principal) {
		String pathStr = context.getRealPath("/images/") + "default.PNG";
		System.out.println(pathStr);
		byte[] avatar = null;
		try {
			avatar = Files.readAllBytes(Paths.get(pathStr));
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (principal == null) {
			return null;
		}
		User user = userService.principleToUser(principal);
		Set<UserPicture> userPicture = user.getUserPicture();
		if (userPicture.isEmpty()) {
//			不存在，沒上傳
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(avatar);
		}
		UserPicturePK userPicturePK = new UserPicturePK(user, 1);
		UserPicture userPictureById = userService.getUserPictureById(userPicturePK);
		byte[] picture = userPictureById.getPicture();
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(picture);
	}

//	信用卡跳轉
	@GetMapping("/users/insertCard")
	public String replaceCard(@ModelAttribute("dto") CreditCardDTO dto, Principal principal, Model model) {
		model.addAttribute("dto", dto);
		CreditCard card = new CreditCard();
		model.addAttribute("card", card);
		return "user/CreditCard";
	}

//	信用卡新增
//	4311952222222222
	@PostMapping("/user/CreditCard")
	public ResponseEntity<Map<String, Object>> saveCard(@ModelAttribute("dto") CreditCardDTO dto, Principal principal) {
		Map<String, Object> response = new HashMap<>();

		User user = userService.principleToUser(principal);
		Long card = formatValidAndTransform.stringToLong(dto.getCard()).get("value");
		Integer cardCvc = formatValidAndTransform.stringToInteger(dto.getCardCvc()).get("value");
		Integer cardYear = formatValidAndTransform.stringToInteger(dto.getCardYear()).get("value");
		Integer cardMonth = formatValidAndTransform.stringToInteger(dto.getCardMonth()).get("value");
		if (card == null || cardCvc == null || cardYear == null || cardMonth == null) {
			response.put("error", "輸入參數須為正確數字");
			return ResponseEntity.badRequest().body(response);
		}
		try {
			CreditCard userCard = new CreditCard();
			userCard.setCardNumber(card);
			userCard.setCvc(cardCvc);
			String date = String.format("%04d-%02d", cardYear + 2000, cardMonth);
			userCard.setValidDate(YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM")));
			userCard.setUserId(user);
			userCard.setCancelStatus(0); // 預設0
			userService.saveCard(userCard);
			response.put("success", "新增信用卡成功!");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("error", "輸入參數須為正確數字");
			return ResponseEntity.ok(response);
		}
	}

//	信用卡查詢、更新(刪除)
	@GetMapping("/user/findCard")
	public String findCard(Principal principal, Model model) {
		User user = userService.principleToUser(principal);
		Map<Integer, String> findCard = userService.findCardById(user);
		if (!findCard.isEmpty()) {
			model.addAttribute("findCard", findCard);
		}
		return "/user/findCard";
	}

	@PostMapping("/user/deleteCard")
	public ResponseEntity<Map<String, Object>> updateCard(Principal principal, @RequestParam("card") Integer cardId) {
		Map<String, Object> response = new HashMap<>();
		try {
			userService.principleToUser(principal);
			CreditCard cards = creditCardService.findById(cardId);
			// 抓到卡號的Id
			CreditCard updateCardCancelStatus = userService.updateCardCancelStatus(cards);
			if (updateCardCancelStatus == null || cardId == null) {
				response.put("status", "刪除失敗");
				return ResponseEntity.badRequest().body(response);
			}
			User user = userService.principleToUser(principal);
			Map<Integer, String> findCard = userService.findCardById(user);
			response.put("findCard", findCard);
			response.put("status", "success");
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("status", "刪除失敗");
			return ResponseEntity.badRequest().body(response);
		}
	}

//	要先取得id，此產品中所有的留言
	@GetMapping("/public/product/bulletin/api/{id}")
	@ResponseBody
	public Map<String, Object> getBulletinByProductId(Model model, @PathVariable("id") Integer productId,
	        Principal principal) {
	    Product product = productService.findProductById(productId);
	    if (product == null) {
	        return null;
	    }
	    List<String> usernames = new ArrayList<>();
	    List<String> myUsername = new ArrayList<>();
	    List<Bulletin> bulletin = product.getBulletin();
	    if (principal != null) { // 檢查使用者是否已經登入
	        User user = userService.principleToUser(principal);
	        for (Bulletin bulletin2 : bulletin) {
	            User userId = bulletin2.getUserId();
	            String username = userId.getUsername();
	            String myName = user.getUsername();
	            myUsername.add(myName);
	            usernames.add(username);
	        }
	    } else { // 如果使用者尚未登入，將 myUsername 設為空字串
	        for (Bulletin bulletin2 : bulletin) {
	            User userId = bulletin2.getUserId();
	            String username = userId.getUsername();
	            myUsername.add("");
	            usernames.add(username);
	        }
	    }
	    model.addAttribute("username", usernames);
	    Map<String, Object> data = new HashMap<>();
	    data.put("bulletins", bulletin);
	    data.put("usernames", usernames);
	    data.put("myUsername", myUsername);
	    return data;
	}

//	留言新增儲存
	@PostMapping("/product/bulletin/api")
	@ResponseBody
	public ResponseEntity<?> addBulletin(@ModelAttribute("dtoText") BulletinDTO bulletinDTO, Principal principal) {
	    if (principal == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
	    }
	    User user = userService.principleToUser(principal);
	    if (bulletinDTO.getText().isBlank() || bulletinDTO.getProductId() == null) {
	        return ResponseEntity.badRequest().body("{\"error\": \"輸入欄位不完全\"}");
	    }
	    Product findBulletinByProductId = productService.findProductById(bulletinDTO.getProductId());

	    Bulletin bulletin = new Bulletin(null, bulletinDTO.getText(), null, new Date(), findBulletinByProductId, user);
	    userService.bulletinSave(bulletin);
	    return ResponseEntity.ok().body("{\"message\": \"留言新增成功\"}");
	}


}
