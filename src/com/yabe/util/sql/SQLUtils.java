package com.yabe.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLUtils {
	public static void closeQuitely(ResultSet rs){
		try{
			rs.close();
		}catch(SQLException e){
			
		}
	}
	public static void closeQuitely(Statement stmt){
		try{
			stmt.close();
		}catch(SQLException e){
			
		}
	}
	public static void closeQuitely(Connection conn){
		try{
			conn.close();
		}catch(SQLException e){
			
		}
	}
}
