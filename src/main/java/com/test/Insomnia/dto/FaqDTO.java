package com.test.Insomnia.dto;

// DTO for using json
// 用hidden form input送product至前端修改頁面(或任何從前端可以讓js知道目前product id的方法)
public class FaqDTO {
	private Integer faqId; // 修改faq用
	private Integer productId;
	private String question;
//	@JsonProperty("contentAns") //這行可以不用寫(可變更json輸入keyName，預設json輸入key為變數名)
	private String answer;
	

	public Integer getFaqId() {
		return faqId;
	}


	public void setFaqId(Integer faqId) {
		this.faqId = faqId;
	}


	public Integer getProductId() {
		return productId;
	}


	public void setProductId(Integer productId) {
		this.productId = productId;
	}


	public String getQuestion() {
		return question;
	}


	public void setQuestion(String question) {
		this.question = question;
	}


	public String getAnswer() {
		return answer;
	}


	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public FaqDTO() {
		super();
	}


	public FaqDTO(Integer faqId, Integer productId, String question, String answer) {
		super();
		this.faqId = faqId;
		this.productId = productId;
		this.question = question;
		this.answer = answer;
	}

}
