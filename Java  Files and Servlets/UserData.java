package com.tsaruka;


// UserData class contains the data of the User
// such as User's username, name and his Encrypted Password
public class UserData {
	String username;
	String name;
	String encryptedPassword;
	
	// Constructor for initializing the values of UserData
	public UserData(String username, String name, String encryptedPassword){
		this.username = username;
		this.name = name;
		this.encryptedPassword = encryptedPassword;
	}
	
	public String getUsername(){
		return this.username;
	}
	public String getName(){
		return this.name;
	}
	public String getEncryptedPassword(){
		return this.encryptedPassword;
	}
	
	@Override
	public String toString() {
		return "Username -> " + username + '\n' + "Name -> " + name + '\n' + "Encrypted Password ->" + encryptedPassword + '\n';
	}
}