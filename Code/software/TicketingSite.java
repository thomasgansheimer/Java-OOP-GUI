package software;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import hardware.*;
import people.*;

public class TicketingSite implements Serializable  {
	private ArrayList<Route> routes;
	private ArrayList<Integer> potentialBus;
	private ArrayList<Passenger> passengers;
	private boolean flag;
	
	public TicketingSite() {
		this.routes = new ArrayList<Route>();
		this.potentialBus = new ArrayList<Integer>();
		this.passengers = new ArrayList<Passenger>();
		this.flag = false;
	}
	
	//initializes data
	public void addData() throws FileNotFoundException {
		routes.add(new Route("Tucson", "Phoenix"));
		routes.add(new Route("Phoenix", "Tucson"));
		routes.add(new Route("Tucson", "Denver"));
		routes.add(new Route("Denver", "Tucson"));
		routes.add(new Route("Tucson", "Flagstaff"));
		routes.add(new Route("Flagstaff", "Tucson"));
		routes.add(new Route("Tucson", "Yuma"));
		routes.add(new Route("Yuma", "Tucson"));
		String str = "";
		File myFile = new File("src/data.txt");
		Scanner inputFile = new Scanner(myFile);
		
		while(inputFile.hasNext()) {
			str = inputFile.nextLine();
			String[] a = str.split(" ");
			for(int i = 0; i < this.routes.size(); i++) {
				Route b = (Route) this.routes.get(i);
				if(b.getDeparture().equals(a[0]) && b.getDestination().equals(a[1])) {
					b.addTrip(new Ticket(Integer.parseInt(a[2]), Integer.parseInt(a[3]), Integer.parseInt(a[4]), b.getDeparture(), b.getDestination(), Integer.parseInt(a[5])));	
				}
			}
		}
		inputFile.close();
	}
	
	//shows list of tickets in given specifications
	public boolean showTickets(String depart, String dest, String date) {
		boolean validSearch = false;
		//putting input into proper syntax, ex: DENVER >> Denver
		String temp1 = depart.substring(0,1).toUpperCase() + depart.substring(1).toLowerCase();
		String temp2 = dest.substring(0,1).toUpperCase() + dest.substring(1).toLowerCase();
				
		if(!checkDepart(temp1)) {
			JOptionPane.showMessageDialog(null, "We are not currently serving " + temp1 + " as a departure city. Please enter another city.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(!checkDest(temp2)) {
			JOptionPane.showMessageDialog(null, "We are not currently serving " + temp2 + " as a destination city. Please enter another city.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(!checkDate(date)) {
			JOptionPane.showMessageDialog(null, "Invalid date entered. Please enter date in the form mm/dd/yy", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(!checkIfDate(date)) {
			JOptionPane.showMessageDialog(null, "There are no trips scheduled for " + date + ". Current booking dates are...\n11/18/20, 11/19/20, 11/20/20", "Error", JOptionPane.ERROR_MESSAGE);
		}
		else if(!checkDepartAndDest(depart, dest)) {
			JOptionPane.showMessageDialog(null, "We are not currently serving trips between " + temp1 + " and " + temp2 + ". Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		else {
			//prints the tickets
			validSearch = true;
			for(int i = 0; i < this.routes.size(); i++) {
				Route a = (Route) this.routes.get(i);
				if(a.getDeparture().equals(temp1) && a.getDestination().equals(temp2)) {
					for(int j = 0; j < a.getSchedule().size(); j++) {
						Ticket b = (Ticket) a.getSchedule().get(j);
						if(b.getDate() == dateToInt(date)) {
							b.printTripData();
							potentialBus.add(b.getBus().getBusNumber());
							this.flag = true;
						}
					}
				}
			}
		}
		return validSearch;
	}
	
	//transfers the date from string into an int 
	public int dateToInt(String date) {
		String[] str = date.split("/");
		String newStr = str[0] + str[1] + str[2];
		return Integer.parseInt(newStr);
	}
	
	public Ticket findTicket(int busNumber) {
		Ticket t = new Ticket();
		
		for(int i = 0; i < this.routes.size(); i++) {
			Route r = (Route) this.routes.get(i);
			for(int j = 0; j < r.getSchedule().size(); j++) {
				Ticket ti = (Ticket) r.getSchedule().get(j);
				if(ti.getBus().getBusNumber() == busNumber) {
					t = ti;
				}
			}
		}
		
		return t;
	}
	
	public void addPassenger(Passenger p) {
		this.passengers.add(p);
	}
	
	public boolean signIn(String email, String password) {
		boolean valid = false;
		if(email.equals("agent")&&password.equals("1234")) {
		valid = true;	
		}
		
		for(int i = 0; i < this.passengers.size(); i++) {
			Passenger p = (Passenger) this.passengers.get(i);
			if(p.getEmail().equals(email) && p.getPassword().equals(password)) {
				valid = true;
			}
		}
		if(!valid) {
			JOptionPane.showMessageDialog(null, "Invalid email or password, please try again", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return valid;
	}
	
	public Passenger findPassenger(String email, String password) {
		Passenger p = new Passenger();
		
		for(int i = 0; i < this.passengers.size(); i++) {
			Passenger pa = (Passenger) this.passengers.get(i);
			if(pa.getEmail().equals(email) && pa.getPassword().equals(password)) {
				p = pa;
			}
		}
		
		return p;
	}
	
	
	public boolean getFlag() {
		return this.flag;
	}
	
	public boolean checkDepart(String depart) {
		boolean flag = false;
		for(int i = 0; i < routes.size(); i++) {
			if(depart.equals(routes.get(i).getDeparture())) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	public boolean checkDest(String dest) {
		boolean flag = false;
		
		for(int i = 0; i < routes.size(); i++) {
			if(dest.equals(routes.get(i).getDestination())) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	public boolean checkDate(String date) {
		String[] test = date.split("/");
		if(test.length != 3) {
			return false;
		}
		else if((test[0].length() != 2) || (test[1].length() != 2) || (test[2].length() != 2)) {
			return false;
		}
		
		return true;	
	}
	
	public boolean checkIfDate(String date) {
		if(date.equals("11/18/20") || date.equals("11/19/20") || date.equals("11/20/20")) {
			return true;
		}
		return false;
	}
	
	public boolean checkDepartAndDest(String depart, String dest) {
		boolean flag = false;
		
		for(int i = 0; i < routes.size(); i++) {
			if(depart.equals(routes.get(i).getDeparture()) && dest.equals(routes.get(i).getDestination())) {
				flag = true;
			}
		}
		
		return flag;
	}
	
	public boolean checkBus(String busNum) {
		boolean flag = false;
		int number = Integer.parseInt(busNum);
		for(int i = 0; i < potentialBus.size(); i++) {
			if(number == potentialBus.get(i)) {
				flag = true;
			}
		}
		if(!flag) {
			JOptionPane.showMessageDialog(null, "Invalid bus number entered", "Error", JOptionPane.ERROR_MESSAGE);
		}
		
		return flag;
	}
	
	public ArrayList<Passenger> getPassengers(){
		return passengers;
	}
}
