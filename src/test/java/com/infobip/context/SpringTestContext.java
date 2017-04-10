package com.infobip.context;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.infobip.config", "com.infobip.domain", "com.infobip.exception",
		"com.infobip.service", "com.infobip.controller" })
public class SpringTestContext {
}