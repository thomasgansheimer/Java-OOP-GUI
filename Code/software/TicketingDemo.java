package software;

import java.io.FileNotFoundException;
import java.io.Serializable;

public class TicketingDemo implements Serializable {
	public static void main(String[] args) throws FileNotFoundException {
		TicketingSite demo = new TicketingSite();
		demo.addData();
		@SuppressWarnings("unused")
		TicketingGUI gui = new TicketingGUI("Greyhound Bus Services", demo);
	}
}

