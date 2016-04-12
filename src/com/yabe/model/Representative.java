package com.yabe.model;

public class Representative extends Account{

	public Representative(String username, String password) {
		super(username, password);
		// TODO Auto-generated constructor stub
	}
	private String email;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Representative(String username, String password,String email) {
		super(username, password);
		this.email = email;
	}

}
