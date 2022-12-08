package com.demo.spring.dto;

public class Customer {

	private Integer customerId;

	private String customerFirstName;

	private String customerLastName;

	private String email;

	public Customer() {

	}

	public Customer(Integer customerId, String customerFirstName, String customerLastName, String email) {
		super();
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.email = email;
	}

	public Customer(String customerFirstName, String customerLastName, String email) {
		super();
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.email = email;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
