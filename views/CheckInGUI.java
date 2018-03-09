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
	
	public CheckInGUI(ArrayList<Passenger> passenger_queue) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		JFrame guiFrame = new JFrame();
		
		JPanel airport_panel = new JPanel(new GridLayout(3,1));
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Airport");
		guiFrame.setSize(500,500);
		
		String[] queue = {"Yola Jones", "Clarissa Cremona", "Kyna Mowat-Gosnell", "Theodore Roosevelt", "Abraham Lincoln", "George Washington"};
		//String[] queue = passenger_queue.toArray(new String[passenger_queue.size()]);
		//String[] queue = checkindesk.getQueue().toArray(new String[checkindesk.getQueue().size()]);
		
		// Panel with Passenger queue
		queue_panel = new JScrollPane();
		//update_values(passenger_queue);

		// Panel with Check in Desks
		JPanel checkInDesks_panel = new JPanel(new GridLayout(1,2));
		//TitledBorder checkInDesks_title = new TitledBorder("Check In Desks");
		//checkInDesks_panel.setBorder(checkInDesks_title);
		
		// Check In Desk 1
		String[] checkInDesk1_info = {"Check in desk 1 is doing this"};
		JScrollPane checkInDesk1 = new JScrollPane();
		TitledBorder checkInDesk1_title = new TitledBorder("Check In Desk 1");
		checkInDesk1.setBorder(checkInDesk1_title);
		JList checkInDesk1_list = new JList(checkInDesk1_info);
		checkInDesk1.setViewportView(checkInDesk1_list);
		
		// Check In Desk 2
		String[] checkInDesk2_info = {"Check in desk 2 is doing this"};
		JScrollPane checkInDesk2 = new JScrollPane();
		TitledBorder checkInDesk2_title = new TitledBorder("Check In Desk 2");
		checkInDesk2.setBorder(checkInDesk2_title);
		JList checkInDesk2_list = new JList(checkInDesk2_info);
		checkInDesk2.setViewportView(checkInDesk2_list);
		
		// Add check in desks to panel
		checkInDesks_panel.add(checkInDesk1);
		checkInDesks_panel.add(checkInDesk2);
		
		JPanel flights_panel = new JPanel(new GridLayout(1,3));
		//TitledBorder flights_title = new TitledBorder("Flights");
		//flights_panel.setBorder(flights_title);
		
		JScrollPane flight1 = new JScrollPane();
		TitledBorder flight1_title = new TitledBorder("Flight 1");
		flight1.setBorder(flight1_title);
		String[] flight1_info = {"Flight AB1234"};
		JList flight1_list = new JList(flight1_info);
		flight1.setViewportView(flight1_list);
		
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
		
		guiFrame.setVisible(true);
	}
	
	public synchronized void update_values(ArrayList<Passenger> queue) {

		ArrayList<String> queue_arraylist = new ArrayList<String>();
		for (Passenger p : queue) {
			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "      " + p.getBaggageVolume() + "m3 " + p.getBaggageWeight() + "kg");
			queue_arraylist.add(passenger_info);
		}

		JList<String> queue_list = new JList<>(queue_arraylist.toArray(new String[0]));
		TitledBorder queue_title = new TitledBorder("Queue");
		queue_panel.setBorder(queue_title);
		queue_panel.setViewportView(queue_list);
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
