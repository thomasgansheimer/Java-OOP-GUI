package people;

import java.io.Serializable;

public class Agent extends User implements Serializable  {
	private int idNumber;
	
	public Agent() {
		this.idNumber = 0;
	}
	
	//getters
	public int getId() {
		return this.idNumber;
	}
	
	//setters
	public void setId(int id) {
		this.idNumber = id;
	}
	
	//methods
	public void addTicket() {
	}
}