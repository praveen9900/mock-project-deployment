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
import com.demo.spring.entity.Account;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.service.EmployeeAccountService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping(path = "/employee")
@OpenAPI30
public class EmployeeAccountController {

	@Autowired
	EmployeeAccountService employeeAccountService;

	@PatchMapping(path = "/deactive", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.activeStatus")
	public ResponseEntity<Message> activeStatus(@RequestBody AccountDTO accountDto) {
		Account account = new Account(accountDto.getAccountNumber(), accountDto.getAccountType(),
				accountDto.getBranch(), accountDto.getBalance(), accountDto.getStatus(), accountDto.getCustomerId());
		return ResponseEntity
				.ok(new Message(employeeAccountService.checkActiveStatus(account.getAccountNumber()).getStatus()));
	}

	
	@PatchMapping(path = "/active", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.deactiveStatus")
	public ResponseEntity<Message> deactiveStatus(@RequestBody AccountDTO accountDto) {
		Account account = new Account(accountDto.getAccountNumber(), accountDto.getAccountType(),
				accountDto.getBranch(), accountDto.getBalance(), accountDto.getStatus(), accountDto.getCustomerId());
		return ResponseEntity
				.ok(new Message(employeeAccountService.checkdeActiveStatus(account.getAccountNumber()).getStatus()));
	}

	@PostMapping(path = "/createaccount", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.openAccount")
	public ResponseEntity<Message> openAccount(@RequestBody AccountDTO accountDto) {
		Account account = new Account(accountDto.getAccountNumber(), accountDto.getAccountType(),
				accountDto.getBranch(), accountDto.getBalance(), accountDto.getStatus(), accountDto.getCustomerId());
		return ResponseEntity.ok(new Message(employeeAccountService.openAccount(account).getStatus()));
	}

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Message> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.ok(new Message("Incorrect Account Number"));
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Message> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return ResponseEntity.ok(new Message("Customer Not Found"));
	}

}
