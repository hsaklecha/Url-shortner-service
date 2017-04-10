package com.infobip;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.infobip.domain.AccountResponse;
import com.infobip.domain.Url;
import com.infobip.domain.User;

public class BaseObjects {
	
	public static User USER;

	public static Url URL;
	
	public static AccountResponse ACCOUNT_RESPONSE_SUCCESS;
	
	public static AccountResponse ACCOUNT_RESPONSE_FAILURE;
	
	public static User NEW_USER;
	
	public static Map<String, Integer> USER_STATISTICS_MAP;
	
	public static String SAMPLE_URL = "localhost:8080/random";
	
	public static ResponseEntity<Object> RESPONSE_ENTITY = null;
	
	public static void initObjects() throws Exception{
		USER = new User();
		USER.setAccountId("test");
		USER.setPassword("test");
		
		NEW_USER = new User();
		NEW_USER.setAccountId("test");
		
		URL = new Url();
		URL.setUrl("http://www.google.com");
		URL.setRedirectType(307);
		
		USER_STATISTICS_MAP = new HashMap<>();
		USER_STATISTICS_MAP.put("http://www.google.com", 1);
		
		ACCOUNT_RESPONSE_SUCCESS = new AccountResponse(true, "Account created successfully", "random");
		
		ACCOUNT_RESPONSE_FAILURE = new AccountResponse(false, "Account already exists", null);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setLocation(new URI("www.google.com"));
		RESPONSE_ENTITY = new ResponseEntity<>(httpHeaders, HttpStatus.PERMANENT_REDIRECT);
	}

}
