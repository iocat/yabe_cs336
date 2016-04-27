// A singleton class for Database connection to mysql
package com.yabe.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class DBConnector {
	private static final String CLASS_PROJECT_DB = "jdbc:mysql://127.0.0.1/yabe";

	private static final String USERNAME = "root";
	private static final String PASSWORD = "AWizN283lNN";

	private static String projectDB = CLASS_PROJECT_DB;

	private static HikariDataSource ds ;
	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
		}
		HikariConfig config = new HikariConfig();
		config.setJdbcUrl("jdbc:mysql://localhost:3306/yabe");
		config.setUsername(USERNAME);
		config.setPassword(PASSWORD);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

		ds = new HikariDataSource(config);
		
	}

	public static HikariDataSource getConnectionPool() {
		return ds;
	}
}
