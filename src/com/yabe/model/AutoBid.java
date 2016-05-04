package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class AutoBid implements Retrievable, Updatable {
	
	public User getBidder() {
		return bidder;
	}
	public void setBidder(User bidder) {
		this.bidder = bidder;
	}
	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}
	public float getMaxAmount() {
		return maxAmount;
	}
	public void setMaxAmount(float maxAmount) {
		this.maxAmount = maxAmount;
	}
	private Item item;
	private float maxAmount;
	private User bidder;
	private Timestamp setTime;
	private final String UPDATE = "INSERT INTO autoBidsOn VALUES(?,?,?,?) ON DUPLICATE KEY UPDATE maxAmount = ?";
	
	
	
	@Override
	public void update() {
		Connection conn = null;
		PreparedStatement stmt = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(UPDATE);
			stmt.setString(1, this.item.getItemId());
			stmt.setString(2, this.bidder.getUsername());
			stmt.setFloat(3, this.maxAmount);
			stmt.setTimestamp(4,this.setTime);
			stmt.setFloat(5, this.maxAmount);
			 stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
	}
	public AutoBid(Item item, float maxAmount, User bidder, Timestamp setTime) {
		this.item = item;
		this.maxAmount = maxAmount;
		this.bidder = bidder;
		this.setTime = setTime;
	}
	public AutoBid() {
	}
	public boolean hasAutoBid(){
		return maxAmount != 0;
	}
	
	final String SQL_RETRIEVE_AUTO_BID = "SELECT maxAmount FROM autoBidsOn WHERE itemId = ? AND bidder = ?";
	@Override
	public boolean retrieve() {
		boolean found = false;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_AUTO_BID);
			stmt.setString(2, this.bidder.getUsername());
			stmt.setString(1, this.getItem().getItemId());
			rs = stmt.executeQuery();
			if (rs.next()) {
				found = true;
				maxAmount = rs.getFloat(1);
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
	
}
