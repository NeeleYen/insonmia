package com.test.Insomnia.controller;

import java.io.IOException;
import java.security.Principal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.test.Insomnia.Service.AreaService;
import com.test.Insomnia.Service.ItemService;
import com.test.Insomnia.Service.ProductService;
import com.test.Insomnia.Service.PurchasePlanItemsService;
import com.test.Insomnia.Service.PurchasePlanService;
import com.test.Insomnia.Service.UserService;
import com.test.Insomnia.dao.ProductDao;
import com.test.Insomnia.dao.PurchasePlanDao;
import com.test.Insomnia.dto.PurchasePlanItemsJsonDTO;
import com.test.Insomnia.model.Area;
import com.test.Insomnia.model.Item;
import com.test.Insomnia.model.Product;
import com.test.Insomnia.model.PurchasePlan;
import com.test.Insomnia.model.PurchasePlanItems;
import com.test.Insomnia.model.PurchasePlanItemsPK;
import com.test.Insomnia.model.User;
import com.test.Insomnia.utils.FormatValidAndTransform;

@Controller
public class PurchasePlanController {

	@Autowired
	private UserService userService;
	@Autowired
	private PurchasePlanService purchasePlanService;
	@Autowired
	private ItemService itemService;
	@Autowired
	private PurchasePlanItemsService purchasePlanItemsService;
	@Autowired
	private FormatValidAndTransform formatValidAndTransform;
	@Autowired
	private AreaService areaService;
	@Autowired
	private ProductService productService;
	@Autowired
	private FormatValidAndTransform validAndTransform;

	@ResponseBody
	@GetMapping("/public/product/purchasePlan/{purchasePlanId}/unusedPurchasePlanArea/api")
	public Map<String, Object> listUnusedAreasByPurchasePlanId(
			@PathVariable("purchasePlanId") String purchasePlanIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer purchasePlanId = validAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		if (purchasePlanId == null) {
			resultMap.put("status", "輸入須為數字");
			resultMap.put("result", null);
			return resultMap;
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			resultMap.put("status", "找不到指定purchasePlan");
			resultMap.put("result", null);
			return resultMap;
		}
		List<Area> listUnusedItemsByPurchasePlanId = areaService.listUnusedAreasByPurchasePlanId(purchasePlanId);
		if (listUnusedItemsByPurchasePlanId.isEmpty()) {
			resultMap.put("status", "該purchasePlan已使用所有area");
			resultMap.put("result", null);
			return resultMap;
		}
		resultMap.put("status", "success");
		resultMap.put("result", listUnusedItemsByPurchasePlanId);
		return resultMap;
	}

	@ResponseBody
	@GetMapping("/public/product/purchasePlan/{purchasePlanId}/unusedPurchasePlanItem/api")
	public Map<String, Object> listUnusedItemsByPurchasePlanId(
			@PathVariable("purchasePlanId") String purchasePlanIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer purchasePlanId = validAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		if (purchasePlanId == null) {
			resultMap.put("status", "輸入須為數字");
			resultMap.put("result", null);
			return resultMap;
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			resultMap.put("status", "找不到指定purchasePlan");
			resultMap.put("result", null);
			return resultMap;
		}
		List<Item> listUnusedItemsByPurchasePlanId = purchasePlanItemsService
				.listUnusedItemsByPurchasePlanId(purchasePlanId);
		if (listUnusedItemsByPurchasePlanId == null) {
			resultMap.put("status", "product下尚無item或該purchasePlan已使用所有item");
			resultMap.put("result", null);
			return resultMap;
		}
		resultMap.put("status", "success");
		resultMap.put("result", listUnusedItemsByPurchasePlanId);
		return resultMap;
	}

	// [跳頁]按下[支持這個專案]跳到[方案列表]
	@GetMapping("/product/purchasePlanList")
	public String purchasePlanList() {
		return "product/purchasePlanList";
	}

	@GetMapping("/product/createPurchasePlanForm")
	public String redirectToCreatePurchasePlanForm(RedirectAttributes redirectAttributes, Principal principal) {
		return "/product/addProductPurchasePlanForm";
	}

