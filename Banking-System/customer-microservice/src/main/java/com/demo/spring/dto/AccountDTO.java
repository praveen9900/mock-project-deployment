package com.demo.spring.dto;

public class AccountDTO {

	private String accountType;

	private String branch;

	private Double balance = 0.0;

	private Integer customerId;

	public AccountDTO() {

	}

	public AccountDTO(String accountType, String branch, Double balance, Integer customerId) {
		super();

		this.accountType = accountType;
		this.branch = branch;
		this.balance = balance;

		this.customerId = customerId;
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

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}