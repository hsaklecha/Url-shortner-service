package com.infobip.domain;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

@Component
public class Url {

	@NotNull
	private String url;
	private Integer redirectType;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getRedirectType() {
		return redirectType;
	}

	public void setRedirectType(Integer redirectType) {
		this.redirectType = redirectType;
	}



}