	/**
	 * 新增purchasePlan api
	 * 
	 * @param productId
	 * @param title
	 * @param intro
	 * @param price
	 * @param inputMinOrderAmount
	 * @param inputMaxOrderAmount
	 * @param shipmentDateStr
	 * @param principal
	 * @return {@link Map} Map<狀態, PurchasePlan>
	 */
	@PostMapping("/product/purchasePlan/api")
	@ResponseBody
//	Map<狀態, PurchasePlan> // 還是回傳purchasePlanId就好?
	public Map<String, Object> addPurchasePlanApi(@RequestParam("productId") Integer productId,
			@RequestParam("title") String title, @RequestParam("intro") String intro,
			@RequestParam("price") Integer price, @RequestParam("minOrderAmount") String inputMinOrderAmount,
			@RequestParam("maxOrderAmount") String inputMaxOrderAmount,
			@RequestParam("shipmentDate") String shipmentDateStr, Principal principal) {
		Map<String, Object> resultMap = new HashMap<>();
		if ( principal == null ) {
			resultMap.put("status", "請先登入");
			resultMap.put("result", null);
			return resultMap;
		}
		if (productId == null) {
			resultMap.put("status", "未指定要新增purchasePlan的product");
			resultMap.put("result", null);
			return resultMap;
		}
		if (title == null || title.isBlank() || price == null || shipmentDateStr == null || shipmentDateStr.isEmpty()) {
			resultMap.put("status", "指定欄位不可為空");
			resultMap.put("result", null);
			return resultMap;
		}
		title = title.trim();
		if (intro == null || intro.isBlank()) {
			intro = null;
		} else {
			intro = intro.trim();
		}
		Integer minOrderAmount = null;
		Integer maxOrderAmount = null;
		try {
			if (inputMinOrderAmount != null) {
				String minOrderAmountStr = inputMinOrderAmount.trim();
				if (minOrderAmountStr.isBlank()) {
					minOrderAmount = null;
				} else {
					minOrderAmount = Integer.valueOf(inputMinOrderAmount);
				}
			}
			if (inputMaxOrderAmount != null) {
				String maxOrderAmountStr = inputMaxOrderAmount.trim();
				if (maxOrderAmountStr.isBlank()) {
					maxOrderAmount = null;
				} else {
					maxOrderAmount = Integer.valueOf(inputMaxOrderAmount);
				}
			}
		} catch (Exception e) {
			resultMap.put("status", "最大最小允許購買量輸入格式有誤");
			resultMap.put("result", null);
			return resultMap;
		}
		if (minOrderAmount != null && maxOrderAmount != null) {
			if (maxOrderAmount < minOrderAmount) {
				resultMap.put("status", "最小所需銷售量不可大於最大限制購買量");
				resultMap.put("result", null);
				return resultMap;
			} else if (maxOrderAmount <= 0 || minOrderAmount <= 0) {
				resultMap.put("status", "最小所需銷售量及最大限制購買量需大於0");
				resultMap.put("result", null);
				return resultMap;
			}
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date shipmentDate = null;
		try {
			shipmentDate = dateFormatter.parse(shipmentDateStr);
		} catch (ParseException e) {
			resultMap.put("status", "日期格式錯誤");
			resultMap.put("result", null);
			return resultMap;
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		Product currentProduct = null;
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == productId && userProduct.getStatus() != -1) {
				currentProduct = userProduct;
				valid = true;
				break;
			}
		}
		if (!valid) {
			resultMap.put("status", "非您的專案或專案已註銷");
			resultMap.put("result", null);
			return resultMap;
		}
		PurchasePlan purchasePlan = new PurchasePlan();
		purchasePlan.setProduct(currentProduct);
		purchasePlan.setTitle(title);
		purchasePlan.setIntro(intro);
		purchasePlan.setPrice(price);
		purchasePlan.setMinOrderAmount(minOrderAmount);
		purchasePlan.setMaxOrderAmount(maxOrderAmount);
		purchasePlan.setShipmentDate(shipmentDate);
		PurchasePlan savedPurchasePlan = purchasePlanService.insert(purchasePlan);
		resultMap.put("status", "success");
		resultMap.put("result", savedPurchasePlan);
		return resultMap;
	}

