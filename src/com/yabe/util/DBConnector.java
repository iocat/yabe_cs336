// A singleton class for Database connection to mysql
package com.yabe.util;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;





public class DBConnector {
	private static final String CLASS_PROJECT_DB= "jdbc:mysql://127.0.0.1/yabe7";
	
	private static final String USERNAME = "root";
	private static final String PASSWORD ="";
	/* AWizN283lNN */
	
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
	
	
	/* Singleton 
	private static DBConnector instance;*/
	/*
	private static Connection conn;
	public static Connection getConnection(){
		return getInstance().conn;
	}
	/* A function which is used to close the database connection
	 * Only call when the server shuts down */
	/*public static void closeConnection(){
		try {
			conn.close();
		} catch (SQLException e) {
			System.out.println("Unable to close the connection");
		}
		
	}
	
	private DBConnector(String connection, String username, String password){
		try{
			// Load the Driver manager class ( did not user import
			// because it  is not compatible to the old version of jdk )
			Class.forName("com.sql.jdbc.DriverManager");
			// Connect to the server database
			this.conn = DriverManager.getConnection(CLASS_PROJECT_DB,USERNAME,PASSWORD);
		
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static DBConnector getInstance(){
		if (instance==null){
			instance = new DBConnector( CLASS_PROJECT_DB,USERNAME, PASSWORD);
		}
		return instance;
	}
	*/
	
	
	
	
	
	
}
