package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.BalanceInput;
import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Transaction;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.exception.LowBalanceException;
import com.demo.spring.exception.ReceiverAccountNotFoundException;
import com.demo.spring.service.CustomerService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping(path = "/customer")
public class CustomerRestController {

	@Autowired
	CustomerService customerService;

	@PostMapping(path = "/createAccount", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.createAccount")
	public ResponseEntity<Message> createAccount(@RequestBody AccountDTO accountDto) {
		Account account = new Account(accountDto.getAccountType(), accountDto.getBranch(), accountDto.getBalance(),
				accountDto.getCustomerId());

		return ResponseEntity.ok(new Message(customerService.create(account).getStatus()));

	}

	@PostMapping(path = "/newcust", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.register")
	public ResponseEntity<Message> register(@RequestBody CustomerDTO customerDto) {
		Customer customer = new Customer(customerDto.getCustomerId(), customerDto.getCustomerFirstName(),
				customerDto.getCustomerLastName(), customerDto.getEmail());
		return ResponseEntity.ok(customerService.registerCustomer(customer));
	}

	@PatchMapping(path = "/withdraw", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.withdraw")
	public ResponseEntity<Message> withdraw(@RequestBody Transaction transacation) {
		return ResponseEntity.ok(new Message(customerService.withdraw(transacation).getStatus()));
	}

	@PatchMapping(path = "/deposit", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.deposit")
	public ResponseEntity<Message> deposit(@RequestBody Transaction transaction) {
		return ResponseEntity.ok(new Message(customerService.deposit(transaction).getStatus()));
	}

	@PatchMapping(path = "/transfer", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.transfer")
	public ResponseEntity<Message> transfer(@RequestBody Transaction transaction) {
		return ResponseEntity.ok(new Message(customerService.transfer(transaction).getStatus()));
	}

	@PostMapping(path = "/checkbalance", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.checkBalance")
	public ResponseEntity<Message> checkBalance(@RequestBody BalanceInput balanceInput) {
		return ResponseEntity.ok(new Message(customerService.checkBalance(balanceInput).getStatus()));
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Message> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.ok(new Message("Incorrect Account Number"));
	}

	@ExceptionHandler(ReceiverAccountNotFoundException.class)
	public ResponseEntity<Message> handleReceiverAccountNotFound(ReceiverAccountNotFoundException ex) {
		return ResponseEntity.ok(new Message("Incorrect Recipient Account Number"));
	}

	@ExceptionHandler(LowBalanceException.class)
	public ResponseEntity<Message> handleLowBalanceException(LowBalanceException ex) {
		return ResponseEntity.ok(new Message("Low-Balance"));
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Message> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return ResponseEntity.ok(new Message("Customer Not Found"));
	}
}