	/**
	 * 新增purchasePlan下的area api
	 * 
	 * @param purchasePlanIdStr
	 * @param areaIdStr
	 * @param principal
	 * @return {@link String} 狀態
	 */
	@PostMapping("/product/purchasePlan/area/api")
	@ResponseBody
	public String addPurchasePlanAreaApi(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("areaId") String areaIdStr, Principal principal) {
		if ( principal == null ) {
			return "請先登入";
		}
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		Integer areaId = formatValidAndTransform.stringToInteger(areaIdStr).get("value");
		if (purchasePlanId == null || areaId == null) {
			return "輸入有誤";
		}
		Area area = areaService.findById(areaId);
		if (area == null) {
			return "找不到指定地區";
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			return "找不到指定購買方案";
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getStatus() != -1 && userProduct.getPId() == purchasePlan.getProduct().getPId()) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "要修改的購買方案非附屬於您的專案或專案已註銷";
		}
		Set<Area> areaNoSet = purchasePlan.getAreaNo();
		for (Area areaNo : areaNoSet) {
			if (areaNo.getAreaId() == areaId) {
				return "指定區域已存在該購買方案內，不須新增。";
			}
		}
		areaNoSet.add(area);
		purchasePlan.setAreaNo(areaNoSet);
		PurchasePlan savedPurchasePlan = purchasePlanService.insert(purchasePlan);
		if (savedPurchasePlan == null) {
			return "儲存失敗(傳入值為null)";
		}
		return "儲存成功";
	}

	/**
	 * 新增purchasePlan下的area form
	 * @param purchasePlanIdStr
	 * @param areaIdStr
	 * @param principal
	 * @return 成功會跳回原編輯頁面
	 */
	@PostMapping("/product/purchasePlan/area/form")
	public String addPurchasePlanAreaForm(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("areaId") String areaIdStr, Principal principal) {
		if ( principal == null ) {
//			return "請先登入";
			return "redirect:/public/signIn";
		}
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		Integer areaId = formatValidAndTransform.stringToInteger(areaIdStr).get("value");
		// 確認購買方案存在
		PurchasePlan inputPurchasePlan = purchasePlanService.findById(purchasePlanId);
		if (inputPurchasePlan == null) {
			return "redirect:/product";
		}
		if (purchasePlanId == null || areaId == null) {
//			return "輸入有誤";
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
		}
		Area area = areaService.findById(areaId);
		if (area == null) {
//			return "找不到指定地區";
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
//			return "找不到指定購買方案";
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getStatus() != -1 && userProduct.getPId() == purchasePlan.getProduct().getPId()) {
				valid = true;
				break;
			}
		}
		if (!valid) {
//			return "要修改的購買方案非附屬於您的專案或專案已註銷";
			return "redirect:/product";
		}
		Set<Area> areaNoSet = purchasePlan.getAreaNo();
		for (Area areaNo : areaNoSet) {
			if (areaNo.getAreaId() == areaId) {
//				return "指定區域已存在該購買方案內，不須新增。";
				return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
			}
		}
		areaNoSet.add(area);
		purchasePlan.setAreaNo(areaNoSet);
		PurchasePlan savedPurchasePlan = purchasePlanService.insert(purchasePlan);
		if (savedPurchasePlan == null) {
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
//			return "儲存失敗(傳入值為null)";
		}
