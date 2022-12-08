package com.demo.spring.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.entity.Register;
import com.demo.spring.entity.Users;
import com.demo.spring.service.LoginService;
import com.demo.spring.util.Message;

import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.OpenAPI30;

@RestController
@RequestMapping(path = "/register")
@OpenAPI30
public class LoginController {

	@Autowired
	LoginService loginService;

	@Autowired
	RestTemplate restTemplate;
	

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	@PostMapping(path = "/customer", consumes = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.registerUser")
	public ResponseEntity<Message> registerUser(@RequestBody Register register) {

		Users user = new Users(register.getUserFirstName(), register.getPassword(), register.getUser());
		Message message = loginService.saveCredentials(user);

		if (register.getUser().equalsIgnoreCase("customer")) {
			HttpHeaders headers = new HttpHeaders();
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);

			CustomerDTO customer = new CustomerDTO(user.getUserId(), register.getUserFirstName(),
					register.getUserLastName(), register.getEmail());

			HttpEntity<CustomerDTO> req = new HttpEntity<>(customer, headers);
			ResponseEntity<Message> response = restTemplate.exchange("http://customer-microservice/customer/newcust",
					HttpMethod.POST, req, Message.class);
			logger.info("Customer Registerted Successfully");
			return ResponseEntity.ok(response.getBody());
		}
		return ResponseEntity.ok(message);
	}

	
	@PostMapping(path = "/", produces = MediaType.APPLICATION_JSON_VALUE)
	@Timed(value = "requests.count.loginToMyAccount")
	public ResponseEntity<Message> loginToMyAccount(@RequestBody CredentialsDTO credentialsDTO) {
		logger.info("Login to the Account");
		return ResponseEntity.ok(loginService.loginToAccount(credentialsDTO));
	}

}
