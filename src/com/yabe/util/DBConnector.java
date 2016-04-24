// A singleton class for Database connection to mysql
package com.yabe.util;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;





public class DBConnector {
	private static final String CLASS_PROJECT_DB= "jdbc:mysql://127.0.0.1/yabe";
	
	private static final String USERNAME = "root";
	private static final String PASSWORD ="AWizN283lNN";
	
	private static String projectDB = CLASS_PROJECT_DB;
	
	private static BasicDataSource bds = new BasicDataSource();
	static{
		try{
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}finally{
			
		}
		bds.setDriverClassName("com.mysql.jdbc.Driver");
		bds.setUrl(CLASS_PROJECT_DB);
		bds.setUsername(USERNAME);
		bds.setPassword(PASSWORD);
	}
	public static BasicDataSource getConnectionPool(){
		return bds;
	}
}