//		return "儲存成功";
		return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
	}

	/**
	 * 刪除purchasePlan下的area api
	 * 
	 * @param purchasePlanIdStr
	 * @param areaIdStr
	 * @param principal
	 * @return {@link String} 狀態
	 */
	@DeleteMapping("/product/purchasePlan/area/api")
	@ResponseBody
	public String deletePurchasePlanAreaApi(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("areaId") String areaIdStr, Principal principal) {
		if ( principal == null ) {
			return "請先登入";
		}
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		Integer areaId = formatValidAndTransform.stringToInteger(areaIdStr).get("value");
		if (purchasePlanId == null || areaId == null) {
			return "輸入有誤";
		}
		Area area = areaService.findById(areaId);
		if (area == null) {
			return "找不到指定地區";
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			return "找不到指定購買方案";
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getStatus() != -1 && userProduct.getPId() == purchasePlan.getProduct().getPId()) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "要修改的購買方案非附屬於您的專案或專案已註銷";
		}
		Boolean areaExists = false;
		Set<Area> areaNoSet = purchasePlan.getAreaNo();
		for (Area areaNo : areaNoSet) {
			if (areaNo.getAreaId() == areaId) {
				areaExists = true;
			}
		}
		if (!areaExists) {
			return "指定區域不存在該購買方案內，不須刪除。";
		}
		areaNoSet.remove(area);
		purchasePlan.setAreaNo(areaNoSet);
		PurchasePlan savedPurchasePlan = purchasePlanService.insert(purchasePlan);
		if (savedPurchasePlan == null) {
			return "刪除地區失敗(傳入值為null)";
		}
		return "刪除地區成功";
	}

	/**
	 * 修改purchasePlan api
	 * 
	 * @param purchasePlanId
	 * @param title
	 * @param intro
	 * @param price
	 * @param inputMinOrderAmount
	 * @param inputMaxOrderAmount
	 * @param shipmentDateStr
	 * @param principal
	 * @return {@link String} 狀態
	 */
	@PutMapping("/product/purchasePlan/api")
	@ResponseBody
	public String editPurchasePlanApi(@RequestParam("purchasePlanId") Integer purchasePlanId,
			@RequestParam("title") String title, @RequestParam("intro") String intro,
			@RequestParam("price") Integer price, @RequestParam("minOrderAmount") String inputMinOrderAmount,
			@RequestParam("maxOrderAmount") String inputMaxOrderAmount,
			@RequestParam("shipmentDate") String shipmentDateStr, Principal principal) {
		if ( principal == null ) {
			return "請先登入";
		}
		if (purchasePlanId == null) {
			return "未指定要修改的purchasePlan的purchasePlanId";
		}
		if (title == null || title.isBlank() || price == null || shipmentDateStr == null || shipmentDateStr.isEmpty()) {
			return "指定欄位不可為空";
		}
		title = title.trim();
		if (intro == null || intro.isBlank()) {
			intro = null;
		} else {
			intro = intro.trim();
		}
		Integer minOrderAmount = null;
		Integer maxOrderAmount = null;
		try {
			if (inputMinOrderAmount != null) {
				String minOrderAmountStr = inputMinOrderAmount.trim();
				if (minOrderAmountStr.isBlank()) {
					minOrderAmount = null;
				} else {
					minOrderAmount = Integer.valueOf(inputMinOrderAmount);
				}
			}
			if (inputMaxOrderAmount != null) {
				String maxOrderAmountStr = inputMaxOrderAmount.trim();
				if (maxOrderAmountStr.isBlank()) {
					maxOrderAmount = null;
				} else {
					maxOrderAmount = Integer.valueOf(inputMaxOrderAmount);
				}
			}
		} catch (Exception e) {
			return "最大最小允許購買量輸入格式有誤";
		}
		if (minOrderAmount != null && maxOrderAmount != null) {
			if (maxOrderAmount < minOrderAmount) {
				return "最小所需銷售量不可大於最大限制購買量";
			} else if (maxOrderAmount <= 0 || minOrderAmount <= 0) {
				return "最小所需銷售量及最大限制購買量需大於0";
			}
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date shipmentDate = null;
		try {
			shipmentDate = dateFormatter.parse(shipmentDateStr);
		} catch (ParseException e) {
			return "日期格式錯誤";
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			return "指定專案不存在";
		}
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == purchasePlan.getProduct().getPId() && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "非您的專案或專案已註銷";
		}
		purchasePlan.setTitle(title);
		purchasePlan.setIntro(intro);
		purchasePlan.setPrice(price);
		purchasePlan.setMinOrderAmount(minOrderAmount);
		purchasePlan.setMaxOrderAmount(maxOrderAmount);
		purchasePlan.setShipmentDate(shipmentDate);
		purchasePlanService.insert(purchasePlan);
		return "修改成功";
	}
	
	/**
	 * 修改purchasePlan form
	 * @param productIdStr
	 * @param purchasePlanIdStr
	 * @param title
	 * @param intro
	 * @param priceStr
	 * @param inputMinOrderAmount
	 * @param inputMaxOrderAmount
	 * @param shipmentDateStr
	 * @param principal
	 * @return
	 */
	@PutMapping("/product/purchasePlan/form")
	public String editPurchasePlanForm(@RequestParam("productId") String productIdStr, @RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("title") String title, @RequestParam("intro") String intro,
			@RequestParam("price") String priceStr, @RequestParam("minOrderAmount") String inputMinOrderAmount,
			@RequestParam("maxOrderAmount") String inputMaxOrderAmount,
			@RequestParam("shipmentDate") String shipmentDateStr, Principal principal) {
		if ( principal == null ) {
//			return "請先登入";
			return "redirect:/public/signIn";
		}
		
		Integer productId = formatValidAndTransform.stringToInteger(productIdStr).get("value");
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		Integer price = formatValidAndTransform.stringToInteger(priceStr).get("value");
		Integer minOrderAmount = formatValidAndTransform.stringToInteger(inputMinOrderAmount).get("value");
		Integer maxOrderAmount = formatValidAndTransform.stringToInteger(inputMaxOrderAmount).get("value");
		
		Product product = productService.findProductById(productId);
		
		if (purchasePlanId == null) {
			if ( product == null ) {
				return "redirect:/product";
			}
//			return "未指定要修改的purchasePlan的purchasePlanId";
			return "redirect:/product/edit/" + productId; 
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
//			return "指定購買方案不存在";
			if ( product == null ) {
				return "redirect:/product";
			}
//			return "未指定要修改的purchasePlan的purchasePlanId";
			return "redirect:/product/edit/" + productId; 
		}
		Integer purchasePlanProductId = purchasePlan.getProduct().getPId();
		if (title == null || title.isBlank() || price == null || shipmentDateStr == null || shipmentDateStr.isEmpty()) {
//			return "指定欄位不可為空";
			return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
		}
		title = title.trim();
		if (intro == null || intro.isBlank()) {
			intro = null;
		} else {
			intro = intro.trim();
		}
		
//			return "最大最小允許購買量輸入格式有誤";
		if (minOrderAmount == null ) { // 轉出來是null，但有可能原本就是null
			// 如果原本就是null
			if ( inputMinOrderAmount == null || inputMinOrderAmount.isBlank() ) {
				;
			} else { // 真的輸入有誤
				return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
			}
		}
		if (maxOrderAmount == null ) { // 轉出來是null，但有可能原本就是null
			// 如果原本就是null
			if ( inputMaxOrderAmount == null || inputMaxOrderAmount.isBlank() ) {
				;
			} else { // 真的輸入有誤
				return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
			}
		}
		// minOrderAmount >= 0
		if (minOrderAmount != null && minOrderAmount <= 0) {
//			return "最小所需銷售量及最大限制購買量需大於0";
			return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
		}
		// maxOrderAmount >= 0
		if (maxOrderAmount != null && maxOrderAmount <= 0) {
//			return "最小所需銷售量及最大限制購買量需大於0";
			return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
		}
//		minOrderAmount < maxOrderAmount  
		if (minOrderAmount != null && maxOrderAmount != null) {
			if (maxOrderAmount < minOrderAmount) {
//				return "最小所需銷售量不可大於最大限制購買量";
				return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
			}
		}
		SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
		Date shipmentDate = null;
		shipmentDateStr = shipmentDateStr.trim();
		try {
			shipmentDate = dateFormatter.parse(shipmentDateStr);
		} catch (ParseException e) {
//			return "日期格式錯誤";
			return "redirect:/product/" + purchasePlanProductId + "/purchasePlan/edit/" + purchasePlan.getPurchasePlanId();
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == purchasePlanProductId && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
//			return "非您的專案或專案已註銷";
			return "redirect:/product";
		}
		purchasePlan.setTitle(title);
		purchasePlan.setIntro(intro);
		purchasePlan.setPrice(price);
		purchasePlan.setMinOrderAmount(minOrderAmount);
		purchasePlan.setMaxOrderAmount(maxOrderAmount);
		purchasePlan.setShipmentDate(shipmentDate);
		purchasePlanService.insert(purchasePlan);
//		return "修改成功";
		return "redirect:/editProduct/" + purchasePlanProductId; 
	}

	/**
	 * 刪除purchasePlan api
	 * 
	 * @param purchasePlanId
	 * @param principal
	 * @return {@link String} 狀態
	 */
	@DeleteMapping("/product/purchasePlan/api")
	@ResponseBody
	public String deletePurchasePlanApi(@RequestParam("purchasePlanId") Integer purchasePlanId, Principal principal) {
		if ( principal == null ) {
			return "請先登入";
		}
		if (purchasePlanId == null) {
			return "未指定要刪除的purchasePlan的purchasePlanId";
		}
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			return "指定方案不存在";
		}
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == purchasePlan.getProduct().getPId() && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "非您的專案或專案已註銷";
		}
		try {
			if (purchasePlanService.delete(purchasePlan) != 0) { // 執行刪除
				return "刪除失敗";
			}
		} catch (Exception e) {
			System.out.println("刪除purchasePlan出現Exception: " + e);
			return "刪除失敗(請檢查是否已有人下訂或有其他相依表格列)";
		}
		return "刪除成功";
	}

	/**
	 * 依productId取得所有purchasePlan json api
	 * 
	 * @param productIdStr
	 * @return Map<狀態, 要的資料>
	 */
	@GetMapping("/public/product/{productId}/purchasePlan/api")
	@ResponseBody
