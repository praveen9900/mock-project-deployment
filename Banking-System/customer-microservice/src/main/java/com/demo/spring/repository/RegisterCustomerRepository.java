package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Customer;

public interface RegisterCustomerRepository extends JpaRepository<Customer, Integer> {

}
