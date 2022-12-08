package com.demo.spring.dto;

public class StatementInput {

	private Integer accountNumber;
	private String fromDate;
	private String toDate;
	private Integer customerId;

	public StatementInput() {

	}

	public StatementInput(Integer accountNumber, String fromDate, String toDate, Integer customerId) {
		super();
		this.accountNumber = accountNumber;
		this.fromDate = fromDate;
		this.toDate = toDate;
		this.customerId = customerId;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

}
