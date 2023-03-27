package com.test.Insomnia.controller.daoTesting;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.test.Insomnia.dao.AreaDao;
import com.test.Insomnia.dao.BulletinDao;
import com.test.Insomnia.dao.ChatDao;
import com.test.Insomnia.dao.CreditCardDao;
import com.test.Insomnia.dao.FAQDao;
import com.test.Insomnia.dao.ItemDao;
import com.test.Insomnia.dao.OrderDao;
import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.ProductPictureDao;
import com.test.Insomnia.dao.PurchasePlanDao;
import com.test.Insomnia.dao.PurchasePlanItemsDao;
import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.dao.UserPictureDao;
import com.test.Insomnia.dao.UserResetPasswordDao;
import com.test.Insomnia.dao.UserVerificationDao;
import com.test.Insomnia.model.Area;
import com.test.Insomnia.model.Bulletin;
import com.test.Insomnia.model.Chat;
import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.FAQ;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Order;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;
import com.test.Insomnia.model.UserPicture;
import com.test.Insomnia.model.UserPicturePK;
import com.test.Insomnia.model.UserResetPassword;
import com.test.Insomnia.model.UserVerification;

// ------------------ 警告 只能輸入一次，因為user不可重複 ----------------------
@Controller
public class CreateAllTestData {

	@Autowired
	private AreaDao areaDao;
	@Autowired
	private UserDao userDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private ItemDao itemDao;
	@Autowired
	private PurchasePlanDao purchasePlanDao;
	@Autowired
	private CreditCardDao creditCardDao;
	@Autowired
	private UserPictureDao userPictureDao;
	@Autowired
	private ChatDao chatDao;
	@Autowired
	private BulletinDao bulletinDao;
	@Autowired
	private OrderDao orderDao;
	@Autowired
	private ProductPictureDao productPictureDao;
	@Autowired
	private PurchasePlanItemsDao purchasePlanItemsDao;
	@Autowired
	private FAQDao faqDao;
	@Autowired
	private UserVerificationDao userVerificationDao;
	@Autowired
	private UserResetPasswordDao userResetPasswordDao ;
	
	@Autowired
	private ServletContext context;
	
