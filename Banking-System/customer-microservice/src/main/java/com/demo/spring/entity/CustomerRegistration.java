package com.demo.spring.entity;

public class CustomerRegistration {
	
	private String customerName;
	private String userName;
	private String password;
	
	
	public CustomerRegistration() {
		
	}


	public CustomerRegistration(String customerName, String userName, String password) {
		super();
		this.customerName = customerName;
		this.userName = userName;
		this.password = password;
	}


	public String getCustomerName() {
		return customerName;
	}


	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
