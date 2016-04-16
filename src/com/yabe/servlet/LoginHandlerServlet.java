/*
 * This LoginHandler would multiplex the receive 
 * account into different types of users based on the input account.
 */

package com.yabe.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabe.model.Account;
import com.yabe.model.Admin;
import com.yabe.model.Representative;
import com.yabe.model.User;
import com.yabe.util.Utils;


public class LoginHandlerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginHandlerServlet() {
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
	 * Receive the user from the other servlet request or create a new user if there is no
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		RequestDispatcher rd;
		User user;	
		if (( user = (User) request.getAttribute("user")) != null){
			// User is passed to the Login handler ( due to creating new account )
			rd = request.getRequestDispatcher("main-page.jsp");
			rd.forward(request, response);
		}else{
			String usernameParam = request.getParameter("username");
			String passwordParam = Utils.encodePassword(request.getParameter("password"));
			if( Utils.isEmpty(usernameParam) || Utils.isEmpty(passwordParam) ){
				response.sendRedirect("login.jsp?login=failed");
			}else{
				System.out.println(usernameParam + passwordParam);
				switch (Account.validate(usernameParam, passwordParam)){
				case ADMIN:
					Admin admin = new Admin(usernameParam,passwordParam);
					rd = request.getRequestDispatcher("admin.jsp");
					request.setAttribute("admin", admin);
					rd.forward(request, response);
					break;
				case REPRESENTATIVE:
					Representative rep = new Representative(usernameParam);
					rep.retrieveData();
					request.setAttribute("rep", rep);
					rd = request.getRequestDispatcher("main-page.jsp");
					rd.forward(request,response);
					break;
				case USER:
					user = new User(usernameParam);
					user.retrieveData();
					request.setAttribute("user", user);
					rd = request.getRequestDispatcher("main-page.jsp");
					rd.forward(request,response);
					break;
				default:	
					response.sendRedirect("login.jsp?login=failed");
				}
			}
		}
	}

}
