package com.infobip.controller;

import java.security.Principal;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.infobip.config.PropertyUtil;
import com.infobip.domain.AccountResponse;
import com.infobip.domain.Url;
import com.infobip.domain.User;
import com.infobip.service.UrlHelperService;

@RestController
public class UrlController {

	@Autowired
	private UrlHelperService urlHelperService;

	@Autowired
	private PropertyUtil propertyUtil;

	@PostMapping(value = "/account")
	public AccountResponse createAccount(@RequestBody User user) {
		return urlHelperService.createUser(user);
	}

	@PostMapping(value = "/register")
	public String registerUrl(@RequestBody Url url) {
		return urlHelperService.registerUrl(url);
	}

	@RequestMapping(value = "/{shortUrl}", method = RequestMethod.GET)
	public ResponseEntity<Object> redirectUrl(HttpServletResponse response, @PathVariable("shortUrl") String shortUrl,
			Principal principal) {
		String shortUrlWithPrefix = propertyUtil.getBaseUrl() + shortUrl;
		return urlHelperService.redirectUrl(response, shortUrlWithPrefix, principal.getName());
	}

	@RequestMapping(value = "/statistic/{accountId}", method = RequestMethod.GET)
	public Map<String, Integer> getUserStatistic(@PathVariable("accountId") String accountId) {

		return urlHelperService.getUserStatistic(accountId);

	}



}
