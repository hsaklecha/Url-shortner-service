package com.infobip.service;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.infobip.domain.UserData;

@Component
public class LoginService {

	private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);
	
	private static Map<String, String> userMap = UserData.getUserMap();

	public Boolean doLogin(String userName, String password) {

		if (userMap.containsKey(userName) && userMap.get(userName).equals(password)) {
			LOGGER.info("login successful");
			return true;
		}
		return false;

	}

}
