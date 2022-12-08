package com.demo.spring.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class Users {

	@SequenceGenerator(sequenceName = "user_sequence", initialValue = 100, allocationSize = 1, name = "user_generator")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_generator")
	@Id
	@Column(name = "USERID")
	private Integer userId;

	@Column(name = "USERNAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "USER")
	private String user;

	public Users() {

	}

	public Users(String userName, String password, String user) {
		super();

		this.userName = userName;
		this.password = password;
		this.user = user;
	}
	
	

	public Users(Integer userId, String userName, String password, String user) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.password = password;
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

}
