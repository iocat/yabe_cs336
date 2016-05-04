package com.yabe.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabe.model.Account;
import com.yabe.model.User;
import com.yabe.model.Auction;
import com.yabe.model.Item;
import com.yabe.model.Computer;
import com.yabe.model.Laptop;
import com.yabe.model.Desktop;
import com.yabe.util.Utils;

import java.util.*;
import java.text.*;

/**
 * Servlet implementation class CreateAuctionServlet
 */

@WebServlet("/create-auction")
public class CreateAuctionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAuctionServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String checkbox =  request.getParameter("isDesktop");
		String sellerName = request.getParameter("seller");
		String name = request.getParameter("name");
		String minimumPrice = request.getParameter("minimumPrice");
		String openDate = request.getParameter("openDate");
		String closeDate = request.getParameter("closeDate");
        String minimumIncrement = request.getParameter("minimumIncrement");  
        String cond = request.getParameter("cond");  
        String manufacturer = request.getParameter("manufacturer");  
        String description = request.getParameter("description");
        String ramSize = request.getParameter("ram");  
        String brandName = request.getParameter("brandName");  
        String wt = request.getParameter("weight");  
        String operatingSystem = request.getParameter("operatingSystem"); 
        String screenType = request.getParameter("screenType");  
        String scrWidth = request.getParameter("screenWidth");  
        String scrHeight = request.getParameter("screenHeight");  
        String scrResolutionX  = request.getParameter("screenResolutionX");  
        String scrResolutionY = request.getParameter("screenResolutionY");  
        String sizeWidth = request.getParameter("sizeWidth");
        String sizeHeight = request.getParameter("sizeHeight");  
        String sizeDepth = request.getParameter("sizeDepth");  
        String color = request.getParameter("color");  
        String batteryCap = request.getParameter("batteryCapacity");
       
        /* Ignoring handheld devices for now
        String externalMemoryMaxSize = request.getParameter("externalMemoryMaxSize");  
        String externalMemoryType = request.getParameter("externalMemoryType");
        String networkProvider = request.getParameter("networkProvider");  
        String simType = request.getParameter("simType");  
        */
        
        //Changing type of parameters to send to constructors
        User seller = new User(sellerName);
        seller.retrieve();
        
        float minPrice = Float.parseFloat(minimumPrice);
        float minIncrement = Float.parseFloat(minimumIncrement);
        com.yabe.model.Item.Condition condition = com.yabe.model.Item.Condition.valueOf(cond);
        
        SimpleDateFormat openformat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date parsedOpenDate = new Date();
        try {
        parsedOpenDate = openformat.parse(openDate);
        } catch(ParseException ex) {}
        java.sql.Timestamp openTimestamp = new java.sql.Timestamp(parsedOpenDate.getTime());
        	

        SimpleDateFormat closeformat = new SimpleDateFormat("EEE MMM dd yyyy");
        Date parsedCloseDate = new Date();
        try {
        parsedCloseDate = closeformat.parse(closeDate);
        } catch (ParseException pe) {}
        java.sql.Timestamp closeTimestamp = new java.sql.Timestamp(parsedCloseDate.getTime());
        
        
        int ram = Integer.parseInt(ramSize);
        //brandNaME
        float weight = Float.parseFloat(wt);
        //operating system
        float width = Float.parseFloat(sizeWidth);
        float height = Float.parseFloat(sizeHeight);
        float depth = Float.parseFloat(sizeDepth);
        //color
        int batteryCapacity = Integer.parseInt(batteryCap);
        //screenType
        float screenWidth = Float.parseFloat(scrWidth);
        float screenHeight = Float.parseFloat(scrHeight);
        int screenResX = Integer.parseInt(scrResolutionX);
        int screenResY = Integer.parseInt(scrResolutionY);
        
        Auction auction = new Auction(name, manufacturer, condition, description, seller, openTimestamp, closeTimestamp, minPrice, minIncrement);
            
		try {
			if (auction.insertIntoDB()) {
			} 
			else {
				response.sendRedirect("create-auction.jsp");
			}
		} catch (SQLException e) {
			response.sendRedirect("create-auction.jsp");
		}
        
		
		String id = auction.getItemId();
		
		
	    if(checkbox.equals("true")){
        	Computer desktop = new Desktop(id,ram,brandName,
        			weight,operatingSystem, screenType, screenWidth,
        			screenHeight, screenResX, screenResY, width,
        			height, depth, color, batteryCapacity);
        	
        	try {
        		if (desktop.insertIntoDB() == true) {
        			response.sendRedirect("main-page.jsp");
        		} 
        		else {
        			response.sendRedirect("create-auction.jsp");
        		}
        	} catch (SQLException e) {
        		e.printStackTrace();
        	}
	    }
	    
	    else {
        	Computer laptop = new Laptop(id,ram,brandName,
        			weight,operatingSystem, screenType, screenWidth,
        			screenHeight, screenResX, screenResY, width,
        			height, depth, color, batteryCapacity);
        	
        	try {
        		if (laptop.insertIntoDB() == true) {
        			response.sendRedirect("main-page.jsp");
        		} 
        		else {
        			response.sendRedirect("create-auction.jsp");
        		}
        	} catch (SQLException e) {
        		response.sendRedirect("create-auction.jsp");
        	}
	    }  
	    
	    
	    
      }
}
