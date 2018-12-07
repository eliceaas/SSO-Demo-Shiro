package com.brt360.client1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.brt360.client1", "com.brt360.common"})
public class SsoDemoShiroClient1Application {

	public static void main(String[] args) {
		SpringApplication.run(SsoDemoShiroClient1Application.class, args);
	}
}
