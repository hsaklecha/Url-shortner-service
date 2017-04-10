package com.infobip.domain;

public class AccountResponse {

	private Boolean Success;
	private String Description;
	private String Password;

	public AccountResponse(Boolean success, String description, String password) {
		super();
		Success = success;
		Description = description;
		Password = password;
	}

	public Boolean getSuccess() {
		return Success;
	}

	public void setSuccess(Boolean success) {
		Success = success;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

}
