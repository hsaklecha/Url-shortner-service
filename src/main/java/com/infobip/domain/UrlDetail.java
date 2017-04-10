package com.infobip.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UrlDetail {

	static Map<String, String> keyData = new HashMap<>();

	static Map<String, String> valueData = new HashMap<>();

	public static Map<String, String> getKeyData() {
		return keyData;
	}

	public static Map<String, String> getValueData() {
		return valueData;
	}

}
