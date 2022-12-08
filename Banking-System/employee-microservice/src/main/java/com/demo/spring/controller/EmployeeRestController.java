package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.spring.entity.Customer;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.service.EmployeeService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping(path = "/employee")
@OpenAPI30
public class EmployeeRestController {

	
	

	@Autowired
	EmployeeService employeeService;

	@GetMapping(path = "/details/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.getDetails")
	public ResponseEntity<Customer> getDetails(@PathVariable("id") Integer customerId) {
		return ResponseEntity.ok(employeeService.getCustomerDetails(customerId));

	}	

	@GetMapping(path = "/listallcustomer", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.getAllCustomer")
	public ResponseEntity<List<Customer>> getAllCustomer() {
		return ResponseEntity.ok(employeeService.getCustomerList());
	}

	@ExceptionHandler(CustomerNotFoundException.class)
	public ResponseEntity<Message> handleCustomerNotFoundException(CustomerNotFoundException ex) {
		return ResponseEntity.ok(new Message("Customer Not Found"));

	}
}
