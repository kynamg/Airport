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
	
	public CheckInGUI(CheckIn model) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		this.checkin = model;
		//model.registerObserver(this);
		//JFrame guiFrame = new JFrame();
		
		JPanel airport_panel = new JPanel(new GridLayout(3,1));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Airport");
		setSize(800,500);
				
		// Panel with Passenger queue
//		queue_panel = new JScrollPane();
//		TitledBorder queue_title = new TitledBorder("Queue");
//		queue_panel.setBorder(queue_title);

		// Panel with Check in Desks
		
		
	    //airport_panel.add(queue_panel);
		airport_panel.add(new QueueGUI(model));
		airport_panel.add(new CheckInDesksGUI(model));
		airport_panel.add(new FlightsGUI(model));
		
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
