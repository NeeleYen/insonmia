package com.test.Insomnia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "FAQ_Table")
public class FAQ {
//	faq是多方 product是一方

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private Integer FAQId;

	@Column(nullable = false, length = Integer.MAX_VALUE)
	private String question;

	@Column(nullable = false, length = Integer.MAX_VALUE)
	private String answer;

	//@JsonBackReference(value="product-faqs")
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "FK_ProductId", nullable = false, foreignKey = @ForeignKey(name = "fk_FAQ_Product"))
	private Product productId;

	public FAQ() {

	}

	public Integer getFAQId() {
		return FAQId;
	}

	public void setFAQId(Integer fAQId) {
		FAQId = fAQId;
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

	public Product getProductId() {
		return productId;
	}

	public void setProductId(Product productId) {
		this.productId = productId;
	}

	public FAQ(Integer fAQId, String question, String answer, Product productId) {
		super();
		FAQId = fAQId;
		this.question = question;
		this.answer = answer;
		this.productId = productId;
	}

}