//	Map<status, result>
	public Map<String, Object> getAllPurchasePlansJsonByProductIdApi(@PathVariable("productId") String productIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer productId = formatValidAndTransform.stringToInteger(productIdStr).get("value");
		if (productId == null) {
			resultMap.put("status", "輸入路徑參數須為數字");
			resultMap.put("result", null);
			return resultMap;
		}
		Product product = productService.findProductById(productId);
		if (product == null || product.getStatus() == -1) {
			resultMap.put("status", "指定專案不存在或已註銷");
			resultMap.put("result", null);
			return resultMap;
		}
		Set<PurchasePlan> purchasePlanSet = product.getPurchasePlan();
		if (purchasePlanSet.isEmpty()) {
			resultMap.put("status", "empty");
			resultMap.put("result", null);
			return resultMap;
		}
		resultMap.put("status", "success");
		resultMap.put("result", purchasePlanSet);
		return resultMap;
	}

	/**
	 * 新增/修改PurchasePlanItem api
	 * 
	 * @param purchasePlanIdStr
	 * @param itemIdStr
	 * @param itemAmountStr
	 * @param principal
	 * @return
	 */
	// 含完整大致建立api流程
	@PostMapping("/product/purchasePlan/purchasePlanItem/api")
	@ResponseBody
	public String addPurchasePlanItemApi(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("itemId") String itemIdStr, @RequestParam("amount") String itemAmountStr,
			Principal principal) {
		// 先登入
		if ( principal == null ) {
			return "請先登入";
		}
		// 不可為空
		if (purchasePlanIdStr == null || purchasePlanIdStr.isBlank() || itemIdStr == null || itemIdStr.isBlank()
				|| itemAmountStr == null || itemAmountStr.isBlank()) {
			return "不可有欄位為空";
		}
		// 去除空白
		purchasePlanIdStr = purchasePlanIdStr.trim();
		itemIdStr = itemIdStr.trim();
		itemAmountStr = itemAmountStr.trim();
		// 轉成Integer
		Integer purchasePlanId = null;
		Integer itemId = null;
		Integer itemAmount = null;
		try {
			purchasePlanId = Integer.valueOf(purchasePlanIdStr);
			itemId = Integer.valueOf(itemIdStr);
			itemAmount = Integer.valueOf(itemAmountStr);
		} catch (NumberFormatException e) {
			System.out.println("addPurchasePlanItemApi: " + e);
			return "所有欄位均須是數字";
		}
		if (itemAmount <= 0) {
			return "數量需大於0";
		}
		// 確認購買方案存在
		PurchasePlan inputPurchasePlan = purchasePlanService.findById(purchasePlanId);
		if (inputPurchasePlan == null) {
			return "指定的購買方案不存在";
		}
		// 確認指定item存在
		Item inputItem = itemService.findById(itemId);
		if (inputItem == null) {
			return "指定的品項不存在";
		}
		// 確認要修改的購買方案是附屬於該user的專案
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == inputPurchasePlan.getProduct().getPId() && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "要修改的購買方案非附屬於您的專案或專案已註銷";
		}
		// 製作要存入的物件
		PurchasePlanItemsPK purchasePlanItemsPK = new PurchasePlanItemsPK(inputItem, inputPurchasePlan);
		PurchasePlanItems purchasePlanItems = new PurchasePlanItems(purchasePlanItemsPK, itemAmount);
		// 確認資料庫是否已存在指定物件
		Boolean isEdit = false;
		if (purchasePlanItemsService.findById(purchasePlanItemsPK) != null) {
			isEdit = true;
		}
		if (purchasePlanItemsService.insert(purchasePlanItems) == null) { // 存入資料庫
			return "存入至資料庫時發生錯誤";
		}
		if (isEdit) {
			return "修改成功";
		}
		return "新增成功";
	}

	/**
	 * 新增/修改PurchasePlanItem form
	 * 
	 * @param purchasePlanIdStr
	 * @param itemIdStr
	 * @param itemAmountStr
	 * @param principal
	 * @return 成功會回編輯頁面
	 */
	@PostMapping("/product/purchasePlan/purchasePlanItem/form")
	public String addPurchasePlanItemForm(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("itemId") String itemIdStr, @RequestParam("amount") String itemAmountStr,
			Principal principal) {
		// 先登入
		if ( principal == null ) {
			return "redirect:/public/signIn";
//			return "請先登入";
		}
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		Integer itemId = formatValidAndTransform.stringToInteger(itemIdStr).get("value");
		Integer itemAmount = formatValidAndTransform.stringToInteger(itemAmountStr).get("value");
		if (purchasePlanId == null) {
			return "redirect:/product";
		}
		// 確認購買方案存在
		PurchasePlan inputPurchasePlan = purchasePlanService.findById(purchasePlanId);
		if (inputPurchasePlan == null) {
			return "redirect:/product";
		}
		if (itemId == null || itemAmount == null) {
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
		}

		if (itemAmount <= 0) {
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
//			return "數量需大於0";
		}
		// 確認指定item存在
		Item inputItem = itemService.findById(itemId);
		if (inputItem == null) {
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
//			return "指定的品項不存在";
		}
		// 確認要修改的購買方案是附屬於該user的專案
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == inputPurchasePlan.getProduct().getPId() && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "redirect:/product";
//			return "要修改的購買方案非附屬於您的專案或專案已註銷";
		}
		// 製作要存入的物件
		PurchasePlanItemsPK purchasePlanItemsPK = new PurchasePlanItemsPK(inputItem, inputPurchasePlan);
		PurchasePlanItems purchasePlanItems = new PurchasePlanItems(purchasePlanItemsPK, itemAmount);
		// 確認資料庫是否已存在指定物件
		Boolean isEdit = false;
		if (purchasePlanItemsService.findById(purchasePlanItemsPK) != null) {
			isEdit = true;
		}
		if (purchasePlanItemsService.insert(purchasePlanItems) == null) { // 存入資料庫
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
//			return "存入至資料庫時發生錯誤";
		}
		if (isEdit) {
			return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
//			return "修改成功";
		}
		return "redirect:/editProduct/" + inputPurchasePlan.getProduct().getPId();
