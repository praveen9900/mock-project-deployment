package com.demo.spring.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Customer;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;

@Service
public class EmployeeService {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	AccountRepository accountRepo;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Customer getCustomerDetails(Integer customerId) {
		if (customerRepo.existsById(customerId)) {
			logger.info("Customer Details of customer Id {}", customerId);
			return customerRepo.getDetailsById(customerId);
		} else {
			logger.error("Customer Not Found");
			throw new CustomerNotFoundException();
		}

	}

	public List<Customer> getCustomerList() {
		logger.info("All Customer Details");
		return customerRepo.findAll();
	}

}
