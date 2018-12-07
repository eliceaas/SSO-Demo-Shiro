package com.brt360.client2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.brt360.client2", "com.brt360.common"})
public class SsoDemoShiroClient2Application {

	public static void main(String[] args) {
		SpringApplication.run(SsoDemoShiroClient2Application.class, args);
	}
}