//		return "新增成功";
	}

	/**
	 * 刪除purchasePlan下的PurchasePlanItem api
	 * 
	 * @param purchasePlanIdStr
	 * @param itemIdStr
	 * @param principal
	 * @return {@link String} 狀態
	 */
	@DeleteMapping("/product/purchasePlan/purchasePlanItem/api")
	@ResponseBody
	public String deletePurchasePlanItemApi(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("itemId") String itemIdStr, Principal principal) {
		// 先登入
		if ( principal == null ) {
			return "請先登入";
		}
		// 不可為空
		if (purchasePlanIdStr == null || purchasePlanIdStr.isBlank() || itemIdStr == null || itemIdStr.isBlank()) {
			return "不可有欄位為空";
		}
		// 去除空白
		purchasePlanIdStr = purchasePlanIdStr.trim();
		itemIdStr = itemIdStr.trim();
		// 轉成Integer
		Integer purchasePlanId = null;
		Integer itemId = null;
		try {
			purchasePlanId = Integer.valueOf(purchasePlanIdStr);
			itemId = Integer.valueOf(itemIdStr);
		} catch (NumberFormatException e) {
			System.out.println("addPurchasePlanItemApi: " + e);
			return "所有欄位均須是數字";
		}
		// 確認購買方案存在
		PurchasePlan inputPurchasePlan = purchasePlanService.findById(purchasePlanId);
		if (inputPurchasePlan == null) {
			return "指定的購買方案不存在";
		}
		// 確認指定item存在
		Item inputItem = itemService.findById(itemId);
		if (inputItem == null) {
			return "指定的品項不存在";
		}
		// 確認要修改的購買方案是附屬於該user的專案
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == inputPurchasePlan.getProduct().getPId() && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "要修改的購買方案非附屬於您的專案或專案已註銷";
		}
		// 製作pk物件
		PurchasePlanItemsPK purchasePlanItemsPK = new PurchasePlanItemsPK(inputItem, inputPurchasePlan);
		// 確認資料庫是否已存在指定物件
		PurchasePlanItems purchasePlanItems = purchasePlanItemsService.findById(purchasePlanItemsPK);
		if (purchasePlanItems == null) {
			return "找不到指定的purchasePlanItem";
		}
		if (purchasePlanItemsService.deleteByEntity(purchasePlanItems) == false) { // 存入資料庫
			return "從資料庫刪除時發生錯誤";
		}
		return "刪除成功";
	}

	/**
	 * 刪除purchasePlan下的PurchasePlanItem form
	 * 
	 * @param purchasePlanIdStr
	 * @param itemIdStr
	 * @param principal
	 * @return 若成功redirect至原編輯頁面
	 */
	@DeleteMapping("/product/purchasePlan/purchasePlanItem/form")
	public String deletePurchasePlanItemForm(@RequestParam("purchasePlanId") String purchasePlanIdStr,
			@RequestParam("itemId") String itemIdStr, Principal principal) {
		// 先登入
		if ( principal == null ) {
//			return "請先登入";
			return "redirect:/public/signIn";
		}
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		Integer itemId = formatValidAndTransform.stringToInteger(itemIdStr).get("value");
		// 無指定purchasePlan或格式有誤
		if (purchasePlanId == null) {
			return "redirect:/product";
		}
		// 確認購買方案存在
		PurchasePlan inputPurchasePlan = purchasePlanService.findById(purchasePlanId);
		if (inputPurchasePlan == null) {
			return "redirect:/product";
//			return "指定的購買方案不存在";
		}
		Integer productId = inputPurchasePlan.getProduct().getPId();
		if (itemId == null) {
			return "redirect:/editProduct/" + productId;
		}
		// 確認指定item存在
		Item inputItem = itemService.findById(itemId);
		if (inputItem == null) {
//			return "指定的品項不存在";
			return "redirect:/editProduct/" + productId;
		}
		// 確認要修改的購買方案是附屬於該user的專案
		User user = userService.principleToUser(principal);
//		User user = userService.findById(1);
		boolean valid = false;
		Set<Product> userProducts = user.getProduct();
		for (Product userProduct : userProducts) {
			if (userProduct.getPId() == inputPurchasePlan.getProduct().getPId() && userProduct.getStatus() != -1) {
				valid = true;
				break;
			}
		}
		if (!valid) {
//			return "要修改的購買方案非附屬於您的專案或專案已註銷";
			return "redirect:/product";
		}
		// 製作pk物件
		PurchasePlanItemsPK purchasePlanItemsPK = new PurchasePlanItemsPK(inputItem, inputPurchasePlan);
		// 確認資料庫是否已存在指定物件
		PurchasePlanItems purchasePlanItems = purchasePlanItemsService.findById(purchasePlanItemsPK);
		if (purchasePlanItems == null) {
//			return "找不到指定的purchasePlanItem";
			return "redirect:/editProduct/" + productId;
		}
		if (purchasePlanItemsService.deleteByEntity(purchasePlanItems) == false) { // 存入資料庫
//			return "從資料庫刪除時發生錯誤";
			return "redirect:/editProduct/" + productId;
		}
//		return "刪除成功";
		return "redirect:/editProduct/" + productId;
	}

	/**
	 * 依purchasePlanId取得其下所有purchasePlanItems json api
	 * 
	 * @param purchasePlanIdStr
	 * @return Map<狀態, 要的資料>
	 */
	@GetMapping("/public/product/purchasePlan/{purchasePlanId}/purchasePlanItem/api")
	@ResponseBody
	public Map<String, Object> getAllPurchasePlanItemsbyPurchasePlanIdApi(
			@PathVariable("purchasePlanId") String purchasePlanIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Set<PurchasePlanItemsJsonDTO> resultData = new HashSet<>();
		// 轉成Integer
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		if (purchasePlanId == null) {
			resultMap.put("status", "輸入路徑參數須為數字");
			resultMap.put("result", null);
			return resultMap;
		}
		// 確認購買方案存在
		PurchasePlan inputPurchasePlan = purchasePlanService.findById(purchasePlanId);
		if (inputPurchasePlan == null) {
			resultMap.put("status", "指定的購買方案不存在");
			resultMap.put("result", null);
			return resultMap;
		}
		Set<PurchasePlanItems> purchasePlanItemsSet = inputPurchasePlan.getPurchasePlanItems();
		if (purchasePlanItemsSet.isEmpty()) {
			resultMap.put("status", "empty");
			resultMap.put("result", null);
			return resultMap;
		}
		// 從purchasePlan取出資料
		for (PurchasePlanItems purchasePlanItem : purchasePlanItemsSet) {
			PurchasePlanItemsJsonDTO purchasePlanItemsJsonDTO = new PurchasePlanItemsJsonDTO();
			purchasePlanItemsJsonDTO.setFk_itemId(purchasePlanItem.getPurchasePlanItemsPK().getItemIdPK().getItemId());
			purchasePlanItemsJsonDTO
					.setFk_itemName(purchasePlanItem.getPurchasePlanItemsPK().getItemIdPK().getItemName());
			purchasePlanItemsJsonDTO.setAmount(purchasePlanItem.getAmount());
			purchasePlanItemsJsonDTO.setFk_purchasePlanId(purchasePlanId);
			resultData.add(purchasePlanItemsJsonDTO);
		}

		resultMap.put("status", "success");
		resultMap.put("result", resultData);
		return resultMap;
	}

	/**
	 * 依purchasePlanId取得其下所有Area json api
	 * 
	 * @param purchasePlanIdStr
	 * @return Map<狀態, 要的資料>
	 */
	@GetMapping("/public/product/purchasePlan/{purchasePlanId}/area/api")
	@ResponseBody
