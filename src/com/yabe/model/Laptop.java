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
	
	//Constructor for Laptop
	public Laptop(String itemId,int ram, String brandName, float weight, String operatingSystem, 
			String screenType, float screenWidth, float screenHeight, int screenResX,
			int screenResY, float width, float height, float depth, String color, int batteryCapacity){
		
		super(itemId,ram,brandName,weight,operatingSystem,screenType,screenWidth,screenHeight,screenResX,
				screenResY,width,height,depth,color,batteryCapacity);
	}
	
	private final String SQL_CREATE_LAPTOP = "INSERT INTO "
			+ "laptop(itemId) "
			+ "VALUES "
			+ " (?)";
	
	/*
	 * insertIntoDB() inserts this laptop data into the database
	 */
	public boolean insertIntoDB() throws SQLException {
		super.insertIntoDB();
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_LAPTOP);
			stmt.setString(1, this.getItemId());
			// Optionally receives
			rows = stmt.executeUpdate();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		return rows == 1;
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
