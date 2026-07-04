package com.example.passwordmanager.model;

public class PasswordEntry {
	
	private String serviceName;
	private String userName;
	private String password;
	
	public PasswordEntry(String serviceName, String userName, String password) {
		this.serviceName = serviceName;
		this.userName = userName;
		this.password = password;
	}
	
	public String getServiceName() {
		return serviceName;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public String getPassword() {
		return password;
	}
}
