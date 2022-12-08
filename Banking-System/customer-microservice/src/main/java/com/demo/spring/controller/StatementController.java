package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.dto.StatementInput;
import com.demo.spring.entity.Statement;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.service.StatementService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@OpenAPI30
@RequestMapping(path = "/customer")
public class StatementController {

	@Autowired
	StatementService statementService;

	@PostMapping(path = "/liststatements", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.statements")
	public ResponseEntity<List<Statement>> fetchStatement(@RequestBody StatementInput statementInput) {
		return ResponseEntity.ok(statementService.getStatements(statementInput));
	}
	
	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Message> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return ResponseEntity.ok(new Message("Customer Not Found"));
	}
	

	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Message> handleAccountNotFound(AccountNotFoundException ex) {
		return ResponseEntity.ok(new Message("Incorrect Account Number"));
	}
}

