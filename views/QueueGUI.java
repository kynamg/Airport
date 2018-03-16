package views;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import airport.InvalidBookingRefException;
import airport.InvalidFlightCodeException;
import airport.InvalidParameterException;
import airport.Passenger;
import interfaces.Observer;
import model.CheckIn;

public class QueueGUI extends JScrollPane implements Observer{
	private CheckIn checkindata;
	//static JScrollPane queue_panel;
	public QueueGUI(CheckIn checkin) {
		this.checkindata = checkin;
		checkin.registerObserver(this);
		// Panel with Passenger queue
		//queue_panel = new JScrollPane();
		TitledBorder queue_title = new TitledBorder("Queue");
		setBorder(queue_title);
		//System.out.println(checkindata.get_passenger_queue());
		//update();
	}
	
//	public JScrollPane getpassengerPanel() {
//		return queue_panel;
//	}
	
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
	
//	public synchronized void update(ArrayList<Passenger> queue) {
//		ArrayList<Passenger> queue_arraylist_test = checkindata.get_passenger_queue();
//		ArrayList<String> queue_test = new ArrayList<String>();
//		for (Passenger p : queue_arraylist_test) {
//			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "  							    "
//		+ p.getBaggageVolume() + "m\u00B3 " + p.getBaggageWeight() + "kg");
//			queue_test.add(passenger_info);
//		}
//		JList<String> queue_list_test = new JList<>(queue_test.toArray(new String[0]));
//		this.setViewportView(queue_list_test);
//		repaint();
//	}


public synchronized void update() {
	ArrayList<Passenger> queue = checkindata.get_passenger_queue();
	ArrayList<String> queue_arraylist = new ArrayList<String>();
	System.out.println("Queue size = "+queue_arraylist.size());
	if(!queue.isEmpty()) {
		for (Passenger p : queue) {
			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "  							    "
		+ p.getBaggageVolume() + "m\u00B3 " + p.getBaggageWeight() + "kg");
			System.out.println("Passenger info = "+passenger_info);
			queue_arraylist.add(passenger_info);
		}
	}

	JList<String> queue_list = new JList<>(queue_arraylist.toArray(new String[0]));
	setViewportView(queue_list);
}

	
//	public static void main(String args[]) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
//		CheckIn check = new CheckIn();
//		passengerGUI pg = new passengerGUI(check);
//	}
}



