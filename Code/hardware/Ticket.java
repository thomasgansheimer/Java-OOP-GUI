package hardware;

public class Ticket {
	private String depart;
	private String dest;
	private int departTime;
	private int arriveTime;
	private int date;
	private Bus bus;
	private int price;
	
	public Ticket() {
		this.depart = "";
		this.dest = "";
		this.departTime = 0;
		this.arriveTime = 0;
		this.date = 0;
		this.price = 0;
		this.bus = new Bus();
	}
	
	public Ticket(int depart, int arrive, int date, String departure, String dest, int price) {
		this.depart = departure;
		this.dest = dest;
		this.departTime = depart;
		this.arriveTime = arrive;
		this.date = date;
		this.bus = new Bus();
		this.price = price;
		
	}
	
	//getters
	public int getDepartTime() {
		return this.departTime;
	}
	
	public int getArriveTime() {
		return this.arriveTime;
	}
	
	public int getDate() {
		return this.date;
	}
	
	public Bus getBus() {
		return this.bus;
	}
	
	public String getDestination() {
		return this.dest;
	}
	
	public String getDeparture() {
		return this.depart;
	}
	
	//methods
	public void printTripData() {
		System.out.println(" Bus Number: " + this.bus.getBusNumber());
		System.out.println("	Departure Time: " + timeToString(this.departTime) + "\n");
		System.out.println("	Arrival Time: " + timeToString(this.arriveTime) + "\n");
		System.out.println("	Date: " + dateToString() + "\n");
		System.out.println("	Price: $" + this.price);
	}
	
	public String dateToString() {
		String str = Integer.toString(this.date);
		char[] digits = str.toCharArray();
		str = "" + digits[0] + digits[1] + "/" + digits[2] + digits[3] + "/" + digits[4] + digits[5];
		
		return str;
	}
	
	public String timeToString(int a) {
		String str = "";
		int b = a / 100;
		a = a - b*100;
		if(b >= 12) {
			if(b > 12) {
				b = b - 12;
			}
			if(a < 10) {
				str = Integer.toString(b) + ":" + "0" + Integer.toString(a) + " pm";
			}
			else {
				str = Integer.toString(b) + ":" + Integer.toString(a) + " pm";
			}
		}
		else  {
			if(a < 10) {
				str = Integer.toString(b) + ":" + "0" + Integer.toString(a) + " am";
			}
			else {
				str = Integer.toString(b) + ":" + Integer.toString(a) + " am";
			}
		}
		return str;
	}
}