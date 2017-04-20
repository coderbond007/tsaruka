package com.tsaruka;


//interface of Methods used
interface methods{
	public void Create(String path, String filename);
	public void Delete(String path);
	public void AddUser(String username);
	public void OpenFile(String path);
}

public class FileHandle implements methods {
	// Create File at given path
	// File types includes all File Types or Folders
	public void Create(String path, String filename) {		
		StringBuilder sb;
		if(filename.contains(".")){
			sb = new StringBuilder("gedit ");
		}
		else{
			sb = new StringBuilder("mkdir ");
		}
		sb.append(path + "/");
		sb.append(filename);
		
		// Process class of Java to directly interact with terminal to create files and folders
		Process p;
		try {
			p = Runtime.getRuntime().exec(sb.toString());
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Delete File at given path
	// File types includes all File Types or Folders
	public void Delete(String path) {
		StringBuilder sb;
		if(path.contains(".")){
			sb = new StringBuilder("rm ");
		}
		else{
			sb = new StringBuilder("rm -rf ");
		}
		sb.append(path);
		
		// Process class of Java to directly interact with terminal to delete files and folders
		Process p;
		try {
			p = Runtime.getRuntime().exec(sb.toString());
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Creates folder for new User to store his all files and folders
	public void AddUser(String username){
		StringBuilder sb;
		String path = "/home/pradyumn/Documents/tsaruka";
		sb = new StringBuilder("mkdir ");
		sb.append(path + "/");
		sb.append(username);
		
		// Process class of Java to directly interact with terminal to create memory space
		// for user to create, view and delete files
		Process p;
		try {
			p = Runtime.getRuntime().exec(sb.toString());
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void OpenFile(String path)
	{
		StringBuilder sb;
		sb = new StringBuilder("gedit ");
		sb.append(path);
		// Process class of Java to directly interact with terminal to create files and folders
		Process p;
		try {
			p = Runtime.getRuntime().exec(sb.toString());
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
