package com.sanderik;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@EnableAutoConfiguration
@SpringBootApplication
public class IbratorServerApplication extends SpringBootServletInitializer {

	private static final Logger log = LoggerFactory.getLogger(IbratorServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(IbratorServerApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(IbratorServerApplication.class);
	}

}
