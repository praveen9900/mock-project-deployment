package com.demo.spring.service;

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.spring.dto.CredentialsDTO;
import com.demo.spring.entity.Users;
import com.demo.spring.repository.LoginRepository;
import com.demo.spring.util.Message;

@Service
public class LoginService {

	@Autowired
	LoginRepository loginRepo;

	private Logger logger = LogManager.getLogger(this.getClass().getName());

	public Message saveCredentials(Users user) {

		loginRepo.save(user);
		logger.info("Customer is Registered Successfully");
		return new Message("User Credentials Saved");
	}

	
	public Message loginToAccount(CredentialsDTO credentialsDTO) {
		Optional<Users> userOp = loginRepo.findById(credentialsDTO.getUserId());
		if (userOp.isPresent()) {
			if (credentialsDTO.getPassword().equals(userOp.get().getPassword())
					&& credentialsDTO.getUserType().equals(userOp.get().getUser())) {
				if (credentialsDTO.getUserType().equalsIgnoreCase("customer")) {
					logger.info("login successfull");
					return new Message("customer");
				} else if (credentialsDTO.getUserType().equalsIgnoreCase("employee")) {
					logger.info("Login Success");
					return new Message("employee");

				}
			} else {
				logger.info("Login Failure");
				return new Message("Login Fail...retry again");
			}
		}
		logger.info("Provide correct password or username");
		return new Message("Incorrect Username or Password");
	}

}

