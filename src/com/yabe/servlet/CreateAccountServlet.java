package com.yabe.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.yabe.model.Account;
import com.yabe.model.User;
import com.yabe.util.Utils;

/**
 * Servlet implementation class CreateAccountHandler
 */

@WebServlet("/create-account")
public class CreateAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String UPLOAD_DIRECTORY = "resources"+File.separator+"img"+File.separator+"user"+File.separator;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateAccountServlet() {
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
		// TODO Auto-generated method stub
		/*String username = request.getParameter("username");
		String password = Utils
				.encodePassword(request.getParameter("password"));
		String name = request.getParameter("name");
		String address = request.getParameter("address");
		String email = request.getParameter("email");
		User user = new User(username, password, name, address, email);
		request.getSession().invalidate();
		try {
			if (user.insertIntoDB() == true) {
				// Sucessfully register + redirect to the main page
				Cookie cookie = new Cookie(Account.COOKIE_NAME, username);
				response.addCookie(cookie);
				new Account(username)
						.storeSession(request.getSession().getId());
				response.sendRedirect("main-page.jsp");
			} else {
				
				response.sendRedirect("new-account.jsp?login=failed");
			}
		} catch (SQLException e) {
			response.sendRedirect("new-account.jsp?login=failed");
		}*/
		String username = null ;
		String password = null;
		String name = null;
		String address = null;
		String email =null ;
		FileItem imageToStore = null;
		List<FileItem> fileItems = null;
		// Parsing field values
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(1024*1024*1);

        // Create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        // maximum file size to be uploaded.
        upload.setSizeMax(10000000);
           
        String uploadPath = getServletContext().getRealPath("/")
                + File.separator + UPLOAD_DIRECTORY;
        try{
        	fileItems = upload.parseRequest(request);
        	if (fileItems != null && fileItems.size() > 0) {
	        	// GEt the user's information
        		Iterator<FileItem> i = fileItems.iterator();
	        	while(i.hasNext()){
	        		FileItem fi = (FileItem) i.next();
	        		if(fi.isFormField()){
	        			switch(fi.getFieldName()){
	        			case "username":
	        				username = fi.getString();
	        				break;
	        			case "password":
	        				password = Utils.encodePassword(fi.getString());
	        				break;
	        			case "email":
	        				email = fi.getString();
	        				break;
	        			case "name":
	        				name=fi.getString();
	        				break;
	        			case "address":
	        				address= fi.getString();
	        				break;
	        			}
	        		}
	        	}
	        	// Get the profile picture and store it
	        	i = fileItems.iterator();
	        	while(i.hasNext()){
	        		FileItem fi = (FileItem) i.next();
	        		if(!fi.isFormField()){
	        			imageToStore = fi;
	        			
	        		}
	        	}
        	}
        }catch (Exception e){
        	e.printStackTrace();
        }
        
        User user = new User(username, password, name, address, email);
        user.setProfilePicture(uploadPath+username);
		request.getSession().invalidate();
		try {
			if (user.insertIntoDB() == true) {
				File storeFile = new File(uploadPath+username);
    			imageToStore.write(storeFile);
				// Sucessfully register + redirect to the main page
				Cookie cookie = new Cookie(Account.COOKIE_NAME, username);
				response.addCookie(cookie);
				new Account(username)
						.storeSession(request.getSession().getId());
				response.sendRedirect("main-page.jsp");
			} else {
				
				response.sendRedirect("new-account.jsp?login=failed");
			}
		} catch (Exception e) {
			response.sendRedirect("new-account.jsp?login=failed");
		}
        

	}

}
