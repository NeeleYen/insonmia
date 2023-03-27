package com.test.Insomnia.dto;

public class UserDTO {

	private String stringEmail;

	public UserDTO() {

	}

	public String getStringEmail() {
		return stringEmail;
	}

	public void setStringEmail(String stringEmail) {
		this.stringEmail = stringEmail;
	}

	public UserDTO(String stringEmail) {
		super();
		this.stringEmail = stringEmail;
	}

}
