package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER")
public class Customer {

	@SequenceGenerator(sequenceName = "customer_sequence", initialValue = 1, allocationSize = 1, name = "customer_generator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_generator")
	@Id
	@Column(name = "CUSTOMERID")
	private Integer customerId;

	@Column(name = "CUSTOMERFIRSTNAME")
	private String customerFirstName;

	@Column(name = "CUSTOMERLASTNAME")
	private String customerLastName;

	@Column(name = "EMAIL")
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
