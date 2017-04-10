package com.infobip.service;

import static org.mockito.Matchers.any;

import java.net.URI;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.infobip.BaseObjects;
import com.infobip.context.SpringTestContext;
import com.infobip.domain.AccountResponse;
import com.infobip.domain.Url;
import com.infobip.domain.UrlDetail;
import com.infobip.domain.User;
import com.infobip.domain.UserData;
import com.infobip.exception.InfobipException;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContext.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UrlHelperServiceTest {

	@Autowired
	UrlHelperService urlHelperService;

	private Map<String, String> userData = UserData.getUserMap();
	private Map<String, String> valueData = UrlDetail.getValueData();

	@Before
	public void setup() {

		try {
			BaseObjects.initObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateUser() {
		if (userData.containsKey(BaseObjects.USER.getAccountId())) {
			userData.remove(BaseObjects.USER.getAccountId());
		}

		AccountResponse accountResponse1 = urlHelperService.createUser(BaseObjects.USER);
		Assert.assertTrue(
				accountResponse1.getDescription().equals(BaseObjects.ACCOUNT_RESPONSE_SUCCESS.getDescription()));
		Assert.assertTrue(accountResponse1.getSuccess().equals(BaseObjects.ACCOUNT_RESPONSE_SUCCESS.getSuccess()));

		User nullUser = new User();
		nullUser.setAccountId("");
		try {
			urlHelperService.createUser(nullUser);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof InfobipException);
		}

		try {
			urlHelperService.createUser(null);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof InfobipException);
		}
	}

	@Test
	public void testRegisterUrl() {

		Url url1 = new Url();
		url1.setUrl("");
		try {
			urlHelperService.registerUrl(url1);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof InfobipException);
		}

		Url url2 = new Url();
		url2.setUrl("www.google");
		try {
			urlHelperService.registerUrl(url2);
		} catch (Exception e) {
			Assert.assertTrue(e instanceof InfobipException);
		}

		String output = urlHelperService.registerUrl(BaseObjects.URL);
		Assert.assertTrue(output != null);
	}

	@Test
	public void testtRedirectUrl() {

		try {
			urlHelperService.redirectUrl(any(HttpServletResponse.class), "testUrl", "test");
		} catch (Exception e) {
			Assert.assertTrue(e instanceof InfobipException);
		}

		String shortUrl = valueData.get("http://www.google.com");

		ResponseEntity<Object> responseEntity = urlHelperService.redirectUrl(any(HttpServletResponse.class), shortUrl,
				BaseObjects.USER.getAccountId());

		Assert.assertTrue(responseEntity.getStatusCode().equals(HttpStatus.TEMPORARY_REDIRECT));

		HttpHeaders httpHeaders = responseEntity.getHeaders();

		try {
			Assert.assertTrue(httpHeaders.getLocation().equals(new URI("http://www.google.com")));
		} catch (Exception e) {
			Assert.assertTrue(e instanceof InfobipException);
		}

	}

	@Test
	public void testtUserMetric() {
		Map<String, Integer> userStatistic = urlHelperService.getUserStatistic(BaseObjects.USER.getAccountId());
		
		Assert.assertTrue(userStatistic.equals(BaseObjects.USER_STATISTICS_MAP));
	}
}
