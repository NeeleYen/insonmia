package com.test.Insomnia.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.test.Insomnia.Service.FaqService;
import com.test.Insomnia.Service.ItemService;
import com.test.Insomnia.Service.ProductPictureService;
import com.test.Insomnia.Service.ProductService;
import com.test.Insomnia.Service.PurchasePlanItemsService;
import com.test.Insomnia.Service.PurchasePlanService;
import com.test.Insomnia.Service.UserService;
import com.test.Insomnia.dto.FaqDTO;
import com.test.Insomnia.model.FAQ;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.ProductPicture;
import com.test.Insomnia.model.ProductPicturePK;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;
import com.test.Insomnia.utils.FormatValidAndTransform;

@Controller
public class ProductController {

	@Autowired
	private PurchasePlanItemsService purchasePlanItemsService;
	@Autowired
	private PurchasePlanService purchasePlanService;
	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;
	@Autowired
	private ProductPictureService productPictureService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private FaqService faqService;
	@Autowired
	private FormatValidAndTransform formatValidAndTransform;
	
	@GetMapping("/public/product/faqs/api/{id}")
	@ResponseBody
	public Set<FAQ> getFaqsByProductId(@PathVariable("id") Integer productId) {
		Product product = productService.findProductById(productId);
		if (product == null) {
			return null;
		}
		Set<FAQ> faqs = product.getFaqId();
		for (FAQ faq : faqs) {
			System.out.println(faq.getAnswer());
		}
		return faqs;
	}
	
	/**
	 * 刪除faq api (用param，非json)
	 * @param faqId
	 * @param principal
	 * @return
	 */
	@DeleteMapping("/product/faq/api")
	@ResponseBody
	public String deleteFaqApi(@RequestParam Integer faqId, Principal principal) {
		
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
		/*
		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
		Integer id = findEmail.getId(); // 找到id
		User user = userService.findById(id);
		 */
//		User user = userService.findById(1);
		User user = userService.principleToUser(principal);
		if ( faqId == null ) {
			return "未給定要刪除的faqId";
		}
		FAQ faq = faqService.findFAQById(faqId); // 依給定faqId拿到的faq
		Integer inputProductId = faq.getProductId().getPId();
//		找尋指定product是否為該user的product
		boolean isValid = false;
		Set<Product> productsUnderUser = user.getProduct();
		for ( Product product:productsUnderUser ) {
			if ( product.getPId() == inputProductId ) {
				isValid = true; 
				break;
			}
		}
		if ( isValid == false ) {
			return "權限不足(非您的專案)";
		}
		faqService.delete(faq);
		return "刪除成功";
	}
	
	@PutMapping("/product/faq/api")
	@ResponseBody
	public String editFaqApi(@RequestBody FaqDTO faqDTO, Principal principal) {
		
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
		/*
		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
		Integer id = findEmail.getId(); // 找到id
		User user = userService.findById(id);
//		User user = userService.findById(1);
		 */
		User user = userService.principleToUser(principal);
		if ( faqDTO.getFaqId() == null ) {
			return "未給定要刪除的faqId";
		}
		FAQ faq = faqService.findFAQById(faqDTO.getFaqId()); // 依給定faqId拿到的faq
		if ( faqDTO.getAnswer().isBlank() || faqDTO.getQuestion().isBlank() ) {
			return "欄位不可有空值";
		}
		Integer inputProductId = faq.getProductId().getPId();
//		找尋指定product是否為該user的product
		boolean isValid = false;
		Set<Product> productsUnderUser = user.getProduct();
		for ( Product product:productsUnderUser ) {
			if ( product.getPId() == inputProductId ) {
				isValid = true; 
				break;
			}
		}
		if ( isValid == false ) {
			return "權限不足(非您的專案)";
		}
		faq.setQuestion(faqDTO.getQuestion());
		faq.setAnswer(faqDTO.getAnswer());
		faqService.insert(faq);
		return "儲存成功";
	}
	
	@GetMapping("/createProductFAQ")
	public String createFaqPage(@RequestParam Integer productId, Principal principal, Model model) {
		if ( principal == null ) {
			return "redirect:/public/signIn";
		}
		model.addAttribute("productId", productId);
		return "product/addProductFaqForm";
	}
	
