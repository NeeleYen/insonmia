package com.test.Insomnia.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class FormatValidAndTransform {
	// wrote by james, for controller use
	/**
	 * 轉換字串為數字，可用於controller @RequestParam
	 * @param inputStr
	 * @return {@link Map}
	 * 	回傳Map<String, Integer>，有兩個key-value pairs。
	 * 	當<"status", 0>時，代表成功轉換，<"value", 轉換成功的Integer>。
	 * 	當<"status", 1>時，代表輸入字串為null，<"value", null>。
	 * 	當<"status", 2>時，代表輸入字串只有空白或換行，<"value", null>。
	 * 	當<"status", 3>時，代表輸入字串非數字格式、無法轉為數字，<"value", null>。
	 */
	public Map<String, Integer> stringToInteger(String inputStr) {
//		Map<status, Result> 
		Map<String, Integer> statusAndResultMap = new HashMap<>();
		if (inputStr == null ) {
			statusAndResultMap.put("status", 1);
			statusAndResultMap.put("value", null);
			return statusAndResultMap;
		}
		if (inputStr.isBlank()) {
			statusAndResultMap.put("status", 2);
			statusAndResultMap.put("value", null);
			return statusAndResultMap;
		}
		inputStr = inputStr.trim();
		Integer inputInteger = null;
		try {
			inputInteger = Integer.valueOf(inputStr);
		} catch (NumberFormatException e) {
			System.out.println("addPurchasePlanItemApi: " + e);
			statusAndResultMap.put("status", 3);
			statusAndResultMap.put("value", null);
			return statusAndResultMap;
		}
		statusAndResultMap.put("status", 0);
		statusAndResultMap.put("value", inputInteger);
		return statusAndResultMap;
	}
	
	public Map<String, Long> stringToLong(String inputStr) {
		Map<String, Long> statusAndResultMap = new HashMap<>();
		if (inputStr == null ) {
			statusAndResultMap.put("status", 1L);
			statusAndResultMap.put("value", null);
			return statusAndResultMap;
		}
		if (inputStr.isBlank()) {
			statusAndResultMap.put("status", 2L);
			statusAndResultMap.put("value", null);
			return statusAndResultMap;
		}
		inputStr = inputStr.trim();
		Long inputInteger = null;
		try {
			inputInteger = Long.valueOf(inputStr);
		} catch (NumberFormatException e) {
			System.out.println("addPurchasePlanItemApi: " + e);
			statusAndResultMap.put("status", 3L);
			statusAndResultMap.put("value", null);
			return statusAndResultMap;
		}
		statusAndResultMap.put("status", 0L);
		statusAndResultMap.put("value", inputInteger);
		return statusAndResultMap;
	}
	
	
}
