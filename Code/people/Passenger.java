package people;

import java.io.Serializable;
import java.util.ArrayList;
import javax.swing.JOptionPane;

import hardware.*;

public class Passenger extends User implements Serializable {
	private int creditCard1;
	private int creditCard2;
	private ArrayList<Ticket> tickets;
	
	public Passenger() {
		this.creditCard1 = 0;
		this.creditCard2 = 0;
		this.tickets = new ArrayList<Ticket>();
	}
	
	public Passenger(String name, String email, String cc, String password) {
		super(name, email, password);
		this.setCreditCard(cc);
		this.tickets = new ArrayList<Ticket>();
	}
	
	//getters
	
	public ArrayList<Ticket> getTickets(){
		return this.tickets;
	}
	
	public String getCreditCard() {
		String oup = Integer.toString(creditCard1);
		oup += Integer.toString(creditCard2);
		return (oup);
	}
	
	//setters
	public void setCreditCard(String cc) {
		boolean cont = false;
		String inputString =cc;
		while (cont == false) {
		
		if(inputString.length() ==16) {
			cont = true;
			char[] tempCC = new char[inputString.length()];
			for (int i = 0; i < inputString.length(); i++) { 
	            tempCC[i] = inputString.charAt(i); 
	        } 
			String tempS = new String();
					
			for (int y= 0; y < 8;y++) {
				tempS += tempCC[y];
			}
			
			creditCard1 = Integer.parseInt(tempS);
			
			tempS = new String();
			for (int y= 8; y < 16;y++) {
				tempS += tempCC[y];
			}
			creditCard2 = Integer.parseInt(tempS);
			}
			else {
				
				inputString = JOptionPane.showInputDialog(null, "Invalid CreditCard Length\nPlease enter a 16 digit CC#", "Error", JOptionPane.QUESTION_MESSAGE);
			}
		}
	}
	
	//called when buying ticket
	public void buyTicket(Ticket ticket) {
		tickets.add(ticket);
		ticket.getBus().addPassenger(this);		
	}
	
	public void printTickets() {
		System.out.println("\nTravel Plans for " + getName() +"\n");
		for(int i = 0; i < this.tickets.size(); i++) {
			Ticket t = (Ticket) this.tickets.get(i);
			System.out.println(t.getDeparture() + " to " + t.getDestination());
			t.printTripData();
		}
	}
	
	//transfers credit card into last four digits
	public String lastFourDigits() {
		String cc = String.valueOf(this.getCreditCard());
		String lastFour = "xxxx-xxxx-xxxx-";
		int tempSize = cc.length();
		
		lastFour += cc.charAt(tempSize-4);
		
		lastFour += cc.charAt(tempSize - 3);
		
		lastFour += cc.charAt(tempSize - 2);
		
		lastFour += cc.charAt(tempSize-1);
		
		return lastFour;
	}
}