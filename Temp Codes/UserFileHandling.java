package com.example.hackerrank;

import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Pradyumn Agrawal coderbond007
 */

public class UserFileHandling {
    public static PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)));
    public static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws java.lang.Exception{
        new UserFileHandling().run();
        out.close();
    }
    void run() throws java.lang.Exception
    {
    	Users users = new Users();
    	out.println("Enter the number of Users");
    	int numberOfUsers = nextInt();
    }

    public int nextInt() {
    	return Integer.parseInt(in.readLine());
    }
    public long nextLong() {
    	return Long.parseLong(in.readLine());
    }
}
public class Users{
   ArrayList<UserData> users = new ArrayList<>();
   Node root = null;
   public void addUserData(UserData userdata)
   {
       users.add(userdata);
       root = insertUsername(root, userdata.username, userdata.password);
   }
}
class UserData {
    String name;
    String username;
    String password;

    UserData(String name, String username, String password){
        this.name = name;
        this.username = username;
        this.password = password;
    }
}
public class Node {
	public Node[] child = new Node[65];
	public boolean leaf = false;
	public String encryptedPassoword = null;

	public static insertUsername(Node root, String username,String password)
	{
		Node v = root;
		String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$";
		for(char s : username.toCharArray()){
			int tempIndex = letters.indexOf(s);
			if(tempIndex == -1){
				System.out.println("Invalid Username to insert");
				System.out.println("Characters should be [A-Z], [a-z], [0-9] and @ or # or $");
				return root;
			}
		}
		for(char s : username.toCharArray()){
			int tempIndex = letters.indexOf(s);
			Node tempNode = v.child[tempIndex];
			if(tempNode == null){
				tempNode = v.child[tempIndex] = new Node();
			}
			v = tempNode;
		}
		v.leaf = true;
		v.encryptedPassoword = EncryptPassword(password);
		return root;
	}

	public byte checkPassword(Node root, String inputUsername, String inputPassword)
	{
		Node v = root;
		String letters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@#$";
		for(char s : inputUsername.toCharArray()){
			int tempIndex = letters.indexOf(s);
			if(tempIndex == -1){
				System.out.println("Invalid Username format");
				System.out.println("Characters should be [A-Z], [a-z], [0-9] and @ or # or $");
				return 0;
			}
		}
		for(char s : inputUsername.toCharArray()){
			int tempIndex = letters.indexOf(s);
			Node tempNode = v.child[tempIndex];
			if(tempNode == null){
				System.out.println("No such Username exist in records");
				return 1;
			}
			v = tempNode;
		}
		return match(v.encryptedPassoword,inputPassword) ? 3 : 2;
	}
	public boolean match(String encryptedPassoword, String inputPassword)
	{

	}
}