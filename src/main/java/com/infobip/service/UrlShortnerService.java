package com.infobip.service;

import java.util.Map;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.infobip.config.PropertyUtil;
import com.infobip.domain.UrlDetail;

@Component
public class UrlShortnerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UrlShortnerService.class);

	@Autowired
	private PropertyUtil propertyUtil;

	private Map<String, String> shortKeyMap;
	private Map<String, String> valueMap;
	private char[] baseCharacters = new char[62];
	private Random random;
	private String baseUrl;

	public UrlShortnerService() {
		shortKeyMap = UrlDetail.getKeyData();
		valueMap = UrlDetail.getValueData();
		random = new Random();
		baseCharacters = getBaseCharacters();

	}

	char[] getBaseCharacters() {

		for (int i = 0; i < 62; i++) {
			int j = 0;
			if (i < 10) {
				j = i + 48;
			} else if (i > 9 && i <= 35) {
				j = i + 55;
			} else {
				j = i + 61;
			}
			baseCharacters[i] = (char) j;
		}
		return baseCharacters;
	}

	public String getShortenedUrl(String url) {
		String shortURL = "";

		baseUrl = propertyUtil.getBaseUrl();
		if (valueMap.containsKey(url)) {
			shortURL = baseUrl + valueMap.get(url);
		} else {
			shortURL = baseUrl + getShortKey(url);
		}

		return shortURL;
	}

	String getShortKey(String longURL) {
		String shortkey;
		shortkey = generateShortKey();
		LOGGER.info("returned short key "+shortkey);
		return shortkey;
	}

	String generateShortKey() {
		String shortkey = "";
		boolean flag = true;
		int length = Integer.parseInt(propertyUtil.getkeyLength());
		while (flag) {
			shortkey = "";

			for (int i = 0; i <= length; i++) {
				shortkey += baseCharacters[random.nextInt(62)];
			}
			if (!keyExists(shortkey)) {
				flag = false;
			}
		}
		return shortkey;
	}

	Boolean keyExists(String key) {
		return shortKeyMap.containsKey(key);
	}

}