	// faq新增api
	@PostMapping("/product/faq/api")
	@ResponseBody
	public String addFaqApi(@RequestBody FaqDTO faqDTO, Principal principal) {
		
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer id = findEmail.getId(); // 找到id
//		User user = userService.findById(id);
//		User user = userService.findById(1);
		User user = userService.principleToUser(principal);
		if ( faqDTO.getAnswer().isBlank() || faqDTO.getQuestion().isBlank() || faqDTO.getProductId() == null ) {
			return "輸入欄位不完全";
		}
		Integer inputProductId = faqDTO.getProductId();
//		找尋指定product是否為該user的product
		boolean isValid = false;
		Set<Product> productsUnderUser = user.getProduct();
		for ( Product product:productsUnderUser ) {
			if ( product.getPId() == inputProductId ) {
				isValid = true; 
				break;
			}
		}
		if ( isValid == false ) {
			return "權限不足(非您的專案)";
		}
		Product faqProduct = productService.findProductById(inputProductId);
		
		FAQ faq = new FAQ(null, faqDTO.getQuestion(), faqDTO.getAnswer(), faqProduct);
		faqService.insert(faq);
		return "儲存成功";
	}
	
	@GetMapping("/product/items/api/{id}")
	@ResponseBody
	public Set<Item> getItemsByProductId(@PathVariable("id") Integer productId) {
		Product product = productService.findProductById(productId);
		if ( product == null ) {
			return null;
		}
		Set<Item> items = product.getItem();
		return items;
	}
	
