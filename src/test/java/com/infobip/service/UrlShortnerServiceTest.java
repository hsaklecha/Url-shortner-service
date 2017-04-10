package com.infobip.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.infobip.BaseObjects;
import com.infobip.context.SpringTestContext;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringTestContext.class)
@TestPropertySource(locations = "classpath:application.properties")
public class UrlShortnerServiceTest {

	@Autowired
	private UrlShortnerService urlShortnerService;

	@Test
	public void TestGetShortenedUrl() {
		char[] baseCharacters = urlShortnerService.getBaseCharacters();

		Assert.assertTrue(baseCharacters.length > 0);
		Assert.assertTrue(baseCharacters.length ==  62);
		
		String key = urlShortnerService.generateShortKey();
		Assert.assertNotNull(key);
		Assert.assertFalse(urlShortnerService.keyExists(key));
		
		String url = urlShortnerService.getShortenedUrl(BaseObjects.SAMPLE_URL);
		Assert.assertNotNull(url);
	}

}
