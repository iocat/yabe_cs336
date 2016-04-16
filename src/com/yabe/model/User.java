package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yabe.util.DBConnector;
import com.yabe.util.Utils;
public class User extends Account {
	private final String SQL_CREATE_USER = "INSERT INTO "
			+ "user(username,name,email,address)"
			+ " VALUES (?, ?, ?, ?) ";
	
	private String name;
	private String email;
	private String address;
	
	/* ACCESSORS */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	// Constructor
	public User(String username, String password, 
			String name,String email,String address) {
		super(username, password);
		this.name = name;
		this.email = email;
		this.address= address;
	}
	
	public User(String username){
		super(username);
	}
	
	/*
	 * insertIntoDB() inserts this user data into the database
	 */
	public boolean insertIntoDB() throws SQLException{
		super.insertIntoDB();
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_USER);
			stmt.setString(1, this.getUsername());
			stmt.setString(2, this.name);
			stmt.setString(3, this.email);
			stmt.setString(4, this.address);
			// Optionally receives
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
	
	public void retrieveData(){
		final String SQL_RETRIEVE_USER = "SELECT name, password, email, address "
				+ "FROM account NATURAL JOIN user "
				+ "WHERE username = ?";
		Connection conn =null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_USER);
			stmt.setString(1,this.getUsername() );
			rs = stmt.executeQuery();
			if (rs.next()){
				this.name=rs.getString(1);
				this.setPassword(rs.getString(2));
				this.email = rs.getString(3);
				this.address=rs.getString(4);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			if (conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (stmt!=null){
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
