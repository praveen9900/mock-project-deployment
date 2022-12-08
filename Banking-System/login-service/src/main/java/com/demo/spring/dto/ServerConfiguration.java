package com.demo.spring.dto;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties
public class ServerConfiguration {

	String customerServer = "http://localhost:8181";

	public String getCustomerServer() {
		return customerServer;
	}

	public void setCustomerServer(String customerServer) {
		this.customerServer = customerServer;
	}

}