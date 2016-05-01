package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Laptop extends Computer implements Updatable{

	public Laptop(String itemId) {
		super(itemId);
	}

	final static String SQL_RETRIEVE ="SELECT * FROM laptop WHERE itemId = ?";
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
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
