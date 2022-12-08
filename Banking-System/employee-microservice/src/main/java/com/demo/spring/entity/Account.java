package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "ACCOUNTS")
public class Account {

	@SequenceGenerator(sequenceName = "account_sequence", initialValue = 10000, allocationSize = 1, name = "account_generator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_generator")
	@Id
	@Column(name = "ACCOUNTNUMBER")
	private Integer accountNumber;

	@Column(name = "ACCOUNTTYPE")
	private String accountType;

	@Column(name = "BRANCH")
	private String branch;

	@Column(name = "BALANCE")
	private Double balance = 0.0;

	@Column(name = "STATUS")
	private String status = "Active";

	@Column(name = "CUSTOMERID")
	private Integer customerId;

	public Account() {

	}

	public Account(String accountType, String branch, Double balance, Integer customerId) {

		this.accountType = accountType;
		this.branch = branch;
		this.balance = balance;
		this.customerId = customerId;
	}

	public Account(Integer accountNumber, String accountType, String branch, Double balance, String status,
			Integer customerId) {
		super();
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.branch = branch;
		this.balance = balance;
		this.status = status;
		this.customerId = customerId;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
