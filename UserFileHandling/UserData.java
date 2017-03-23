public class UserData {
    String name;
    String username;
    String encryptedPassword;

    UserData(String name, String username, String encryptedPassword){
        this.name = name;
        this.username = username;
        this.encryptedPassword = encryptedPassword;
    }
    @Override
    public String toString() {
    	return "Username -> " + username + " Passoword -> " + password;
    }
}