package com.yabe.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

public class Item implements Retrievable {

	private static final String ITEM_PICTURE_LOCATION = "resources/img/item/";
	private String itemId;
	private String name;
	private String manufacturer;
	private Condition condition;

	private enum Condition {
		NEW, NEW_OTHER, MANU_REFUR, SELL_REFUR, USED, FOR_PARTS, NOT_DEFINED
	}

	private String description;
	private String pictureURL;

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPictureURL() {
		return pictureURL;
	}

	public void setPictureURL(String pictureURL) {
		this.pictureURL = pictureURL;
	}
	public JSONObject getJSONObject() throws JSONException{
		JSONObject json = new JSONObject();
		json.put("pictureURL", ITEM_PICTURE_LOCATION+this.pictureURL+".jpg");
		json.put("name", name);
		json.put("description", this.description);
		
		return json;
	}

	public void retrieve() {
		final String ITEM_SQL = "SELECT name, manufacturer, cond, description, picture "
				+ "FROM item " + "WHERE itemId = ? ";
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(ITEM_SQL);
			stmt.setString(1, this.itemId);
			rs = stmt.executeQuery();
			if (rs.next()) {
				this.name = rs.getString(1);
				this.manufacturer = rs.getString(2);
				switch (rs.getString(3)) {
				case "NEW":
					this.condition = Condition.NEW;
					break;
				case "NEW_OTHER":
					this.condition = Condition.NEW_OTHER;
					break;
				case "MANU_REFUR":
					this.condition = Condition.MANU_REFUR;
					break;
				case "SELL_REFUR":
					this.condition = Condition.SELL_REFUR;
					break;
				case "USED":
					this.condition = Condition.USED;
					break;
				case "FOR_PARTS":
					this.condition = Condition.FOR_PARTS;
					break;
				default:
					this.condition = Condition.NOT_DEFINED;
				}
				this.description = rs.getString(4);
				this.pictureURL = rs.getString(5);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);

		}

	}
}
