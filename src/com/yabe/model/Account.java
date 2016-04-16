package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
	
	public Account(String username){
		this.username=username;
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
	
	public enum AccountType {ADMIN, REPRESENTATIVE, USER, NON_USER};
	
	public static AccountType validate(String username,String password){
		final String admin = "SELECT * FROM admin NATURAL JOIN account WHERE username = ? AND password = ?";
		final String user = "SELECT * FROM user NATURAL JOIN account WHERE username = ? AND password = ?";
		final String rep = "SELECT * FROM representative NATURAL JOIN account WHERE username = ? AND password = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(admin);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()){
				return AccountType.ADMIN;
			}else{
				rs.close();
				stmt.close();
				stmt = conn.prepareStatement(user);
				stmt.setString(1, username);
				stmt.setString(2, password);
				rs = stmt.executeQuery();
				if(rs.next()){
					return AccountType.USER;
				}else{
					rs.close();
					stmt.close();
					stmt = conn.prepareStatement(rep);
					stmt.setString(1,username);
					stmt.setString(2, password);
					rs = stmt.executeQuery();
					if(rs.next()){
						return AccountType.REPRESENTATIVE;
					}
				}
			}
		} catch (SQLException e) {
			
		}finally{
			try {
				if(conn!= null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}		
			try {
				if(stmt!= null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(rs!=null){
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return AccountType.NON_USER;
	}
}
