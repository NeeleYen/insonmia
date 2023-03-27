package com.test.Insomnia.controller;

import java.security.Principal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.Insomnia.Service.ProductPictureService;
import com.test.Insomnia.Service.ProductService;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.User;

@Controller
public class HomeController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductPictureService pPictureService; 

	@GetMapping("/public/oldHome")
	public String home() {
		return "home";
	}
	
	@GetMapping("/")
	public String oldHome(Model model) {
		
//		!!!建議重寫(此段僅加注釋提供修改建議，無更動程式碼)
		// 專案一個
		List<ProductPicture> allPicOneOfWebsiteList = pPictureService.findAllPicOneOfWebsite();
		// !!!沒有圖片就偵測不到專案
		// 若回傳之coverImage陣列不為空(好像不應該這樣判斷，應先找出專案再考慮圖片問題，而不是以圖片去找專案。會發生沒圖片就偵測不到專案的現象。)
		// 建議改用: 找出專案，判斷有無coverImage，若無則放預設圖至該專案
		if (allPicOneOfWebsiteList != null && allPicOneOfWebsiteList.size() >= 1) {
			// 無考慮若無任何專案時要顯示什麼，或可直接return減少伺服器負載(popOfWebsite == null)
			// 此段建議放在if外面，因最熱門方案與有無封面圖無關(但建議改為先找出專案再考慮圖片問題)
		    Product popOfWebsite = productService.mostPopOfWebsite();
		    Integer pId = popOfWebsite.getPId();
		 // 隨機最熱門專案的coverImage(會抓預設圖)
		    ProductPicture thumbNail = pPictureService.findThumbNail(pId); 

//		    輪播圖列表
		    ArrayList<ProductPicture> picOneForSlideList = new ArrayList<>(5);
		    // 用Random()輸入至set是無用的，因為set不具順序，輸入後random完全失效，此處應改用Array<Integer> number宣告
		    Set<Integer> number = new HashSet<>();

		    for ( int i = 0; i < allPicOneOfWebsiteList.size() && i < 5; i++) {
		    	Integer randomIndex = new Random().nextInt(allPicOneOfWebsiteList.size());
		    	number.add(randomIndex);
		    }
		    
//		    while (number.size() <= 2 && allPicOneOfWebsiteList.size() >= 2) {
//		    	System.out.println(number.size() + ":" + allPicOneOfWebsiteList.size());
//		        Integer randomIndex = new Random().nextInt(allPicOneOfWebsiteList.size());
//		        number.add(randomIndex);
//		    }
		    // 改用Array宣告後，此處可併入上方for迴圈
		    for (Integer index : number) {
//		        ProductPicture productPicture = allPicOneOfWebsiteList.get(index);
		        ProductPicture productPicture = pPictureService.findThumbNail(allPicOneOfWebsiteList.get(index).getPicId().getProductId().getPId());
		        picOneForSlideList.add(productPicture);
		    }

		    System.out.println("數字集合："+picOneForSlideList.size());

		    model.addAttribute("thumbNail", thumbNail);
		    model.addAttribute("picOneForSlideList", picOneForSlideList);
		} else { // 如果大家的product都是使用預設圖(僅修改此段，上方亦可修改if判斷、popOfWebsite位置後直接改用此段)
			// 隨機抓5個專案顯示在輪播圖
			Product popOfWebsite = productService.mostPopOfWebsite();
			if (popOfWebsite == null) {
				return "homePage";
			}
		    Integer pId = popOfWebsite.getPId();
		 // 隨機最熱門專案的coverImage(輪播圖第一張)(會抓預設圖)
		    ProductPicture thumbNail = pPictureService.findThumbNail(pId); 
		    model.addAttribute("thumbNail", thumbNail);
		    // 可自行指定要隨機取幾張
		    List<Product> find5ActiveProductRandomlyList = productService.findActiveProductRandomly();
		    ArrayList<ProductPicture> picOneForSlideList = new ArrayList<>(5);
		    model.addAttribute("picOneForSlideList", picOneForSlideList);
		 // 若無任何專案
		    if ( find5ActiveProductRandomlyList == null || find5ActiveProductRandomlyList.isEmpty() ) {
		    	return "homePage";
		    }
		    for (Product oneProduct : find5ActiveProductRandomlyList) {
		    	if ( oneProduct.getPId() == pId ) { // 不重複抓同專案
		    		continue;
		    	}
		        ProductPicture productPicture = pPictureService.findThumbNail(oneProduct.getPId());
		        picOneForSlideList.add(productPicture);
		    }
		    
		}

		return "homePage";
	}

	@GetMapping("/public/product")
	public String product() {
		return "/product/productPage";
	}
	
	//跳頁到會員頁
	@GetMapping("/public/user/profile")
	public String profile(Principal principal, Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "/user/userPage";
	}
	
	//跳頁到測試類型頁
