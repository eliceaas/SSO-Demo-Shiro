package com.brt360.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.brt360.auth", "com.brt360.common"})
public class SsoDemoShiroAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoDemoShiroAuthApplication.class, args);
	}
}
