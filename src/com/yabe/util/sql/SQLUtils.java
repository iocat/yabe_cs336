package com.yabe.util.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class SQLUtils {
	public static void closeQuitely(ResultSet rs) {
		try {
			rs.close();
		} catch (SQLException e) {

		}
	}

	public static void closeQuitely(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {

		}
	}

	public static void closeQuitely(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {

		}
	}
	
	public static PreparedStatement constructPreparedStatement(PreparedStatement stmt, ArrayList<Object> params) 
			throws SQLException{
		int i = 1;
		for (Object param: params){
			if(param instanceof Date){
				stmt.setDate(i++, (java.sql.Date) param);
			}else if (param instanceof Integer){
				stmt.setInt(i++, (Integer) param);
			}else if(param instanceof Long){
				stmt.setLong(i++, (Long) param);
			} else if (param instanceof Double) {
		        stmt.setDouble(i++, (Double) param);
		    } else if (param instanceof Float) {
		        stmt.setFloat(i++, (Float) param);
		    } else {
		        stmt.setString(i++, (String) param);
		    }
		}
		
		
		
		return stmt;
		
	}
}