//	Map<status, result>
//	Map<status, string>
//	Map<result, jsondata>
	public Map<String, Object> getAllAreasJsonByPurchasePlanIdApi(
			@PathVariable("purchasePlanId") String purchasePlanIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		if (purchasePlanId == null) {
			resultMap.put("status", "輸入路徑參數須為數字");
			resultMap.put("result", null);
			return resultMap;
		}
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null || purchasePlan.getProduct().getStatus() == -1) {
			resultMap.put("status", "指定購買方案不存在或所附屬的專案已註銷");
			resultMap.put("result", null);
			return resultMap;
		}
		Set<Area> areaSet = purchasePlan.getAreaNo();
		if (areaSet.isEmpty()) {
			resultMap.put("status", "empty");
			resultMap.put("result", null);
			return resultMap;
		}
		resultMap.put("status", "success");
		resultMap.put("result", areaSet);
		return resultMap;
	}
	
	/**
	 * 依purchasePlanId取得purchasePlan json api
	 * @param purchasePlanIdStr
	 * @return
	 */
	@GetMapping("/public/product/purchasePlan/{purchasePlanId}/api")
	@ResponseBody
	public Map<String, Object> getPurchasePlanByPurchasePlanIdApi(@PathVariable("purchasePlanId") String purchasePlanIdStr) {
		Map<String, Object> resultMap = new HashMap<>();
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		
		if ( purchasePlanId == null ) {
			resultMap.put("status", "輸入值有誤");
			resultMap.put("result", null);
			return resultMap;
		}
		
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		if (purchasePlan == null) {
			resultMap.put("status", "找不到指定purchasePlan");
			resultMap.put("result", null);
			return resultMap;
		}
		
		resultMap.put("status", "success");
		resultMap.put("result", purchasePlan);
		return resultMap;
		
	}
	
	//??TODO
	@GetMapping("/product/{productId}/purchasePlan/edit/{purchasePlanId}")
	public String redirectToEditPurchasePlanSelfPage(@PathVariable("purchasePlanId") String purchasePlanIdStr, @PathVariable("productId") String productIdStr, Model model, Principal principal) throws IOException {
//		========= new page =========
		if ( principal == null ) {
			return "redirect:/public/signIn";
		}
		Integer productId = formatValidAndTransform.stringToInteger(productIdStr).get("value");
		Integer purchasePlanId = formatValidAndTransform.stringToInteger(purchasePlanIdStr).get("value");
		// 輸入有誤，回專案編輯頁
		if (purchasePlanId == null) {
			// 若無法辨識productId，回專案列表
			if (productId == null) {
				return "redirect:/product";
			}
			return "redirect:/editProduct/" + productId;
		}
		
		// 確認purchasePlan是否存在
		PurchasePlan purchasePlan = purchasePlanService.findById(purchasePlanId);
		// 若找不到頁面purchasePlan，重定向回專案列表
		if (purchasePlan == null) {
			return "redirect:/editProduct/" + productId;
		}
		
		Integer purchasePlanProductId = purchasePlan.getProduct().getPId();
		// 確認user是否為該product擁有者
		User user = userService.principleToUser(principal);
		Set<Product> userProducts = user.getProduct();
		boolean valid = false;
		for ( Product userProduct:userProducts ) {
			if ( userProduct.getPId() == purchasePlanProductId ) {
				valid = true;
				break;
			}
		}
		if (!valid) {
			return "redirect:/product";
		}
		
		model.addAttribute("purchasePlan", purchasePlan);
		model.addAttribute("ProductId", purchasePlanProductId);
		
		return "product/purchasePlanSelfEdit"; // 導到編輯用預覽畫面jsp
	}
}
