package com.demo.spring.dto;

public class Statement {

	private Integer statementId;

	private Integer accountNumber;

	private String transactionType;

	private Double amount;

	private Double balance = 0.0;

	private String date;

	public Statement() {

	}

	public Statement(Integer accountNumber, String transactionType, Double amount, Double balance, String date) {
		super();

		this.accountNumber = accountNumber;
		this.transactionType = transactionType;
		this.amount = amount;
		this.balance = balance;
		this.date = date;
	}

	public Integer getStatementId() {
		return statementId;
	}

	public void setStatementId(Integer statementId) {
		this.statementId = statementId;
	}

	public Integer getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}
