package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yabe.util.DBConnector;

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
	
	public Representative(String username){
		super(username);
	}
	
	public void retrieveData(){
		final String SQL_RETRIEVE_REP = "SELECT password, email "
				+ "FROM account NATURAL JOIN representative "
				+ "WHERE username = ?";
		Connection conn =null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_REP);
			stmt.setString(1,this.getUsername() );
			rs = stmt.executeQuery();
			if (rs.next()){
				this.email=rs.getString(1);
				this.setPassword(rs.getString(2));
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
