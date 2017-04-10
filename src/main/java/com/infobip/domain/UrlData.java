package com.infobip.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UrlData {

	
	private static Map<String, Integer> urlData = new HashMap<>();

	public static Map<String, Integer> getUrlData() {
		return urlData;
	}
	
}
