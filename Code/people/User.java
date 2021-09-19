package people;

import java.io.Serializable;

public abstract class User implements Serializable  {
	private String name;
	private String email;
	private String password;
	
	public User() {
		this.name = "unknown";
		this.email = "unknown";
		this.password = "unknown";	
	}
	
	public User(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	//getters
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	//setters
	public void setName(String name) {
		this.name = name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
}
