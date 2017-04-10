package com.infobip.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UserData {

	private static Map<String, String> userMap = new HashMap<String, String>();

	public static Map<String, String> getUserMap() {
		return userMap;
	}
}