	// TODO 要做Page?
	/**
	 * item新增api
	 * @param itemName {@link String}
	 * 	item品名
	 * @param productId {@link Integer}
	 * 	要新增item的productId
	 * @param principal {@link Principal} 
	 * 	以登入狀態確認用戶能否更改
	 * @return {@link String}
	 */
	@PostMapping("/product/item/api")
	@ResponseBody
	public String addItemApi(@RequestParam String itemName, @RequestParam("productId") Integer productId, Principal principal) {
		Item item = new Item();
		
//		 確認使用者是否已登入
		if ( principal == null ) {
			return "請先登入";
		}
		/*
		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
		Integer id = findEmail.getId(); // 找到id
		User user = userService.findById(id);
		*/
		if ( itemName == null || itemName.isBlank() ) {
			return "欄位值不可為空";
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		Product currentProduct = null;
//		找尋指定product是否為該user的product
		boolean isValid = false;
		Set<Product> productsUnderUser = user.getProduct();
		for ( Product product:productsUnderUser ) {
			if ( product.getPId() == productId ) {
				isValid = true;
				currentProduct = product;
				break;
			}
		}
		if ( isValid == false ) {
			return "權限不足(非您的專案)";
		}

		itemName = itemName.trim();
		item.setItemName(itemName);
		item.setProductId(currentProduct);
		itemService.insert(item);
		return "儲存成功";
	}
	
	/**
	 * item修改api
	 * @param itemName {@link String}
	 * 	要修改成成什麼品名
	 * @param itemId {@link Integer}
	 * 	要修改的目標item的id
	 * @param principal {@link Principal}
	 * 	以登入狀態確認用戶能否更改
	 * @return {@link String}
	 */
	@PutMapping("/product/item/api")
	@ResponseBody
	public String editItemApi(@RequestParam String itemName, @RequestParam Integer itemId, Principal principal) {
		
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
		/*
		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
		Integer id = findEmail.getId(); // 找到id
		User user = userService.findById(id);
//		User user = userService.findById(1);
		 */
		User user = userService.principleToUser(principal);
//		Product currentProduct = null;
//		找尋指定product是否為該user的product
		boolean isValid = false;
		Item item = itemService.findById(itemId); // 要更改的item
		if ( item == null ) {
			return "找不到item";
		}
		Product productId = item.getProductId(); // item所屬的product
		Set<Product> productsUnderUser = user.getProduct();
		for ( Product product:productsUnderUser ) {
			if ( product.getPId() == productId.getPId() ) {
				isValid = true;
//				currentProduct = product;
				break;
			}
		}
		if ( isValid == false ) {
			return "權限不足(非您的專案)";
		}
		
		item.setItemName(itemName);
		itemService.insert(item);
		return "儲存成功";
	}
	
	/**
	 * 刪除item
	 * @param itemId {@link Integer} 
	 * 	指定要刪除的item id
	 * @param principal {@link Principal}
	 *  以登入狀態確認用戶能否更改
	 * @return {@link String}
	 */
	@DeleteMapping("/product/item/api")
	@ResponseBody
	public String deleteItemApi(@RequestParam Integer itemId, Principal principal) {
		
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
		/*
		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
		Integer id = findEmail.getId(); // 找到id
		User user = userService.findById(id);
		 */
		User user = userService.principleToUser(principal);
//		找尋指定product是否為該user的product
		boolean isValid = false;
		Item item = itemService.findById(itemId);
		if ( item == null ) {
			return "找不到item";
		}
		Product productId = item.getProductId(); // item所屬的product
		Set<Product> productsUnderUser = user.getProduct();
		for ( Product product:productsUnderUser ) {
			if ( product.getPId() == productId.getPId() ) {
				isValid = true;
				break;
			}
		}
		if ( isValid == false ) {
			return "權限不足(非您的專案)";
		}
		
		itemService.delete(item);
		return "刪除成功";
	}

	// mark down status to -1
	@DeleteMapping("/product/api")
	@ResponseBody
	public String deleteProductApi(@RequestParam Integer pid, Principal principal) {
		// 自postman發送post請求。讓user新增product資料。
//		@RequestMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//		@PostMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE)
//		get user data from session, how?
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer id = findEmail.getId(); // 找到id
//
//		User user = userService.findById(id); // Lisa is here
		
//		User user = userService.findById(1); // Lisa is here
		User user = userService.principleToUser(principal);
		
		if ( pid == null ) {
			return "未指定要刪除的專案";
		}
		// 看user是否真的擁有此專案
		Product currentProduct = null;
		boolean valid = false;
		Set<Product> userProductSet = user.getProduct();
		for ( Product userProduct:userProductSet) {
			if ( userProduct.getPId() == pid ) {
				valid = true;
				currentProduct = userProduct;
				break;
			}
		}
		if ( valid == false ) {
			return "權限不足(非您的專案)";
		}
		
		
//		只有controller可以叫service做事
//		在controller包好物件，再傳給service處理資料。service處理完資料再請求底層Dao儲存。
		currentProduct.setStatus(-1);
		productService.save(currentProduct);
		return "已標記為刪除";
	}
	
	/**
	 * 依ProductId取得product json api
	 * @param productIdStr
	 * @param principal
	 * @return
	 */
	@GetMapping("/public/product/{productId}/api")
	@ResponseBody
	public Map<String, Object> getProductByProductIdApi(@PathVariable("productId") String productIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer productId = formatValidAndTransform.stringToInteger(productIdStr).get("value");
		
		if ( productId == null ) {
			resultMap.put("status", "輸入值有誤");
			resultMap.put("result", null);
			return resultMap;
		}
		
		Product product = productService.findProductById(productId);
		if (product == null) {
			resultMap.put("status", "找不到指定product");
			resultMap.put("result", null);
			return resultMap;
		}
		
		resultMap.put("status", "success");
		resultMap.put("result", product);
		return resultMap;
		
	}
	
	@DeleteMapping("/product/form")
	public String deleteProductForm(@RequestParam Integer pid, Principal principal) {
		// 自postman發送post請求。讓user新增product資料。
//		@RequestMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//		@PostMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE)
//		get user data from session, how?
		// 確認使用者是否已登入
		if ( principal == null ) {
			return "redirect:/public/signIn";
//			return "請先登入";
		}
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer id = findEmail.getId(); // 找到id
//
//		User user = userService.findById(id); // Lisa is here
		
//		User user = userService.findById(1); // Lisa is here
		User user = userService.principleToUser(principal);
		
		if ( pid == null ) {
//			return "未指定要刪除的專案";
			return "redirect:/product";
		}
		// 看user是否真的擁有此專案
		Product currentProduct = null;
		boolean valid = false;
		Set<Product> userProductSet = user.getProduct();
		for ( Product userProduct:userProductSet) {
			if ( userProduct.getPId() == pid ) {
				valid = true;
				currentProduct = userProduct;
				break;
			}
		}
		if ( valid == false ) {
			return "redirect:/product";
//			return "權限不足(非您的專案)";
		}
		
		
//		只有controller可以叫service做事
//		在controller包好物件，再傳給service處理資料。service處理完資料再請求底層Dao儲存。
		currentProduct.setStatus(-1);
		productService.save(currentProduct);
//		return "已標記為刪除";
		return "redirect:/product";
	}
	
	/**
	 * 修改product
	 * @param inputProduct
	 *  以json給定pid及title、category、status、intro、closeDate修改值，對product進行修改
	 * @param principal
	 * 	以登入狀態查找用戶
	 * @return
	 * 	回應處理結果字串
	 */
	@PutMapping("/product/api")
	@ResponseBody
	public String editProductApi(@RequestBody Product inputProduct, Principal principal) {
		// 自postman發送post請求。讓user新增product資料。
//		@RequestMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//		@PostMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE)
//		get user data from session, how?
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
//		只有controller可以叫service做事
//		在controller包好物件，再傳給service處理資料。service處理完資料再請求底層Dao儲存。
		if ( inputProduct.getTitle() == null || inputProduct.getTitle().isBlank() || inputProduct.getCategory() == null || 
				inputProduct.getStatus() == null || inputProduct.getIntro() == null || inputProduct.getIntro().isBlank() ||
				inputProduct.getCloseDate() == null ) {
			return "欄位輸入值不可為空";
		}
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer id = findEmail.getId(); // 找到id
//
//		User user = userService.findById(id); // Lisa is here
		
//		User user = userService.findById(1); // Lisa is here
		User user = userService.principleToUser(principal);
		
		if ( inputProduct.getPId() == null ) {
			return "未指定要修改的專案";
		}
		// 看user是否真的擁有此專案
		Product currentProduct = null;
		boolean valid = false;
		Set<Product> userProductSet = user.getProduct();
		for ( Product userProduct:userProductSet) {
			if ( userProduct.getPId() == inputProduct.getPId() ) {
				valid = true;
				currentProduct = userProduct;
				break;
			}
		}
		if ( valid == false ) {
			return "權限不足(非您的專案)";
		}
		
		currentProduct.setTitle(inputProduct.getTitle());
		currentProduct.setCategory(inputProduct.getCategory());
		currentProduct.setStatus(inputProduct.getStatus());
		currentProduct.setIntro(inputProduct.getIntro());
		currentProduct.setCloseDate(inputProduct.getCloseDate());
		productService.save(currentProduct);
		return "修改完成";
	}
	
	@PutMapping("/product/form")
	public String editProductForm(@RequestParam("productId") String productIdStr, @RequestParam String title,
			@RequestParam("category") String categoryStr, @RequestParam("status") String statusStr, @RequestParam String intro, 
			@RequestParam("closeDate") String closeDateStr, Model model, Principal principal) {
		
		// 確認使用者是否已登入
		if ( principal == null ) {
			return "redirect:/public/signIn";
		}
		
		Integer productId = formatValidAndTransform.stringToInteger(productIdStr).get("value");
		Integer category = formatValidAndTransform.stringToInteger(categoryStr).get("value");
		Integer status = formatValidAndTransform.stringToInteger(statusStr).get("value");
		
		if ( productId == null ) {
//			return "未指定要修改的專案";
			return "redirect:/product";
		}
		
		Product product = productService.findProductById(productId);
		if ( product == null || product.getStatus() == -1 ) {
			// 專案不存在或已刪除
			return "redirect:/product";
		}
		
		if ( closeDateStr == null || closeDateStr.isBlank() ) {
//			return "日期不可為空";
			return "redirect:/product/edit/" + productId;
		}
		closeDateStr = closeDateStr.trim();
		
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date closeDate = null;
		try {
			closeDate = dateFormatter.parse(closeDateStr);
		} catch (ParseException e) {
//			return "日期格式錯誤";
			return "redirect:/product/edit/" + productId;
		}
		
		if ( title == null || title.isBlank() || intro == null || intro.isBlank() ) {
//			return "欄位輸入值不可為空";
			return "redirect:/product/edit/" + productId;
		}
		title = title.trim();
		intro = intro.trim();
		
//		User user = userService.findById(1); // Lisa is here
		User user = userService.principleToUser(principal);
		
		// 看user是否真的擁有此專案
		Product currentProduct = null;
		boolean valid = false;
		Set<Product> userProductSet = user.getProduct();
		for ( Product userProduct:userProductSet) {
			if ( userProduct.getPId() == productId ) {
				valid = true;
				currentProduct = userProduct;
				break;
			}
		}
		if ( valid == false ) {
//			return "權限不足(非您的專案)";
			return "redirect:/product";
		}
		
		currentProduct.setTitle(title);
		currentProduct.setCategory(category);
		currentProduct.setStatus(status);
		currentProduct.setIntro(intro);
		currentProduct.setCloseDate(closeDate);
		productService.save(currentProduct);
//		return "修改完成";
		return "redirect:/product";
	}
	
	// 新增product資料 api
	@PostMapping("/product/api")
	@ResponseBody
	public String addProductApi(@RequestBody Product product, Principal principal) {
		// 自postman發送post請求。讓user新增product資料。
//		@RequestMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//		@PostMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE)
//		get user data from session, how?
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "尚未登入";
		}
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer id = findEmail.getId(); // 找到id
//		
//		User user = userService.findById(id); // Lisa is here
		
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1); // Lisa is here
		if ( product.getTitle() == null || product.getTitle().isBlank() || product.getCategory() == null || 
				product.getIntro() == null || product.getIntro().isBlank() || product.getCloseDate() == null ) {
			return "欄位值不可為空";
		}
		if ( product.getStatus() == null ) {
			product.setStatus(0); // 預設status為0
		}
		if (user != null) {
			product.setUser(user);
		}
//		只有controller可以叫service做事
//		在controller包好物件，再傳給service處理資料。service處理完資料再請求底層Dao儲存。
		productService.save(product);
//		Page<Product> productByPage = productService.findProductByUserOrderByPage(user, 1);
		return "新增成功";
	}
	
	// 新增product資料 form
	@PostMapping("/product/form")