	public CreateAllTestData() {}

//	private AreaDao areaDao;
//	private UserDao userDao;
//	private ProductDao productDao;
//	private ItemDao itemDao;
//	private PurchasePlanDao purchasePlanDao;
//	private CreditCardDao creditCardDao;
//	private UserPictureDao userPictureDao;
//	private ChatDao chatDao;
//	private BulletinDao bulletinDao;
//	private OrderDao orderDao;
//	private ProductPictureDao productPictureDao;
//	private PurchasePlanItemsDao purchasePlanItemsDao;
//	
//
//	@Autowired
//	public CreateAllTestData(AreaDao areaDao, UserDao userDao, ProductDao productDao, ItemDao itemDao,
//			PurchasePlanDao purchasePlanDao, CreditCardDao creditCardDao, UserPictureDao userPictureDao,
//			ChatDao chatDao, BulletinDao bulletinDao, OrderDao orderDao, ProductPictureDao productPictureDao,
//			PurchasePlanItemsDao purchasePlanItemsDao) {
//		super();
//		this.areaDao = areaDao;
//		this.userDao = userDao;
//		this.productDao = productDao;
//		this.itemDao = itemDao;
//		this.purchasePlanDao = purchasePlanDao;
//		this.creditCardDao = creditCardDao;
//		this.userPictureDao = userPictureDao;
//		this.chatDao = chatDao;
//		this.bulletinDao = bulletinDao;
//		this.orderDao = orderDao;
//		this.productPictureDao = productPictureDao;
//		this.purchasePlanItemsDao = purchasePlanItemsDao;
//	}

//	http://localhost:8888/my-insomnia/addAll
	@GetMapping("/addAll")
	public String addAll() {
		
		// add user
		User neele = new User();
		neele.setAddress("高雄市前金區");
		neele.setEmail("neele@github.com");
		neele.setUsername("Neele");
		neele.setPassword(new BCryptPasswordEncoder().encode("neele"));
		neele.setPermissions("member");
		userDao.save(neele);

		User Lisa = new User();
		Lisa.setAddress("高雄市前金區");
		Lisa.setEmail("Lisa@github.com");
		Lisa.setUsername("Lisa");
		Lisa.setPassword(new BCryptPasswordEncoder().encode("lisa"));
		Lisa.setPermissions("member");
		userDao.save(Lisa);

		User Xuan = new User();
		Xuan.setAddress("高雄市前金區");
		Xuan.setEmail("Xuan@github.com");
		Xuan.setUsername("Xuan");
		Xuan.setPassword(new BCryptPasswordEncoder().encode("xuan"));
		Xuan.setPermissions("member");
		userDao.save(Xuan);
		
		User Java = new User();
		Java.setAddress("高雄市前金區");
		Java.setEmail("Java@gmail.com");
		Java.setUsername("Java");
		Java.setPassword(new BCryptPasswordEncoder().encode("aaa"));
		Java.setPermissions("unchecked");
		userDao.save(Java);
		
//		-----------------------設定權限用的驗證信-----------------------------
//		UserVerification userVerificationinsomnia = new UserVerification();
//		userVerificationinsomnia.setUserId(insomnia);
//		userVerificationinsomnia.setVerificationMaturity(new Date());
//		userVerificationinsomnia.setVerificationToken("123");
//		userVerificationDao.save(userVerificationinsomnia);
		
		UserVerification userVerificationJava = new UserVerification();
		userVerificationJava.setUserId(Java);
		userVerificationJava.setVerificationMaturity(new Date());
		userVerificationJava.setVerificationToken("123");
		userVerificationDao.save(userVerificationJava);
		
		UserVerification userVerificationXuan = new UserVerification();
		userVerificationXuan.setUserId(Xuan);
		userVerificationXuan.setVerificationMaturity(new Date());
		userVerificationXuan.setVerificationToken("123");
		userVerificationDao.save(userVerificationXuan);
		
		UserVerification userVerificationLisa = new UserVerification();
		userVerificationLisa.setUserId(Lisa);
		userVerificationLisa.setVerificationMaturity(new Date());
		userVerificationLisa.setVerificationToken("123");
		userVerificationDao.save(userVerificationLisa);
		
		UserVerification userVerificationNeele = new UserVerification();
		userVerificationNeele.setUserId(neele);
		userVerificationNeele.setVerificationMaturity(new Date());
		userVerificationNeele.setVerificationToken("123");
		userVerificationDao.save(userVerificationNeele);
		
//		-----------------------設定修改密碼用的驗證信-----------------------------
//		UserResetPassword userResetPasswordinsomnia = new UserResetPassword();
//		userResetPasswordinsomnia.setUserId(insomnia);
//		userResetPasswordinsomnia.setMaturity(new Date());
//		userResetPasswordinsomnia.setResetPasswordToken("123");
//		userResetPasswordDao.save(userResetPasswordinsomnia);
		
		UserResetPassword userResetPasswordJava = new UserResetPassword();
		userResetPasswordJava.setUserId(Java);
		userResetPasswordJava.setMaturity(new Date());
		userResetPasswordJava.setResetPasswordToken("123");
		userResetPasswordDao.save(userResetPasswordJava);
		
		UserResetPassword userResetPasswordXuan = new UserResetPassword();
		userResetPasswordXuan.setUserId(Xuan);
		userResetPasswordXuan.setMaturity(new Date());
		userResetPasswordXuan.setResetPasswordToken("123");
		userResetPasswordDao.save(userResetPasswordXuan);
		
		UserResetPassword userResetPasswordLisa = new UserResetPassword();
		userResetPasswordLisa.setUserId(Lisa);
		userResetPasswordLisa.setMaturity(new Date());
		userResetPasswordLisa.setResetPasswordToken("123");
		userResetPasswordDao.save(userResetPasswordLisa);
		
		UserResetPassword userResetPasswordNeele = new UserResetPassword();
		userResetPasswordNeele.setUserId(neele);
		userResetPasswordNeele.setMaturity(new Date());
		userResetPasswordNeele.setResetPasswordToken("123");
		userResetPasswordDao.save(userResetPasswordNeele);	
		
		// area移到user後才不會重複新增
		// add area1
		Area europe = new Area();
		europe.setArea("europe");
		areaDao.save(europe);
		// add area1
		Area asia = new Area();
		asia.setArea("asia");
		areaDao.save(asia);

//		----------add信用卡---------------
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

//		---------- add 使用者圖片---------
		String pathStr = context.getRealPath("/images/") + "default.PNG";
		byte[] avatar = null;
		try {
			avatar = Files.readAllBytes(Paths.get(pathStr));
		} catch (IOException e) {
			e.printStackTrace();
		}
		UserPicture neelePicture = new UserPicture();
		neelePicture.setPicture(avatar); // 要放圖片檔案的...
		Optional<User> findById1 = userDao.findById(1); // 連接的user id
		if (findById1.isPresent()) {
			User user1 = findById1.get();
			neelePicture.setUserPicturePk(new UserPicturePK(user1, 1)); // 雙主鍵
			userPictureDao.save(neelePicture);
		}

		UserPicture neelePicture2 = new UserPicture();
		neelePicture2.setPicture(avatar);
		Optional<User> findById11 = userDao.findById(1);
		if (findById11.isPresent()) {
			User user1 = findById11.get();
			neelePicture2.setUserPicturePk(new UserPicturePK(user1, 2));
			userPictureDao.save(neelePicture2);
		}

		UserPicture lisaPicture = new UserPicture();
		lisaPicture.setPicture(avatar);
		Optional<User> findById2 = userDao.findById(2);
		if (findById2.isPresent()) {
			User user1 = findById2.get();
			lisaPicture.setUserPicturePk(new UserPicturePK(user1, 1));
			userPictureDao.save(lisaPicture);
		}

		UserPicture xuanPicture = new UserPicture();
		xuanPicture.setPicture(avatar);
		Optional<User> findById3 = userDao.findById(3);
		if (findById3.isPresent()) {
			User user1 = findById3.get();
			xuanPicture.setUserPicturePk(new UserPicturePK(user1, 2));
			userPictureDao.save(xuanPicture);
		}
		
		
		pathStr = context.getRealPath("/images/") + "logoBrain.jpg";
		avatar = null;
		try {
			avatar = Files.readAllBytes(Paths.get(pathStr));
		} catch (IOException e) {
			e.printStackTrace();
		}

		UserPicture insomniaPicture = new UserPicture();
		insomniaPicture.setPicture(avatar);
		Optional<User> insomniafindById3 = userDao.findById(5);
		if (insomniafindById3.isPresent()) {
			User user1 = insomniafindById3.get();
			insomniaPicture.setUserPicturePk(new UserPicturePK(user1, 1));
			userPictureDao.save(insomniaPicture);
		}

//		----------------add 聊天Chat -------------------
		Chat neeleChat = new Chat();
		neeleChat.setContent("neele測試資料");
		Timestamp datetime = new Timestamp(System.currentTimeMillis());
		neeleChat.setTime(datetime);
		Optional<User> findId1 = userDao.findById(1);
		if (findId1.isPresent()) {
			User user = findId1.get();
			neeleChat.setUserIdSender(user); // 發送者
		}
		Optional<User> findId2 = userDao.findById(2);
		if (findId2.isPresent()) {
			User user = findId2.get();
			neeleChat.setUserIdReceiver(user); // 接收者
			chatDao.save(neeleChat);
		}

		Chat lisaChat = new Chat();
		lisaChat.setContent("lisa測試資料");
		Timestamp datetime2 = new Timestamp(System.currentTimeMillis());
		lisaChat.setTime(datetime2);
		Optional<User> findId3 = userDao.findById(2);
		if (findId3.isPresent()) {
			User user = findId3.get();
			lisaChat.setUserIdSender(user); // 發送者
		}
		Optional<User> findId4 = userDao.findById(1);
		if (findId4.isPresent()) {
			User user = findId4.get();
			lisaChat.setUserIdReceiver(user); // 接收者
			chatDao.save(lisaChat);
		}

		Chat xuanChat = new Chat();
		xuanChat.setContent("xuan測試資料");
		Timestamp datetime3 = new Timestamp(System.currentTimeMillis());
		xuanChat.setTime(datetime3);
		Optional<User> findId5 = userDao.findById(3);
		if (findId5.isPresent()) {
			User user = findId5.get();
			xuanChat.setUserIdSender(user); // 發送者
		}
		Optional<User> findId6 = userDao.findById(1);
		if (findId6.isPresent()) {
			User user = findId6.get();
			xuanChat.setUserIdReceiver(user); // 接收者
			chatDao.save(xuanChat);
		}

//		---------------add product [neele part]------------------

		Product popokemonGo = new Product();
		popokemonGo.setCategory(1);
//		popokemonGo.setFaq("Q:販售捕捉到的神奇寶貝的款項多久會匯到帳戶? A:大約2~3個工作天。");
		popokemonGo.setIntro("「弦外有音」專注於優質的吉他周邊產品，我們提供各種"
				+ "琴布、吉他盒、背帶扣、吉他架等產品，以滿足吉他愛好者的需求。"
				+ "我們相信，音樂不僅僅存在於樂器本身，還存在於各種周邊產品和體驗當中");
		popokemonGo.setTitle("弦外有音");

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			java.util.Date closeDate = dateFormatter.parse("2023-08-01");
			popokemonGo.setCloseDate(closeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Optional<User> userQuery = userDao.findById(1);
		if (userQuery.isPresent()) {
			User user1 = userQuery.get();
			popokemonGo.setUser(user1);
			productDao.save(popokemonGo);
		}
//		List<Item> findAll = itemDao.findAll();
//		List<Item> findAll1 = new ArrayList<Item>(0);
	/////////////////////////////////////////////////////////////////
		Optional<Product> productPokemonQuery = productDao.findById(1);
//		Optional<Product> productPokemonQuery2 = productDao.findById(2);
//		Item pokeBall3 = new Item();
//		Item pokeBall4 = new Item();

		// 若相依物件齊全才開始輸入
		if (productPokemonQuery.isPresent()) {
			Product productPokemon = productPokemonQuery.get();
			Item item1 = new Item();
			Item item2 = new Item();
			Item item3 = new Item();
			item1.setProductId(productPokemon);
			item1.setItemName("吉他保養拋光琴布");
			item1.setPurchasePlanItems(null);
			item2.setProductId(productPokemon);
			item2.setItemName("古典吉他軟盒");
			item2.setPurchasePlanItems(null);
			item3.setProductId(productPokemon);
			item3.setItemName("琴頭背帶扣");
			item3.setPurchasePlanItems(null);

			itemDao.save(item1);
			itemDao.save(item2);
			itemDao.save(item3);
		}
///////////////////////////////////////////////////////////		
//		if (!findAll.isEmpty()) {
//			Set<Item> allAreaSet = new HashSet<>(findAll);
//			popokemonGo.setItem(allAreaSet);
//		}
				
		
		Product music2 = new Product();
		music2.setCategory(1);
//		popokemonGo.setFaq("Q:販售捕捉到的神奇寶貝的款項多久會匯到帳戶? A:大約2~3個工作天。");
		music2.setIntro("鋼琴不僅僅是一個樂器，還是一種生活態度和精神追求。我們相信每個鋼琴愛好者"
				+ "都擁有自己獨特的音樂風格和情感表達方式，因此我們提供多樣化的產品和服務，以滿足"
				+ "不同愛好者的需求。我們希望成為鋼琴愛好者的好夥伴，一起走過音樂之路，分享"
				+ "音樂帶來的樂趣和感動。");
		music2.setTitle("琴韻之聲");		
		
		try {
			java.util.Date closeDateM2 = dateFormatter.parse("2024-08-01");
			music2.setCloseDate(closeDateM2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Optional<User> userQueryM2 = userDao.findById(2);
		if (userQuery.isPresent()) {
			User user1 = userQueryM2.get();
			music2.setUser(user1);
			productDao.save(music2);
		}
//		List<Item> findAllM2 = itemDao.findAll();
//		if (!findAllM2.isEmpty()) {
//			Set<Item> allItemSet = new HashSet<>(findAllM2);
//			music2.setItem(allItemSet);
//		}
		Optional<Product> musicQuery2 = productDao.findById(2);
		if (musicQuery2.isPresent()) {
			Product productPokemon = musicQuery2.get();
			Item item1 = new Item();
			Item item2 = new Item();
			item1.setProductId(productPokemon);
			item1.setItemName("鋼琴鍵盤布");
			item1.setPurchasePlanItems(null);
			item2.setProductId(productPokemon);
			item2.setItemName("鋼琴腳墊");
			item2.setPurchasePlanItems(null);

			itemDao.save(item1);
			itemDao.save(item2);
		}
		
		
		Product music3 = new Product();
		music3.setCategory(1);
		music3.setIntro("「琴弦旋律」專注於小提琴周邊產品。小提琴是一種優雅、細膩和充滿表現力的樂器，"
				+ "它不僅帶給人們美妙的音樂體驗，還有助於培養人的藝術氣質和生活品味。"
				+ "我們提供多種小提琴配件、樂譜、書籍、小提琴保養用品等產品，"
				+ "讓擁有小提琴的人們能夠更好地照顧自己的樂器，並享受音樂。");
		music3.setTitle("琴弦旋律");		
		
		try {
			java.util.Date closeDateM3 = dateFormatter.parse("2024-10-01");
			music3.setCloseDate(closeDateM3);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Optional<User> userQueryM3 = userDao.findById(3);
		if (userQueryM3.isPresent()) {
			User user1 = userQueryM3.get();
			music3.setUser(user1);
			productDao.save(music3);
		}
//		List<Item> findAllM3 = itemDao.findAll();
//		if (!findAllM3.isEmpty()) {
//			Set<Item> allItemSet = new HashSet<>(findAllM3);
//			music3.setItem(allItemSet);
//		}
		Optional<Product> musicQuery3 = productDao.findById(3);
		if (musicQuery3.isPresent()) {
			Product productPokemon = musicQuery3.get();
			Item item1 = new Item();
			Item item2 = new Item();
			item1.setProductId(productPokemon);
			item1.setItemName("琴盒");
			item1.setPurchasePlanItems(null);
			item2.setProductId(productPokemon);
			item2.setItemName("調音器");
			item2.setPurchasePlanItems(null);

			itemDao.save(item1);
			itemDao.save(item2);
		}
		
		Product music4 = new Product();
		music4.setCategory(1);
		music4.setIntro("打擊樂器是一種充滿活力和激情的音樂樂器，能夠讓人們感受到強烈的節奏感和振奮的情緒。"
				+ "「鼓動心弦」專注於提供打擊樂器周邊產品，我們提供多樣化的產品和服務，"
				+ "包括鼓棒、鼓面、鼓垫、樂譜、書籍、鼓手配件等，讓打擊樂器愛好者能夠更好地"
				+ "照顧自己的樂器，並提升演奏水平。");
		music4.setTitle("鼓動心弦");		
		
		try {
			java.util.Date closeDateM4 = dateFormatter.parse("2024-12-01");
			music4.setCloseDate(closeDateM4);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Optional<User> userQueryM4 = userDao.findById(1);
		if (userQueryM4.isPresent()) {
			User user1 = userQueryM4.get();
			music4.setUser(user1);
			productDao.save(music4);
		}
//		List<Item> findAllM4 = itemDao.findAll();
//		if (!findAllM4.isEmpty()) {
//			Set<Item> allItemSet = new HashSet<>(findAllM4);
//			music4.setItem(allItemSet);
//		}
		Optional<Product> musicQuery4 = productDao.findById(4);
		if (musicQuery4.isPresent()) {
			Product productPokemon = musicQuery4.get();
			Item item1 = new Item();
			Item item2 = new Item();
			item1.setProductId(productPokemon);
			item1.setItemName("大鼓鼓錘");
			item1.setPurchasePlanItems(null);
			item2.setProductId(productPokemon);
			item2.setItemName("鼓棒收納夾");
			item2.setPurchasePlanItems(null);

			itemDao.save(item1);
			itemDao.save(item2);
		}
		
		
		
		Product art1 = new Product();
		art1.setCategory(2);
		art1.setIntro("「流轉之藝」專注於探索藝術與時間、空間之關聯。"
				+ "我們融合了不同藝術形式，例如視覺藝術、舞蹈、音樂等，並通過數字技術和互動裝置，"
				+ "將藝術作品與觀眾之間建立起一種新的互動關係，探索藝術的流動性和變幻性。");
		art1.setTitle("流轉之藝");
		SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date closeDateA1 = dateFormatter1.parse("2023-10-01");
			art1.setCloseDate(closeDateA1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Optional<User> query = userDao.findById(2);
		if (query.isPresent()) {
			User user1 = query.get();
			art1.setUser(user1);
			productDao.save(art1);
		}
//		List<Item> findAllA1 = itemDao.findAll();
//		if (!findAllA1.isEmpty()) {
//			Set<Item> allItemSet = new HashSet<>(findAllA1);
//			art1.setItem(allItemSet);
//		}
		Optional<Product> artQuery1 = productDao.findById(5);
		if (artQuery1.isPresent()) {
			Product productPokemon = artQuery1.get();
			Item item1 = new Item();
			Item item2 = new Item();
			item1.setProductId(productPokemon);
			item1.setItemName("油畫");
			item1.setPurchasePlanItems(null);
			item2.setProductId(productPokemon);
			item2.setItemName("畫框");
			item2.setPurchasePlanItems(null);

			itemDao.save(item1);
			itemDao.save(item2);
		}


//		----------------- productPicture 產品圖片------------
		ProductPicture musicProductPicture1_1 = new ProductPicture();
		ProductPicture musicProductPicture1_2 = new ProductPicture();
		ProductPicture musicProductPicture1_3 = new ProductPicture();
		musicProductPicture1_1.setFilePath("/image/music1/pic1.jpg"); 
		musicProductPicture1_2.setFilePath("/image/music1/pic2.jpg"); 
		musicProductPicture1_3.setFilePath("/image/music1/pic3.jpg"); 
		Optional<Product> findByIdProduct = productDao.findById(1);
		if (findByIdProduct.isPresent()) {
			Product product = findByIdProduct.get();
			musicProductPicture1_1.setPicId(new ProductPicturePK(product, 1));
			musicProductPicture1_2.setPicId(new ProductPicturePK(product, 2));
			musicProductPicture1_3.setPicId(new ProductPicturePK(product, 3));
			productPictureDao.save(musicProductPicture1_1);
			productPictureDao.save(musicProductPicture1_2);
			productPictureDao.save(musicProductPicture1_3);
		}
		
		ProductPicture musicProductPicture2_1 = new ProductPicture();
		ProductPicture musicProductPicture2_2 = new ProductPicture();
		ProductPicture musicProductPicture2_3 = new ProductPicture();
		musicProductPicture2_1.setFilePath("/image/music2/pic1.jpg"); // 懶得用圖片暫時用null
		musicProductPicture2_2.setFilePath("/image/music2/pic2.jpg"); // 懶得用圖片暫時用null
		musicProductPicture2_3.setFilePath("/image/music2/pic3.jpg"); // 懶得用圖片暫時用null
		Optional<Product> findByIdProduct2 = productDao.findById(2);
		if (findByIdProduct2.isPresent()) {
			Product product = findByIdProduct2.get();
			musicProductPicture2_1.setPicId(new ProductPicturePK(product, 1));
			musicProductPicture2_2.setPicId(new ProductPicturePK(product, 2));
			musicProductPicture2_3.setPicId(new ProductPicturePK(product, 3));
			productPictureDao.save(musicProductPicture2_1);
			productPictureDao.save(musicProductPicture2_2);
			productPictureDao.save(musicProductPicture2_3);
		}
		
		ProductPicture musicProductPicture3_1 = new ProductPicture();
		ProductPicture musicProductPicture3_2 = new ProductPicture();
		ProductPicture musicProductPicture3_3 = new ProductPicture();
		musicProductPicture3_1.setFilePath("/image/music3/pic1.jpg");
		musicProductPicture3_2.setFilePath("/image/music3/pic2.jpg");
		musicProductPicture3_3.setFilePath("/image/music3/pic3.jpg");
		Optional<Product> findByIdProduct3 = productDao.findById(3);
		if (findByIdProduct3.isPresent()) {
			Product product = findByIdProduct3.get();
			musicProductPicture3_1.setPicId(new ProductPicturePK(product, 1));
			musicProductPicture3_2.setPicId(new ProductPicturePK(product, 2));
			musicProductPicture3_3.setPicId(new ProductPicturePK(product, 3));
			productPictureDao.save(musicProductPicture3_1);
			productPictureDao.save(musicProductPicture3_2);
			productPictureDao.save(musicProductPicture3_3);
		}
		
		ProductPicture musicProductPicture4_1 = new ProductPicture();
		ProductPicture musicProductPicture4_2 = new ProductPicture();
		ProductPicture musicProductPicture4_3 = new ProductPicture();
		musicProductPicture4_1.setFilePath("/image/music4/pic1.jpg");
		musicProductPicture4_2.setFilePath("/image/music4/pic2.jpg");
		musicProductPicture4_3.setFilePath("/image/music4/pic3.jpg");
		Optional<Product> findByIdProduct4 = productDao.findById(4);
		if (findByIdProduct4.isPresent()) {
			Product product = findByIdProduct4.get();
			musicProductPicture4_1.setPicId(new ProductPicturePK(product, 1));
			musicProductPicture4_2.setPicId(new ProductPicturePK(product, 2));
			musicProductPicture4_3.setPicId(new ProductPicturePK(product, 3));
			productPictureDao.save(musicProductPicture4_1);
			productPictureDao.save(musicProductPicture4_2);
			productPictureDao.save(musicProductPicture4_3);
		}
		
		ProductPicture artProductPicture1_1 = new ProductPicture();
		ProductPicture artProductPicture1_2 = new ProductPicture();
		ProductPicture artProductPicture1_3 = new ProductPicture();
		artProductPicture1_1.setFilePath("/image/art1/pic1.jpg");
		artProductPicture1_2.setFilePath("/image/art1/pic2.jpg");
		artProductPicture1_3.setFilePath("/image/art1/pic3.jpg");
		Optional<Product> findByIdProduct5 = productDao.findById(5);
		if (findByIdProduct5.isPresent()) {
			Product product = findByIdProduct5.get();
			artProductPicture1_1.setPicId(new ProductPicturePK(product, 1));
			artProductPicture1_2.setPicId(new ProductPicturePK(product, 2));
			artProductPicture1_3.setPicId(new ProductPicturePK(product, 3));
			productPictureDao.save(artProductPicture1_1);
			productPictureDao.save(artProductPicture1_2);
			productPictureDao.save(artProductPicture1_3);			
		}

//		---------- add 留言板Bulletin------------------
		Bulletin musicBulletin1 = new Bulletin();
		musicBulletin1.setContent("吉他的弦是怎麼安裝的？");
		Timestamp datetimeBulletin1_1 = new Timestamp(System.currentTimeMillis());
		musicBulletin1.setAddedTime(datetimeBulletin1_1);
		Timestamp datetimeBulletin1_2 = new Timestamp(System.currentTimeMillis());
		musicBulletin1.setModifyTime(datetimeBulletin1_2);
		Optional<Product> findByIdBulletinM1P = productDao.findById(1);
		if (findByIdBulletinM1P.isPresent()) {
			Product product = findByIdBulletinM1P.get();
			musicBulletin1.setProduct(product);
		}
		Optional<User> findByIdBulletinM1U = userDao.findById(1);
		if (findByIdBulletinM1U.isPresent()) {
			User user = findByIdBulletinM1U.get();
			musicBulletin1.setUserId(user);
		}
		bulletinDao.save(musicBulletin1);
		
		Bulletin musicBulletin2 = new Bulletin();
		musicBulletin2.setContent("調音鋼琴需要多久？");
		Timestamp datetimeBulletin2_1 = new Timestamp(System.currentTimeMillis());
		musicBulletin2.setAddedTime(datetimeBulletin2_1);
		Timestamp datetimeBulletin2_2 = new Timestamp(System.currentTimeMillis());
		musicBulletin2.setModifyTime(datetimeBulletin2_2);
		Optional<Product> findByIdBulletinM2P = productDao.findById(2);
		if (findByIdBulletinM2P.isPresent()) {
			Product product = findByIdBulletinM2P.get();
			musicBulletin2.setProduct(product);
		}
		Optional<User> findByIdBulletinM2U = userDao.findById(2);
		if (findByIdBulletinM2U.isPresent()) {
			User user = findByIdBulletinM2U.get();
			musicBulletin2.setUserId(user);
		}
		bulletinDao.save(musicBulletin2);
		
		
		Bulletin musicBulletin3 = new Bulletin();
		musicBulletin3.setContent("如何調整小提琴的音準？");
		Timestamp datetimeBulletin3_1 = new Timestamp(System.currentTimeMillis());
		musicBulletin3.setAddedTime(datetimeBulletin3_1);
		Timestamp datetimeBulletin3_2 = new Timestamp(System.currentTimeMillis());
		musicBulletin3.setModifyTime(datetimeBulletin3_2);
		Optional<Product> findByIdBulletinM3P = productDao.findById(3);
		if (findByIdBulletinM3P.isPresent()) {
			Product product = findByIdBulletinM3P.get();
			musicBulletin3.setProduct(product);
		}
		Optional<User> findByIdBulletinM3U = userDao.findById(3);
		if (findByIdBulletinM3U.isPresent()) {
			User user = findByIdBulletinM3U.get();
			musicBulletin3.setUserId(user);
		}
		bulletinDao.save(musicBulletin3);
		
		Bulletin musicBulletin4 = new Bulletin();
		musicBulletin4.setContent("如何調整爵士鼓的聲音？");
		Timestamp datetimeBulletin4_1 = new Timestamp(System.currentTimeMillis());
		musicBulletin4.setAddedTime(datetimeBulletin4_1);
		Timestamp datetimeBulletin4_2 = new Timestamp(System.currentTimeMillis());
		musicBulletin4.setModifyTime(datetimeBulletin4_2);
		Optional<Product> findByIdBulletinM4P = productDao.findById(4);
		if (findByIdBulletinM4P.isPresent()) {
			Product product = findByIdBulletinM4P.get();
			musicBulletin4.setProduct(product);
		}
		Optional<User> findByIdBulletinM4U = userDao.findById(1);
		if (findByIdBulletinM4U.isPresent()) {
			User user = findByIdBulletinM4U.get();
			musicBulletin4.setUserId(user);
		}
		bulletinDao.save(musicBulletin4);

		Bulletin artBulletin1 = new Bulletin();
		artBulletin1.setContent("畫的顏料有毒嗎？");
		Timestamp datetimeBulletin5_1 = new Timestamp(System.currentTimeMillis());
		artBulletin1.setAddedTime(datetimeBulletin5_1);
		Timestamp datetimeBulletin5_2 = new Timestamp(System.currentTimeMillis());
		artBulletin1.setModifyTime(datetimeBulletin5_2);
		Optional<Product> findByIdBulletinA1P = productDao.findById(5);
		if (findByIdBulletinA1P.isPresent()) {
			Product product = findByIdBulletinA1P.get();
			artBulletin1.setProduct(product);
		}
		Optional<User> findByIdBulletinA1U = userDao.findById(2);
		if (findByIdBulletinA1U.isPresent()) {
			User user = findByIdBulletinA1U.get();
			artBulletin1.setUserId(user);
		}
		bulletinDao.save(artBulletin1);


//		-----add item-----
		// 先檢查相依物件是否齊全(not null外鍵欄位)
//		Optional<Product> productPokemonQuery = productDao.findById(1);
//		Optional<Product> productPokemonQuery2 = productDao.findById(2);
//		Item pokeBall3 = new Item();
//		Item pokeBall4 = new Item();
//
//		// 若相依物件齊全才開始輸入
//		if (productPokemonQuery.isPresent()) {
//			Product productPokemon = productPokemonQuery.get();
//			Item pokeBall = new Item();
//			pokeBall.setProductId(productPokemon);
//			pokeBall.setItemName("琴布");
//			pokeBall.setPurchasePlanItems(null);
//
//			itemDao.save(pokeBall);
//		}

//		-----add purchasePlan ！！下面有兩個一樣的-----
//		將目前列表有的area輸入至purchasePlan
		List<Area> allArea = areaDao.findAll();
		Optional<Product> product1Opt = productDao.findById(1);
		if (!allArea.isEmpty() && product1Opt.isPresent()) { // 所有正向條件均符合
			PurchasePlan purchasePlan1 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet = new HashSet<>(allArea);
			purchasePlan1.setAreaNo(allAreaSet);
			// 取出product
			Product product1 = product1Opt.get();
			purchasePlan1.setProduct(product1);
			purchasePlan1.setTitle("吉他保養拋光琴布");
			purchasePlan1.setIntro("吉他保養拋光琴布是一種專門用於吉他保養的工"
					+ "	具，可以有效地去除吉他表面的灰塵和污漬，保持吉他"
					+ "	的外觀和音質。");
			purchasePlan1.setPrice(410);
			purchasePlan1.setMinOrderAmount(10);
			purchasePlan1.setMaxOrderAmount(500);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-10-31");
				purchasePlan1.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan1);
			
			PurchasePlan purchasePlan2 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet2 = new HashSet<>(allArea);
			purchasePlan2.setAreaNo(allAreaSet2);
			// 取出product
//			Product product1 = product1Opt.get();
			purchasePlan2.setProduct(product1);
			purchasePlan2.setTitle("軟盒琴布組合");
			purchasePlan2.setIntro("吉他愛好者"
					+ "	或音樂家必不可少的配件。");
			purchasePlan2.setPrice(5200);
			purchasePlan2.setMinOrderAmount(20);
			purchasePlan2.setMaxOrderAmount(150);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-10-31");
				purchasePlan2.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan2);
			
			PurchasePlan purchasePlan3 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet3 = new HashSet<>(allArea);
			purchasePlan3.setAreaNo(allAreaSet3);
			// 取出product
//			Product product1 = product1Opt.get();
			purchasePlan3.setProduct(product1);
			purchasePlan3.setTitle("古典吉他軟盒");
			purchasePlan3.setIntro("採用高品質的"
					+ "	防震、防水、防潮材料，可保護您的古典吉他免受損壞。");
			purchasePlan3.setPrice(4790);
			purchasePlan3.setMinOrderAmount(10);
			purchasePlan3.setMaxOrderAmount(200);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-10-31");
				purchasePlan3.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan3);
			
			PurchasePlan purchasePlan4 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet4 = new HashSet<>(allArea);
			purchasePlan4.setAreaNo(allAreaSet4);
			// 取出product
//			Product product1 = product1Opt.get();
			purchasePlan4.setProduct(product1);
			purchasePlan4.setTitle("琴頭背帶扣");
			purchasePlan4.setIntro("讓您輕鬆地將吉他背在背上，減輕手臂的負擔，"
					+ "	方便長時間演奏。");
			purchasePlan4.setPrice(499);
			purchasePlan4.setMinOrderAmount(50);
			purchasePlan4.setMaxOrderAmount(500);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-10-31");
				purchasePlan4.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan4);
		}

		Optional<Product> product2Opt = productDao.findById(2);
		if (!allArea.isEmpty() && product2Opt.isPresent()) {
			PurchasePlan purchasePlan1 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet = new HashSet<>(allArea);
			purchasePlan1.setAreaNo(allAreaSet);
			// 取出product
			Product product1 = product2Opt.get();
			purchasePlan1.setProduct(product1);
			purchasePlan1.setTitle("絨布鋼琴鍵盤布");
			purchasePlan1.setIntro("採用高品質絨布製成，柔軟舒適，"
					+ "	能夠保護鋼琴鍵盤不受灰塵和污垢污染，同時也能增加演奏時的手感舒適度。");
			purchasePlan1.setPrice(200);
			purchasePlan1.setMinOrderAmount(10);
			purchasePlan1.setMaxOrderAmount(500);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-12-31");
				purchasePlan1.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan1);
			
			PurchasePlan purchasePlan2 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet2 = new HashSet<>(allArea);
			purchasePlan2.setAreaNo(allAreaSet2);
			// 取出product
//			Product product1 = product1Opt.get();
			purchasePlan2.setProduct(product1);
			purchasePlan2.setTitle("鋼琴腳墊 4個/組");
			purchasePlan2.setIntro("減少鋼琴腳和地板之間的摩擦，防止刮傷"
					+ "	和損壞，同時還可以提高鋼琴的穩定性和音質，是鋼琴愛好者必備的配件之一。");
			purchasePlan2.setPrice(180);
			purchasePlan2.setMinOrderAmount(5);
			purchasePlan2.setMaxOrderAmount(100);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-12-31");
				purchasePlan2.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan2);
		}
		
		List<Area> allArea2 = areaDao.findAll();
		Optional<Product> product1Opt2 = productDao.findById(5);
		if (!allArea2.isEmpty() && product1Opt2.isPresent()) { // 所有正向條件均符合
			PurchasePlan purchasePlan1 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet = new HashSet<>(allArea2);
			purchasePlan1.setAreaNo(allAreaSet);
			// 取出product
			Product product1 = product1Opt2.get();
			purchasePlan1.setProduct(product1);
			purchasePlan1.setTitle("流水落花");
			purchasePlan1.setIntro("絢麗多彩的景色、短暫美好的時光，如同奔騰的水流一樣，瞬間即逝。");
			purchasePlan1.setPrice(50000);
			purchasePlan1.setMinOrderAmount(1);
			purchasePlan1.setMaxOrderAmount(10);
//			purchasePlan1.setCurrentOrderAmount(80);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				java.util.Date shipmentDate = dateFormatter.parse("2024-01-01");
				purchasePlan1.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan1);
		}
		
//		--------------- 購買方案 貨品項目purchasePlanItems ------------
//		PurchasePlanItems purchasePlanItems = new PurchasePlanItems();
//		purchasePlanItems.setAmount(100);
//
//		Optional<PurchasePlan> findByIdPurchasePlan = purchasePlanDao.findById(1);
//		Optional<Item> findByIdItem = itemDao.findById(1);
//
//			Item pokeBall2 = new Item();
//			pokeBall2.setProductId(productPokemon);
//			pokeBall2.setItemName("神奇寶貝球-朱");
////			pokeBall.setPurchasePlanItems(null)
//			
//			itemDao.save(pokeBall);
//			itemDao.save(pokeBall2);
//			
//		}
		
//		if (productPokemonQuery2.isPresent()) {
//			Product productPokemon2 = productPokemonQuery2.get();
//			
//			pokeBall3.setProductId(productPokemon2);
//			pokeBall3.setItemName("神奇寶貝球-紫2");
//			pokeBall3.setProductId(productPokemon2);
////			pokeBall.setPurchasePlanItems(null)
//			
//			pokeBall4.setProductId(productPokemon2);
//			pokeBall4.setItemName("神奇寶貝球-日2");
//			pokeBall4.setProductId(productPokemon2);
////			pokeBall.setPurchasePlanItems(null)
//			itemDao.save(pokeBall3);
//			itemDao.save(pokeBall4);
//		}

//		-----add purchasePlan-----
//		將目前列表有的area輸入至purchasePlan
//		List<Area> allArea = areaDao.findAll();
//		Optional<Product> product1Opt = productDao.findById(1);
//		if (!allArea.isEmpty() && product1Opt.isPresent()) { // 所有正向條件均符合
//			PurchasePlan purchasePlan1 = new PurchasePlan();
//			// 轉換list至set
//			Set<Area> allAreaSet = new HashSet<>(allArea);
//			purchasePlan1.setAreaNo(allAreaSet);
//			// 取出product
//			Product product1 = product1Opt.get();
//			purchasePlan1.setProduct(product1);
//			purchasePlan1.setTitle("中級訓練家方案");
//			purchasePlan1.setIntro("內含大師球3顆，適合成為訓練師一年的玩家。");
//			purchasePlan1.setPrice(5500);
//			purchasePlan1.setMinOrderAmount(51);
//			purchasePlan1.setMaxOrderAmount(501);
//			purchasePlan1.setCurrentOrderAmount(52);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
//			try {
//				Date shipmentDate = dateFormatter.parse("2023-10-01");
//				purchasePlan1.setShipmentDate(shipmentDate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			purchasePlanDao.save(purchasePlan1);
//		}
//
//		List<Area> allArea2 = areaDao.findAll();
//		Optional<Product> product1Opt2 = productDao.findById(2);
//		if (!allArea2.isEmpty() && product1Opt2.isPresent()) { // 所有正向條件均符合
//			PurchasePlan purchasePlan1 = new PurchasePlan();
//			// 轉換list至set
//			Set<Area> allAreaSet = new HashSet<>(allArea2);
//			purchasePlan1.setAreaNo(allAreaSet);
//			// 取出product
//			Product product1 = product1Opt2.get();
//			purchasePlan1.setProduct(product1);
//			purchasePlan1.setTitle("妙花種子方案");
//			purchasePlan1.setIntro("內含一隻妙花種子，適合所有玩家，老少咸宜。");
//			purchasePlan1.setPrice(50000);
//			purchasePlan1.setMinOrderAmount(10);
//			purchasePlan1.setMaxOrderAmount(100);
//			purchasePlan1.setCurrentOrderAmount(79);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
//			try {
//				java.util.Date shipmentDate = dateFormatter.parse("2024-01-01");
//				purchasePlan1.setShipmentDate(shipmentDate);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			purchasePlanDao.save(purchasePlan1);
//		}
//		
//		--------------- 購買方案 貨品項目purchasePlanItems ------------
		PurchasePlanItems purchasePlanItems = new PurchasePlanItems();
		purchasePlanItems.setAmount(1);

