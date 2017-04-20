package com.tsaruka;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.*;


// interface of Methods used
interface Methods{
	public void Create(String path,String filename);
	public void Delete(String path,String filename);
	public ArrayList<String> View(String path);
	public void AddUser(String username, String name, String password);
	public ArrayList<ArrayList<String>> UserDetails();
	public ArrayList<ArrayList<String>> getFileNames(String username);
}


public class DBManagement implements Methods {
	
	
	// Details for connecting MySQL with Java (frontend with backend) using Java Database Connectivity
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost:3306/tsaruka";
	static final String USER = "root";
	static final String PASS = "linux";
	Connection conn = null;
	Statement stmt = null;

	// Constructor for creating connection with Database and executing statements in MySQL
	public DBManagement(){
		
		try{
			Class.forName(JDBC_DRIVER);
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("ClassNotFoundException :"+e.getMessage());
		}
		
		System.out.println("Making a database connection....");
		
		try{
			conn = DriverManager.getConnection(DB_URL,USER,PASS);
			stmt = conn.createStatement();	
			System.out.println("Database connection established!");
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
	}

	// Create method creates a file at given path
	@SuppressWarnings("unused")
	public void Create(String path, String filename) {
		// DateFormatter used for storing the details of file
		// i.e. when was file created
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate localDate = LocalDate.now();
		String[] parts = path.split("/");
		String username = parts[5];
		
		// Creates SQL Statement for updation in File table of Database
		int id = -1;
		String sql = "select uID from User where UserName = \""+ username+"\";";
		int output ;
		try{
			//Result Set have data after execution of queries
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt("uID");
			}
			Random gen = new Random();
			float folder_size = gen.nextFloat() * 100;
			System.out.println("1 -- > "  + sql);
			sql = "insert into File values(null,'"+id+"','"+filename+"','"+dtf.format(localDate)+"','"+folder_size + "','" + path+"');";
			System.out.println("2 -- > "  + sql);
            output = stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("SQLException: "+ ex.getMessage());
		}
	}

	// Delete method deletes a file at given path
	@SuppressWarnings("unused")
	public void Delete( String path, String filename) {
		String[] parts = path.split("/");
		String username = parts[5];
		filename = parts[parts.length - 1];
		System.out.println(" -- >" + filename);
		// Creates SQL Statement for deletion in File table of Database
		int id = -1;
		String sql = "select uID from User where UserName = \""+ username+"\";";
		int output ;
		try{
			//Result Set have data after execution of queries
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				id = rs.getInt("uID");
			}
			sql = "delete from File where uID = \""+ id +"\" and FileName = \""+ filename+"\" ;";
			System.out.println("{{{" + sql);
            output = stmt.executeUpdate(sql);			
		} catch (SQLException ex) {
			System.err.println("SQLException: "+ ex.getMessage());
		}
	}
	
	// Views all files at particular path using RegEx of Java
	public ArrayList<String> View(String path) {
		// Creates SQL Statement for getting Result Set in File table of Database
		String sql = "select FileName from File where Path regexp \""+ path+"\"";
		ArrayList<String> filenames = new ArrayList<>();
		try{
			//Result Set have data after execution of queries
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String  filename = rs.getString("FileName");
				filenames.add(filename);
			}
		} catch (SQLException ex) {
			System.err.println("SQLException: " + ex.getMessage());
		}
		return filenames;
	}
	
	// Add new user to Database table User with its encrypted password
	@SuppressWarnings("unused")
	public void AddUser(String username, String name, String password){
		//Encryption of the password takes place
		Encryption encrypt = new Encryption();
		String encPass = null;
		try {
			encPass = encrypt.generateStorngPasswordHash(password);
		} catch (NoSuchMethodException | InvalidKeySpecException | NoSuchAlgorithmException e1) {
			e1.printStackTrace();
		}
		
		// Creates SQL Statement for adding new User Details into User table of Database
		String sql = "insert into User values (null,'" + name + "', '" + encPass + "', '" + username + "');";
		int output;
		try {
			output = stmt.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//Exports All Users Data from Database containing Usernames and Encrypted Passowrds
	public ArrayList<ArrayList<String>> UserDetails() {
		// Creates SQL Statement for getting Result Set of all Usernames with
		// corresponding Encrypted Passwords
		String sql = "select UserName,Password from User;";
		ArrayList<String> usernames = new ArrayList<>();
		ArrayList<String> passwords = new ArrayList<>();
		
		try{
			//Result Set have data after execution of queries
			ResultSet rs = stmt.executeQuery(sql);
			System.out.println(sql);
			while(rs.next()){
				String username = rs.getString("UserName");
				String password = rs.getString("Password");
				usernames.add(username);
				passwords.add(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		// ArrayList of ArrayList having Usernames and Passowrds
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		list.add(usernames);
		list.add(passwords);
		return list;
	}
	
	// Get File Names method fetches all files created by user
	// All file Details are fetched from Database of MySQL
	public ArrayList<ArrayList<String>> getFileNames(String username){
		
		String sql = "select FileName,Path from File where uID in (select uID from User where UserName = '" + username + "');";
		ArrayList<String> fileNames = new ArrayList<>();
		ArrayList<String> filePaths = new ArrayList<>();
		System.out.println(sql);
		try {
			//Result Set have data after execution of queries
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				String filename = rs.getString("FileName");
				String filepath = rs.getString("Path");
				fileNames.add(filename);
				filePaths.add(filepath);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
		ArrayList<ArrayList<String>> list = new ArrayList<>();
		list.add(filePaths);
		list.add(fileNames);
		return list;
	}
}
