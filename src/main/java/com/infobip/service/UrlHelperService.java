package com.infobip.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.infobip.domain.AccountResponse;
import com.infobip.domain.Url;
import com.infobip.domain.UrlData;
import com.infobip.domain.UrlDetail;
import com.infobip.domain.User;
import com.infobip.domain.UserData;
import com.infobip.domain.UserMetric;
import com.infobip.exception.InfobipException;

@Component
public class UrlHelperService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlHelperService.class);

	private Map<String, String> userData = UserData.getUserMap();
	private Map<String, String> keyData = UrlDetail.getKeyData();
	private Map<String, String> valueData = UrlDetail.getValueData();
	private Map<String, Integer> urlData = UrlData.getUrlData();
	private Map<String, Map<String, Integer>> userMetric = UserMetric.getUserMetric();

	private Integer redirectType = 307;

	@Autowired
	private UrlShortnerService urlShortnerService;

	public AccountResponse createUser(User user) {
		if (user == null || user.getAccountId() == null || user.getAccountId().equals("")) {
			LOGGER.error("Account id cannot be null, please enter your account id");
			throw new InfobipException(HttpStatus.BAD_REQUEST.value(),
					"Account id cannot be null, please enter your account id");
		}
		if (userData.containsKey(user.getAccountId())) {
			return new AccountResponse(false, "Account with that ID already exists", null);
		}

		String password = getRandomStringForPassword();
		userData.put(user.getAccountId(), password);

		return new AccountResponse(true, "Account created successfully", password);

	}

	String getRandomStringForPassword() {
		return RandomStringUtils.randomAlphanumeric(8);
	}

	public String registerUrl(Url url) {
		if (url == null || url.getUrl() == null || url.getUrl().equals("")) {
			LOGGER.error("url is null, please enter valid url");
			throw new InfobipException(HttpStatus.BAD_REQUEST.value(), "url is null, please enter valid url");
		}
		if (!(new UrlValidator().isValid(url.getUrl()))) {
			LOGGER.error("Url is not valid");
			throw new InfobipException(HttpStatus.BAD_REQUEST.value(), "Url is not valid");
		}
		if (valueData.containsKey(url.getUrl())) {
			return valueData.get(url.getUrl());
		}
		String shortenedUrl = urlShortnerService.getShortenedUrl(url.getUrl());
		updateUrlData(url, shortenedUrl);

		return shortenedUrl;

	}

	void updateUrlData(Url url, String shortenedUrl) {

		if (url.getRedirectType() != null) {
			redirectType = url.getRedirectType();
		}

		urlData.put(url.getUrl(), redirectType);
		keyData.put(shortenedUrl, url.getUrl());
		valueData.put(url.getUrl(), shortenedUrl);
	}

	public Map<String, Integer> getUserStatistic(String accountId) {
		return userMetric.get(accountId);
	}

	public ResponseEntity<Object> redirectUrl(HttpServletResponse response, String shortUrl, String name) {
		
	    if(shortUrl ==  null){
	    	LOGGER.error("please enter the valid url");
	    	throw new InfobipException(HttpStatus.BAD_REQUEST.value(), "please enter the valid url");
	    }

		String url = keyData.get(shortUrl);

		if (url == null) {
			LOGGER.error("Passed url is not registered");
			throw new InfobipException(HttpStatus.NOT_FOUND.value(), "Passed url is not registered.");
		}

		Integer type = urlData.get(url);

		updateUserMetric(url, name);

		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			httpHeaders.setLocation(new URI(url));
		} catch (URISyntaxException e) {
			LOGGER.error(e.getMessage());
			throw new InfobipException(HttpStatus.BAD_REQUEST.value(), "Url syntax is not valid");
		}

		ResponseEntity<Object> responseEntity = null;
		if (type == HttpStatus.TEMPORARY_REDIRECT.value()) {
			responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.TEMPORARY_REDIRECT);
		} else {
			responseEntity = new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
		}
		return responseEntity;

	}

	void updateUserMetric(String url, String name) {
		if (userMetric.containsKey(name)) {
			Map<String, Integer> userData = userMetric.get(name);
			if (userData.containsKey(url)) {
				Integer hitCount = userData.get(url);
				userData.put(url, ++hitCount);

			} else {
				userData.put(url, 1);
			}
		} else {
			Map<String, Integer> userData = new HashMap<>();
			userData.put(url, 1);
			userMetric.put(name, userData);
		}
	}

}
