package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Handheld extends Computer implements Retrievable, Updatable{

	enum SimType{
		STANDARD, MICRO, NANO
	}
	private boolean isTablet;
	public boolean isTablet() {
		return isTablet;
	}

	public void setTablet(boolean isTablet) {
		this.isTablet = isTablet;
	}

	public int getExternalMemoryMaxSize() {
		return externalMemoryMaxSize;
	}

	public void setExternalMemoryMaxSize(int externalMemoryMaxSize) {
		this.externalMemoryMaxSize = externalMemoryMaxSize;
	}

	public String getExternalMemoryType() {
		return externalMemoryType;
	}

	public void setExternalMemoryType(String externalMemoryType) {
		this.externalMemoryType = externalMemoryType;
	}

	public boolean isSimLock() {
		return simLock;
	}

	public void setSimLock(boolean simLock) {
		this.simLock = simLock;
	}

	public String getNetWorkProvider() {
		return netWorkProvider;
	}

	public void setNetWorkProvider(String netWorkProvider) {
		this.netWorkProvider = netWorkProvider;
	}

	public SimType getSimType() {
		return simType;
	}

	public void setSimType(SimType simType) {
		this.simType = simType;
	}

	private int externalMemoryMaxSize;
	private String externalMemoryType;
	private boolean simLock;
	private String netWorkProvider;
	private SimType simType;
	public Handheld(String itemId) {
		super(itemId);
	}

	final static String SQL_RETRIEVE ="SELECT * FROM handheld WHERE itemId = ?";
	public boolean retrieve(){
		boolean found = false ;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt  = conn.prepareStatement(SQL_RETRIEVE);
			stmt.setString(1, this.getItemId());
			rs = stmt.executeQuery();
			if(rs.next()){
				found = true;
				this.externalMemoryMaxSize = rs.getInt("externalMemoryMaxSize");
				this.externalMemoryType = rs.getString("externalMemoryType");
				this.netWorkProvider = rs.getString("networkProvider");
				this.isTablet = rs.getBoolean("hasSimLock");
				this.simLock = rs.getBoolean("hasSimLock");
				switch(rs.getString("simType")){
					case "STANDARD":
						this.simType = SimType.STANDARD;
						break;
					case "MICRO":
						this.simType = SimType.MICRO;
						break;
					case "NANO":
						this.simType = SimType.NANO;
						break;
					default: 
						this.simType = null;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);
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
