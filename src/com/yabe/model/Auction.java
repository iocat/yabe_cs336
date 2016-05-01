package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
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
	
	private Timestamp soldTime;
	private Timestamp openDate;
	private Timestamp closeDate;
	private float minimumPrice = 0;
	private float minimumIncrement;
	private ArrayList<Bid> bids;
	private Bid maxBid;
	public Bid getMaxBid(){
		if (bids != null && bids.size() > 0){
			return bids.get(0);
		}else{
			return null;
		}
	}
	public Auction(String itemId){
		super(itemId);
	}
	public float getSoldPrice() {
		return soldPrice;
	}

	public void setSoldPrice(float soldPrice) {
		this.soldPrice = soldPrice;
	}

	public Date getSoldTime() {
		return soldTime;
	}

	public void setSoldTime(Timestamp soldTime) {
		this.soldTime = soldTime;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Timestamp closeDate) {
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
	
	public float getCurrentMaxBixAmount(){
		if(this.getMaxBid() == null){
			return 0;
		}else{
			return this.getMaxBid().getAmount();
		}
	}
	
	public boolean isSold(){
		return this.purchaser != null;
	}
	
	private final String SQL_RETRIEVE_DATA = "SELECT seller, purchaser,soldPrice, "
			+ "soldTime, openDate, closeDate, minimumPrice, minimumIncrement FROM auction WHERE itemId = ?";
	private final String SQL_RETRIEVE_BIDS = "SELECT bidder,time, amount FROM bidsOn WHERE itemId = ?";
	/*
	 * (Javadoc)
	 * Retrieve data from the database with one item Id
	 */
	@Override
	public boolean retrieve() {
		boolean found = false;
		super.retrieve();
		setBids(Bid.getBids(this.getItemId()));
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_DATA);
			stmt.setString(1, this.getItemId());
			rs = stmt.executeQuery();
			if (rs.next()){
				found = true;
				this.seller = new User(rs.getString(1));
				this.seller.retrieve();
				String purchaserId ;
				if ((purchaserId = rs.getString(2))!=null){
					this.purchaser = new User(purchaserId);
					this.purchaser.retrieve();
				}
				this.soldPrice = rs.getFloat(3);
				this.soldTime = rs.getTimestamp(4);
				this.openDate = rs.getTimestamp(5);
				this.closeDate = rs.getTimestamp(6);
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
		return found;
	}
	public ArrayList<Bid> getBids() {
		return bids;
	}
	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}

}
