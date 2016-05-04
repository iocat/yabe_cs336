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
	
	//Constructor for Desktop
	public Desktop(String itemId,int ram, String brandName, float weight, String operatingSystem, 
			String screenType, float screenWidth, float screenHeight, int screenResX,
			int screenResY, float width, float height, float depth, String color, int batteryCapacity){
		
		super(itemId,ram,brandName,weight,operatingSystem,screenType,screenWidth,screenHeight,screenResX,
				screenResY,width,height,depth,color,batteryCapacity);
	}
	
	private final String SQL_CREATE_DESKTOP = "INSERT INTO "
			+ "desktop(itemId) "
			+ "VALUES "
			+ " (?)";
	
	/*
	 * insertIntoDB() inserts this desktop data into the database
	 */
	public boolean insertIntoDB() throws SQLException {
		super.insertIntoDB();
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_DESKTOP);
			stmt.setString(1, this.getItemId());
			// Optionally receives
			rows = stmt.executeUpdate();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		return rows == 1;
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
