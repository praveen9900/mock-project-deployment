package com.demo.spring;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.entity.Register;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = {
		"customerServer=http://localhost:${wiremock.server.port}" })
@AutoConfigureWireMock(port = 0)
class RegisterCustomerTests {

	String customerServer = "http://localhost:${wiremock.server.port}";

	@Autowired
	TestRestTemplate restTemplate;

	@LocalServerPort
	int port;

	@Test
	void testRegisterCustomer() throws Exception {

		Register registerInput = new Register("Ravi", "M", "root", "ravi123@gmail.com", "customer");
		ObjectMapper mapper = new ObjectMapper();

		Message message = new Message("Customer Registered Sucessfully");
		String messageJson = mapper.writeValueAsString(message);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Register> req = new HttpEntity<>(registerInput, headers);

		stubFor(post(urlEqualTo("/customer/newcust"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/register/customer/",
				HttpMethod.POST, req, String.class);

		Assertions.assertEquals(messageJson, response.getBody());
	}

	@Test
	void testRegisterEmployee() throws Exception {

		Register registerInput = new Register("Ravi", "M", "root", "ravi123@gmail.com", "employee");
		ObjectMapper mapper = new ObjectMapper();
		

		Message message = new Message("User Credentials Saved");
		String messageJson = mapper.writeValueAsString(message);

		HttpHeaders headers = new HttpHeaders();
		headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);

		HttpEntity<Register> req = new HttpEntity<>(registerInput, headers);

		stubFor(post(urlEqualTo("/customer/newcust"))
				.willReturn(aResponse().withHeader("Content-Type", "application/json").withBody(messageJson)));

		ResponseEntity<String> response = restTemplate.exchange("http://localhost:" + port + "/register/customer/",
				HttpMethod.POST, req, String.class);

		Assertions.assertEquals(messageJson, response.getBody());
	}
	
    
    



}