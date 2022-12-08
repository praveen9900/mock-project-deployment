package com.demo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.entity.Users;
import com.demo.spring.util.Message;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

class LoginApplicationTests {

	@Autowired
	TestRestTemplate testRestTemplate;

	@LocalServerPort
	Integer port;

	@Test
	void testLoginCustomer() throws Exception {
		CredentialsDTO input = new CredentialsDTO(100, "123", "customer");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/register/",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("customer", resp2.getBody().getStatus());
	}

	@Test
	void testLoginEmployee() throws Exception {
		CredentialsDTO input = new CredentialsDTO(105, "456", "employee");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/register/",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("employee", resp2.getBody().getStatus());
	}

	@Test
	void testCredentialFailure() throws Exception {
		CredentialsDTO input = new CredentialsDTO(105, "root", "employee");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/register/",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("Login Fail...retry again", resp2.getBody().getStatus());
	}

	@Test
	void testUserNotFound() throws Exception {
		CredentialsDTO input = new CredentialsDTO(1246, "root", "customer");
		HttpHeaders headers = new HttpHeaders();
		headers.set("Content-type", MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<CredentialsDTO> req = new HttpEntity<>(input, headers);
		ResponseEntity<Message> resp2 = testRestTemplate.exchange("http://localhost:" + port + "/register/",
				HttpMethod.POST, req, Message.class);
		Assertions.assertEquals("Incorrect Username or Password", resp2.getBody().getStatus());
	}
	
	@Test
    void credentialTest() {
        CredentialsDTO credentialDTO = new CredentialsDTO();
        credentialDTO.setUserId(12);
        credentialDTO.setPassword("root");
        credentialDTO.setUserType("customer");
        
        assertEquals(12, credentialDTO.getUserId());
        assertEquals("root", credentialDTO.getPassword());
        assertEquals("customer", credentialDTO.getUserType());
        
        
    }
    
    @Test
    void customerTest() {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(19);
        customerDTO.setCustomerFirstName("Vinay");
        customerDTO.setCustomerLastName("Kenduli");
        customerDTO.setEmail("vinayK@gmail.com");
        
        assertEquals(19,customerDTO.getCustomerId());
        assertEquals("Vinay",customerDTO.getCustomerFirstName());
        assertEquals("Kenduli",customerDTO.getCustomerLastName());
        assertEquals("vinayK@gmail.com",customerDTO.getEmail());
        
    }
    
    @Test
    void userTest() {
    	Users user = new Users();
    	user.setUserId(10);
    	user.setPassword("root");
    	user.setUser("customer");
    	
    	 assertEquals(10,user.getUserId());
         assertEquals("root",user.getPassword());
         assertEquals("customer",user.getUser());
    }
    
   

}
