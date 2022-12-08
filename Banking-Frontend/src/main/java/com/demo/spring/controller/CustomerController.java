package com.demo.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.AccountDTO;
import com.demo.spring.dto.BalanceInput;
import com.demo.spring.dto.Statement;
import com.demo.spring.dto.StatementInput;
import com.demo.spring.dto.Transaction;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class CustomerController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/checkBalance")
	public ModelAndView getBalance(@RequestParam(name = "accountNumber", required = true) Integer accountNumber,
			@RequestParam(name = "customerId", required = true) Integer customerId)  {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		BalanceInput input = new BalanceInput(accountNumber, customerId);
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<BalanceInput> req = new HttpEntity<>(input, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/customer/checkbalance",
				HttpMethod.POST, req, String.class);

		mv.addObject("response", response.getBody());
		mv.setViewName("balanceResponse");

		return mv;

	}

	@PostMapping(path = "/create")
	public ModelAndView createAccount(AccountDTO accountDto) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountDTO> req = new HttpEntity<>(accountDto, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/customer/createAccount",
				HttpMethod.POST, req, String.class);

		mv.addObject("response", response.getBody());
		mv.setViewName("createAccResp");
		return mv;

	}

	@GetMapping(path = "/statements")
	public ModelAndView fetchStatements(StatementInput input) throws JsonProcessingException {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<StatementInput> req = new HttpEntity<>(input, headers);
		ObjectMapper mapper = new ObjectMapper();
		Message msg1 = new Message("Customer Not Found");

		Message msg2 = new Message("Incorrect Account Number");
		String msgJson1 = mapper.writeValueAsString(msg1);
		String msgJson2 = mapper.writeValueAsString(msg2);

		String url = restTemplate
				.exchange("http://localhost:8185/customer/liststatements", HttpMethod.POST, req, String.class)
				.getBody();

		if (url.equals(msgJson1)) {
			mv.addObject("response", "Customer Not Found");
			mv.setViewName("statementRespError");
			return mv;
		} else if (url.equals(msgJson2)) {

			mv.addObject("response", "Incorrect Account Number");
			mv.setViewName("statementRespError");
			return mv;
		}
		ResponseEntity<List<Statement>> response = restTemplate.exchange(
				"http://localhost:8185/customer/liststatements", HttpMethod.POST, req,
				new ParameterizedTypeReference<List<Statement>>() {
				});

		mv.addObject("statementList", response.getBody());
		mv.setViewName("statementResp");
		return mv;
	}

	@PostMapping(path = "/depositAmount")
	public ModelAndView withdrawAmount(Transaction input){
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Transaction> req = new HttpEntity<>(input, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/customer/deposit",
				HttpMethod.PATCH, req, String.class);

		mv.addObject("response", response.getBody());
		mv.setViewName("depositResp");
		return mv;
	}

	@PostMapping(path = "/withdrawAmount")
	public ModelAndView withdraw(Transaction accountDTO)  {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Transaction> request = new HttpEntity<>(accountDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/customer/withdraw",
				HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("withdrawResp");
		return mv;
	}

	@PostMapping(path = "/transferAmount")
	public ModelAndView transfer(Transaction accountDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Transaction> request = new HttpEntity<>(accountDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/customer/transfer",
				HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("transferResp");
		return mv;
	}

}
