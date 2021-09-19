package hardware;

import java.util.ArrayList;
import people.*;

public class Bus {
	private int busNumber;
	private int capacity;
	private static int count = 1;
	private ArrayList<User> passengers;
	
	public Bus() {
		this.busNumber = count++;
		this.capacity = 20;
		passengers = new ArrayList<User>();
	}
	
	//getters
	public int getBusNumber() {
		return this.busNumber;
	}
	
	public int getCapacity() {
		return this.capacity;
	}
	
	public ArrayList<User> getPassengerList(){
		return this.passengers;
	}
	
	//setters
	public void setBusNumber(int busNumber) {
		this.busNumber = busNumber;
	}
	
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	//methods
	
	public void addPassenger(User u) {
		this.capacity = this.capacity - 1;
		this.passengers.add(u);
	}
	
	public void printPassengerList() {
		for(int i = 0; i < this.passengers.size(); i++) {
			User u = (User) this.passengers.get(i);
			System.out.println(u.getName());
		}
	}
}