//	@GetMapping("/public/category/{category}")
//	public String category(@PathVariable Integer category) {
//		Set<Entry<Integer, String>> entrySet = Product.CATEGORY_OPTIONS.entrySet();
//		return "/layout/category";
//	}
	
	
	
	
	//跳頁到類型頁
	@GetMapping("/public/category/{category}")
	public String music(@PathVariable Integer category, Model model) {			
		
		Product popProductByCate = productService.findPopProductByCate(category);	//最多人支持，不確定幾筆
		Set<Product> threeNewest = productService.findThreeNewest(category);	//最新，三筆
		
		if (popProductByCate==null) {
			return "/layout/noProduct";
		}
		
		ProductPicture popthumbNailSet = new ProductPicture();
		Map<ProductPicture, String> newThumbNailAndStatus = new HashMap<ProductPicture, String>(0);	
//		Map<String, String> forNewestMap = new HashMap<String, String>(0);
		Map<ProductPicture, String> forNewestMap2 = new HashMap<ProductPicture, String>(0);
		
		if (newThumbNailAndStatus==null || forNewestMap2==null) {
			return "/layout/noProduct";
		}
		
		String categoryValue = Product.CATEGORY_OPTIONS.get(category);
		
		//抓封面圖，類型頁只抓一張封面
		Integer pId = popProductByCate.getPId();
		popthumbNailSet = pPictureService.findThumbNail(pId);

		for (Product product : threeNewest) {
			Integer pIdNew = product.getPId();
			//抓封面
			ProductPicture thumbNail = pPictureService.findThumbNail(pIdNew);
			//抓募資狀態百分比
			Integer fundationGoal = productService.fundationGoal(pIdNew);			
			Integer fundationStatus = productService.fundationStatus(pIdNew);
			
			if (fundationStatus == 0) {
				newThumbNailAndStatus.put(thumbNail, "已籌集 0 %");
			} else if (fundationGoal == 0) {
				newThumbNailAndStatus.put(thumbNail, "無設定目標");
			} else {

				String fundationPercent = productService.fundationPercent(fundationStatus, fundationGoal);
				newThumbNailAndStatus.put(thumbNail, "已籌集 "+fundationPercent+" %");			
			}
		}
		
		Set<Entry<ProductPicture, String>> entrySetNew = newThumbNailAndStatus.entrySet();
		for (Entry<ProductPicture, String> entry : entrySetNew) {
			ProductPicture key = entry.getKey();
//			String title = key.getPicId().getProductId().getTitle();
			String value = entry.getValue();
//			System.out.println("結果："+title+value);
//			forNewestMap.put(title, value);
			forNewestMap2.put(key, value);
		}
		
		model.addAttribute("category", category);
		model.addAttribute("categoryValue", categoryValue);
		model.addAttribute("popthumbNailSet", popthumbNailSet);
		model.addAttribute("newthumbNailSet", entrySetNew);
//		model.addAttribute("forNewestMap", forNewestMap);
		model.addAttribute("forNewestMap2", forNewestMap2);				
		
		return "/layout/category";
	}
	
	//類型頁跳頁到類型下清單
	@GetMapping("/public/category/{category}/list")
	public String allProductInCate(@PathVariable Integer category, Model model) {
		
		Set<ProductPicture> productPicture = new HashSet<>();
		
		String categoryValue = Product.CATEGORY_OPTIONS.get(category);
		Set<Product> allByCate = productService.findAllByCate(category);
		for (Product product : allByCate) {
			Integer pId = product.getPId();
			ProductPicture thumbNail = pPictureService.findThumbNail(pId);
			productPicture.add(thumbNail);
		}
		
		model.addAttribute("productPicture", productPicture);
		model.addAttribute("categoryValue", categoryValue);
		return "/product/productCategoryList";
	}
	
	
	//沒有product的類型頁
	@GetMapping("/public/noProduct")
	public String noProduct() {
		return "/layout/noProduct";
	}
	
	@GetMapping("/public/editProduct")
	public String editProduct() {
		return "/product/productOldEdit";
	}
	
//	@GetMapping("/public/orderCanceled")
//	public String orderCanceled() {
//		return "/product/orderCanceled";
//	}
	
//	@GetMapping(value = "/public/homePage", produces="text/html;charset=utf-8")
//	public String homePage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		request.getRequestDispatcher("/WEB-INF/jsp/home.jsp").forward(request, response);
//		return "/WEB-INF/jsp/home.jsp";
//	}

	
	// 記得要有註解，在run那
//	@GetMapping("/update")
//	@Secured(value = { "ROLE_sale","ROLE_manger" })
//	public String update() {
//		return "hello update";
//	}
	
	

}
