package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yabe.util.DBConnector;

public class Account {
	private String username;
	private String password;
	
	private final String SQL_CREATE_ACCOUNT = "INSERT INTO account(username, password) VALUES "
			+" (?,?)";
	
	/* ACCESSORS */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public Account(String username, String password){
		this.username=username;
		this.password=password;
	}
	
	/*
	 * insertIntoDB() inserts this user data into the account table
	 */
	public boolean insertIntoDB() throws SQLException{
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_ACCOUNT);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rows = stmt.executeUpdate();
		} finally{
			if (stmt!= null){
				stmt.close();
			}
			if(conn!=null){
				conn.close();
			}
		}
		return rows == 1;
	}
	
	
	
}
