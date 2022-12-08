package com.demo.spring.dto;

public class CredentialsDTO {

	private Integer userId;
	private String password;
	private String userType;

	public CredentialsDTO() {

	}

	public CredentialsDTO(Integer userId, String password, String userType) {
		super();
		this.userId = userId;
		this.password = password;
		this.userType = userType;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

}
