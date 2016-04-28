package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Auction extends Item implements Retrievable {
	private static final String AUCTION_PAGE_URL = "auction.jsp";
	private User seller;
	private User purchaser;
	private float soldPrice;
	private Date soldTime;
	private Date openDate;
	private Date closeDate;
	private float minimumPrice;
	private float minimumIncrement;
	private ArrayList<Bid> bids;

	
	public float getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(float soldPrice) {
		this.soldPrice = soldPrice;
	}

	public Date getSoldTime() {
		return soldTime;
	}

	public void setSoldTime(Date soldTime) {
		this.soldTime = soldTime;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public float getMinimumPrice() {
		return minimumPrice;
	}

	public void setMinimumPrice(float minimumPrice) {
		this.minimumPrice = minimumPrice;
	}

	public float getMinimumIncrement() {
		return minimumIncrement;
	}

	public void setMinimumIncrement(float minimumIncrement) {
		this.minimumIncrement = minimumIncrement;
	}

	public User getSeller() {
		return seller;
	}

	public void setSeller(User seller) {
		this.seller = seller;
	}

	public User getPurchaser() {
		return purchaser;
	}

	public void setPurchaser(User purchaser) {
		this.purchaser = purchaser;
	}

	public JSONObject getJSONObject() throws JSONException{
		JSONObject json = super.getJSONObject();
		json.put("url", AUCTION_PAGE_URL+"?id="+this.getItemId());
		return json;
	}
	private final String SQL_RETRIEVE_DATA = "SELECT seller, purchaser,soldPrice, "
			+ "soldTime, openDate, closeDate, minimumPrice, minimumIncrement FROM auction WHERE itemId = ?";
	private final String SQL_RETRIEVE_BIDS = "SELECT bidder,time, amount FROM bidsOn WHERE itemId = ?";
	/*
	 * (Javadoc)
	 * Retrieve data from the database with one item Id
	 */
	@Override
	public void retrieve() {
		super.retrieve();
		bids = Bid.getBids(this.getItemId());
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_DATA);
			stmt.setString(1, this.getItemId());
			rs = stmt.executeQuery();
			if (rs.next()){
				this.seller = new User(rs.getString(1));
				this.seller.retrieve();
				String purchaserId ;
				if ((purchaserId = rs.getString(2))!=null){
					this.purchaser = new User(purchaserId);
					this.purchaser.retrieve();
				}
				this.soldPrice = rs.getFloat(3);
				this.soldTime = rs.getDate(4);
				this.openDate = rs.getDate(5);
				this.closeDate = rs.getDate(6);
				this.minimumPrice = rs.getFloat(7);
				this.minimumIncrement = rs.getFloat(8);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);
		}
	}

}
