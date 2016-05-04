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
import com.yabe.model.Representative;
import com.yabe.model.User;
import com.yabe.util.ServletUtils;
import com.yabe.util.Utils;

@WebFilter({"/main-page.jsp","/auction.jsp","/bid","/create-auction.jsp"})
public class AccountFilter implements Filter {

	public AccountFilter() {
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		Cookie[] cookies = req.getCookies();
		Account acc;
		ServletUtils.noCache(res);
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				switch (cookie.getName()) {
				case "account":
					// Found the account
					acc = new Account(cookie.getValue());
					acc.retrieve();
					if (Utils.isEmpty(acc.getSessionId())
							|| !acc.getSessionId().equals(
									req.getSession().getId())) {
						res.sendRedirect("sign-out");
					}else if(acc.getType()==Account.AccountType.USER){
						User user = new User(acc.getUsername());
						user.retrieve();
						request.setAttribute("account", user);
					}else if(acc.getType() == Account.AccountType.ADMIN){
						Representative rep = new Representative(acc.getUsername());
						rep.retrieve();
						request.setAttribute("account", rep);
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
