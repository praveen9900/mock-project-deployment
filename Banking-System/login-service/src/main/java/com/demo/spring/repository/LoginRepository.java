package com.demo.spring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.spring.entity.Users;

public interface LoginRepository extends JpaRepository<Users, Integer> {

}
