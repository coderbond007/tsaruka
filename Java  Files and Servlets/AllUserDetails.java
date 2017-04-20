package com.tsaruka;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;


//AllUserDetails class contains all details of all Users
// using a data structure TRIE
public class AllUserDetails {

	ArrayList<UserData> users;
	
	// Inner static class Node to keep
	// at certain node
	public static class Node {
		public Node[] child;
		public boolean leaf;
		public String encryptedPassword;

		public Node() {
			this.child = new Node[66];
			this.leaf = false;
			this.encryptedPassword = null;
		}
	}
	
	// public Node root ensures that its
	// value remains same after execution
	// of each query
	public Node root;
	
	//Constructor AllUserDetails ensures all data
	// from Database is imported and inserted
	// into TRIE data structure
	public AllUserDetails(){
		
		// ArrayList of users data is kept to list
		// all users in the databse
		users = new ArrayList<UserData>();
		root = new Node();
		
		// Usernames and corresponding Encrypted Passwords are inserted into
		// TRIE
		ArrayList<ArrayList<String>> list = new DBManagement().UserDetails();
		ArrayList<String> usernames = list.get(0);
		ArrayList<String> passwords = list.get(1);
		if(usernames.size() != passwords.size()){
			throw new RuntimeException();
		}
		for(int i = 0;i < usernames.size(); i++){
			try {
				root = insertUserDatainTrie(root, usernames.get(i), passwords.get(i));
			} catch (NoSuchMethodException | InvalidKeySpecException | NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
		}
	}
	
	// Add User to TRIE and users ArrayList to keep track of changes
	// in the currenet session for future sessions
	public void addUser(String newUsername, String newName, String newPassword) throws NoSuchMethodException, InvalidKeySpecException, NoSuchAlgorithmException{
		String name = newName;
		String username = newUsername;
		String password = newPassword;
		
		//Encryption to encrypt the password
		Encryption encryption = new Encryption();
		String encryptedPassword = encryption.generateStorngPasswordHash(password);
		root = insertUserDatainTrie(root, username, encryptedPassword);
		users.add(new UserData(username, name, encryptedPassword));
	}
	
	// Inserts data into TRIE for feasibility in searching
	// of username and matching of passwords
	public Node insertUserDatainTrie(Node root, String username, String password) throws NoSuchMethodException, InvalidKeySpecException, NoSuchAlgorithmException
	{
		Node v = root;
		String possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@$_.";
		for(char s : username.toCharArray()) {
			int ind = possibleCharacters.indexOf(s);
			Node temp = v.child[ind];

			if(temp == null){
				temp = v.child[ind] = new Node();
			}
			v = v.child[ind];
		}
		v.leaf = true;
		v.encryptedPassword = password;
		return root;
	}
	
	// Validates the username and password entered in current session
	// for creating, editing and deleting of files and folders
	public boolean validateUser(String username, String password) throws NoSuchMethodException, InvalidKeySpecException, NoSuchAlgorithmException{
		Node v = root;
		String possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@$_.";
		for(char s : username.toCharArray()) {
			int ind = possibleCharacters.indexOf(s);

			Node temp = v.child[ind];
			if(temp == null){
				return false;
			}
			v = v.child[ind];
		}
		Encryption encryption = new Encryption();
		boolean valid = encryption.validatePassword(password, v.encryptedPassword);
		System.out.println(password + " " + v.encryptedPassword);
		return valid;
	}
	
	//Validate if the username for new users
	// registering to avoid clash of usernames
	public boolean checkUsername(String username){
		Node v = root;
		String possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@$_.";
		for(char s : username.toCharArray()) {
			int ind = possibleCharacters.indexOf(s);

			Node temp = v.child[ind];
			if(temp == null){
				return false;
			}
			v = v.child[ind];
		}
		return true;
	}
}