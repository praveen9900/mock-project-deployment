package com.demo.spring.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Account;

public interface CustomerRepository extends JpaRepository<Account, Integer> {

	@Query("update Account a set a.balance=:balance where a.accountNumber=:accountNumber")
	@Transactional
	@Modifying
	public Integer updateBalance(Double balance, Integer accountNumber);

	@Query("select a from Account a where a.customerId=:customerId")
	public List<Account> listAccountsByUserId(Integer customerId);

}
