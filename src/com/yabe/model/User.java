package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yabe.util.DBConnector;
import com.yabe.util.Utils;
import com.yabe.util.sql.SQLUtils;

public class User extends Account implements Retrievable {
	public static final String USER_PAGE_URL = "user.jsp?uname=";
	private static final String USER_PROFILE_PICTURE_LOCATION = "resources/img/user/";
	private static final String SQL_CREATE_USER = "INSERT INTO "
			+ "user(username,name,email,address)" + " VALUES (?, ?, ?, ?) ";

	private String name;
	private String email;
	private String address;
	private String profilePicture;

	/* ACCESSORS */
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	// Constructor
	public User(String username, String password, String name, String email,
			String address) {
		super(username, password);
		this.name = name;
		this.email = email;
		this.address = address;
	}

	public User(String username) {
		super(username);
	}

	/*
	 * insertIntoDB() inserts this user data into the database
	 */
	public boolean insertIntoDB() throws SQLException {
		super.insertIntoDB();
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_USER);
			stmt.setString(1, this.getUsername());
			stmt.setString(2, this.name);
			stmt.setString(3, this.email);
			stmt.setString(4, this.address);
			// Optionally receives
			rows = stmt.executeUpdate();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		return rows == 1;
	}

	public boolean retrieve() {
		super.retrieve();
		boolean found = false;
		final String SQL_RETRIEVE_USER = "SELECT name, email, address, profilePicture "
				+ "FROM user " + "WHERE username = ?";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_USER);
			stmt.setString(1, this.getUsername());
			rs = stmt.executeQuery();
			if (rs.next()) {
				found = true;
				this.name = rs.getString(1);
				this.email = rs.getString(2);
				this.address = rs.getString(3);
				this.profilePicture = USER_PROFILE_PICTURE_LOCATION+rs.getString(4)+".jpg";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);
		}
		return found;
	}
	
	public JSONObject getJSONObject(){
		JSONObject user = new JSONObject();
		try {
			user.put("username", this.getUsername());
			user.put("name", this.name);
			user.put("profile-picture", this.profilePicture);
			user.put("url", USER_PAGE_URL+this.getUsername());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}
	
}
