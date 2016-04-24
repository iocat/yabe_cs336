package com.yabe.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabe.model.Account;
import com.yabe.util.Utils;

@WebFilter("/main-page.jsp")
public class MainPageFilter implements Filter {

    public MainPageFilter() {
    }

	public void destroy() {
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Cookie[] cookies = req.getCookies();
		Account acc;
		if (cookies != null){
			for (Cookie cookie : cookies){
				switch (cookie.getName()){
				case "account":
					// Found the account 
					acc = new Account(cookie.getValue());
					acc.retrieveData();
					System.out.println(acc.getSessionId());
					if ( Utils.isEmpty(acc.getSessionId() ) ||
							!acc.getSessionId().equals(req.getSession().getId())){
						res.sendRedirect("login.jsp");
					}
					break;
				}
			}
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}