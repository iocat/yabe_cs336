package com.yabe.model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
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
	
	//Constructor
	public Auction(String name, String manufacturer, Condition condition, String description ,User seller, Timestamp openDate, Timestamp closeDate, float minimumPrice,
			float minimumIncrement) {
		super(name, manufacturer, condition, description);
		this.seller = seller;
		this.openDate = openDate;
		this.closeDate = closeDate;
		this.minimumPrice = minimumPrice;
		this.minimumIncrement = minimumIncrement;
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

	public Timestamp getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Timestamp openDate) {
		this.openDate = openDate;
	}

	public Timestamp getCloseDate() {
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
	
	private final String SQL_CREATE_AUCTION = "INSERT INTO "
			+ "auction(itemId, seller, openDate,closeDate, minimumPrice, minimumIncrement) "
			+ "VALUES "
			+ " (?,?,?,?,?,?)";
	
	/*
	 * insertIntoDB() inserts this auction data into the database
	 */
	public boolean insertIntoDB() throws SQLException {
		super.insertIntoDB();
		Connection conn = null;
		PreparedStatement stmt = null;
		int rows = 0;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_CREATE_AUCTION);
			stmt.setString(1, this.getItemId());
			stmt.setString(2, this.seller.getUsername());
			stmt.setTimestamp(3, this.openDate);
			stmt.setTimestamp(4, this.closeDate);
			stmt.setFloat(5, this.minimumPrice);
			stmt.setFloat(6,this.minimumIncrement);
			// Optionally receives
			
			rows = stmt.executeUpdate();
		} catch(SQLException ex){
			ex.printStackTrace();
		}
		finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
		}
		return rows == 1;
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
				
				Timestamp actualTime = null;

				try {
					Date actual = new Date();
					actualTime = new Timestamp(actual.getTime());
				} catch (Exception exc) {
					exc.printStackTrace();
				}

				if (this.closeDate.before(actualTime)){
					retrievePurchaser();
				}
				
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
	
	private final String ITEM_SOLD = "{call setItemAsSold(?,?,?,?)}";
	public void retrievePurchaser(){
		Connection conn = null;
		CallableStatement cstmt = null;
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			cstmt = conn.prepareCall(ITEM_SOLD);
			cstmt.setString(1,this.getItemId());
			cstmt.registerOutParameter(2,Types.CHAR);
			cstmt.registerOutParameter(3, Types.FLOAT);
			cstmt.registerOutParameter(4, Types.DATE);
			cstmt.executeUpdate();
			this.purchaser = new User(cstmt.getString(2));
			this.soldPrice = cstmt.getFloat(3);
			
		}catch(SQLException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(cstmt);
			SQLUtils.closeQuitely(conn);
		}
	}

	
	public ArrayList<Bid> getBids() {
		return bids;
	}
	public void setBids(ArrayList<Bid> bids) {
		this.bids = bids;
	}
	
}

