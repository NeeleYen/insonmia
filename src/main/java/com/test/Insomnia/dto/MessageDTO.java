package com.test.Insomnia.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MessageDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("content") //這行可以不用寫
	private String inputText;

	public MessageDTO() {
		
	}

	public String getInputText() {
		return inputText;
	}

	public void setInputText(String inputText) {
		this.inputText = inputText;
	}
	
	
	
}
