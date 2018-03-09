package views;

import interfaces.Observer;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import airport.CheckInDesk;
import airport.InvalidBookingRefException;
import airport.InvalidFlightCodeException;
import airport.InvalidParameterException;
import airport.Passenger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CheckInGUI extends JPanel implements Observer{
	//JFrame guiFrame;
	static JScrollPane queue_panel;
	private int count = 0;
	private CheckInDesk checkindesk;
	private JPanel checkInDesks_panel;
	private JPanel checkInDesk1;
	private JPanel checkInDesk2;
	private JTextArea desk1_label;
	private JTextArea desk2_label;
	private JPanel flight1;
	private JTextArea flight1_code;
	private JTextArea flight1_info;
	
	public CheckInGUI(ArrayList<Passenger> passenger_queue) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		JFrame guiFrame = new JFrame();
		
		JPanel airport_panel = new JPanel(new GridLayout(3,1));
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Airport");
		guiFrame.setSize(200,200);
		
		String[] queue = {"Yola Jones", "Clarissa Cremona", "Kyna Mowat-Gosnell", "Theodore Roosevelt", "Abraham Lincoln", "George Washington"};
		
		// Panel with Passenger queue
		queue_panel = new JScrollPane();
		TitledBorder queue_title = new TitledBorder("Queue");
		queue_panel.setBorder(queue_title);

		// Panel with Check in Desks
		checkInDesks_panel = new JPanel(new GridLayout(1,2));
		
		// Check In Desk 1
		checkInDesk1 = new JPanel();
		checkInDesk1.setLayout(new BorderLayout());
		TitledBorder checkInDesk1_title = new TitledBorder("Check In Desk 1");
		checkInDesk1.setBorder(checkInDesk1_title);
		desk1_label = new JTextArea("This check in desk is currently closed.");
		desk1_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk1_label.setLineWrap(true);
        desk1_label.setWrapStyleWord(true);
        desk1_label.setOpaque(false);
        desk1_label.setEditable(false);
		checkInDesk1.add(desk1_label);
		
		// Check In Desk 2
		checkInDesk2 = new JPanel();
		checkInDesk2.setLayout(new BorderLayout());
		TitledBorder checkInDesk2_title = new TitledBorder("Check In Desk 2");
		checkInDesk2.setBorder(checkInDesk2_title);
		desk2_label = new JTextArea("This check in desk is currently closed.");
		desk2_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk2_label.setLineWrap(true);
        desk2_label.setWrapStyleWord(true);
        desk2_label.setOpaque(false);
        desk2_label.setEditable(false);
		checkInDesk2.add(desk2_label);
		
		// Add check in desks to panel
		checkInDesks_panel.add(checkInDesk1);
		checkInDesks_panel.add(checkInDesk2);
		
		JPanel flights_panel = new JPanel(new GridLayout(1,3));
		
		flight1 = new JPanel();
		flight1.setLayout(new GridLayout(2,1));
		TitledBorder flight1_title = new TitledBorder("Flight 1");
		flight1.setBorder(flight1_title);
		flight1_code = new JTextArea("Flight Code here");
		flight1_info = new JTextArea("Flight info here");
		flight1_code.setLineWrap(true);
        flight1_code.setWrapStyleWord(true);
        flight1_code.setOpaque(false);
        flight1_code.setEditable(false);
		flight1.add(flight1_code);
		flight1.add(flight1_info);
		//String[] flight1_info = {"Flight AB1234"};
		//JList flight1_list = new JList(flight1_info);
		//flight1.setViewportView(flight1_list);
		
		JScrollPane flight2 = new JScrollPane();
		TitledBorder flight2_title = new TitledBorder("Flight 2");
		flight2.setBorder(flight2_title);
		String[] flight2_info = {"Flight CA23456"};
		JList flight2_list = new JList(flight2_info);
		flight2.setViewportView(flight2_list);
		
		JScrollPane flight3 = new JScrollPane();
		TitledBorder flight3_title = new TitledBorder("Flight 3");
		flight3.setBorder(flight3_title);
		String[] flight3_info = {"Flight DA1245"};
		JList flight3_list = new JList(flight3_info);
		flight3.setViewportView(flight3_list);
		
		flights_panel.add(flight1);
		flights_panel.add(flight2);
		flights_panel.add(flight3);

	    airport_panel.add(queue_panel);
		airport_panel.add(checkInDesks_panel);
		airport_panel.add(flights_panel);
		
		guiFrame.add(airport_panel);
		guiFrame.pack();
		guiFrame.setVisible(true);
		
	}
	
	public synchronized void update_values(ArrayList<Passenger> queue) {

		ArrayList<String> queue_arraylist = new ArrayList<String>();
		for (Passenger p : queue) {
			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "      " + p.getBaggageVolume() + "m3 " + p.getBaggageWeight() + "kg");
			queue_arraylist.add(passenger_info);
		}

		JList<String> queue_list = new JList<>(queue_arraylist.toArray(new String[0]));
		queue_panel.setViewportView(queue_list);
	}
	
	public synchronized void update_checkInDesk1(String status1) {
		desk1_label = new JTextArea(status1);
	}
	
	public synchronized void update_checkInDesk2(String status2) {
		desk2_label = new JTextArea(status2);
	}
	
	public synchronized void update_flights() {
		
	}
	
	public void update() {
		repaint();
		/// pass queue and other values as parameters and call method which stores values into JList etc.
	}
	
//	//MVC main method
//	CheckIn model = new CheckIn();
//	CheckInGUI view = new CheckInGUI(model);
//	CheckInController controller = new CheckInController(view,model);
	
}
