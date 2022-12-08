package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "STATEMENT")
public class Statement {

	@SequenceGenerator(sequenceName = "statement_sequence", initialValue = 1, allocationSize = 1, name = "statement_generator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "statement_generator")
	@Id
	@Column(name = "STATEMENTID")
	private Integer statementId;

	@Column(name = "ACCOUNTNUMBER")
	private Integer accountNumber;

	@Column(name = "TRANSACTIONTYPE")
	private String transactionType;

	@Column(name = "AMOUNT")
	private Double amount;

	@Column(name = "BALANCE")
	private Double balance = 0.0;

	@Column(name = "DATE")
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
