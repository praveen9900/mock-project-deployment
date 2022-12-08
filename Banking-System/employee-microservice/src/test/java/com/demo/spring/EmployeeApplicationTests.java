package com.demo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class EmployeeApplicationTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	AccountRepository accountRepo;

	@MockBean
	CustomerRepository customerRepo;

	@Test
	void testCreateAccount() throws Exception {
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		Account account = new Account(10700, "Cureent", "Bengaluru", 107970.0, "Active", 20);
		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		when(customerRepo.findById(20)).thenReturn(Optional.of(customer));
		mvc.perform(post("/employee/createaccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account Created"));

	}

	@Test
	void testCreateAccountFailure() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 107970.0, "Active", 20);
		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		when(customerRepo.findById(20)).thenReturn(Optional.empty());
		mvc.perform(post("/employee/createaccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));

	}

	@Test
	void testCheckDeactiveTest() throws Exception {
		Account account = new Account(10200, "Savings", "Noida", 19000.0, "Active", 20);

		String status = "Active";

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepo.findById(10200)).thenReturn(Optional.of(account));
		when(accountRepo.activityById(10200)).thenReturn(status);

		mvc.perform(patch("/employee/deactive").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account is deactivated"));

	}

	@Test
	void testCheckDeactiveTestFailure() throws Exception {
		Account account = new Account(10200, "Savings", "Noida", 19000.0, "Deactive", 20);

		String status = "Deactive";

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepo.findById(10200)).thenReturn(Optional.of(account));
		when(accountRepo.activityById(10200)).thenReturn(status);

		mvc.perform(patch("/employee/deactive").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account is already deactive"));

	}

	@Test
	void testCheckActiveTest() throws Exception {
		Account account = new Account(10200, "Savings", "Noida", 19000.0, "Deactive", 20);

		String status = "Deactive";

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepo.findById(10200)).thenReturn(Optional.of(account));
		when(accountRepo.activityById(10200)).thenReturn(status);

		mvc.perform(patch("/employee/active").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account is activated"));

	}

	@Test
	void testCheckActiveTestFailure() throws Exception {
		Account account = new Account(10500, "Savings", "Noida", 19000.0, "Active", 20);

		String status = "Active";

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepo.findById(10500)).thenReturn(Optional.of(account));
		when(accountRepo.activityById(10500)).thenReturn(status);

		mvc.perform(patch("/employee/active").contentType(MediaType.APPLICATION_JSON).content(accountJson))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account is already active"));

	}

	@Test
	void testCheckActiveTestException() throws Exception {
		Account account = new Account(10500, "Savings", "Noida", 19000.0, "Active", 20);

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepo.findById(10500)).thenReturn(Optional.empty());

		mvc.perform(patch("/employee/active").content(accountJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));

	}

	@Test
	void testCheckDeActiveTestException() throws Exception {
		Account account = new Account(10500, "Savings", "Noida", 19000.0, "Active", 20);

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);

		when(accountRepo.findById(10500)).thenReturn(Optional.empty());

		mvc.perform(patch("/employee/deactive").content(accountJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));

	}

	@Test
	void testAllCustomerDetailsWithCustId() throws Exception {
		Customer customer = new Customer(10, "Praveen", "Patil", "abc@gmail.com");

		when(customerRepo.existsById(10)).thenReturn(true);
		when(customerRepo.getDetailsById(10)).thenReturn(customer);
		mvc.perform(get("/employee/details/10")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						" {'customerId': 10,'customerFirstName': 'Praveen','customerLastName': 'Patil','email': 'abc@gmail.com'}"));

	}

	@Test
	void testGetAllCustomer() throws Exception {
		List<Customer> list = new ArrayList<>();
		list.add(new Customer(10, "Praveen", "Patil", "abc@gmail.com"));
		when(customerRepo.findAll()).thenReturn(list);
		mvc.perform(get("/employee/listallcustomer")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[ {'customerId': 10,'customerFirstName': 'Praveen','customerLastName': 'Patil','email': 'abc@gmail.com'}]"));

	}

	@Test
	void testAllCustomerDetailsWithCustIdFailure() throws Exception {
		List<Customer> list = new ArrayList<>();
		list.add(new Customer(10, "Praveen", "Patil", "abc@gmail.com"));
		when(customerRepo.existsById(10)).thenReturn(false);
		mvc.perform(get("/employee/details/10")).andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));

	}

	@Test
	void Customer() {

		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setCustomerFirstName("Praveen");
		customer.setCustomerLastName("Patil");
		customer.setEmail("abc@gmail.com");

		assertEquals(1, customer.getCustomerId());
		assertEquals("Praveen", customer.getCustomerFirstName());
		assertEquals("Patil", customer.getCustomerLastName());
		assertEquals("abc@gmail.com", customer.getEmail());
	}

	@Test
	void testMessage() {
		Message message = new Message();
		message.setStatus("Hello");

		assertEquals("Hello", message.getStatus());
	}

	@Test
	void testAccount() {
		Account account = new Account();
		account.setCustomerId(111);
		account.setAccountType("FD");
		account.setBalance(1000.0);
		account.setBranch("Ramnagar");
		account.setStatus("active");

		assertEquals("FD", account.getAccountType());
		assertEquals(1000.0, account.getBalance());
		assertEquals("Ramnagar", account.getBranch());
		assertEquals("active", account.getStatus());
		assertEquals(111, account.getCustomerId());

	}
}
