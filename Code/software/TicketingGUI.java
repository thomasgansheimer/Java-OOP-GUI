package software;
import javax.swing.*;
import javax.swing.border.Border;

import hardware.*;
import people.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class TicketingGUI extends JFrame {
	private JButton searchTickets;
	private JButton enterBusNumber;
	private JButton createAccount;
	private JButton confirmPurchase;
	private JMenuBar menu;
	private JMenuItem login;
	private JMenuItem logout;
	private JMenuItem print;
	private TicketingSite demo;
	private JTextField departCity;
	private JTextField destCity;
	private JTextField date;
	private JTextField busNumber;
	private JTextField name;
	private JTextField email;
	private JTextField password;
	private JTextField cc;
	private JTextField aDepartCity;
	private JTextField aDestCity;
	private JTextField aDate;
	private JTextField cDepartCity;
	private JTextField cDestCity;
	private JTextField cDate;
	private JFrame newSchedule;
	private JFrame newAccount;
	private JFrame purchase;
	private JFrame agentSearch;
	private JFrame main;
	private boolean isLogged = false;
	private boolean isAgent = false;
	private boolean runningAgent = false;
	private Passenger p = new Passenger();
	private Ticket t = new Ticket();
	
	
	public TicketingGUI(String windowName, TicketingSite demo) {
		super(windowName);
		setSize(600, 500);
		
		this.demo = demo;
		
		menu = new JMenuBar();
		login = new JMenuItem("Login");
		login.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
		logout = new JMenuItem("Logout");
		logout.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
		print = new JMenuItem("Trips");
		print.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createRaisedBevelBorder(),BorderFactory.createLoweredBevelBorder()));
		login.addActionListener(new MenuListener());
		logout.addActionListener(new MenuListener());
		print.addActionListener(new MenuListener());
		
		menu.add(login);
		menu.add(print);
		menu.add(logout);
		
		
		setJMenuBar(menu);
	
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(Color.DARK_GRAY);
		add(new JLabel(new ImageIcon("src/logo.png")));
		
		cDepartCity = new JTextField(20);
		cDestCity = new JTextField(20);
		cDate = new JTextField(20);
		searchTickets = new JButton("Find Tickets");
		searchTickets.setBackground(Color.LIGHT_GRAY);
		
		searchTickets.addActionListener(new ButtonListener());
		
		JPanel newPanel = new JPanel();
		newPanel.add(new JLabel("Departure City:"));
		newPanel.add(cDepartCity);
		newPanel.add(new JLabel("Destination City:"));
		newPanel.add(cDestCity);
		newPanel.add(new JLabel("Date:"));
		newPanel.add(cDate);
		searchTickets.setPreferredSize(new Dimension(10, 10));
		newPanel.add(searchTickets);
		newPanel.setLayout(new GridLayout(7,1));
		
		add(newPanel);
		setLayout(new GridLayout(2, 1));
		
		setVisible(true);
		main = this;
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream p = new PrintStream(out);
			PrintStream old  = System.out;
			System.setOut(p);
			String temp1 = cDepartCity.getText().substring(0,1).toUpperCase() + cDepartCity.getText().substring(1).toLowerCase();
			String temp2 = cDestCity.getText().substring(0,1).toUpperCase() + cDestCity.getText().substring(1).toLowerCase();
			boolean valid = demo.showTickets(temp1,temp2, cDate.getText());
			System.out.flush();
			System.setOut(old);
			if(valid) {
				newSchedule = new ShowSchedule(out.toString(),cDepartCity,cDestCity,cDate);
			}
		}
	}
	
	private class ShowSchedule extends JFrame{
		public ShowSchedule(String text, JTextField depCity, JTextField desCity, JTextField tripDate) {
			super("Schedule for " + depCity.getText().substring(0,1).toUpperCase()+depCity.getText().substring(1).toLowerCase() + " to " + desCity.getText().substring(0,1).toUpperCase() + desCity.getText().substring(1).toLowerCase() + " on " + tripDate.getText());
			departCity = depCity;
			destCity = desCity;
			date = tripDate;
			setSize(600,500);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			JTextArea textArea = new JTextArea(text);
			textArea.setBackground(getBackground());
			Font boldFont=new Font(textArea.getFont().getName(), Font.BOLD, textArea.getFont().getSize());
			textArea.setFont(boldFont);
			textArea.setEditable(false);
			Border borderE = BorderFactory.createEmptyBorder();
			textArea.setBorder(borderE);
			JScrollPane scroll = new JScrollPane(textArea);
			add(scroll);
			
			JPanel newPanel = new JPanel();
			busNumber = new JTextField(20);
			enterBusNumber = new JButton("Continue");
			enterBusNumber.addActionListener(new BusNumButtonListener());
			enterBusNumber.setBackground(Color.LIGHT_GRAY);
			newPanel.add(new JLabel("Enter Bus Number:"));
			newPanel.add(busNumber);
			newPanel.add(enterBusNumber);
			newPanel.setLayout(new GridLayout(3,1));
			add(newPanel);
			
			setLayout(new GridLayout(2,1));
			setVisible(true);
		}
	}
	
	private class BusNumButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			boolean valid = demo.checkBus(busNumber.getText());
			
			if(valid) {
				newSchedule.dispose();
				t = demo.findTicket(Integer.parseInt(busNumber.getText()));
				if(runningAgent) {
					purchase = new purchaseTicket();
				}else if (isAgent) {
					isLogged = false;
					isAgent = false;
					p = new Passenger();
					JOptionPane.showMessageDialog(null,"You have been logged out of Agent. Proceeding as a regular customer", "Important", JOptionPane.OK_CANCEL_OPTION);
					newAccount = new CreateAccount();
				}
				else if(isLogged) {
					purchase = new purchaseTicket();
				}
				else {
					
					newAccount = new CreateAccount();
				}
			}
		}
	}
	
	private class CreateAccount extends JFrame{
		public CreateAccount() {
			super("Create Account");
			setSize(600, 500);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			
			JPanel newPanel = new JPanel();
			createAccount = new JButton("Create Account");
			createAccount.addActionListener(new createAccountListener());
			createAccount.setBackground(Color.LIGHT_GRAY);
			name = new JTextField(20);
			email = new JTextField(20);
			password = new JTextField(20);
			cc = new JTextField(20);
			newPanel.add(new JLabel("Enter your first name:"));
			newPanel.add(name);
			newPanel.add(new JLabel("Enter your email:"));
			newPanel.add(email);
			newPanel.add(new JLabel("Enter your password:"));
			newPanel.add(password);
			newPanel.add(new JLabel("Enter your credit card number (16 digits):"));
			newPanel.add(cc);
			newPanel.add(createAccount);
			newPanel.setLayout(new GridLayout(9,1));
			
			add(newPanel);
		}
	}
	
	private class createAccountListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			boolean error = false;
			newAccount.dispose();
			for(int i = 0; i<demo.getPassengers().size();i++) {
				if (demo.getPassengers().get(i).getEmail().equals(email.getText())){
					error = true;
					JOptionPane.showMessageDialog(null, "Login already in use. Select a new email", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
			if(error == false) {
			p.setName(name.getText());
			p.setEmail(email.getText());
			
			p.setPassword(password.getText());
			String credCard = cc.getText();
			credCard.replace(" ", "-");
			credCard.replace("-", "");
			
			p.setCreditCard(credCard);
			
			
			isLogged = true;
			//demo.addPassenger(p);
			
			purchase = new purchaseTicket();
			
			}
		}
	}
	
	private class purchaseTicket extends JFrame{
		public purchaseTicket() {
			super("Confirm Purchase");
			setSize(600, 500);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			setVisible(true);
			confirmPurchase = new JButton("Confirm Purchase");
			confirmPurchase.setBackground(Color.LIGHT_GRAY);
			confirmPurchase.addActionListener(new pruchaseListener());
			
			JPanel newPanel = new JPanel();
			newPanel.add(new JLabel("Here are " + p.getName() + "'s trip details:"));
			newPanel.add(new JLabel("Depart from: " + departCity.getText() + ", Arrive in: " + destCity.getText()));
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream p = new PrintStream(out);
			PrintStream old  = System.out;
			System.setOut(p);
			t.printTripData();
			System.out.flush();
			System.setOut(old);
			JTextArea text = new JTextArea(out.toString());
			text.setBackground(getBackground());
			Font boldFont=new Font(text.getFont().getName(), Font.BOLD, text.getFont().getSize());
			text.setFont(boldFont);
			newPanel.add(text);
			text.setEditable(false);
			newPanel.setLayout(new GridLayout(4, 1));
			newPanel.add(confirmPurchase);
			add(newPanel);
		}
	}
	
	private class pruchaseListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			purchase.dispose();
			p.buyTicket(t);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream p1 = new PrintStream(out);
			PrintStream old  = System.out;
			System.setOut(p1);
			p.printTickets();
			System.out.flush();
			System.setOut(old);
		
			JOptionPane.showMessageDialog(null, out.toString(), "Purchase Complete!", JOptionPane.INFORMATION_MESSAGE);
			if(runningAgent == true) {
				
				runningAgent = false;
			}
			demo.addPassenger(p);	
		}
	}
	
	private class MenuListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JMenuItem source = (JMenuItem)(e.getSource());
			if(source.equals(login)) {
				email = new JTextField(20);
				password = new JTextField(20);
			
				JPanel newPanel = new JPanel();
				newPanel.add(new JLabel("Email: "));
				newPanel.add(email);
				newPanel.add(new JLabel("Password: "));
				newPanel.add(password);
				newPanel.setLayout(new GridLayout(2,1));
			
				int check = JOptionPane.showConfirmDialog(null, newPanel, "Login", JOptionPane.OK_CANCEL_OPTION);
				if(check == JOptionPane.OK_OPTION) {
					boolean isValid = demo.signIn(email.getText(), password.getText());
					if(isValid&&!(email.getText().equals("agent"))) {
						p = demo.findPassenger(email.getText(), password.getText());
						isLogged = true;
						JOptionPane.showMessageDialog(null, "Successfully Logged In", "Logged In", JOptionPane.INFORMATION_MESSAGE);
					}else if (isValid&&(email.getText().equals("agent"))) {
						
						agentSearch = new agentTicket();
						
					}
				}
			}
			
			else if(source.equals(logout)) {
				isLogged = false;
				isAgent = false;
				runningAgent = false;
				JOptionPane.showMessageDialog(null, "Successfully Logged Out", "Logged Out", JOptionPane.INFORMATION_MESSAGE);
				p = new Passenger();
			}else if (source.equals(print)) {
				if (isAgent == true ) {
					JOptionPane.showMessageDialog(null, "Please login to view your current tickets", "Please Log In", JOptionPane.INFORMATION_MESSAGE);
				}else if (isLogged == false ) {
					boolean exists = false;
					JFrame frame = new JFrame("Master Schedule");
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					frame.setSize(400,600);
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					PrintStream p1 = new PrintStream(out);
					PrintStream old  = System.out;
					System.setOut(p1);
					ArrayList<Passenger> temp = new ArrayList<Passenger>();
					if (demo.getPassengers().size() <1) {
						System.out.println("No tickets purchased");
					}
					for(int i = 0; i<demo.getPassengers().size();i++) {
						exists = false;
						for (int y = 0; y<temp.size();y++) {
							if (demo.getPassengers().get(i).equals(temp.get(y))) {
								exists = true;
							}
						}if (exists == false) {
						demo.getPassengers().get(i).printTickets();
						temp.add(demo.getPassengers().get(i));
						}
						
					}
					System.out.flush();
					System.setOut(old);
					JTextArea textArea = new JTextArea(out.toString());
					textArea.setEditable(false);
					textArea.setBackground(getBackground());
					Font boldFont=new Font(textArea.getFont().getName(), Font.BOLD, textArea.getFont().getSize());
					textArea.setFont(boldFont);
					JScrollPane scrollPane = new JScrollPane(textArea);
					frame.add(scrollPane);
					frame.setVisible(true);
					
				}else if (isLogged==true) {
					JFrame frame = new JFrame("Customer's Tickets");
					frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
					frame.setSize(400,600);
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					PrintStream p1 = new PrintStream(out);
					PrintStream old  = System.out;
					System.setOut(p1);
					if (demo.getPassengers().size() <1) {
						System.out.println("No tickets purchased");
					}
					for(int i = 0; i<demo.getPassengers().size();i++) {
						if(demo.getPassengers().get(i).getEmail().equals(email.getText())) {
						demo.getPassengers().get(i).printTickets();
						break;
						}
						
					}
					System.out.flush();
					System.setOut(old);
					JTextArea textArea = new JTextArea(out.toString());
					textArea.setEditable(false);
					textArea.setBackground(getBackground());
					Font boldFont=new Font(textArea.getFont().getName(), Font.BOLD, textArea.getFont().getSize());
					textArea.setFont(boldFont);
					JScrollPane scrollPane = new JScrollPane(textArea);
					frame.add(scrollPane);
					frame.setVisible(true);
				}
			}
		}
	}
	
	private class agentTicket extends JFrame {
		public agentTicket() {
			super("Agent Search");
			setSize(600, 500);
			setVisible(true);
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		runningAgent = true;	
		aDepartCity = new JTextField(20);
		aDestCity = new JTextField(20);
		aDate = new JTextField(20);
		name = new JTextField(20);
		email = new JTextField(20);
		cc = new JTextField(20);
		
		searchTickets = new JButton("Find Tickets");
		searchTickets.setBackground(Color.LIGHT_GRAY);
		
		
		searchTickets.addActionListener(new agentAccountListener());
		password = new JTextField();
		p = new Passenger();
		JPanel newPanel = new JPanel();
		newPanel.add(new JLabel("Client Name"));
		newPanel.add(name);
		newPanel.add(new JLabel("Client Email"));
		newPanel.add(email);
		newPanel.add(new JLabel("Client Password"));
		newPanel.add(password);
		newPanel.add(new JLabel("Client Credit Card Number"));
		newPanel.add(cc);
		isLogged = true;
		isAgent = true;
		newPanel.add(new JLabel("Departure City:"));
		newPanel.add(aDepartCity);
		newPanel.add(new JLabel("Destination City:"));
		newPanel.add(aDestCity);
		newPanel.add(new JLabel("Date:"));
		newPanel.add(aDate);
		newPanel.add(searchTickets);
		newPanel.setLayout(new GridLayout(15,1));
		
		add(newPanel);
		//setLayout(new GridLayout(2, 1));
		
		setVisible(true);
		
		}
	}
	private class agentAccountListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println(aDepartCity.getText());
			agentSearch.dispose();
			boolean error = false;
			//newAccount.dispose();
			for(int i = 0; i<demo.getPassengers().size();i++) {
				if (demo.getPassengers().get(i).getEmail().equals(email.getText())){
					error = true;
					JOptionPane.showMessageDialog(null, "Login already in use. Select a new email", "Account Creation Error", JOptionPane.INFORMATION_MESSAGE);
					break;
				}
			}
			if(error == false) {
				p.setName(name.getText());
				p.setEmail(email.getText());
				
				p.setPassword(password.getText());
				String credCard = cc.getText();
				credCard.replace(" ", "-");
				credCard.replace("-", "");
				
				p.setCreditCard(cc.getText());
				
				
				isLogged = true;
				System.out.println(demo.getPassengers().size());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				PrintStream p = new PrintStream(out);
				PrintStream old  = System.out;
				System.setOut(p);
				String temp1 = aDepartCity.getText().substring(0,1).toUpperCase() + aDepartCity.getText().substring(1).toLowerCase();
				String temp2 = aDestCity.getText().substring(0,1).toUpperCase() + aDestCity.getText().substring(1).toLowerCase();
				boolean valid = demo.showTickets(temp1,temp2, aDate.getText());
				System.out.flush();
				System.setOut(old);
				if(valid) {
					newSchedule = new ShowSchedule(out.toString(),aDepartCity,aDestCity,aDate);
				}
			}
		}
	}
}


