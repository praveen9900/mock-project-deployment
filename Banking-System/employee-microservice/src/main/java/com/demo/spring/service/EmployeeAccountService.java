package com.demo.spring.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.repository.AccountRepository;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.util.Message;

@Service
public class EmployeeAccountService {

	@Autowired
	AccountRepository accountRepo;

	@Autowired
	CustomerRepository customerRepo;

	
	
	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Message checkActiveStatus(int accountNumber) {

		Optional<Account> account = accountRepo.findById(accountNumber);
		if (account.isPresent()) {
			if (accountRepo.activityById(accountNumber).equals("Active")) {

				account.get().setStatus("Deactive");
				accountRepo.save(account.get());
				logger.info("Account is deactivated with accountNumber {}", accountNumber);
				return new Message("Account is deactivated");

			} else {
				logger.info("Account is in Deactive Mode");
				return new Message("Account is already deactive");
			}
		} else {
			throw new AccountNotFoundException();
		}
	}

	public Message checkdeActiveStatus(int accountNumber) {

		Optional<Account> account = accountRepo.findById(accountNumber);
		if (account.isPresent()) {
			if (accountRepo.activityById(accountNumber).equals("Deactive")) {
				account.get().setStatus("Active");
				accountRepo.save(account.get());
				logger.info("Account is activated with accountNumber {}", accountNumber);
				return new Message("Account is activated");

			} else {
				logger.info("Account is in Active Mode");
				return new Message("Account is already active");

			}
		} else {
			logger.error("Account Not Found With AccountNumber {} ", accountNumber);
			throw new AccountNotFoundException();

		}

	}

	public Message openAccount(Account account) {
		Optional<Customer> customerOp = customerRepo.findById(account.getCustomerId());
		if (customerOp.isPresent()) {
			accountRepo.save(account);
			logger.info("Account is Created with accountNumber {}", account.getAccountNumber());
			return new Message("Account Created");
		} else {
			logger.error("Customer Not Found");
			throw new CustomerNotFoundException();
		}

	}

}
