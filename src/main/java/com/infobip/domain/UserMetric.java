package com.infobip.domain;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class UserMetric {
	
	private static Map<String, Map<String, Integer>> userMetric = new HashMap<>();

	public static Map<String, Map<String, Integer>> getUserMetric() {
		return userMetric;
	}

}
