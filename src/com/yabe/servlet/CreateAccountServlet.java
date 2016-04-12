package com.yabe.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabe.model.User;
import com.yabe.util.Utils;

/**
 * Servlet implementation class CreateAccountHandler
 */
@WebServlet("/CreateAccountHandler")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateAccountServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String username = request.getParameter("username");
		String password = Utils.encodePassword(request.getParameter("password"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		User user = new User(username, password, name, address, email);
		request.getSession().invalidate();
		try {
			if (user.insertIntoDB() == true){	
				RequestDispatcher rd = this.getServletContext().getRequestDispatcher("homepage.jsp");
				
			}else{
				// TODO: Create an unable to create an account page or rediect back to the login page
				response.sendRedirect("");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			response.setStatus(302);
			response.setHeader("Location", this.getServletContext().getContextPath()+"/new-account.jsp?login=failed");
		}
	
	}

}
