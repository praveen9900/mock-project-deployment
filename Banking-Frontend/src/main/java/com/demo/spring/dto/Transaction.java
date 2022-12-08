package com.demo.spring.dto;

public class Transaction {

	private Integer fromAccountNumber;
	private Double amount;
	private Integer toAccountNumber = 0;
	private String date;
	private Integer customerId;

	public Transaction() {

	}

	public Transaction(Integer fromAccountNumber, Double amount, Integer toAccountNumber, String date,
			Integer customerId) {
		super();
		this.fromAccountNumber = fromAccountNumber;
		this.amount = amount;
		this.toAccountNumber = toAccountNumber;
		this.date = date;
		this.customerId = customerId;
	}
	
	

	public Transaction(Integer fromAccountNumber, Double amount, String date, Integer customerId) {
		super();
		this.fromAccountNumber = fromAccountNumber;
		this.amount = amount;
		this.date = date;
		this.customerId = customerId;
	}

	public Integer getFromAccountNumber() {
		return fromAccountNumber;
	}

	public void setFromAccountNumber(Integer fromAccountNumber) {
		this.fromAccountNumber = fromAccountNumber;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Integer getToAccountNumber() {
		return toAccountNumber;
	}

	public void setToAccountNumber(Integer toAccountNumber) {
		this.toAccountNumber = toAccountNumber;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Integer getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}
}