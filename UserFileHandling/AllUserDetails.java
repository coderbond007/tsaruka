import java.sql.*;

public class AllUserDetails {

	ArrayList<UserData> users;

	public static class Node {
		public Node[] child;
		public boolean leaf;
		public String encryptedPassword;

		public Node() {
			this.child = new Node[65];
			this.leaf = false;
			this.encryptedPassword = null;
		}
	}

	public Node root;

	public AllUserDetails(){
		users = new ArrayList<>();
		root = new Node();
	}

	public void addUser(String... details) {
		Stirng name = details[0];
		String username = details[1];
		String password = details[2];
		Encryption encryption = new Encryption();
		String encryptedPassword = encryption.generateStorngPasswordHash(password);
		/*
		* Add User details to Database 
		*/
		root = insertUserDatainTrie(root, username, encryptedPassword);
	}

	public Node insertUserDatainTrie(Node root, String username, String password)
	{
		Node v = root;
		String possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@$_";
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

	public void getDataFromDatabase(String[][] data)
	{

	}
	public boolean validateUser(String username, String password) {
		Node v = root;
		String possibleCharacters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789@$_";
		for(char s : username.toCharArray()) {
			int ind = possibleCharacters.indexOf(s);

			Node temp = v.child[ind];
			if(temp == null){
				return false;
			}
			v = v.child[ind];
		}
		Encryption encryption = new Encryption();
		return encryption.validatePassword(password, v.encryptedPassword);
	}
}