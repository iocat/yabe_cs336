package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.yabe.util.DBConnector;
import com.yabe.util.SQLUtils;

public class Account implements Retrievable {
	private String username;
	private String password;
	private String sessionId;

	private final String SQL_CREATE_ACCOUNT = "INSERT INTO account(username, password) VALUES "
			+ " (?,?)";

	/* ACCESSORS */
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Account(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public Account(String username) {
		this.username = username;
	}

	/*
	 * insertIntoDB() inserts this user data into the account table
	 */
	public boolean insertIntoDB() throws SQLException {
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_ACCOUNT);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rows = stmt.executeUpdate();
		} finally {
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return rows == 1;
	}

	public enum AccountType {
		ADMIN, REPRESENTATIVE, USER, NON_USER
	};

	public static AccountType validate(String username, String password) {
		final String admin = "SELECT * FROM admin NATURAL JOIN account WHERE username = ? AND password = ?";
		final String user = "SELECT * FROM user NATURAL JOIN account WHERE username = ? AND password = ?";
		final String rep = "SELECT * FROM representative NATURAL JOIN account WHERE username = ? AND password = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(admin);
			stmt.setString(1, username);
			stmt.setString(2, password);
			rs = stmt.executeQuery();
			if (rs.next()) {
				return AccountType.ADMIN;
			} else {
				rs.close();
				stmt.close();
				stmt = conn.prepareStatement(user);
				stmt.setString(1, username);
				stmt.setString(2, password);
				rs = stmt.executeQuery();
				if (rs.next()) {
					return AccountType.USER;
				} else {
					rs.close();
					stmt.close();
					stmt = conn.prepareStatement(rep);
					stmt.setString(1, username);
					stmt.setString(2, password);
					rs = stmt.executeQuery();
					if (rs.next()) {
						return AccountType.REPRESENTATIVE;
					}
				}
			}
		} catch (SQLException e) {

		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (stmt != null) {
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return AccountType.NON_USER;
	}

	public void storeSession(String sessionId) {
		final String storeQuery = "UPDATE account SET sessionId = ? WHERE username = ?";
		PreparedStatement stmt = null;
		Connection conn = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(storeQuery);
			stmt.setString(1, sessionId);
			stmt.setString(2, this.username);
			stmt.executeUpdate();

		} catch (SQLException e) {
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
	}

	public void retrieveData() {
		final String SQL_RETRIEVE_ACCOUNT = "SELECT username, password, sessionId "
				+ " FROM account " + " WHERE username = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_ACCOUNT);
			stmt.setString(1, this.getUsername());
			rs = stmt.executeQuery();
			if (rs.next()) {
				this.username = rs.getString(1);
				this.password = rs.getString(2);
				this.sessionId = rs.getString(3);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);
		}
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
}
