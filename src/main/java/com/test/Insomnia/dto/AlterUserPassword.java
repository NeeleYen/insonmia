package com.test.Insomnia.dto;

public class AlterUserPassword {
	
	private String passwordNow;
	private String passwordAlter;
	private String passwordCheck;

	public String getPasswordNow() {
		return passwordNow;
	}

	public void setPasswordNow(String passwordNow) {
		this.passwordNow = passwordNow;
	}

	public String getPasswordAlter() {
		return passwordAlter;
	}

	public void setPasswordAlter(String passwordAlter) {
		this.passwordAlter = passwordAlter;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public AlterUserPassword(String passwordNow, String passwordAlter, String passwordCheck) {
		super();
		this.passwordNow = passwordNow;
		this.passwordAlter = passwordAlter;
		this.passwordCheck = passwordCheck;
	}

	public AlterUserPassword() {

	}

}
