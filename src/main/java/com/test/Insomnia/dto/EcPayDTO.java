package com.test.Insomnia.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EcPayDTO {
	
	/* AioCheckOutALL中列舉的需要項目：
	 * 綠界提供：
	 * MerchantID(設定檔)
	 * 
	 * controller要加的：
	 * MerchantTradeNo
	 * MerchantTradeDate
	 * 
	 * */

	@JsonProperty("MerchantTradeNo")
	private String merchantTradeNo;
	
	@JsonProperty("TotalAmount")	//交易金額
	private String totalAmount;
	
	@JsonProperty("TradeDesc")	//交易描述
	private String tradeDesc;
	
	@JsonProperty("ItemName")	//商品名稱
	private String itemName;

	public EcPayDTO() {
		super();
	}

	public String getMerchantTradeNo() {
		return merchantTradeNo;
	}

	public void setMerchantTradeNo(String merchantTradeNo) {
		this.merchantTradeNo = merchantTradeNo;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTradeDesc() {
		return tradeDesc;
	}

	public void setTradeDesc(String tradeDesc) {
		this.tradeDesc = tradeDesc;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	
}
