package com.test.Insomnia.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.Insomnia.Service.AreaService;
import com.test.Insomnia.Service.CreditCardService;
import com.test.Insomnia.Service.OrderService;
import com.test.Insomnia.Service.ProductPictureService;
import com.test.Insomnia.Service.ProductService;
import com.test.Insomnia.Service.PurchasePlanItemsService;
import com.test.Insomnia.Service.PurchasePlanService;
import com.test.Insomnia.Service.UserService;
import com.test.Insomnia.dto.BulletinDTO;
import com.test.Insomnia.dto.OrderDTO;
import com.test.Insomnia.model.Area;
import com.test.Insomnia.model.CreditCard;
import com.test.Insomnia.model.Order;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.User;

@Controller
public class ProductPageController {

	@Autowired
	private ProductService productService;
	@Autowired
	private ProductPictureService pPictureService;
	@Autowired
	private UserService userService;
	@Autowired
	private PurchasePlanService pPlanService;
	@Autowired
	private CreditCardService creditCardService;
	@Autowired
	private AreaService areaService;
	@Autowired
	private OrderService orderService;
	@Autowired
	private PurchasePlanItemsService pPlanItemsService;
	
	// [跳頁]進產品頁
	@GetMapping("/public/product/{id}")
	public String product(@PathVariable Integer id, Model model,@ModelAttribute("dtoText") BulletinDTO dtoText) throws IOException {
		Product product = productService.findProductById(id);
		// 若找不到頁面，重定向回首頁
			if (product == null) {
				return "redirect:/";
			}
	
			model.addAttribute("product", product);
					
		//抓封面圖
		String thumbNailPath = pPictureService.findThumbNail(id).getFilePath();
		model.addAttribute("thumbNailPath", thumbNailPath);
//		Map<Integer, String> productPicturePath = pPictureService.getAllProductPicturePathByProduct(product);
//		model.addAttribute("productPictures", productPicturePath);
				
		//抓購買方案
		Set<PurchasePlan> pPlanSetTest = product.getPurchasePlan();
		Set<PurchasePlan> pPlanSet = new HashSet<>();		
		//確認到上限
		for (PurchasePlan purchasePlan : pPlanSetTest) {
			String purchasePlanToClose = pPlanService.isPurchasePlanToClose(purchasePlan,0);
			
			if (purchasePlanToClose == "false" || purchasePlanToClose == null) {
				pPlanSet.add(purchasePlan);
			}
		}
		
		model.addAttribute("pPlanSet", pPlanSet);	
				
		//抓購買方案 貨品清單&數量
		Map<String, String> pPlanMap = new HashMap<>();
		for (PurchasePlan purchasePlan : pPlanSet) {
			Set<PurchasePlanItems> pPlanItemSet = purchasePlan.getPurchasePlanItems();
			pPlanMap = pPlanItemsService.findItemNameAmount(pPlanItemSet);
		}
		Set<String> itemNameAmountStrSet = pPlanItemsService.getItemNameAmountStr(pPlanMap);				
		
		// TODO 缺運送地區
			
//		model.addAttribute("pPlanMap", pPlanMap);
		model.addAttribute("itemNameAmountStrSet", itemNameAmountStrSet);
				
		//抓募資狀態、總目標
		Integer fundationGoalInt = productService.fundationGoal(id);		
		String fundationGoal = productService.thousandsSeparator(fundationGoalInt);
		
		Integer fundationStatusInt = productService.fundationStatus(id);
		String fundationStatus = productService.thousandsSeparator(fundationStatusInt);
		model.addAttribute("fundationGoal", fundationGoal);
		model.addAttribute("fundationStatus", fundationStatus);
		
		//抓募資百分比
		String fundationPercentStr = productService.fundationPercent(fundationStatusInt, fundationGoalInt);
		Float fundationPercent = Float.parseFloat(fundationPercentStr);
		model.addAttribute("fundationPercent", fundationPercent);

		//抓支持人數
		Integer findSupporterCount = productService.findSupporterCount(id);
		model.addAttribute("supporterCount", findSupporterCount);
			
		//抓截止日
		Date closeDate = product.getCloseDate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String closeDateStr = simpleDateFormat.format(closeDate);
		model.addAttribute("closeDateStr", closeDateStr);
		
		//抓剩餘天數
		Long productTime = productService.getProductTime(id);
		model.addAttribute("dayLeft", productTime);
		
		// 留言版儲存跳轉用，用dto，save等方法寫在userController - ByLisa
		model.addAttribute("dtoText", dtoText);

		//產品介紹圖
		Set<ProductPicture> introPic = pPictureService.findIntroPic(id);
		model.addAttribute("introPic", introPic);
		
		return "product/productPage2"; //要改
	}
	
