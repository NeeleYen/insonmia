package com.test.Insomnia.controller.daoTesting;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.Insomnia.Service.ItemService;
import com.test.Insomnia.Service.OrderService;
import com.test.Insomnia.Service.ProductPictureService;
import com.test.Insomnia.Service.ProductService;
import com.test.Insomnia.dao.ItemDao;
import com.test.Insomnia.dao.OrderDao;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;

@Controller
public class TestControllerForJames {
	@Autowired
	private ProductPictureService productPictureService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ItemService itemService;  
	
	@Autowired
	private OrderService orderService;
	
	@GetMapping("/showPath")
	public void testShowPath() {
		Product product = productService.findProductById(1);
		ProductPicturePK productPicturePK = new ProductPicturePK(product, 3);
		ProductPicture productPicture = productPictureService.getProductPictureById(productPicturePK);
		System.out.println(productPictureService.deleteProductPictureFile(productPicture));
	}
	
	// 用迴圈及Map取出一個product下的所有圖片編號及路徑-測試
	@GetMapping("/ttt")
	public void testGetAllProductPicture() {
		Product product = productService.findProductById(1);
		Set<ProductPicture> productPictureSet = product.getProductPicture();
		Map<Integer, String> picNumAndFilePathMap = new HashMap<>(); 
		for ( ProductPicture productPicture:productPictureSet ) {
			picNumAndFilePathMap.put(productPicture.getPicId().getPicNum(), productPicture.getFilePath()); 
		}
		for ( Integer picNum:picNumAndFilePathMap.keySet() ) {
			System.out.println("picNum: " + picNum + " - " + "picPath: " + picNumAndFilePathMap.get(picNum));
		}
	}
	
	@GetMapping("/bbb")
	public String testPurchasePlanForm() {
		
		return "product/addProductPurchasePlanForm";
	}
	
	@GetMapping("/public/ccc/{planId}")
	public void testSumOrderAmount(@PathVariable("planId") Integer planId) {
//		OrderService orderService = new OrderService();
		Integer sumOfOrderedAmountByPurchasePlan = orderService.getCurrentOrderAmountByPurchasePlanId(planId);
//		Integer sumOfOrderedAmountByPurchasePlan = orderDao.sumOfOrderedAmountByPurchasePlan(planId);
		System.out.println(sumOfOrderedAmountByPurchasePlan);
	}
	
}
