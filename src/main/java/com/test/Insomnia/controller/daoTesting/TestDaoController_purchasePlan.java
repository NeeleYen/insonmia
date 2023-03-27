package com.test.Insomnia.controller.daoTesting;

import java.io.File;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.model.Area;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;

@Controller
public class TestDaoController_purchasePlan {

	@Autowired
	private ProductDao pDao;
//	@Autowired
//	private ItemDao itemDao;
//	@Autowired
//	private PurchasePlanDao pPlanDao;
	@Autowired
	private UserDao userDao;

	@GetMapping("/product/{id}/addPurchasePlan")
	public String addPurchasePlan(@PathVariable Integer id) throws ParseException {
//				Area taiwan = new Area();
		// 輸入專案12購買方案
		PurchasePlan purchasePlan1 = new PurchasePlan();
		PurchasePlan purchasePlan2 = new PurchasePlan();
		Set<PurchasePlan> purchasePlans = new HashSet<PurchasePlan>(0);
		Date shipmentDate1 = new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/20");
		Date shipmentDate2 = new SimpleDateFormat("yyyy/MM/dd").parse("2023/02/17");

		Optional<Product> optional = pDao.findById(id);
		if (optional.isEmpty()) {
			System.out.println("no product");
			return null;
		}
		Product product = optional.get();
		// 設定方案必要欄位
		purchasePlan1.setProduct(product);
		purchasePlan1.setTitle("購買方案1");
		purchasePlan1.setPrice(1000);
//		purchasePlan1.setCurrentOrderAmount(0);
		purchasePlan1.setShipmentDate(shipmentDate1);

		purchasePlan2.setProduct(product);
		purchasePlan2.setTitle("購買方案2");
		purchasePlan2.setPrice(2000);
//		purchasePlan2.setCurrentOrderAmount(0);
		purchasePlan2.setShipmentDate(shipmentDate2);
		// 購買方案連到專案12
		purchasePlans.add(purchasePlan1);
		purchasePlans.add(purchasePlan2);
		product.setPurchasePlan(purchasePlans);

//		Set<Item> pokemons = product.getItem();
//		Item[] arrPokemons = (Item[]) pokemons.toArray();
//		Item pokemon1 = arrPokemons[0];
//		Item pokemon2 = arrPokemons[1];
//		Item pokemon3 = arrPokemons[2];

//				taiwan.setArea("taiwan");
//		purchasePlan1.setAreaNo(1);
////		popokemonGo.setP_id(1);
////			popokemonGo.set(1); // 沒有getter setter無法設定fk欄位值
//		Optional<User> userQuery = userDao.findById(1);
//		if (userQuery.isPresent()) {
//			User user1 = userQuery.get();
//			popokemonGo.setUser(user1);
//			productDao.save(popokemonGo);
//		}
//				areaDao.save(taiwan);
//		productDao.save(popokemonGo);

		Optional<User> userQuery = userDao.findById(1);
		if (userQuery.isPresent()) {
			User user1 = userQuery.get();
			product.setUser(user1);
			pDao.save(product);
		}

		return "redirect:/product/{id}";
	}

//	query all purchasePlan items by for loop
	@GetMapping("/product/PurchasePlan/{id}")
	public String queryPurchasePlan(@PathVariable Integer id) throws ParseException {
		Optional<Product> productOptional = pDao.findById(id);
		Product product01 = productOptional.get();
		Set<PurchasePlan> purchasePlanSet = product01.getPurchasePlan();
//		get each purchasePlan by for loop
		for( PurchasePlan purchasePlan:purchasePlanSet ) {
			System.out.println("=====認繳款項=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			System.out.println(purchasePlan.getPrice());
			System.out.println("=====標題=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			System.out.println(purchasePlan.getTitle());
			System.out.println("=====產品介紹=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			System.out.println(purchasePlan.getIntro());
			Set<PurchasePlanItems> purchasePlanItems = purchasePlan.getPurchasePlanItems();
			for (PurchasePlanItems purchasePlanItem:purchasePlanItems) {
				System.out.println("=====品項數量=====");
				// 拿取數量
				System.out.println(purchasePlan.getPurchasePlanId());
				System.out.println(purchasePlanItem.getAmount());
				System.out.println("=====包含品項=====");
				System.out.println(purchasePlan.getPurchasePlanId());
				// 拿取項目名稱
				PurchasePlanItemsPK purchasePlanItemsPK = purchasePlanItem.getPurchasePlanItemsPK();
				Item itemIdPK = purchasePlanItemsPK.getItemIdPK();
				System.out.println(itemIdPK.getItemName());
			}
//			System.out.println(purchasePlanItems); // 陣列不可取
			System.out.println("=====預計交貨日=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			System.out.println(purchasePlan.getShipmentDate());
			System.out.println("=====運送地區=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			Set<Area> areaNoSet = purchasePlan.getAreaNo();
			for ( Area areaNo:areaNoSet) {
				System.out.println(areaNo.getArea());
			}
//			System.out.println(areaNo); // 陣列不可取
			System.out.println("=====幾名支持者=====");
			System.out.println(purchasePlan.getPurchasePlanId());
//			System.out.println(purchasePlan.getCurrentOrderAmount());
			System.out.println("=====限量: 剩餘=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			System.out.println(purchasePlan.getMaxOrderAmount()-purchasePlan.getMinOrderAmount());
			System.out.println("=====限量: 共幾組=====");
			System.out.println(purchasePlan.getPurchasePlanId());
			System.out.println(purchasePlan.getMaxOrderAmount());
			// 不須顯示的項目
//			System.out.println(purchasePlan.getOrder());
//			System.out.println(purchasePlan.findProductById());
		}
//		System.out.println(purchasePlan.);
		return "home";
	}
	
	public void name() {
//		upload pic to server disk
//		Files.write
//		https://ithelp.ithome.com.tw/articles/10196021 
		
//		(FileOutputStream fos = new FileOutputStream("pathname")) {
//		https://stackoverflow.com/questions/4350084/byte-to-file-in-java try 
		
//		https://www.baeldung.com/spring-file-upload
//		Files file;
//		file.write(null, null, null);
//		File file1;
	}
	
}