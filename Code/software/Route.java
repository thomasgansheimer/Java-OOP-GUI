package software;

import java.io.Serializable;
import java.util.ArrayList;

import hardware.Ticket;

public class Route implements Serializable {
	private String destination;
	private String departure;
	private ArrayList<Ticket> schedule;
	
	public Route() {
		this.destination = "unknown";
		this.departure = "unknown";
		this.schedule = new ArrayList<Ticket>();
	}
	
	public Route(String depart, String dest) {
		this.destination = dest;
		this.departure = depart;
		this.schedule = new ArrayList<Ticket>();
	}
	
	//getters
	public String getDestination() {
		return this.destination;
	}
	
	public String getDeparture() {
		return this.departure;
	}
	
	public ArrayList<Ticket> getSchedule(){
		return this.schedule;
	}
	
	//setters
	public void setDestination(String destination) {
		this.destination = destination;
	}
	
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	
	public void addTrip(Ticket a) {
		schedule.add(a);
	}
	
	//methods
	public void printRoute() {
		System.out.println("Schedule for " + this.departure + " to " + this.destination + ":");
		for(int index = 0; index < this.schedule.size(); index++) {
			Ticket a = (Ticket) this.schedule.get(index);
			a.printTripData();
		}
	}
}