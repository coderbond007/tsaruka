package com.tsaruka;

import java.util.*;
import java.io.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ButtonClick
 */
@WebServlet("/ButtonClick")
public class ButtonClick extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ButtonClick() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@SuppressWarnings("resource")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String act = request.getParameter("act");
		System.out.println(act);
		if(act.contains("/open")){
			String path = act.substring(0, act.length() - 6);
			new FileHandle().OpenFile(path);
			response.sendRedirect("UserView.jsp");
		} else if(act.contains("/delete")){
			String path = act.substring(0, act.length() - 8);
			new FileHandle().Delete(path);
			new DBManagement().Delete(path, "");
			response.sendRedirect("UserView.jsp");
		} else if(act.contains("Create")){
			String filename = request.getParameter("name");
			BufferedReader input =  new BufferedReader(new FileReader("/home/pradyumn/Documents/currentUser.txt"));
			try {
				ArrayList<String> user = new ArrayList<>();
				String line;
				while (( line = input.readLine()) != null){
			          user.add(line);
			    }
				String username = user.get(0);
				String path = "/home/pradyumn/Documents/tsaruka/";
				path += username;
				path += "/";
				new FileHandle().Create(path, filename);
				new DBManagement().Create(path, filename);
				response.sendRedirect("UserView.jsp");
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

}
