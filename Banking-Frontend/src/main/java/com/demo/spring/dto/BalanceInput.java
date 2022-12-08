package com.demo.spring.dto;

public class BalanceInput {

	private Integer accountNumber;
	private Integer customerId;

	public BalanceInput() {

	}

	public BalanceInput(Integer accountNumber) {
		super();
		this.accountNumber = accountNumber;
	}

	public BalanceInput(Integer accountNumber, Integer customerId) {
		super();
		this.accountNumber = accountNumber;
		this.customerId = customerId;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