		Optional<PurchasePlan> findByIdPurchasePlan = purchasePlanDao.findById(1);
		Optional<Item> findByIdItem = itemDao.findById(1);

		if (findByIdPurchasePlan.isPresent() && findByIdItem.isPresent()) {
			PurchasePlan purchasePlan = findByIdPurchasePlan.get();
			Item item = findByIdItem.get();
			purchasePlanItems.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan));
		}
		purchasePlanItemsDao.save(purchasePlanItems);
		
		
		
		PurchasePlanItems purchasePlanItems2_1 = new PurchasePlanItems();
		PurchasePlanItems purchasePlanItems2_2 = new PurchasePlanItems();
		purchasePlanItems2_1.setAmount(1);
		purchasePlanItems2_2.setAmount(1);
		
		Optional<PurchasePlan> findByIdPurchasePlan2 = purchasePlanDao.findById(2);
		Optional<Item> findByIdItem2_1 = itemDao.findById(1);
		Optional<Item> findByIdItem2_2 = itemDao.findById(2);
		
		if (findByIdPurchasePlan2.isPresent() && findByIdItem2_1.isPresent() && findByIdItem2_2.isPresent()) {
			PurchasePlan purchasePlan = findByIdPurchasePlan2.get();
			Item item2_1 = findByIdItem2_1.get();
			Item item2_2 = findByIdItem2_2.get();
			purchasePlanItems2_1.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item2_1, purchasePlan));
			purchasePlanItems2_2.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item2_2, purchasePlan));
		}
		purchasePlanItemsDao.save(purchasePlanItems2_1);
		purchasePlanItemsDao.save(purchasePlanItems2_2);
	
		
		PurchasePlanItems purchasePlanItems3 = new PurchasePlanItems();
		purchasePlanItems3.setAmount(1);
		
		Optional<PurchasePlan> findByIdPurchasePlan3 = purchasePlanDao.findById(3);
		Optional<Item> findByIdItem3 = itemDao.findById(2);
		if (findByIdPurchasePlan3.isPresent() && findByIdItem3.isPresent()) {
			PurchasePlan purchasePlan2 = findByIdPurchasePlan3.get();
			Item item = findByIdItem3.get();
			purchasePlanItems3.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan2));
		}
		purchasePlanItemsDao.save(purchasePlanItems3);
		
		
		PurchasePlanItems purchasePlanItems4 = new PurchasePlanItems();
		purchasePlanItems4.setAmount(1);
		
		Optional<PurchasePlan> findByIdPurchasePlan4 = purchasePlanDao.findById(4);
		Optional<Item> findByIdItem4 = itemDao.findById(3);
		if (findByIdPurchasePlan4.isPresent() && findByIdItem4.isPresent()) {
			PurchasePlan purchasePlan2 = findByIdPurchasePlan4.get();
			Item item = findByIdItem4.get();
			purchasePlanItems4.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan2));
		}
		purchasePlanItemsDao.save(purchasePlanItems4);
		
		
		PurchasePlanItems purchasePlanItems5 = new PurchasePlanItems();
		purchasePlanItems5.setAmount(1);
		
		Optional<PurchasePlan> findByIdPurchasePlan5 = purchasePlanDao.findById(5);
		Optional<Item> findByIdItem5 = itemDao.findById(4);
		if (findByIdPurchasePlan5.isPresent() && findByIdItem5.isPresent()) {
			PurchasePlan purchasePlan2 = findByIdPurchasePlan5.get();
			Item item = findByIdItem5.get();
			purchasePlanItems5.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan2));
		}
		purchasePlanItemsDao.save(purchasePlanItems5);
		
		
		PurchasePlanItems purchasePlanItems6 = new PurchasePlanItems();
		purchasePlanItems6.setAmount(4);
		
		Optional<PurchasePlan> findByIdPurchasePlan6 = purchasePlanDao.findById(6);
		Optional<Item> findByIdItem6 = itemDao.findById(5);
		if (findByIdPurchasePlan6.isPresent() && findByIdItem6.isPresent()) {
			PurchasePlan purchasePlan2 = findByIdPurchasePlan6.get();
			Item item = findByIdItem6.get();
			purchasePlanItems6.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan2));
		}
		purchasePlanItemsDao.save(purchasePlanItems6);
		
		PurchasePlanItems purchasePlanItems7 = new PurchasePlanItems();
		purchasePlanItems7.setAmount(1);
		
		Optional<PurchasePlan> findByIdPurchasePlan7 = purchasePlanDao.findById(7);
		Optional<Item> findByIdItem7 = itemDao.findById(10);
		if (findByIdPurchasePlan7.isPresent() && findByIdItem7.isPresent()) {
			PurchasePlan purchasePlan2 = findByIdPurchasePlan7.get();
			Item item = findByIdItem7.get();
			purchasePlanItems7.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan2));
		}
		purchasePlanItemsDao.save(purchasePlanItems7);
		


