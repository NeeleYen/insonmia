package com.test.Insomnia.controller.daoTesting;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

// ------------------ 警告 只能輸入一次，因為user不可重複 ----------------------
@Controller
public class AllTestDataByLisa {

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
	
	public AllTestDataByLisa() {}

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
	@GetMapping("/addAll/Lisa")
	public String addAll() {
		
		// add area1
		Area europe = new Area();
		europe.setArea("europe");
		areaDao.save(europe);
		// add area1
		Area asia = new Area();
		asia.setArea("asia");
		areaDao.save(asia);

		// add user
		User neele = new User();
		neele.setAddress("高雄市前金區");
		neele.setEmail("neele@github.com");
		neele.setUsername("Neele");
		neele.setPassword(new BCryptPasswordEncoder().encode("neele"));
		userDao.save(neele);

		User Lisa = new User();
		Lisa.setAddress("高雄市前金區");
		Lisa.setEmail("Lisa@github.com");
		Lisa.setUsername("Lisa");
		Lisa.setPassword(new BCryptPasswordEncoder().encode("lisa"));
		userDao.save(Lisa);

		User Xuan = new User();
		Xuan.setAddress("高雄市前金區");
		Xuan.setEmail("Xuan@github.com");
		Xuan.setUsername("Xuan");
		Xuan.setPassword(new BCryptPasswordEncoder().encode("xuan"));
		userDao.save(Xuan);
		
		User Java = new User();
		Java.setAddress("高雄市前金區");
		Java.setEmail("Java@gmail.com");
		Java.setUsername("Java");
		Java.setPassword(new BCryptPasswordEncoder().encode("aaa"));
		userDao.save(Java);


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
		popokemonGo.setIntro("這是一款神奇寶貝的AR遊戲，快來一起捕捉神奇寶貝吧。");
		popokemonGo.setTitle("popokemonGo手遊");

		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

		try {
			java.util.Date closeDate = dateFormatter.parse("2023-08-01");
			popokemonGo.setCloseDate(closeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<User> userQuery = userDao.findById(1);
		if (userQuery.isPresent()) {
			User user1 = userQuery.get();
			popokemonGo.setUser(user1);
			productDao.save(popokemonGo);
		}
		List<Item> findAll = itemDao.findAll();
		if (!findAll.isEmpty()) {
			Set<Item> allAreaSet = new HashSet<>(findAll);
			popokemonGo.setItem(allAreaSet);
		}

		Product popokemonGo1 = new Product();
		popokemonGo1.setCategory(2);
//		popokemonGo1.setFaq("Q:猜猜我是誰? A:小火龍。");
		popokemonGo1.setIntro("就決定是你了小火龍。");
		popokemonGo1.setTitle("小火龍");
		SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date closeDate = dateFormatter1.parse("2023-10-01");
			popokemonGo1.setCloseDate(closeDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Optional<User> query = userDao.findById(2);
		if (userQuery.isPresent()) {
			User user1 = query.get();
			popokemonGo1.setUser(user1);
			productDao.save(popokemonGo1);
		}


//		----------------- productPicture 產品圖片------------
		ProductPicture neeleProductPicture = new ProductPicture();
		neeleProductPicture.setFilePath("/image/prod2pic1.jpg"); // 懶得用圖片暫時用null
		Optional<Product> findByIdProduct = productDao.findById(2);
		if (findByIdProduct.isPresent()) {
			Product product = findByIdProduct.get();
			neeleProductPicture.setPicId(new ProductPicturePK(product, 1));
			productPictureDao.save(neeleProductPicture);
		}
		ProductPicture neeleProductPicture2 = new ProductPicture();
		neeleProductPicture2.setFilePath("/image/testImage.jpg"); // 懶得用圖片暫時用null
		Optional<Product> findByIdProduct2 = productDao.findById(1);
		if (findByIdProduct2.isPresent()) {
			Product product = findByIdProduct2.get();
			neeleProductPicture2.setPicId(new ProductPicturePK(product, 2));
			productPictureDao.save(neeleProductPicture2);
		}
		ProductPicture neeleProductPicture3 = new ProductPicture();
		neeleProductPicture3.setFilePath(null); // 懶得用圖片暫時用null
		Optional<Product> findByIdProduct3 = productDao.findById(1);

//		---------- add 留言板Bulletin------------------
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


//		-----add item-----
		// 先檢查相依物件是否齊全(not null外鍵欄位)
		Optional<Product> productPokemonQuery = productDao.findById(1);
		Optional<Product> productPokemonQuery2 = productDao.findById(2);
		Item pokeBall3 = new Item();
		Item pokeBall4 = new Item();

		// 若相依物件齊全才開始輸入
		if (productPokemonQuery.isPresent()) {
			Product productPokemon = productPokemonQuery.get();
			Item pokeBall = new Item();
			pokeBall.setProductId(productPokemon);
			pokeBall.setItemName("神奇寶貝球-一般");
			pokeBall.setPurchasePlanItems(null);

			itemDao.save(pokeBall);
		}

//		-----add purchasePlan-----
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
			purchasePlan1.setTitle("普通訓練家方案");
			purchasePlan1.setIntro("內含普通寶貝球5顆，適合剛成為訓練師的玩家。");
			purchasePlan1.setPrice(3000);
			purchasePlan1.setMinOrderAmount(50);
			purchasePlan1.setMaxOrderAmount(500);
//			purchasePlan1.setCurrentOrderAmount(63);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				Date shipmentDate = dateFormatter.parse("2023-10-01");
				purchasePlan1.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			purchasePlanDao.save(purchasePlan1);
		}

		List<Area> allArea2 = areaDao.findAll();
		Optional<Product> product1Opt2 = productDao.findById(2);
		if (!allArea2.isEmpty() && product1Opt2.isPresent()) { // 所有正向條件均符合
			PurchasePlan purchasePlan1 = new PurchasePlan();
			// 轉換list至set
			Set<Area> allAreaSet = new HashSet<>(allArea2);
			purchasePlan1.setAreaNo(allAreaSet);
			// 取出product
			Product product1 = product1Opt2.get();
			purchasePlan1.setProduct(product1);
			purchasePlan1.setTitle("小火龍方案");
			purchasePlan1.setIntro("內含一隻小火龍，適合所有玩家，老少咸宜。");
			purchasePlan1.setPrice(50000);
			purchasePlan1.setMinOrderAmount(10);
			purchasePlan1.setMaxOrderAmount(100);
//			purchasePlan1.setCurrentOrderAmount(80);
//			SimpleDateFormat dateFormatter = new SimpleDateFormat ("yyyy-MM-dd"); 
			try {
				java.util.Date shipmentDate = dateFormatter.parse("2024-01-01");
				purchasePlan1.setShipmentDate(shipmentDate);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
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
//				// TODO Auto-generated catch block
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
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			purchasePlanDao.save(purchasePlan1);
//		}
//		
//		--------------- 購買方案 貨品項目purchasePlanItems ------------
		PurchasePlanItems purchasePlanItems = new PurchasePlanItems();
		purchasePlanItems.setAmount(11);

		Optional<PurchasePlan> findByIdPurchasePlan = purchasePlanDao.findById(1);
		Optional<Item> findByIdItem = itemDao.findById(1);

		if (findByIdPurchasePlan.isPresent() && findByIdItem.isPresent()) {
			PurchasePlan purchasePlan = findByIdPurchasePlan.get();
			Item item = findByIdItem.get();
			purchasePlanItems.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan));
		}
		purchasePlanItemsDao.save(purchasePlanItems);
	
		
		PurchasePlanItems purchasePlanItems2 = new PurchasePlanItems();
		purchasePlanItems2.setAmount(21);
		
		Optional<PurchasePlan> findByIdPurchasePlan2 = purchasePlanDao.findById(2);
		Optional<Item> findByIdItem2 = itemDao.findById(1);
		if (findByIdPurchasePlan2.isPresent() && findByIdItem2.isPresent()) {
			PurchasePlan purchasePlan2 = findByIdPurchasePlan2.get();
			Item item = findByIdItem2.get();
			purchasePlanItems2.setPurchasePlanItemsPK(new PurchasePlanItemsPK(item, purchasePlan2));
		}
		purchasePlanItemsDao.save(purchasePlanItems2);


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

//		---------------- 訂單order -----------------
//		在product popokemonGo下新增兩筆留言
		FAQ faq1 = new FAQ();
		faq1.setProductId(popokemonGo);
		faq1.setQuestion("Q:猜猜我是誰?");
		faq1.setAnswer("A:小火龍。");
		faqDao.save(faq1);
		FAQ faq2 = new FAQ();
		faq2.setProductId(popokemonGo);
		faq2.setQuestion("Q:販售捕捉到的神奇寶貝的款項多久會匯到帳戶?");
		faq2.setAnswer("A:大約2~3個工作天。");
		faqDao.save(faq2);
		
//		在product popokemonGo1下新增兩筆留言
		FAQ faq3 = new FAQ();
		faq3.setProductId(popokemonGo1);
		faq3.setQuestion("Q:如何養育傑尼龜?");
		faq3.setAnswer("A:請放置於日曬充足處，並給予水分及遮蔭處。");
		faqDao.save(faq3);
		FAQ faq4 = new FAQ();
		faq4.setProductId(popokemonGo1);
		faq4.setQuestion("Q:傑尼龜多久會進化?");
		faq4.setAnswer("A:若養育得當，大約1年會進化為水箭龜。");
		faqDao.save(faq4);
		return "redirect:/";

	}

}
