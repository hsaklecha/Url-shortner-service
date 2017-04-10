package com.infobip.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * This class is used to get the property values configured in
 * application.properties
 *
 * @author H. Saklecha
 */
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class PropertyUtil {

	@Value("${infobip.base.domain}")
	private String baseUrl;
	
	@Value("${default.key.length}")
	private String keyLength;

	public String getBaseUrl() {
		return baseUrl;
	}
	
	public String getkeyLength() {
		return keyLength;
	}
	
}