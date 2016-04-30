package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Date;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Bid implements Retrievable, Updatable {
	private String itemId;
	public Bid(String itemId) {
		this.itemId = itemId;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public User getBidder() {
		return bidder;
	}
	public void setBidder(User bidder) {
		this.bidder = bidder;
	}
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public float getAmount() {
		return amount;
	}
	public void setAmount(float amount) {
		this.amount = amount;
	}

	private User bidder;
	private Date time;
	private float amount = 0;
	private static final String SQL_RETRIEVE_DATA = "SELECT amount FROM bidsOn WHERE itemId = ? AND bidder = ? AND time = ?";
	@Override
	public boolean retrieve() {
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		boolean found = false;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_DATA);
			stmt.setString(1,itemId);
			stmt.setString(2, this.bidder.getUsername());
			stmt.setDate(3, this.time);
			rs = stmt.executeQuery();
			if(rs.next()){
				found = true;
				this.amount = rs.getFloat(1);
			}
		}catch (SQLException e){
		
		}finally{
		
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(rs);
			SQLUtils.closeQuitely(stmt);
		}
		return found;
	}
	
	private static final String SQL_GET_BIDS = "SELECT bidder, time, amount FROM bidsOn WHERE itemId = ? ORDER BY amount DESC";
	public static ArrayList<Bid> getBids(String itemId){
		ArrayList<Bid> bids  = new ArrayList<Bid>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_GET_BIDS);
			stmt.setString(1,itemId);
			rs = stmt.executeQuery();
			while(rs.next()){
				Bid bid = new Bid(itemId);
				
				User bidder = new User(rs.getString(1));
				bidder.retrieve();
				
				bid.setBidder(bidder);
				bid.setTime(rs.getDate(2));
				bid.setAmount(rs.getFloat(3));
				bids.add(bid);
			}
		}catch (SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(rs);
			SQLUtils.closeQuitely(stmt);
		}
		return bids;
	}
	private static final String SQL_UPDATE = "INSERT INTO bidsOn (itemId,bidder,time,amount)"
			+ " VALUES (?,?,?,?) ON DUPLICATE KEY UPDATE amount = ?";
	@Override
	public void update() {
		Connection conn = null;
		PreparedStatement stmt = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_UPDATE);
			stmt.setString(1, this.itemId);
			stmt.setString(2, this.bidder.getUsername());
			stmt.setDate(3, this.time);
			stmt.setFloat(4, this.amount);
			stmt.setFloat(5, this.amount);
			stmt.executeUpdate();
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		
	}
	
}
