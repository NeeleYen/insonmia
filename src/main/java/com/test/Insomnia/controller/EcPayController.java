package com.test.Insomnia.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.test.Insomnia.Service.AllInOneService;
import com.test.Insomnia.Service.OrderService;
import com.test.Insomnia.model.Order;

import ecpay.payment.integration.AllInOne;
import ecpay.payment.integration.domain.AioCheckOutALL;




@Controller
public class EcPayController {

	@Autowired
	private static AllInOne all;
	@Autowired
	private AllInOneService allInOneService; 
	@Autowired
	private OrderService orderService;

	//本控制器用以在接到用戶端(瀏覽器)送出的結帳請求時，利用綠界API產生一個只具有<form>元素的頁面來回應給用戶端，
	//用戶端接收該頁面後將自動展開後續的結帳作業。
	//付款
	@ResponseBody
	@PostMapping(value = "/public/product/pay", produces = "text/html;charset=utf-8")
	public String pay(HttpServletRequest request, HttpServletResponse response) {
		String aioCheckOutALL = getAioCheckOutALL(request);
		System.out.println("有進第一個controller");
		return aioCheckOutALL;
	}
	
	//本控制器用以接收付款者在付款成功後，從用戶端回傳的付款結果。
	@ResponseBody
	@GetMapping(value = "/public/product/payResult", produces = "text/html;charset=utf-8")
	public String payResult() {
		System.out.println("有進第二個controller");
		return "<h1>付款成功</h1>";
	}
	
//	public String payResult2(@RequestBody Map<String, String> reqMap) {	//可以改嗎？
//		Hashtable<String, String> result = new Hashtable<String, String>(0);
//		Set<String> keySet = reqMap.keySet();
//		for (String paramN : keySet) {
//			String paramName = paramN;
//			String paramValue = reqMap.get(paramN);
//			result.put(paramName, paramValue);
//		}
//		boolean checkStatus = all.compareCheckMacValue(result);
//		if ("1".equals(result.get("RtnCode")) && checkStatus==true ) {
//			
//		}
//	}
//	@ResponseBody
	@PostMapping(value = "/public/product/payResult", produces = "text/html;charset=utf-8")
	public String payResult2(HttpServletRequest request) {	//可以改嗎？
		System.out.println("有進第三個controller");
		
		Hashtable<String, String> hashtable = new Hashtable<String, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String paramName = parameterNames.nextElement();	//找key
			String paramValue = request.getParameter(paramName);	//找value
			hashtable.put(paramName, paramValue);
		}
		
		all = allInOneService.newAllInOne();
		boolean check = all.compareCheckMacValue(hashtable);
		if("1".equals(hashtable.get("RtnCode")) && check==true) {	//綠界那邊狀態為已付款
//			Integer i = Integer.parseInt(hashtable.get("orderId"));						
			
			String strId = hashtable.get("MerchantTradeNo");
			StringBuilder sb = new StringBuilder();
			sb.append(strId);			
			String strShortId = sb.delete(0, 15).toString();
			System.out.println("訂單短編號："+strShortId);
			Integer i = Integer.parseInt(strShortId);
			orderService.setPaymentStatus(i, 1);
//			Order findById = orderService.findById(i);
//			findById.setPaymentStatus(1);	//先設 1 為已付款
			
			
			return "redirect:/public/product/paySuccess";
		} else {
			return "redirect:/public/product/payFail";
		}
	}
	
//	跳頁到成功頁
	@GetMapping("/public/product/paySuccess")
	public String paySuccess() {
		return "/product/paySuccess";
	}
//	跳頁到失敗頁
	@GetMapping("/public/product/payFail")
	public String payFail() {
		return "/product/payFail";
	}

	//藉由AioCheckOutALL產生類似json的字串 (在AioCheckOutALL.java中)
	private String getAioCheckOutALL(HttpServletRequest request) {
		AioCheckOutALL obj = new AioCheckOutALL();
		Date date = new Date();
		String simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(date);
		String random = genMerchantTradeNo();
		String strMerchantTradeNo =  random + request.getParameter("orderId");
		obj.setMerchantTradeNo(strMerchantTradeNo);
		obj.setMerchantTradeDate(simpleDateFormat);
		
		obj.setTotalAmount(request.getParameter("TotalAmount"));
		obj.setTradeDesc(request.getParameter("TradeDesc"));
		obj.setItemName(request.getParameter("ItemName"));
		obj.setNeedExtraPaidInfo("N");
		//ResultURL先不放
		obj.setReturnURL("http://localhost:8888/my-insomnia/public/product/payResul");
		obj.setOrderResultURL("http://localhost:8888/my-insomnia/public/product/payResult");
		
		all = allInOneService.newAllInOne();
		String aioCheckOut = all.aioCheckOut(obj, null);
		return aioCheckOut;
	}
	
	//生成英文大小寫、數字隨機組合 for MerchantTradeNo 長度15
	//https://gist.github.com/HabaCo/b93fc556a6999de31c13
	private String genMerchantTradeNo() {
		Integer[] arr = new Integer[62];
		String merchantTradeNo = "";
	//	char[] c = new char[];
		/*ASCII：
		 * 數字0~9：48~57 10
		 * 大寫英文：65~90 26
		 * 小寫英文：97~122 26
		 * 
		 * 不带参数的nextInt()会生成所有有效的整数（包含正数，负数，0）
		 * 带参的nextInt(int x)则会生成一个范围在0~x（不包含X）内的任意正整数*/
		for (int i = 0; i < arr.length; i++) {
			if (i<10) {
				arr[i] = 48 + i;
			} else if (i<36) {
				arr[i] = 55 + i;
			} else {
				arr[i] = 61 + i;
			}
		}
		
		int arrSize = 15;
		int[] tradeNo = new int[arrSize];
		Random random = new Random();
		for (int i = 0; i < tradeNo.length; i++) {
			tradeNo[i] = arr[random.nextInt(62)];	//在上面的數字&大小寫ASCII碼目錄中隨機選一個
		}
		for (int i = 0; i < tradeNo.length; i++) {
			  merchantTradeNo += (char)tradeNo[i];
		}
		return merchantTradeNo;
	}

}