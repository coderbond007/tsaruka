package com.tsaruka;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserSignUpServlet
 */
@WebServlet("/UserSignUpServlet")
public class UserSignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String username, password, name;
	public static String filename = "/home/pradyumn/Documents/currentUser";
	public static PrintWriter out;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignUpServlet() {
        super();
        try {
			out = new PrintWriter(new FileOutputStream(new File(filename +  ".txt"), false));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// HttpSession to keep track if the user is logged in or logged out
		HttpSession session = request.getSession();
		
		// Retrieves username, password and name for Adding User in Database
		username = (String)request.getParameter("username");
		password = (String)request.getParameter("password");
		name = (String)request.getParameter("name");
		AllUserDetails allUsers = new AllUserDetails();
		session.setAttribute("username", null);
		try {
			// Validate if the "Username" exist in Database or not
			// If yes than user is redirected to home page
			// Else he is redirected to user page
			if(username != null && password != null && name != null && !allUsers.checkUsername(username)){
				out.println(username);
				allUsers.addUser(username, name, password);
				new DBManagement().AddUser(username, name, password);
				new FileHandle().AddUser(username);
				session.setAttribute("username", username);
				response.sendRedirect("UserView.jsp");
			}else {
				out.println("null");
				session.setAttribute("username", null);
				response.sendRedirect("Home.jsp");
			}
		} catch(Exception e){
			e.printStackTrace();
		}
		out.close();
	}
	
}