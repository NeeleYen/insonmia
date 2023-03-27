package com.test.Insomnia.controller.daoTesting;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.Insomnia.dao.PurchasePlanItemsDao;
import com.test.Insomnia.dao.UserDao;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;

@Controller
public class TestDaoController_purchasePlanItems {

	@Autowired
	private PurchasePlanItemsDao pPlanItemsDao;
//	@Autowired
//	private PurchasePlanItemsPK pPlanItemsPKDao;
	@Autowired
	private UserDao userDao;
//	@Autowired
//	private ItemDao itemDao; 
	
	
	@GetMapping("/product/{id}/addPurchasePlanItems")
	public String addPurchasePlanItems(@PathVariable Integer id){
		//輸入專案12購買方案1的貨品項目
		
		//抓id=1的會員，此會員的id=12的專案，此專案下id=1的購買方案，此專案的貨品
		User user = new User();
		Product product = new Product();
//		PurchasePlan purPlan = new PurchasePlan();
//		Item item = new Item();
//		Item item2 = new Item();
//		Item item3 = new Item();
//		Optional<User> opUser = userDao.findById(1);
//		Optional<Product> opProduct = pDao.findById(id);
//		Optional<PurchasePlan> opPurchasePlan = pPlanDao.findById(1);
//		if (opUser.isEmpty() || opProduct.isEmpty() || opPurchasePlan.isEmpty()) {
//			System.out.println("no data");
//			return null;
//		}
//		user = opUser.get();
//		product = opProduct.get();
//		purPlan = opPurchasePlan.get();
		Optional<User> opUser = userDao.findById(1);
		if (opUser.isEmpty()) {
			System.out.println("no user");
			return null;
		}
		user = opUser.get();
		Set<Product> products = user.getProduct();
		Product[] arrProduct = products.toArray(new Product[products.size()]);
		product = arrProduct[2];
		Set<PurchasePlan> pPlan = product.getPurchasePlan();
		PurchasePlan[] arrPurchasePlan = pPlan.toArray(new PurchasePlan[pPlan.size()]);
//		purPlan = arrPurchasePlan[0];
		Set<Item> items = product.getItem();
		Item[] arrItem = items.toArray(new Item[items.size()]);
//		item = arrItem[0];	//小火龍
		
		
		//設定新的購買項目 貨品清單
		PurchasePlanItemsPK purPlanItemsPK = new PurchasePlanItemsPK();
		PurchasePlanItems purPlanItems = new PurchasePlanItems();
		purPlanItemsPK.setItemIdPK(arrItem[0]);//小火龍
		purPlanItemsPK.setPurchasePlanIdPK(arrPurchasePlan[0]);//1號方案
		
		purPlanItems.setPurchasePlanItemsPK(purPlanItemsPK);
		purPlanItems.setAmount(35);
		pPlanItemsDao.save(purPlanItems);
		
		return "redirect:/product/{id}";
	}
}
