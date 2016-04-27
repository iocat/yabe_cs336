package com.yabe.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yabe.model.Item;
import com.yabe.util.DBConnector;
import com.yabe.util.Utils;
import com.yabe.util.sql.SQLBuilder;
import com.yabe.util.sql.SQLUtils;

/**
 * Servlet implementation class FilterItemServlet
 */
@WebServlet("/filter/item")
public class FilterItemServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FilterItemServlet() {

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String[] conditions = request.getParameterValues("condition");
		String[] brands = request.getParameterValues("brand");
		String itemName = request.getParameter("name");
		Integer minBid = new Integer(request.getParameter("min-bid"));
		Integer maxBid = new Integer(request.getParameter("max-bid"));
		Integer minInc = new Integer(request.getParameter("min-increment"));
		Integer minRam = new Integer(request.getParameter("min-ram"));
		
		boolean isDesktop = Boolean.parseBoolean(request.getParameter("desktop"));
		boolean isLaptop = Boolean.parseBoolean(request.getParameter("laptop"));
		boolean isTablet = Boolean.parseBoolean(request.getParameter("tablet"));
		boolean isSmartphone = Boolean.parseBoolean(request.getParameter("smartphone"));
		String inputOpenDate = request.getParameter("opened-date");
		Date openDate = null;
		if (!Utils.isEmpty(inputOpenDate)){
			try {
				openDate = new SimpleDateFormat("EEE MMM dd yyyy").parse(inputOpenDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String inputCloseDate = request.getParameter("closed-date");
		Date closeDate = null;
		if(!Utils.isEmpty(inputCloseDate)){
			try {
				closeDate = new SimpleDateFormat("EEE MMM dd yyyy").parse(inputCloseDate);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		String FILTER_ITEM = "SELECT I.itemId, I.name, I.description, I.picture"
				+ " FROM item I, computer C, auction A"
				+ " WHERE I.itemId = C.itemId AND"
				+ 		" I.itemId = A.itemId AND"
				+		" A.minimumIncrement > ? AND"
				+ 		" C.ram IS NOT NULL AND C.ram >= ? AND"
				
				+ 		" ( (NOT EXISTS(  SELECT *"
				+ 						" FROM bidsOn BO "
				+ 						" WHERE BO.itemId = A.itemId) ) "
				+ 		" OR "
				+ 		" ( A.itemId IN (SELECT BO.itemId"
				+ 						" FROM bidsOn BO"
				+ 						" GROUP BY itemId"
				+ 						" HAVING MAX(BO.amount) >= ? AND MAX(BO.amount) <= ?)"
				+ 				  "))";
		ArrayList<Object> params = new ArrayList<Object>();
		params.add(new Integer(minInc));
		params.add(new Integer(minRam));
		params.add(new Integer(minBid));
		params.add(maxBid);
		//Name construct
		if(!Utils.isEmpty(itemName)){
			FILTER_ITEM += " AND I.name LIKE ?";
			params.add(new String("%"+itemName+"%"));
		}
		// Category query construct
		if (isDesktop || isLaptop || isTablet || isSmartphone){
			FILTER_ITEM += " AND (";
			int time = 0 ;
			if (isDesktop){
				FILTER_ITEM += appendQueryCategory("desktop",time);
				time ++;
			}
			if(isLaptop){
				FILTER_ITEM += appendQueryCategory("laptop",time);
				time++;
			}
			if(isTablet){
				FILTER_ITEM += appendQueryCategory("tablet",time);
				time++;
			}
			if(isSmartphone){
				FILTER_ITEM += appendQueryCategory( "smartphone",time);
				time++;
			}
			FILTER_ITEM += ") ";
		}
		// Item condition query construct
		if (conditions!= null && conditions.length > 0){
			FILTER_ITEM += "AND (";
			int time = 0;
			for (String condition: conditions){
				if (time != 0){
					FILTER_ITEM += "OR";
				}
				String cond = "";
				switch(condition){
				case "New":
					cond ="NEW";
					break;
				case "NewOther":
					cond="NEW_OTHER";
					break;
				case "ManuRefur":
					cond="MANU_REFUR";
					break;
				case "Used":
					cond="USED";
					break;
				case "SellRefur":
					cond="SELL_REFUR";
					break;
				}
				FILTER_ITEM += " I.cond = '"+ cond+"' "; 
				time++;
			}
			FILTER_ITEM += ") ";
		}
		// Brand name construct
		if (brands!= null && brands.length > 0){
			FILTER_ITEM += "AND (";
			int time = 0;
			for (String brand: brands){
				if (time != 0){
					FILTER_ITEM += "OR";
				}
				FILTER_ITEM += " C.brandName = '"+ brand+"' "; 
				time++;
			}
			FILTER_ITEM += ") ";
		}
		// Open date and Close date query construct
		if(openDate != null){
			FILTER_ITEM += " AND A.openDate >= '" + new SimpleDateFormat("yyyy-MM-dd").format(openDate)+"' ";
		}
		if(closeDate != null){
			FILTER_ITEM += " AND A.closeDate <= '" + new SimpleDateFormat("yyyy-MM-dd").format(closeDate)+"' ";
		}
		JSONArray items = new JSONArray();
		JSONObject result = new JSONObject();
		PreparedStatement stmt = null;
		Connection conn = null;
		ResultSet rs = null;
		try {
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(FILTER_ITEM);
			SQLUtils.constructPreparedStatement(stmt, params);
			System.out.println(stmt.toString());
			rs = stmt.executeQuery();
			while(rs.next()){
				Item item = new Item();
				item.setItemId(rs.getString("itemId"));
				item.setName(rs.getString("name"));
				item.setDescription(rs.getString("description"));
				item.setPictureURL("resources/img/item/"+rs.getString("picture"));
				items.put(item.getJSONObject());
			}
			result.put("items", items);
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);
		}
		System.out.println(result.toString());
		response.setContentType("text/json");
		response.getWriter().write(result.toString());
		
	}
	private static String appendQueryCategory(String category,int time){
		String append ="";
		if(time != 0){
			append+= " OR ";
		}
		append+=  "I.itemId IN ( SELECT I1.itemId FROM "+ category +" I1 )" ;
		return append;
	}
}
