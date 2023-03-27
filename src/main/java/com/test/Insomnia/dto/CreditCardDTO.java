package com.test.Insomnia.dto;

public class CreditCardDTO {

	private String card;
	private String cardCvc;
	private String cardYear;
	private String cardMonth;

	public CreditCardDTO() {

	}

	public String getCard() {
		return card;
	}

	public void setCard(String card) {
		this.card = card;
	}

	public String getCardCvc() {
		return cardCvc;
	}

	public void setCardCvc(String cardCvc) {
		this.cardCvc = cardCvc;
	}

	public String getCardYear() {
		return cardYear;
	}

	public void setCardYear(String cardYear) {
		this.cardYear = cardYear;
	}

	public String getCardMonth() {
		return cardMonth;
	}

	public void setCardMonth(String cardMonth) {
		this.cardMonth = cardMonth;
	}

	public CreditCardDTO(String card, String cardCvc, String cardYear, String cardMonth) {
		super();
		this.card = card;
		this.cardCvc = cardCvc;
		this.cardYear = cardYear;
		this.cardMonth = cardMonth;
	}

}
