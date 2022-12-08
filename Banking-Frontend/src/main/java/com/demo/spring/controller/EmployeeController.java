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
import com.demo.spring.dto.Customer;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
public class EmployeeController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/getCustDetails")
	public ModelAndView customerDetails(@RequestParam(name = "customerId", required = true) Integer customerId)
			throws JsonProcessingException {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);

		ObjectMapper mapper = new ObjectMapper();
		Message msg = new Message("Customer Not Found");
		String msgJson = mapper.writeValueAsString(msg);

		String url = restTemplate
				.exchange("http://localhost:8185/employee/details/" + customerId, HttpMethod.GET, req, String.class)
				.getBody();

		if (url.equals(msgJson)) {
			mv.addObject("response", "Customer Not Found");
			mv.setViewName("detailsErrorResp");
			return mv;
		} else {

			ResponseEntity<Customer> response = restTemplate.exchange(
					"http://localhost:8185/employee/details/" + customerId, HttpMethod.GET, req, Customer.class);

			mv.addObject("details", response.getBody());
			mv.setViewName("detailsResp");
			return mv;
		}

	}

	@GetMapping(path = "/getAllCustomers")
	public ModelAndView listAllCustomers() {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Void> req = new HttpEntity<>(headers);
		ResponseEntity<List<Customer>> response = restTemplate.exchange(
				"http://localhost:8185/employee/listallcustomer", HttpMethod.GET, req,
				new ParameterizedTypeReference<List<Customer>>() {
				});
		ModelAndView mv = new ModelAndView();
		mv.addObject("details", response.getBody());
		mv.setViewName("detailsResp");
		return mv;

	}

	@PostMapping(path = "/createAccByEmp")
	public ModelAndView createAccount(AccountDTO accountDto)  {

		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountDTO> req = new HttpEntity<>(accountDto, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/employee/createaccount",

				HttpMethod.POST, req, String.class);

		mv.addObject("response", response.getBody());
		mv.setViewName("createAccResponseEmp");
		return mv;
	}

	@PostMapping(path = "/activateAcc")
	public ModelAndView active(AccountDTO accountDTO)  {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();

		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountDTO> request = new HttpEntity<>(accountDTO, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/employee/active",
				HttpMethod.PATCH, request, String.class);
		mv.addObject("response", response.getBody());
		mv.setViewName("activateResp");
		return mv;
	}

	@PostMapping(path = "/deactivateAcc")
	public ModelAndView deactivateAccount(AccountDTO accountDto) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<AccountDTO> req = new HttpEntity<>(accountDto, headers);

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/employee/deactive",
				HttpMethod.PATCH, req, String.class);

		mv.addObject("response", response.getBody());
		mv.setViewName("deactivateResp");
		return mv;

	}
}
