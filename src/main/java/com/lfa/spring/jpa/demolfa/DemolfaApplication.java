package com.lfa.spring.jpa.demolfa;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

@SpringBootApplication(proxyBeanMethods = false)
public class DemolfaApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(DemolfaApplication.class);
		String port = System.getenv("PORT");
		app.setDefaultProperties(Collections.singletonMap("server.port", port == null ? "8080" : port));
		app.run(args);

	}



}