//	@ResponseBody // form版直接重新導向，不回傳json
	public String addProductForm(@RequestParam("title") String title, @RequestParam("category") Integer category, @RequestParam("intro") String intro, 
			@RequestParam("closeDate") String inputCloseDate, @RequestParam("status") Integer status, Model model, Principal principal, RedirectAttributes redirectAttributes) {
//		https://www.baeldung.com/spring-web-flash-attributes redirect傳參
		// 自postman發送post請求。讓user新增product資料。
//		@RequestMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
//		@PostMapping(path = "/product/api", consumes = MediaType.APPLICATION_JSON_VALUE)
		// 確認使用者是否已登入
		if ( principal == null ) {
			return "redirect:/public/signIn";
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1); // Lisa is here
		System.out.println(222);
		if ( title == null || title.isBlank() || category == null || 
				intro == null || intro.isBlank() || inputCloseDate == null ) {
			return "欄位值不可為空";
		}
		if ( status == null ) {
			status = 0; // 預設status為0
		}
		if (user == null) {
			return "帳號不存在"; // 有可能發生?
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date closeDate = null;
		try {
			closeDate = dateFormatter.parse(inputCloseDate);
		} catch (ParseException e) {
			return "日期格式錯誤";
		}
		Product product = new Product(null, title, category, status, intro, closeDate, user, null, null, null, null, null);
//		只有controller可以叫service做事
//		在controller包好物件，再傳給service處理資料。service處理完資料再請求底層Dao儲存。
		Product savedProduct = productService.save(product);
		System.out.println(savedProduct.getPId());
		redirectAttributes.addFlashAttribute("productId", savedProduct.getPId());
//		model.addAttribute("productId", savedProduct.getPId());
//		Page<Product> productByPage = productService.findProductByUserOrderByPage(user, 1);
//		return "product/addProductItemForm";
		return "redirect:/editProduct/" + savedProduct.getPId();
	}
	
	@GetMapping("/product/createProductItem")
	public String redirectToaddProductItemForm(HttpServletRequest request, RedirectAttributes redirectAttributes) {
		Map<String, ?> inputFlashMap = RequestContextUtils.getInputFlashMap(request);
		if ( inputFlashMap == null ) {
			// how導向指定的用戶專案列表?
			return "redirect:/product/addProductAjax";
		}
		Integer productId = (Integer)inputFlashMap.get("productId");
//		redirectAttributes.addAttribute("productId", productId);
		return "product/productEdit/" + productId;
	}
	
	@GetMapping("/product/api")
	@ResponseBody
	public Page<Product> getProductByUserApi(@RequestParam(name="p", defaultValue = "1") Integer pageNumber, Principal principal) {
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return null;
		}
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer id = findEmail.getId(); // 找到id
//		User user = userService.findById(id); // Lisa is here
//		User user = userService.findById(1); // Lisa is here
		User user = userService.principleToUser(principal);
		if (user == null) {
			return null;
		}
		Page<Product> productByPage = productService.findProductByUserOrderByPage(user, pageNumber);
		return productByPage;
	}
	
	@GetMapping("/createProduct")
	public String createProductPage() {
		return "product/addProductForm";
	}
	
	/**
	 * 顯示所有自己的專案
	 * 導至ajax產品新增頁面
	 * @return /product/addProductAjax
	 */
	@GetMapping("/product")
	public String showProductPage(Principal principal) {
		// 確認使用者是否已登入
		if ( principal == null ) {
			return "redirect:/public/signIn";
		}
		return "/product/addProductAjax";
	}

	
	/**
	 * ajax 圖片上傳api(新增、修改)
	 * 
	 * @param productId Integer 產品id
	 * @param picNum    Integer 該專案圖片號碼
	 * @param photo     MultipartFile 圖片檔
	 * @param principal spring Principle 登入即會有(確認用戶權限)
	 * @return String 上傳狀態字串
	 * @throws IOException
	 */
	@PostMapping("/product/picture/api")
	@ResponseBody
	public String uploadProductPictureApi(@RequestParam("productId") Integer productId,
			@RequestParam("picNum") Integer picNum, @RequestParam("productPhoto") MultipartFile photo,
			Principal principal) throws IOException {
		/*
		 * 參考資料:
		 * https://stackoverflow.com/questions/45907559/java-spring-web-app-file-upload-
		 * with-relative-path (含path自動依環境尋找) https://www.baeldung.com/java-path-vs-file
		 * java-path-vs-file
		 * https://stackoverflow.com/questions/4350084/byte-to-file-in-java
		 * byte-to-file-in-java https://www.baeldung.com/java-write-byte-array-file
		 * java-write-byte-array-file https://spring.io/guides/gs/uploading-files/
		 * 官方圖片上傳範例git
		 */
//		get user data from session, how?
//		TODO ===等前端ajax做好後開啟start===
		// 確認使用者是否已登入
		if ( principal == null ) {
//			return "redirect:/public/signIn";
			return "請先登入";
		}
//		上傳前先確認目前登入之使用者資訊，是否為該product擁有者
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer userId = findEmail.getId(); // 找到user的id
//		User user = userService.findById(userId); // 前端ajax做好後修改
//		===等前端ajax做好後開啟end===
//		User user = userService.findById(1); // 前端ajax做好後修改
		User user = userService.principleToUser(principal);
		// 取得user之下的專案列表，確認user是否為專案擁有者
		Set<Product> productSet = user.getProduct();
		boolean valid = false;
		for (Product checkProduct : productSet) {
			if (checkProduct.getPId() == productId) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "權限不足(非您的專案)";
		}
		// 前一步跑完，這步就不需要了。
//		if (product == null) {
//			return "專案不存在";
//		}
		
//		確認檔案是否為空
		if (photo.isEmpty()) {
			return "未選擇檔案";
		}
		// TODO ===判斷檔案種類使否為圖片===(後期有時間做)
		
//		只有controller可以叫service做事
//		===controller負責出入資料格式判斷，其餘交給service===
//		先判斷資料庫是否已有指定位置的圖片
//		取得product編號以及圖片位置編號
		Product product = productService.findProductById(productId);
		ProductPicturePK productPicturePK = new ProductPicturePK(product, picNum);
		ProductPicture productPicture = productPictureService.getProductPictureById(productPicturePK);
		// 有出現舊圖片就先砍了
		if (productPicture != null) {
			// 若已有圖片，先刪檔案，再刪資料庫，再行新增
//			===若指定圖片已存在，刪除磁碟對應檔案===
			productPictureService.deleteProductPictureFile(productPicture);
//			===若指定圖片已存在，刪除資料庫對應列===
			productPictureService.deleteProductPicture(productPicture);
		}

//		舊的如果有，已經被砍了，故直接新增 // 若沒找到資料庫對應圖片，直接新增
		// 存至server disk
		String storedPath = productPictureService.saveProductPictureFile(photo);

		// 存路徑至sql
		if (storedPath == null) {
			return "存檔至server磁碟失敗";
		}
		ProductPicture productPictureModel = new ProductPicture(productPicturePK, storedPath);
		productPictureService.saveProductPicture(productPictureModel);
		System.out.println(storedPath);
		return storedPath; // 另外寫一個controller取圖片路徑
//		return "redirect:/test/editProduct/" + productId; // 基本款

	}

	/**
	 * 圖片刪除 form
	 * @param productId Integer 產品id
	 * @param picNum    Integer 該專案圖片號碼
	 * @param principal spring Principle 登入即會有(確認用戶權限)
	 * @return String 刪除狀態字串
	 * @throws IOException
	 */
	@DeleteMapping("/product/picture/form")
