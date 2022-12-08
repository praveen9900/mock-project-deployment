package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
	
	@Query(value = "select a.status from  Account a where a.accountNumber= ?1")
	public String activityById(int accountNumber);

}
