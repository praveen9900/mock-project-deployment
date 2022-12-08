package com.demo.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.dto.Register;
import com.demo.spring.util.Message;

@Controller
public class RegisterController {

	@Autowired
	RestTemplate restTemplate;

	@GetMapping(path = "/loginPage")
	public ModelAndView login(CredentialsDTO credentialDTO) {
		ModelAndView mv = new ModelAndView();
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> request = new HttpEntity<>(credentialDTO, headers);
		
		ResponseEntity<Message> response = restTemplate.exchange("http://localhost:8185/register/", HttpMethod.POST,
				request, Message.class);
		if (response.getBody().getStatus().equalsIgnoreCase("customer")) {

			
			mv.setViewName("customerHome");
			return mv;
		}

		else if (response.getBody().getStatus().equalsIgnoreCase("employee")) {
			mv.setViewName("employeeHomeDesign");
			return mv;

		} else {

			mv.addObject("response", "Invalid Credentials");
			mv.setViewName("loginFailure");
			return mv;
		}

	}
	
	@PostMapping(path = "/registerCustomer")
	public ModelAndView withdrawAmount(Register input) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<Register> req = new HttpEntity<>(input, headers);
		ResponseEntity<String> response = restTemplate.exchange("http://localhost:8185/register/customer",
				HttpMethod.POST, req, String.class);
		ModelAndView mv = new ModelAndView();
		mv.addObject("response", response.getBody());
		mv.setViewName("loginDesign");
		return mv;

	}

}