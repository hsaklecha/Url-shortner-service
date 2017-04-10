package com.infobip.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infobip.BaseObjects;
import com.infobip.config.PropertyUtil;
import com.infobip.context.SpringTestContext;
import com.infobip.domain.Url;
import com.infobip.domain.User;
import com.infobip.service.UrlHelperService;
import com.infobip.service.UrlShortnerService;

import junit.framework.TestCase;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContext.class)
@WebAppConfiguration
@TestPropertySource(locations="classpath:application.properties")
public class UrlControllerTest extends TestCase{

	@Mock
	private UrlShortnerService urlShortnerService;

	@Mock
	private UrlHelperService urlHelperService;
	
	@InjectMocks
	private PropertyUtil propertyUtil;
	
	@InjectMocks
	private UrlController urlController;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {

		mockMvc = MockMvcBuilders.standaloneSetup(urlController).build();

		try {
			BaseObjects.initObjects();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testCreateAccount() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(BaseObjects.NEW_USER);

		String jsonResult = mapper.writeValueAsString(BaseObjects.ACCOUNT_RESPONSE_SUCCESS);

		when(urlHelperService.createUser(any(User.class))).thenReturn(BaseObjects.ACCOUNT_RESPONSE_SUCCESS);
		String result = null;

		result = mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();
		Assert.assertEquals(jsonResult, result);
	}

	@Test
	public void testRegisterUrl() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String jsonResult = BaseObjects.SAMPLE_URL;

		String json = mapper.writeValueAsString(BaseObjects.URL);

		when(urlHelperService.registerUrl(any(Url.class))).thenReturn(BaseObjects.SAMPLE_URL);
		String result = null;

		result = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON).content(json)).andExpect(status().isOk()).andReturn().getResponse()
				.getContentAsString();

		Assert.assertEquals(result, jsonResult);
	}

	@Test
	public void testGetUserStatistic() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		String json = mapper.writeValueAsString(BaseObjects.USER_STATISTICS_MAP);

		when(urlHelperService.getUserStatistic(any(String.class))).thenReturn(BaseObjects.USER_STATISTICS_MAP);
		String result = null;

		result = mockMvc
				.perform(get("/statistic/{accountId}", "test").contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
		Assert.assertEquals(json, result);
	}

//	@Test
//	public void testRedirectUrl() throws Exception {
//		
//		ObjectMapper mapper = new ObjectMapper();
//		String json = mapper.writeValueAsString(BaseObjects.RESPONSE_ENTITY);
//		when(urlHelperService.redirectUrl(any(HttpServletResponse.class), any(String.class), any(String.class)))
//				.thenReturn(BaseObjects.RESPONSE_ENTITY);
//		
//		String result = null;
//		result = mockMvc
//				.perform(get("/{shortUrl}", "test").contentType(MediaType.APPLICATION_JSON)
//						.accept(MediaType.APPLICATION_JSON))
//				.andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
//		
//		Assert.assertEquals(json, result);
//	}

}
