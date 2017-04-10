package com.infobip.domain;

import org.springframework.stereotype.Component;

@Component
public class UserDetail {

	private String longUrl;
	private Integer urlRedirect;

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public Integer getUrlRedirect() {
		return urlRedirect;
	}

	public void setUrlRedirect(Integer urlRedirect) {
		this.urlRedirect = urlRedirect;
	}
}
