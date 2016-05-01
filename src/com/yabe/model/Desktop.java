package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Desktop extends Computer implements Updatable, Retrievable {

	public Desktop(String itemId) {
		super(itemId);
	}
	final static String SQL_RETRIEVE ="SELECT * FROM desktop WHERE itemId = ?";
	public boolean retrieve(){
		boolean found = false ;
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt  = conn.prepareStatement(SQL_RETRIEVE);
			stmt.setString(1, this.getItemId());
			found = stmt.executeQuery().next();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		if(found){
			super.retrieve();
		}
		return found;
	}
	
	public void update(){
		super.update();
		//TODO
	}
	
}
