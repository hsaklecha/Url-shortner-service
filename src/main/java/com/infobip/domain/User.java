package com.infobip.domain;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class User {

	@NotNull
	private String accountId;

	private String password;

	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
