// A singleton class for Database connection to mysql
package com.yabe;

public class DBConnector {
	private static final String CLASS_PROJECT_DB= "jdbc:mysql://classvm123./yabe";
	private static final String USERNAME = "root";
	private static final String PASSWORD ="UFNOisduen7";
	
	private static String projectDB = CLASS_PROJECT_DB;
	/* Singleton */
	private DBConnector instance;
	public DBConnector getInstance(){
		return instance;
	}
	
	
	
}