//		---------------- 訂單order -----------------
		Order neeleOrder = new Order();
		neeleOrder.setAmount(1);
		neeleOrder.setCancelStatus(0);
		neeleOrder.setDetailAddr(null); // 要設定成不能null嗎?
		neeleOrder.setNote(null);

		Date date = new Date(); // 當前時間
		neeleOrder.setOrderDate(date);
		neeleOrder.setPaymentStatus(0);
		neeleOrder.setPrice(50000); // 可能要用購買方案去連結
		neeleOrder.setShippingstatus(0);

		Optional<Area> findByIdArea = areaDao.findById(1);
		if (findByIdArea.isPresent()) {
			Area area = findByIdArea.get();
			neeleOrder.setAreaId(area);
		}
		Optional<CreditCard> findByIdCreditCard = creditCardDao.findById(1);
		if (findByIdCreditCard.isPresent()) {
			neeleOrder.setCreditCard(findByIdCreditCard.get());
		}
		Optional<PurchasePlan> findByIdPurchasePlan1 = purchasePlanDao.findById(2);
		if (findByIdPurchasePlan1.isPresent()) {
			neeleOrder.setPurchasePlan(findByIdPurchasePlan1.get());
		}
		Optional<User> findByIdUser = userDao.findById(1);
		if (findByIdUser.isPresent()) {
			neeleOrder.setUserId(findByIdUser.get());
		}
		orderDao.save(neeleOrder);

