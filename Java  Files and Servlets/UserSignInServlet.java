package com.tsaruka;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserSignInServlet
 */
@WebServlet("/UserSignInServlet")
public class UserSignInServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public String username,password;
	public static String filename = "/home/pradyumn/Documents/currentUser";
	public static PrintWriter out;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSignInServlet() {
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
		username = request.getParameter("username");
		password = request.getParameter("password");
		AllUserDetails allUsers = new AllUserDetails();
		session.setAttribute("username", null);
		try {
			
			// Validate if the "Username and Password" exist in Database or not
			// If yes than user is redirected to his page
			// Else he is redirected to home page again
			if(username != null && password != null && allUsers.validateUser(username, password)){
				out.println(username);
				session.setAttribute("username", username);
				response.sendRedirect("UserView.jsp");
			} else {
				session.setAttribute("username", null);
				out.println("null");
				response.sendRedirect("Home.jsp");
			}
		} catch(Exception e) {
			out.println("null");
			e.printStackTrace();
		}
		out.close();
	}
}