	//進購買方案列表頁
	@GetMapping("/public/product/{id}/purchasePlanList")
	public String toPurPlanList(@PathVariable Integer id, Model model) {

		Product product = productService.findProductById(id);
		model.addAttribute("productId", id);
		if (product!=null) {			
			model.addAttribute("product", product);
		}
		//抓購買方案
		Set<PurchasePlan> pPlanSet = product.getPurchasePlan();
		model.addAttribute("pPlanSet", pPlanSet);
		
		//抓購買方案 貨品清單&數量
		Map<String, String> pPlanMap = new HashMap<>();
		for (PurchasePlan purchasePlan : pPlanSet) {
			Set<PurchasePlanItems> pPlanItemSet = purchasePlan.getPurchasePlanItems();
			pPlanMap = pPlanItemsService.findItemNameAmount(pPlanItemSet);
		}
		Set<String> itemNameAmountStrSet = pPlanItemsService.getItemNameAmountStr(pPlanMap);
	
		model.addAttribute("itemNameAmountStrSet", itemNameAmountStrSet);
		
		return "/product/purchasePlanList";
	}
	
	//進入訂單表單
//	https://matthung0807.blogspot.com/2019/12/java-number-left-padding-zero.html
	@GetMapping("/public/product/{id}/order")
	public String inputOrder(@PathVariable Integer id, 
				@RequestParam("amount") int amount, 
				@RequestParam("purPlanId") int purPlanId, Model model, 
				Principal principal) {
		if (principal == null) {
			return "redirect:/public/signIn";	//呼叫controller
		}
		
		Product product = productService.findProductById(id);
		if (productService.getProductTime(id) == -1L) {
			model.addAttribute("message", "此專案已過期");
			return "/layout/msgPage";
		}
//		User findUserFromEmail = new User();
		User findUserFromEmail = userService.findEmail(principal.getName());
		
		//到達購買上限不能下訂
		PurchasePlan purchasePlan = pPlanService.findById(purPlanId);
		String purchasePlanToClose = pPlanService.isPurchasePlanToClose(purchasePlan, amount);
		if (purchasePlanToClose=="true") {
			//此專案訂單上限
			Integer maxOrderAmount = purchasePlan.getMaxOrderAmount();
			//已有訂單數
			Integer currentOrderAmount = orderService.getCurrentOrderAmountByPurchasePlanId(purPlanId);
			//USER下單數 amount
			String username = findUserFromEmail.getUsername();
			model.addAttribute("maxOrderAmount", maxOrderAmount);
			model.addAttribute("currentOrderAmount", currentOrderAmount);
			model.addAttribute("username", username);
			model.addAttribute("amount", amount);
			return "/layout/purchasePlanClosed";
		}
		
		Map<Integer, String> cardNumxx = userService.findCardById(findUserFromEmail);
		Set<CreditCard> creditCards = findUserFromEmail.getCreditCards();
		if (creditCards==null||creditCards.size()==0) {
			return "redirect:/users/insertCard";
		}
//		Set<StringBuffer> cardNumxx = new HashSet<StringBuffer>(0);
//		for (CreditCard creditCard : creditCards) {
//			String replace = "-xxxx-xxxx-";
//			Long cradNum = creditCard.getCardNumber();
////			String strCardNum = cradNum.toString();	//信用卡號字串
//			String strCardNumFull = String.format("%016d", cradNum); // 格式化字串，整數，長度16，不足部分左邊補0
//			StringBuffer stringBuffer = new StringBuffer(strCardNumFull);
//			stringBuffer.replace(4, 12, replace);
//			cardNumxx.add(stringBuffer);
//		}
		
		if (product!=null) {			
			model.addAttribute("product", product);
		}
		
		PurchasePlan purPlan = purchasePlan;
		Set<Area> areaNo = purPlan.getAreaNo();
		Integer purPrice = purPlan.getPrice();
		Integer price = purPrice * amount;
		
		//這邊用JSON？
		if (!cardNumxx.isEmpty()) {
			model.addAttribute("cardNumxx", cardNumxx);			
		}
		model.addAttribute("productId", id);
		model.addAttribute("amount", amount);
		model.addAttribute("purPlanId", purPlanId);
		model.addAttribute("purPlan", purPlan);
		model.addAttribute("areaNo", areaNo);
		model.addAttribute("user", findUserFromEmail);
		model.addAttribute("creditCards", creditCards);
		model.addAttribute("price", price);
		return "/product/orderForm";
	}
	
