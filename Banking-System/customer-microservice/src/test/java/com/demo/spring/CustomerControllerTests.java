package com.demo.spring;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
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

import com.demo.spring.dto.CustomerDTO;
import com.demo.spring.dto.StatementInput;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Statement;
import com.demo.spring.entity.Transaction;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.repository.RegisterCustomerRepository;
import com.demo.spring.repository.StatementRepo;
import com.demo.spring.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CustomerControllerTests {

	@Autowired
	MockMvc mvc;

	@MockBean
	CustomerRepository customerRepo;

	@MockBean
	StatementRepo statementRepo;

	@MockBean
	RegisterCustomerRepository registerCustomerRepo;

	@Test
	void testCreateAccount() throws Exception {

		Account account = new Account(10700, "Cureent", "Bengaluru", 107970.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		mvc.perform(post("/customer/createAccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Account Created"));

	}

	@Test
	void testCreateAccountFailure() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 107970.0, "Active", 20);

		ObjectMapper objectMapper = new ObjectMapper();
		String accountJson = objectMapper.writeValueAsString(account);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.empty());
		mvc.perform(post("/customer/createAccount").content(accountJson).contentType(MediaType.APPLICATION_JSON))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));

	}

	@Test
	void testRegisterCustomer() throws Exception {

		Customer customer = new Customer(10, "Sanju", "singh", "abc@gmail.com");

		when(registerCustomerRepo.save(customer)).thenReturn(customer);
		ObjectMapper objectMapper = new ObjectMapper();

		String customerJson = objectMapper.writeValueAsString(customer);
		mvc.perform(post("/customer/newcust").contentType(MediaType.APPLICATION_JSON_VALUE).content(customerJson))
				.andExpect(jsonPath("$.status").value("Customer Registered Sucessfully"));

	}

	@Test
	void testDeposit() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 12000.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		Transaction transaction = new Transaction(10700, 12000.0, "20221016", 20);
		ObjectMapper objectMapper = new ObjectMapper();
		String transJson = objectMapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));
		when(customerRepo.updateBalance(12000.0, 10700)).thenReturn(1);

		mvc.perform(patch("/customer/deposit").content(transJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andDo(print())
				.andExpect(status().isOk()).andExpect(jsonPath("$.status").value("Balance is " + 24000.0));

	}

	@Test
	void testDepositFailure() throws Exception {

		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		Transaction transaction = new Transaction(10700, 12000.0, "20221016", 20);
		ObjectMapper objectMapper = new ObjectMapper();
		String transJson = objectMapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.empty());
		when(customerRepo.updateBalance(12000.0, 10700)).thenReturn(1);

		mvc.perform(patch("/customer/deposit").content(transJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));

	}

	@Test
	void testDepositCustomerFailure() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 12000.0, "Active", 20);

		Transaction transaction = new Transaction(10700, 12000.0, "20221016", 20);
		ObjectMapper objectMapper = new ObjectMapper();
		String transJson = objectMapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(30)).thenReturn(Optional.empty());
		when(customerRepo.findById(1020)).thenReturn(Optional.of(account));
		when(customerRepo.updateBalance(12000.0, 1020)).thenReturn(1);

		mvc.perform(patch("/customer/deposit").content(transJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));
	}

	@Test
	void testGetBalance() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 12000.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		ObjectMapper mapper = new ObjectMapper();
		String balanceJson = mapper.writeValueAsString(account);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));
		mvc.perform(post("/customer/checkbalance").content(balanceJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance is " + 12000.0));

	}

	@Test
	void testGetBalanceFailure() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 12000.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		ObjectMapper mapper = new ObjectMapper();
		String balanceJson = mapper.writeValueAsString(account);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.empty());
		mvc.perform(post("/customer/checkbalance").content(balanceJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));
	}

	@Test
	void testGetBalanceCustomerFailure() throws Exception {
		Account account = new Account(10700, "Cureent", "Bengaluru", 12000.0, "Active", 20);

		ObjectMapper mapper = new ObjectMapper();
		String balanceJson = mapper.writeValueAsString(account);
		when(registerCustomerRepo.findById(30)).thenReturn(Optional.empty());
		when(customerRepo.findById(1000)).thenReturn(Optional.of(account));
		mvc.perform(post("/customer/checkbalance").content(balanceJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));

	}

	@Test
	void testWithdraw() throws Exception {

		Account account = new Account(10700, "Cureent", "Bengaluru", 12000.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		Transaction transaction = new Transaction(10700, 1000.0, "20221016", 20);

		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));
		when(customerRepo.updateBalance(12000.0, 10700)).thenReturn(1);
		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance is " + 11000.0));
	}

	@Test
	void testWithdrawFailure() throws Exception {

		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		Transaction transaction = new Transaction(10700, 12000.0, "20221016", 20);

		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.empty());
		when(customerRepo.updateBalance(1000.0, 10700)).thenReturn(1);
		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));
	}

	@Test
	void testWithDrawFailureLowBalance() throws Exception {

		Account account = new Account(10700, "Cureent", "Bengaluru", 100.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		Transaction transaction = new Transaction(10700, 1000.0, "20221016", 20);

		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);

		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));

		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Low-Balance"));
	}

	@Test
	void testWithdrawCustomerFailure() throws Exception {

		Account account = new Account(10700, "Cureent", "Bengaluru", 100.0, "Active", 20);

		Transaction transaction = new Transaction(10700, 1000.0, "20221016", 20);

		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);

		when(registerCustomerRepo.findById(20)).thenReturn(Optional.empty());
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));

		mvc.perform(patch("/customer/withdraw").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));
	}

	@Test
	void testTransfer() throws Exception {

		Transaction transaction = new Transaction(10700, 1000.0, 10800, "20221016", 20);
		Account account1 = new Account(10700, "Cureent", "Bengaluru", 10000.0, "Active", 20);
		Account account2 = new Account(10800, "Cureent", "Delhi", 15000.0, "Active", 30);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account1));
		when(customerRepo.findById(10800)).thenReturn(Optional.of(account2));
		when(customerRepo.updateBalance(1000.0, 10700)).thenReturn(1);
		when(customerRepo.updateBalance(13000.0, 10800)).thenReturn(1);
		mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Balance is " + 9000.0));
	}

	@Test
	void testTransferFailureSender() throws Exception {

		Transaction transaction = new Transaction(10700, 1000.0, 10800, "20221016", 20);

		Account account2 = new Account(10800, "Cureent", "Delhi", 15000.0, "Active", 30);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.empty());
		when(customerRepo.findById(10800)).thenReturn(Optional.of(account2));
		when(customerRepo.updateBalance(11000.0, 10700)).thenReturn(1);
		when(customerRepo.updateBalance(13000.0, 10800)).thenReturn(1);
		mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));
	}

	@Test
	void testTransferFailureReceiver() throws Exception {

		Transaction transaction = new Transaction(10700, 1000.0, 10800, "20221016", 20);
		Account account1 = new Account(10700, "Cureent", "Bengaluru", 10000.0, "Active", 20);

		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account1));
		when(customerRepo.findById(10800)).thenReturn(Optional.empty());
		when(customerRepo.updateBalance(11000.0, 10700)).thenReturn(1);
		when(customerRepo.updateBalance(13000.0, 10800)).thenReturn(1);
		mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Recipient Account Number"));
	}

	@Test
	void testTransferFailureLowBalance() throws Exception {

		Transaction transaction = new Transaction(10700, 1000.0, 10800, "20221016", 20);
		Account account1 = new Account(10700, "Cureent", "Bengaluru", 500.0, "Active", 20);
		Account account2 = new Account(10800, "Cureent", "Delhi", 15000.0, "Active", 30);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account1));
		when(customerRepo.findById(10800)).thenReturn(Optional.of(account2));
		mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Low-Balance"));
	}

	@Test
	void testTransferFailureCustomerFailure() throws Exception {

		Transaction transaction = new Transaction(10700, 1000.0, 10800, "20221016", 20);
		Account account1 = new Account(10700, "Cureent", "Bengaluru", 500.0, "Active", 20);
		Account account2 = new Account(10800, "Cureent", "Delhi", 15000.0, "Active", 30);

		ObjectMapper mapper = new ObjectMapper();

		String transactionJson = mapper.writeValueAsString(transaction);
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.empty());
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account1));
		when(customerRepo.findById(10800)).thenReturn(Optional.of(account2));
		mvc.perform(patch("/customer/transfer").content(transactionJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));
	}

	@Test
	void testFetchStatement() throws Exception {
		Account account = new Account(10700, "Current", "Bengaluru", 100.0, "Active", 20);
		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		StatementInput statement = new StatementInput(10700, "20221015", "20221020", 20);

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(statement);

		List<Statement> list = new ArrayList<>();
		list.add(new Statement(10700, "Withdraw", 15000.0, 29000.0, "20221016"));
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));
		when(statementRepo.fetchStatements(10700, "20221015", "20221020")).thenReturn(list);
		mvc.perform(post("/customer/liststatements").content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE)).andExpect(content().json(
						"[ {'accountNumber': 10700,'transactionType': 'Withdraw','amount': 15000.0,'balance': 29000.0,'date': '20221016'}]"));
	}

	@Test
	void testFetchStatementFailure() throws Exception {

		Customer customer = new Customer(20, "Ram", "K", "fko@gmail.com");
		StatementInput statement = new StatementInput(10700, "20221015", "20221020", 20);

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(statement);

		List<Statement> list = new ArrayList<>();
		list.add(new Statement(10700, "Withdraw", 15000.0, 29000.0, "20221016"));
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.of(customer));
		when(customerRepo.findById(10700)).thenReturn(Optional.empty());
		when(statementRepo.fetchStatements(10700, "20221015", "20221020")).thenReturn(list);
		mvc.perform(post("/customer/liststatements").content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE))

				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Incorrect Account Number"));
	}

	@Test
	void testFetchStatementCustomerFailure() throws Exception {
		Account account = new Account(10700, "Current", "Bengaluru", 100.0, "Active", 20);

		StatementInput statement = new StatementInput(10700, "20221015", "20221020", 20);

		ObjectMapper mapper = new ObjectMapper();
		String inputJson = mapper.writeValueAsString(statement);

		List<Statement> list = new ArrayList<>();
		list.add(new Statement(10700, "Withdraw", 15000.0, 29000.0, "20221016"));
		when(registerCustomerRepo.findById(20)).thenReturn(Optional.empty());
		when(customerRepo.findById(10700)).thenReturn(Optional.of(account));
		when(statementRepo.fetchStatements(10700, "20221015", "20221020")).thenReturn(list);
		mvc.perform(post("/customer/liststatements").content(inputJson).contentType(MediaType.APPLICATION_JSON_VALUE))
				.andDo(print()).andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
				.andExpect(jsonPath("$.status").value("Customer Not Found"));
	}

	@Test
	void customerTest() {
		CustomerDTO customerDTO = new CustomerDTO();
		customerDTO.setCustomerId(19);
		customerDTO.setCustomerFirstName("Vinay");
		customerDTO.setCustomerLastName("Kenduli");
		customerDTO.setEmail("vinayK@gmail.com");

		assertEquals(19, customerDTO.getCustomerId());
		assertEquals("Vinay", customerDTO.getCustomerFirstName());
		assertEquals("Kenduli", customerDTO.getCustomerLastName());
		assertEquals("vinayK@gmail.com", customerDTO.getEmail());

	}

	@Test
	void testMessage() {
		Message message = new Message();
		message.setStatus("Hello");

		assertEquals("Hello", message.getStatus());
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
}
