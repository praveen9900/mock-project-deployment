package com.demo.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.demo.spring.entity.Statement;

public interface StatementRepo extends JpaRepository<Statement, Integer> {

	
	
	@Query("select t from Statement t where t.accountNumber=:accountNumber and t.date>=:fromDate and t.date<=:toDate")
    public List<Statement> fetchStatements(Integer accountNumber, String fromDate,String toDate);

}