	@SuppressWarnings("unused")
	@ResponseBody
	@PostMapping("/public/product/{id}/makeOrder")
	public ResponseEntity<Map<String, Object>> makeOrder(@PathVariable Integer id, 
			@RequestBody OrderDTO orderDTO, Principal principal
//			, RedirectAttributes redirectAttributes
			) {
		
		HashMap<String, Object> hashMap = new HashMap<String, Object>(0);
		
		Order order = new Order();
		Integer cardNum = orderDTO.getCardId();
		Integer purPlanId = orderDTO.getPurPlanId();
		Integer intAreaId = orderDTO.getIntAreaId();
		Integer userId = orderDTO.getUserId();
		Integer amount = orderDTO.getAmount();
		Integer price = orderDTO.getPrice();
		String note = orderDTO.getNote();
		String detailAddr = orderDTO.getDetailAddr();
		PurchasePlan purPlan = pPlanService.findById(purPlanId);
		// 改用sql抓取currentOrderAmount
//		purPlan.setCurrentOrderAmount(purPlan.getCurrentOrderAmount() + amount);
		Area area = areaService.findById(intAreaId);
		User user = userService.findById(userId);
		CreditCard card = creditCardService.findById(cardNum);
		order.setCreditCard(card);
		order.setPurchasePlan(purPlan);
		order.setAreaId(area);
		order.setUserId(user);

		order.setAmount(amount);
		order.setPrice(price*amount);		
		order.setNote(note);		
		order.setDetailAddr(detailAddr);		
		Order savedOrder = orderService.createOrder(order);

		if (order!=null) {
			hashMap.put("status", "success");
			return ResponseEntity.ok(hashMap);
		} else {
			//其他問題之後填
			hashMap.put("status", "have problem");
			return ResponseEntity.ok(hashMap);
		}
	}
	
	//立即付款 【先用id找最新order】
	//填完表格送出跳頁到訂單資料和付款頁面
	@GetMapping("/public/product/{id}/toPay")
	public String toPay(@PathVariable Integer id, Model model, Principal principal
//			, HttpServletRequest request
			) {
		if (principal == null) {
			return "redirect:/public/signIn";	//呼叫controller
		}

		Order lastestOrder = orderService.findLastestOrder();

		Integer orderId = lastestOrder.getOrderId();
		System.out.println( "訂單編號：" + orderId);
		User userId = userService.findEmail(principal.getName());
		Product product = lastestOrder.getPurchasePlan().getProduct();
		//付款頁面圖
		String thumbNailPath = pPictureService.findThumbNail(id).getFilePath();
		
		//抓該訂單的購買方案(一個) 貨品清單
		PurchasePlan findPurPlan = orderService.findPurPlan(orderId);
		Set<PurchasePlanItems> purchasePlanItems = findPurPlan.getPurchasePlanItems();
		Map<String, String> map = new HashMap<String, String>(0);
		map = pPlanItemsService.findItemNameAmount(purchasePlanItems);
		
		String[] array = map.keySet().toArray(new String[map.size()]);	
		
		String itemAndAmount = "";
		for (int i = 0; i < map.size(); i++) {
			if (i < map.size()-1) {
				itemAndAmount += array[i] + map.get(array[i]) + "元#";
			} else {
				itemAndAmount += array[i] + map.get(array[i]) + "元";
			}
		}
		
		Date orderDate = lastestOrder.getOrderDate();
		String strOrderDate = new SimpleDateFormat("yyyy/MM/dd").format(orderDate);

		model.addAttribute("order", lastestOrder);
		model.addAttribute("strOrderDate", strOrderDate);
		model.addAttribute("user", userId);
		model.addAttribute("product", product);
		model.addAttribute("thumbNailPath", thumbNailPath);
		model.addAttribute("itemAndAmount", itemAndAmount);
		return "/product/orderAndPay";
	}
	
	
	//從首頁進訂單列表
	@GetMapping("/public/product/toOrderList")
	public String toOrderList(
			@RequestParam(name = "p", defaultValue = "1") Integer pageNum, 
			Principal principal, Model model) {
		if (principal == null) {
			System.out.println("沒有登入");
			return "redirect:/public/signIn";	//呼叫controller
		}

		User user = userService.findEmail(principal.getName());
		Integer userId = user.getId();

		Page<Order> orderPage = orderService.findByPage(pageNum, userId);
		model.addAttribute("orderPage", orderPage);				
		model.addAttribute("user", user);
		return "/product/orderList";
	}
	
