package com.demo.spring.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dto.BalanceInput;
import com.demo.spring.entity.Account;
import com.demo.spring.entity.Customer;
import com.demo.spring.entity.Statement;
import com.demo.spring.entity.Transaction;
import com.demo.spring.exception.AccountNotFoundException;
import com.demo.spring.exception.CustomerNotFoundException;
import com.demo.spring.exception.LowBalanceException;
import com.demo.spring.exception.ReceiverAccountNotFoundException;
import com.demo.spring.repository.CustomerRepository;
import com.demo.spring.repository.RegisterCustomerRepository;
import com.demo.spring.repository.StatementRepo;
import com.demo.spring.util.Message;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	RegisterCustomerRepository registerCustomerRepo;

	@Autowired
	StatementRepo statementRepo;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Message create(Account account) {
		Optional<Customer> customerOp = registerCustomerRepo.findById(account.getCustomerId());
		if (customerOp.isPresent()) {

			customerRepository.save(account);
			logger.info("Account Created With Customer ID {}", account.getCustomerId());
			return new Message("Account Created");
		} else {
			logger.error("Customer Doesn't Exists");
			throw new CustomerNotFoundException();

		}
	}

	public Message registerCustomer(Customer customer) {

		registerCustomerRepo.save(customer);
		logger.info("Customer Registered Succesfully");
		return new Message("Customer Registered Sucessfully");

	}

	public Message deposit(Transaction transaction) {

		Optional<Account> account = customerRepository.findById(transaction.getFromAccountNumber());
		Optional<Customer> customer = registerCustomerRepo.findById(transaction.getCustomerId());
		if (customer.isPresent()) {
			if (account.isPresent() && account.get().getCustomerId().equals(transaction.getCustomerId())) {
				Double updatedBalance = account.get().getBalance() + transaction.getAmount();
				customerRepository.updateBalance(updatedBalance, transaction.getFromAccountNumber());
				Statement statement = new Statement();
				statement.setAccountNumber(transaction.getFromAccountNumber());
				statement.setAmount(transaction.getAmount());
				statement.setBalance(updatedBalance);
				statement.setTransactionType("credit");
				statement.setDate(transaction.getDate());
				statementRepo.save(statement);
				logger.info("Amount deposited successfully. Balance is {} ", updatedBalance);
				return new Message("Balance is " + updatedBalance);
			} else {
				logger.error("Account number {} doesn't exists", transaction.getFromAccountNumber());
				throw new AccountNotFoundException();
			}
		} else {
			logger.error("Customer with id {} doesn't exists", transaction.getCustomerId());
			throw new CustomerNotFoundException();
		}
	}

	public Message checkBalance(BalanceInput balanceInput) {
		Optional<Account> accountOp = customerRepository.findById(balanceInput.getAccountNumber());
		Optional<Customer> customer = registerCustomerRepo.findById(balanceInput.getCustomerId());
		if (customer.isPresent()) {
			if (accountOp.isPresent() && accountOp.get().getCustomerId().equals(balanceInput.getCustomerId())) {
				logger.info("Remaining Balance is {} ", accountOp.get().getBalance());
				return new Message("Balance is " + accountOp.get().getBalance());
			} else {
				logger.error("Account {} doesn't exists", balanceInput.getAccountNumber());
				throw new AccountNotFoundException();
			}
		} else {
			logger.error("Customer with id {} doesn't exists", balanceInput.getCustomerId());
			throw new CustomerNotFoundException();
		}

	}

	public Message withdraw(Transaction transaction) {
		Optional<Account> account = customerRepository.findById(transaction.getFromAccountNumber());
		Optional<Customer> customer = registerCustomerRepo.findById(transaction.getCustomerId());
		if (customer.isPresent()) {
			if (account.isPresent() && account.get().getCustomerId().equals(transaction.getCustomerId())) {
				if (account.get().getBalance() < transaction.getAmount()) {
					throw new LowBalanceException();
				} else {
					Double updatedBalance = account.get().getBalance() - transaction.getAmount();
					customerRepository.updateBalance(updatedBalance, transaction.getFromAccountNumber());
					Statement statement = new Statement();
					statement.setAccountNumber(transaction.getFromAccountNumber());
					statement.setAmount(transaction.getAmount());
					statement.setBalance(updatedBalance);
					statement.setTransactionType("debit");
					statement.setDate(transaction.getDate());
					statementRepo.save(statement);
					logger.info("Amount withdraw successfully.Balance is {}", updatedBalance);
					return new Message("Balance is " + updatedBalance);
				}
			} else {
				logger.error("Account {} doen't exists", transaction.getFromAccountNumber());
				throw new AccountNotFoundException();
			}
		} else {
			logger.error("Customer with id {} doesn't exists", transaction.getCustomerId());
			throw new CustomerNotFoundException();
		}
	}

	public Message transfer(Transaction transaction) {
		Optional<Account> senderAccount = customerRepository.findById(transaction.getFromAccountNumber());
		Optional<Account> receiverAccount = customerRepository.findById(transaction.getToAccountNumber());
		Optional<Customer> sendercustomer = registerCustomerRepo.findById(transaction.getCustomerId());

		if (sendercustomer.isPresent()) {
			if (senderAccount.isPresent() && senderAccount.get().getCustomerId().equals(transaction.getCustomerId())) {
				if (receiverAccount.isPresent()) {

					if (senderAccount.get().getBalance() < transaction.getAmount()) {
						logger.error("Balance is low");
						throw new LowBalanceException();

					} else {
						Double senderBalance = senderAccount.get().getBalance() - transaction.getAmount();

						Double receiverBalance = receiverAccount.get().getBalance() + transaction.getAmount();

						customerRepository.updateBalance(senderBalance, transaction.getFromAccountNumber());
						customerRepository.updateBalance(receiverBalance, transaction.getToAccountNumber());
						Statement statement = new Statement(senderAccount.get().getAccountNumber(), "debit",
								transaction.getAmount(), senderBalance, transaction.getDate());
						statementRepo.save(statement);
						Statement statement2 = new Statement(receiverAccount.get().getAccountNumber(), "credit",
								transaction.getAmount(), receiverBalance, transaction.getDate());
						statementRepo.save(statement2);

						logger.info("Remaining sender balance is {} ", senderBalance);
						return new Message("Balance is " + senderBalance);
		
					}

				} else {
					logger.error("Reciever Account {} doen't exists", transaction.getToAccountNumber());
					throw new ReceiverAccountNotFoundException();
				}

			} else {
				logger.error("Account {} doen't exists", transaction.getFromAccountNumber());
				throw new AccountNotFoundException();
			}
		} else {
			logger.error("Customer with id {} doesn't exists", transaction.getCustomerId());
			throw new CustomerNotFoundException();
		}

	}

}