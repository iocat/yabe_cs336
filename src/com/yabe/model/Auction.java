package com.yabe.model;

import java.util.ArrayList;
import java.util.Date;

public class Auction extends Item implements Retrievable {
	private User seller;
	private User purchaser;
	private float soldPrice;
	private float soldTime;
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

	public float getSoldTime() {
		return soldTime;
	}

	public void setSoldTime(float soldTime) {
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

	@Override
	public void retrieveData() {

	}

}
