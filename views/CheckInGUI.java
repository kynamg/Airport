package views;

import interfaces.Observer;
import model.CheckIn;

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

public class CheckInGUI extends JFrame{
	//JFrame guiFrame;
	private CheckIn checkin;
	static JScrollPane queue_panel;
	private int count = 0;
	private JPanel checkInDesks_panel;
	private JPanel checkInDesk1, checkInDesk2, checkInDesk3, checkInDesk4, checkInDesk5;
	private JTextArea desk1_label, desk2_label, desk3_label, desk4_label, desk5_label;
	private JPanel flight1, flight2, flight3;
	private JTextArea flight1_code, flight2_code, flight3_code;
	private JTextArea flight1_info, flight2_info, flight3_info;
	
	public CheckInGUI(CheckIn model) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		this.checkin = model;
		//model.registerObserver(this);
		//JFrame guiFrame = new JFrame();
		
		JPanel airport_panel = new JPanel(new GridLayout(3,1));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Airport");
		setSize(800,500);
		
		checkInDesks_panel = new JPanel(new GridLayout(1,5));
		//checkInDesks_panel.setLayout(new GridLayout(1,5));
		// Check In Desk 1
		checkInDesk1 = new JPanel();
		checkInDesk1.setLayout(new BorderLayout());
		TitledBorder checkInDesk1_title = new TitledBorder("Check In Desk 1");
		checkInDesk1.setBorder(checkInDesk1_title);
		desk1_label = new JTextArea("CLOSED");
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
		desk2_label = new JTextArea("CLOSED");
		desk2_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk2_label.setLineWrap(true);
        desk2_label.setWrapStyleWord(true);
        desk2_label.setOpaque(false);
        desk2_label.setEditable(false);
		checkInDesk2.add(desk2_label);
		
		// Check In Desk 3
		checkInDesk3 = new JPanel();
		checkInDesk3.setLayout(new BorderLayout());
		TitledBorder checkInDesk3_title = new TitledBorder("Check In Desk 3");
		checkInDesk3.setBorder(checkInDesk3_title);
		desk3_label = new JTextArea("CLOSED");
		desk3_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk3_label.setLineWrap(true);
        desk3_label.setWrapStyleWord(true);
        desk3_label.setOpaque(false);
        desk3_label.setEditable(false);
		checkInDesk3.add(desk3_label);
		
		// Check In Desk 4
		checkInDesk4 = new JPanel();
		checkInDesk4.setLayout(new BorderLayout());
		TitledBorder checkInDesk4_title = new TitledBorder("Check In Desk 4");
		checkInDesk4.setBorder(checkInDesk4_title);
		desk4_label = new JTextArea("CLOSED");
		desk4_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk4_label.setLineWrap(true);
        desk4_label.setWrapStyleWord(true);
        desk4_label.setOpaque(false);
        desk4_label.setEditable(false);
		checkInDesk4.add(desk4_label);
		
		// Check In Desk 5
		checkInDesk5 = new JPanel();
		checkInDesk5.setLayout(new BorderLayout());
		TitledBorder checkInDesk5_title = new TitledBorder("Check In Desk 5");
		checkInDesk5.setBorder(checkInDesk5_title);
		desk5_label = new JTextArea("CLOSED");
		desk5_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk5_label.setLineWrap(true);
        desk5_label.setWrapStyleWord(true);
        desk5_label.setOpaque(false);
        desk5_label.setEditable(false);
		checkInDesk5.add(desk5_label);
		
		// Add check in desks to panel
		checkInDesks_panel.add(checkInDesk1);
		checkInDesks_panel.add(checkInDesk2);
		checkInDesks_panel.add(checkInDesk3);
		checkInDesks_panel.add(checkInDesk4);
		checkInDesks_panel.add(checkInDesk5);
		
		airport_panel.add(new QueueGUI(model));
		//airport_panel.add(new CheckInDesksGUI(model));
		airport_panel.add(checkInDesks_panel);
		airport_panel.add(new flightsGUI(model));
				
		// Panel with Passenger queue
//		queue_panel = new JScrollPane();
//		TitledBorder queue_title = new TitledBorder("Queue");
//		queue_panel.setBorder(queue_title);



	    //airport_panel.add(queue_panel);
		//airport_panel.add(checkInDesks_panel);
		//airport_panel.add(flights_panel);
		
		add(airport_panel);
		//guiFrame.pack();
		setVisible(true);
		
	}
	
//	public synchronized void update_values(ArrayList<Passenger> queue) {
//
//		ArrayList<String> queue_arraylist = new ArrayList<String>();
//		for (Passenger p : queue) {
//			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "  							    "
//		+ p.getBaggageVolume() + "m\u00B3 " + p.getBaggageWeight() + "kg");
//			queue_arraylist.add(passenger_info);
//		}
//
//		JList<String> queue_list = new JList<>(queue_arraylist.toArray(new String[0]));
//		queue_panel.setViewportView(queue_list);
//	}
//	
	public synchronized void update_checkInDesk(int desk_no, String desk_status) {
		switch(desk_no) {
			case 1:  desk1_label.setText(desk_status);
	                 break;
			case 2:  desk2_label.setText(desk_status);
	        			break;
			case 3:  desk3_label.setText(desk_status);
	        			break;
			case 4:  desk4_label.setText(desk_status);
	        			break;
			case 5:  desk5_label.setText(desk_status);
	        			break;
	        default: break;
		}
	}
	
//	public synchronized void update_flight(String flight_code, String flight_info) {	
//		if(flight_code.contains("LG2212")) {
//			flight1_code.setText(flight_code);
//			flight1_info.setText(flight_info);
//		}
//		else if(flight_code.contains("LG1254")) {
//			flight2_code.setText(flight_code);
//			flight2_info.setText(flight_info);
//		}
//		else if(flight_code.contains("AB1234")) {
//			flight3_code.setText(flight_code);
//			flight3_info.setText(flight_info);
//		}
//		
//
//	}
//	
//	public void update() {
//		ArrayList<Passenger> queue = checkin.get_passenger_queue();
//		ArrayList<String> queue_arraylist = new ArrayList<String>();
//		for (Passenger p : queue) {
//			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "  							    "
//		+ p.getBaggageVolume() + "m\u00B3 " + p.getBaggageWeight() + "kg");
//			queue_arraylist.add(passenger_info);
//		}
//
//		JList<String> queue_list = new JList<>(queue_arraylist.toArray(new String[0]));
//		queue_panel.setViewportView(queue_list);
//		/// pass queue and other values as parameters and call method which stores values into JList etc.
//	}
	
//	//MVC main method
//	CheckIn model = new CheckIn();
//	CheckInGUI view = new CheckInGUI(model);
//	CheckInController controller = new CheckInController(view,model);
	
}
