package com.demo.spring.service;

import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dto.StatementInput;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Statement;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.repository.RegisterCustomerRepository;
import com.demo.spring.repository.StatementRepo;

@Service
public class StatementService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	StatementRepo statementRepo;

	@Autowired
	RegisterCustomerRepository registerCustomerRepo;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public List<Statement> getStatements(StatementInput statement) {
		Optional<Account> accountOp = customerRepository.findById(statement.getAccountNumber());
		Optional<Customer> customer = registerCustomerRepo.findById(statement.getCustomerId());
		if (customer.isPresent()) {
			if (accountOp.isPresent()&& accountOp.get().getCustomerId().equals(statement.getCustomerId())) {
				logger.info("Fetch all statement from {} to {}", statement.getAccountNumber(), statement.getToDate());
				return statementRepo.fetchStatements(statement.getAccountNumber(), statement.getFromDate(),
						statement.getToDate());
			} else {
				logger.info("Acount with {} doesn't exists", statement.getAccountNumber());
				throw new AccountNotFoundException();
			}
		} else {
			logger.error("Customer with id {} doesn't exists", statement.getCustomerId());
			throw new CustomerNotFoundException();
		}
	}
	

}
