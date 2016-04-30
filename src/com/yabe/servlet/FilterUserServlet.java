package com.yabe.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.yabe.model.User;
import com.yabe.util.DBConnector;
import com.yabe.util.sql.SQLUtils;

/**
 * Servlet implementation class FilterUserServlet
 */
@WebServlet("/filter/user")
public class FilterUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public FilterUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("name");
		final String SQL_RETRIEVE_USER = "SELECT username,name,profilePicture FROM user WHERE username LIKE ? OR name LIKE ?";
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		JSONArray users = new JSONArray() ;
		JSONObject result = new JSONObject();
		try{
			conn = DBConnector.getConnectionPool().getConnection();
			stmt = conn.prepareStatement(SQL_RETRIEVE_USER);
			stmt.setString(1, "%"+username+"%");
			stmt.setString(2, "%"+username+"%");
			rs = stmt.executeQuery();
			while(rs.next()){
				User user = new User(rs.getString(1));
				user.retrieve();
				users.put(user.getJSONObject());
			}
			result.put("users", users);
		}catch(SQLException | JSONException e){
			e.printStackTrace();
		}finally{
			SQLUtils.closeQuitely(conn);
			SQLUtils.closeQuitely(stmt);
			SQLUtils.closeQuitely(rs);
		}
		response.setContentType("text/json");
		response.getWriter().write(result.toString());
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

}