	//從清單付款(跳到同一付款頁面)
	@GetMapping("/public/product/order{orderId}/toPayFromList")
	public String toPayFromList(@PathVariable Integer orderId, Model model, 
			Principal principal) {
		if (principal == null) {			
			return "redirect:/public/signIn";	//呼叫controller
		}
		Order order = orderService.findById(orderId);
		User user = userService.findEmail(principal.getName());
		Product product = order.getPurchasePlan().getProduct();
		ProductPicturePK productPicturePK = new ProductPicturePK(product, 1);
		ProductPicture productPicture = pPictureService.getProductPictureById(productPicturePK);
		String thumbNailPath = productPicture.getFilePath();
		
		//抓該訂單的購買方案(一個) 貨品清單
		PurchasePlan findPurPlan = orderService.findPurPlan(orderId);
		Set<PurchasePlanItems> purchasePlanItems = findPurPlan.getPurchasePlanItems();
		Map<String, String> map = new HashMap<String, String>(0);
		map = pPlanItemsService.findItemNameAmount(purchasePlanItems);

		//要用#隔開，和service的不一樣
		String[] array = map.keySet().toArray(new String[map.size()]);	
		
		String itemAndAmount = "";
		for (int i = 0; i < map.size(); i++) {
			if (i < map.size()-1) {
				itemAndAmount += array[i] + map.get(array[i]) + "元#";
			} else {
				itemAndAmount += array[i] + map.get(array[i]) + "元";
			}
		}

		Date orderDate = order.getOrderDate();
		String strOrderDate = new SimpleDateFormat("yyyy/MM/dd").format(orderDate);
		
		model.addAttribute("order", order);
		model.addAttribute("strOrderDate", strOrderDate);
		model.addAttribute("user", user);
		model.addAttribute("product", product);
		model.addAttribute("thumbNailPath", thumbNailPath);
//		model.addAttribute("findItemNameAmount", itemAndAmount);
		model.addAttribute("itemAndAmount", itemAndAmount);
		return "/product/orderAndPay";
	}
		
	//跳到確認頁
	@GetMapping("/public/product/order{orderId}/toOrderDelete")
	public String toOrderCanceled(@PathVariable Integer orderId, Principal principal, Model model) {
		if (principal == null) {
			return "redirect:/public/signIn";	//呼叫controller
		}
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//貨品清單前端從購買方案抓
		Order order = orderService.findById(orderId);
		Date orderDate = order.getOrderDate();
		String strOrderDate = new SimpleDateFormat("yyyy/MM/dd").format(orderDate);
		PurchasePlan purchasePlan = order.getPurchasePlan();						
		
		model.addAttribute("order", order);
		model.addAttribute("strOrderDate", strOrderDate);
		model.addAttribute("purchasePlan", purchasePlan);
		return "/product/orderCancelConfirm";
	}
	
	//取消訂單
	@GetMapping("/public/product/order{orderId}/delete")
	public String deleteStatusOrder(@PathVariable Integer orderId, Model model, 
			Principal principal) {				
		if (principal == null) {
			return "redirect:/public/signIn";	//呼叫controller
		}
		
		orderService.setOrderCancelStatus(orderId);

		return "/product/orderCanceled";
	}
		
}
