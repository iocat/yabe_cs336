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

import com.sun.xml.internal.fastinfoset.stax.events.Util;
import com.yabe.model.Account;
import com.yabe.util.Utils;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
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
	 * Receive the user from the other servlet request or create a new user if
	 * there is no
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd;
		String usernameParam = request.getParameter("username");
		String passwordParam = Utils.encodePassword(request.getParameter("password"));
		String item =request.getParameter("item");
		String redirectItem = item ;
		if(Util.isEmptyString(item)){
			redirectItem="main-page.jsp";
		}else{
			redirectItem ="auction.jsp?id="+redirectItem;
		}
		if (Utils.isEmpty(usernameParam) || Utils.isEmpty(passwordParam)) {
			response.sendRedirect("login.jsp?login=failed");
		} else {
			switch (Account.validate(usernameParam, passwordParam)) {
			case ADMIN:
				response.sendRedirect("admin.jsp");
				break;
			case REPRESENTATIVE:
			case USER:
				Cookie cookie = new Cookie(Account.COOKIE_NAME, usernameParam);
				new Account(usernameParam).storeSession(request.getSession()
						.getId());
				response.addCookie(cookie);
				response.sendRedirect(redirectItem);
				break;
			default:
				if(Utils.isEmpty(item)){
					response.sendRedirect("login.jsp?login=failed");
				}else{
					response.sendRedirect("login.jsp?login=failed&item="+request.getParameter("item"));
				}
				
			}

		}
	}

}