//		---------------- FAQ -----------------
//		在product popokemonGo下新增兩筆留言
		FAQ faq1_1 = new FAQ();
		faq1_1.setProductId(popokemonGo);
		faq1_1.setQuestion("Q：如何保養吉他？");
		faq1_1.setAnswer("A：保養吉他需要注意定期更換弦、清潔吉他外殼、調整吉他頸部等。可以參考專業人"
				+ "員提供的保養指南，或自行學習吉他保養的相關知識。");
		faqDao.save(faq1_1);
		FAQ faq1_2 = new FAQ();
		faq1_2.setProductId(popokemonGo);
		faq1_2.setQuestion("Q：吉他的弦是怎麼安裝的？");
		faq1_2.setAnswer("A：首先，從底部將弦穿過機頭上的弦軸，然後在緊張調整螺絲上拉緊弦子。最後剪斷多餘的弦子。");
		faqDao.save(faq1_2);
		
//		在product popokemonGo1下新增兩筆留言
		FAQ faq5_1 = new FAQ();
		faq5_1.setProductId(art1);
		faq5_1.setQuestion("Q:如何保養油畫?");
		faq5_1.setAnswer("油畫應該放置在乾燥、避光、通風的地方，不要放在潮濕或有陽光直射的地方，以免畫布發黃、斑駁或色彩褪去。");
		faqDao.save(faq5_1);
		FAQ faq5_2 = new FAQ();
		faq5_2.setProductId(art1);
		faq5_2.setQuestion("Q:油畫如何清潔?");
		faq5_2.setAnswer("A:可以用乾淨、柔軟的刷子輕輕刷去灰塵和污垢，不要使用濕布或擦拭劑。");
		faqDao.save(faq5_2);
		return "redirect:/";

	}

}
