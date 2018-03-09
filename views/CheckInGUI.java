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
	private JPanel checkInDesks_panel;
	private JPanel checkInDesk1, checkInDesk2, checkInDesk3, checkInDesk4, checkInDesk5;
	private JTextArea desk1_label, desk2_label, desk3_label, desk4_label, desk5_label;
	private JPanel flight1, flight2, flight3;
	private JTextArea flight1_code, flight2_code, flight3_code;
	private JTextArea flight1_info, flight2_info, flight3_info;
	
	public CheckInGUI(ArrayList<Passenger> passenger_queue) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		JFrame guiFrame = new JFrame();
		
		JPanel airport_panel = new JPanel(new GridLayout(3,1));
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Airport");
		guiFrame.setSize(500,200);
		
		String[] queue = {"Yola Jones", "Clarissa Cremona", "Kyna Mowat-Gosnell", "Theodore Roosevelt", "Abraham Lincoln", "George Washington"};
		
		// Panel with Passenger queue
		queue_panel = new JScrollPane();
		TitledBorder queue_title = new TitledBorder("Queue");
		queue_panel.setBorder(queue_title);

		// Panel with Check in Desks
		checkInDesks_panel = new JPanel(new GridLayout(1,5));
		
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
		
		// Check In Desk 3
		checkInDesk3 = new JPanel();
		checkInDesk3.setLayout(new BorderLayout());
		TitledBorder checkInDesk3_title = new TitledBorder("Check In Desk 3");
		checkInDesk3.setBorder(checkInDesk3_title);
		desk3_label = new JTextArea("This check in desk is currently closed.");
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
		desk4_label = new JTextArea("This check in desk is currently closed.");
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
		desk5_label = new JTextArea("This check in desk is currently closed.");
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
		
		JPanel flights_panel = new JPanel(new GridLayout(1,3));
		
		flight1 = new JPanel();
		flight1.setLayout(new GridLayout(2,1));
		TitledBorder flight1_title = new TitledBorder("Flight 1");
		flight1.setBorder(flight1_title);
		flight1_code = new JTextArea("Flight Code here");
		flight1_info = new JTextArea("Flight info here");
		flight1_code.setFont(new Font("Calibri", Font.BOLD, 14));
		flight1_code.setLineWrap(true);
        flight1_code.setWrapStyleWord(true);
        flight1_code.setOpaque(false);
        flight1_code.setEditable(false);
		flight1.add(flight1_code);
		flight1.add(flight1_info);
		
		flight2 = new JPanel();
		flight2.setLayout(new GridLayout(2,1));
		TitledBorder flight2_title = new TitledBorder("Flight 2");
		flight2.setBorder(flight2_title);
		flight2_code = new JTextArea("Flight Code here");
		flight2_info = new JTextArea("Flight info here");
		flight2_code.setLineWrap(true);
        flight2_code.setWrapStyleWord(true);
        flight2_code.setOpaque(false);
        flight2_code.setEditable(false);
		flight2.add(flight2_code);
		flight2.add(flight2_info);
		
		flight3 = new JPanel();
		flight3.setLayout(new GridLayout(2,1));
		TitledBorder flight3_title = new TitledBorder("Flight 3");
		flight3.setBorder(flight3_title);
		flight3_code = new JTextArea("Flight Code here");
		flight3_info = new JTextArea("Flight info here");
		flight3_code.setFont(new Font("Calibri", Font.BOLD, 14));
		flight3_code.setLineWrap(true);
        flight3_code.setWrapStyleWord(true);
        flight3_code.setOpaque(false);
        flight3_code.setEditable(false);
        flight3_code.setLineWrap(true);
        flight3_info.setWrapStyleWord(true);
        flight3_info.setOpaque(false);
        flight3_info.setEditable(false);
		flight3.add(flight3_code);
		flight3.add(flight3_info);
		
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
	
	public synchronized void update_checkInDesk(int desk_no, String desk_status) {
        
		switch(desk_no) {
			case 1:  desk1_label.setText(desk_status);;
	                 break;
			case 2:  desk2_label.setText(desk_status);;
	        			break;
			case 3:  desk3_label.setText(desk_status);;
	        			break;
			case 4:  desk4_label.setText(desk_status);;
	        			break;
			case 5:  desk5_label.setText(desk_status);;
	        			break;
	        default: break;
		}
	}
	
	public synchronized void update_flight(String flight_code, String flight_info) {	
		if(flight_code.equals("LG2212")) {
			flight1_code.setText(flight_code);
			flight1_info.setText(flight_info);
		}
		else if(flight_code.equals("LG1254")) {
			flight2_code.setText(flight_code);
			flight2_info.setText(flight_info);
		}
		else if(flight_code.equals("AB1234")) {
			flight3_code.setText(flight_code);
			flight3_info.setText(flight_info);
		}
		

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
