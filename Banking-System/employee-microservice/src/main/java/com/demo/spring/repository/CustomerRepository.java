package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

	@Query("select c from Customer c where c.customerId=:customerId")
	public Customer getDetailsById(Integer customerId);

}