//	@ResponseBody
	public String deleteProductPictureFormApi(@RequestParam("productId") Integer productId,
			@RequestParam("picNum") Integer picNum, Principal principal) throws IOException {
//		===等前端ajax做好後開啟start===
		// 確認使用者是否已登入
		if ( principal == null ) {
			return "請先登入";
		}
//		上傳前先確認目前登入之使用者資訊，是否為該product擁有者
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer userId = findEmail.getId(); // 找到user的id
//		===等前端ajax做好後開啟end===
//		User user = userService.findById(1); // 前端ajax做好後修改
		User user = userService.principleToUser(principal);
		// 取得user之下的專案列表，確認user是否為專案擁有者
		Set<Product> productSet = user.getProduct();
		boolean valid = false;
		for (Product checkProduct : productSet) {
			if (checkProduct.getPId() == productId) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "權限不足(非您的專案)";
		}
		Product product = productService.findProductById(productId);
		if (product == null) {
			return "專案不存在";
		}
//		先判斷資料庫是否已有指定位置的圖片
//		取得product編號以及圖片位置編號
		ProductPicturePK productPicturePK = new ProductPicturePK(product, picNum);
		ProductPicture productPicture = productPictureService.getProductPictureById(productPicturePK);
		if (productPicture == null) {
			return "檔案不存在server";
		}
		// 若已有圖片，先刪檔案，再刪資料庫，再行新增
//			===若指定圖片已存在，刪除磁碟對應檔案===
		productPictureService.deleteProductPictureFile(productPicture);
//			===若指定圖片已存在，刪除資料庫對應列===
		productPictureService.deleteProductPicture(productPicture);
		return "redirect:/editProduct/" + productId;
//		return "檔案已刪除";
//		目標是ajax直接顯示修改 不行就新增中介頁面用model傳資料再redirect?(jsp可redirect?) 不行就直接在此導回原編輯頁面(無法顯示刪除狀態?)
	}
	
	/**
	 * ajax 圖片刪除api
	 * @param productId Integer 產品id
	 * @param picNum    Integer 該專案圖片號碼
	 * @param principal spring Principle 登入即會有(確認用戶權限)
	 * @return String 刪除狀態字串
	 * @throws IOException
	 */
	@DeleteMapping("/product/picture/api")
	@ResponseBody
	public String deleteProductPictureApi(@RequestParam("productId") Integer productId,
			@RequestParam("picNum") Integer picNum, Principal principal) throws IOException {
//		===等前端ajax做好後開啟start===
		// 確認使用者是否已登入
		if ( principal == null ) {
			return "請先登入";
		}
//		上傳前先確認目前登入之使用者資訊，是否為該product擁有者
//		User findEmail = userService.findEmail(principal.getName()); // 找出登入的email
//		Integer userId = findEmail.getId(); // 找到user的id
//		===等前端ajax做好後開啟end===
//		User user = userService.findById(1); // 前端ajax做好後修改
		User user = userService.principleToUser(principal);
		// 取得user之下的專案列表，確認user是否為專案擁有者
		Set<Product> productSet = user.getProduct();
		boolean valid = false;
		for (Product checkProduct : productSet) {
			if (checkProduct.getPId() == productId) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "權限不足(非您的專案)";
		}
		Product product = productService.findProductById(productId);
		if (product == null) {
			return "專案不存在";
		}
//		先判斷資料庫是否已有指定位置的圖片
//		取得product編號以及圖片位置編號
		ProductPicturePK productPicturePK = new ProductPicturePK(product, picNum);
		ProductPicture productPicture = productPictureService.getProductPictureById(productPicturePK);
		if (productPicture == null) {
			return "檔案不存在server";
		}
		// 若已有圖片，先刪檔案，再刪資料庫，再行新增
//			===若指定圖片已存在，刪除磁碟對應檔案===
		productPictureService.deleteProductPictureFile(productPicture);
//			===若指定圖片已存在，刪除資料庫對應列===
		productPictureService.deleteProductPicture(productPicture);
//		return "redirect:/editProduct/" + productId;
		return "success";
//		目標是ajax直接顯示修改 不行就新增中介頁面用model傳資料再redirect?(jsp可redirect?) 不行就直接在此導回原編輯頁面(無法顯示刪除狀態?)
	}
	
	@GetMapping("/public/product/{productId}/picture/api")
	@ResponseBody
	public Map<String, Object> getAllProductPictureByProductIdApi(@PathVariable("productId") String productIdStr) throws IOException {
		Map<String, Object> resultMap = new HashMap<>();
		Integer productId = formatValidAndTransform.stringToInteger(productIdStr).get("value");
		if ( productId == null ) {
			resultMap.put("status", "輸入值有誤");
			resultMap.put("result", null);
			return resultMap;
		}
		Product product = productService.findProductById(productId);
		if (product == null) {
			resultMap.put("status", "找不到指定product");
			resultMap.put("result", null);
			return resultMap;
		}
		Map<Integer, String> allProductPicturePathByProduct = productPictureService.getAllProductPicturePathByProduct(product);
		if ( allProductPicturePathByProduct.isEmpty() ) {
			resultMap.put("status", "empty");
			resultMap.put("result", null);
			return resultMap;
		}
		resultMap.put("status", "success");
		resultMap.put("result", allProductPicturePathByProduct);
		return resultMap;
	}

	
	
		
		// TODO 將它變成正式編輯頁面controller
		@GetMapping("/tempEditProduct/{id}")
		public String tempEditProduct(@PathVariable Integer id, Model model, Principal principal) throws IOException {
//			========= new page =========
			if ( principal == null ) {
				return "redirect:/public/signIn";
			}
			Product product = productService.findProductById(id);
			// 若找不到頁面，重定向回首頁
			if (product == null) {
				return "redirect:/";
			}
			// 確認user是否為該product擁有者
			User user = userService.principleToUser(principal);
			Set<Product> userProducts = user.getProduct();
			boolean valid = false;
			for ( Product userProduct:userProducts ) {
				if ( userProduct.getPId() == id ) {
					valid = true;
					break;
				}
			}
			if (!valid) {
				return "redirect:/";
			}
		
			model.addAttribute("product", product);
						
			//抓封面圖
//			String thumbNailPath = productPictureService.getThumbNailPath(id);
//			model.addAttribute("thumbNailPath", thumbNailPath);
			Map<Integer, String> productPicturePath = productPictureService.getAllProductPicturePathByProduct(product);
			model.addAttribute("productPictures", productPicturePath);
					
			//抓購買方案
			Set<PurchasePlan> pPlanSet = product.getPurchasePlan();
			model.addAttribute("pPlanSet", pPlanSet);	
					
			//抓購買方案 貨品清單&數量
			Map<String, String> pPlanMap = new HashMap<>();
			for (PurchasePlan purchasePlan : pPlanSet) {
				Set<PurchasePlanItems> pPlanItemSet = purchasePlan.getPurchasePlanItems();
				pPlanMap = purchasePlanItemsService.findItemNameAmount(pPlanItemSet);
			}
					
			// TODO 缺運送地區
				
			model.addAttribute("pPlanMap", pPlanMap);
					
			//抓募資狀態、總目標
			Integer fundationGoal = productService.fundationGoal(id);
			Integer fundationStatus = productService.fundationStatus(id);
			model.addAttribute("fundationGoal", fundationGoal);
			model.addAttribute("fundationStatus", fundationStatus);

			//抓支持人數
			Integer findSupporterCount = productService.findSupporterCount(id);
			model.addAttribute("supporterCount", findSupporterCount);
					
			//抓剩餘天數
			Long productTime = productService.getProductTime(id);
			model.addAttribute("dayLeft", productTime);
			return "product/productEditNewOld"; // 導到編輯用預覽畫面jsp
		}
		
		@GetMapping("/editProduct/{id}")
		public String editProduct(@PathVariable("id") String idStr, Model model, Principal principal) throws IOException {
//			========= new page =========
			if ( principal == null ) {
				return "redirect:/public/signIn";
			}
			Integer id = formatValidAndTransform.stringToInteger(idStr).get("value");
			// 若指定productId有誤，重定向回專案列表
			if (id == null) {
				return "redirect:/product";
			}
			Product product = productService.findProductById(id);
			// 若找不到頁面，重定向回專案列表
			if (product == null) {
				return "redirect:/product";
			}
			// 確認user是否為該product擁有者
			User user = userService.principleToUser(principal);
			Set<Product> userProducts = user.getProduct();
			boolean valid = false;
			for ( Product userProduct:userProducts ) {
				if ( userProduct.getPId() == id ) {
					valid = true;
					break;
				}
			}
			if (!valid) {
				return "redirect:/product";
			}
			
			model.addAttribute("productId", product.getPId());
			
			return "product/productEdit"; // 導到編輯用預覽畫面jsp
		}
		
		@GetMapping("/product/edit/{id}")
		public String redirectToEditProductSelfPage(@PathVariable Integer id, Model model, Principal principal) throws IOException {
//			========= new page =========
			if ( principal == null ) {
				return "redirect:/public/signIn";
			}
			Product product = productService.findProductById(id);
			// 若找不到頁面，重定向回首頁
			if (product == null) {
				return "redirect:/product";
			}
			// 確認user是否為該product擁有者
			User user = userService.principleToUser(principal);
			Set<Product> userProducts = user.getProduct();
			boolean valid = false;
			for ( Product userProduct:userProducts ) {
				if ( userProduct.getPId() == id ) {
					valid = true;
					break;
				}
			}
			if (!valid) {
				return "redirect:/product";
			}
			
			model.addAttribute("product", product);
			
			return "product/productSelfEdit"; // 導到編輯用預覽畫面jsp
		}
		
	@PostMapping("/productPicture/edit") // 導到修改表單(暫時的，目標是可在編輯用預覽畫面jsp直接彈出上傳視窗，並用ajax直接更新圖片)
	public String editProductPicture(@RequestParam("productId") String productId, @RequestParam("picNum") String picNum, Model model) {
		// redirect to upload picture page
		model.addAttribute("productId",productId);
		model.addAttribute("picNum",picNum);
		return "product/productPictureUpload";
	}
	
}
