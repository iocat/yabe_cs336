package com.yabe.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yabe.model.Auction;
import com.yabe.model.AutoBid;
import com.yabe.model.Computer;
import com.yabe.model.Desktop;
import com.yabe.model.Handheld;
import com.yabe.model.Laptop;
import com.yabe.util.Utils;

/**
 * Servlet Filter implementation class AuctionPageFilter
 */
@WebFilter("/auction.jsp")
public class AuctionPageFilter implements Filter {

    /**
     * Default constructor. 
     */
    public AuctionPageFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		String itemId = req.getParameter("id");
		RequestDispatcher unfoundDispatcher ;
		if (Utils.isEmpty(itemId)){
			unfoundDispatcher = req.getRequestDispatcher("/Error404.jsp");
			unfoundDispatcher.forward(req, res);
			return;
		}else{
			Auction auction = new Auction(itemId);
			boolean found = auction.retrieve();
			Computer computer = Computer.getComputer(itemId);
			
			if (found){
				// set the auction
				req.setAttribute("auction", auction);
				req.setAttribute("computer",computer);
				System.out.println(computer.toString());
			}else{
				// Unfound auction
				unfoundDispatcher = req.getRequestDispatcher("/Error404.jsp");
				unfoundDispatcher.forward(req, res);
				return;
			}
		}
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
