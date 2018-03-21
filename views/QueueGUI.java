package views;

import java.util.ArrayList;

import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.border.TitledBorder;

import airport.Passenger;
import interfaces.Observer;
import model.CheckIn;

public class QueueGUI extends JScrollPane implements Observer{
	private CheckIn checkindata;
	
	public QueueGUI(CheckIn checkin) {
		super();
		this.checkindata = checkin;
		checkin.registerObserver(this);
		TitledBorder queue_title = new TitledBorder("Queue");
		setBorder(queue_title);
		//update();
	}
	
	public synchronized void update() {
		ArrayList<Passenger> queue = checkindata.get_passenger_queue();
		ArrayList<String> queue_arraylist = new ArrayList<String>();
		//System.out.println("Check if queue is null : " + queue);
		if(queue!=null) {
		for (Passenger p : queue) {
			String passenger_info = (p.getBookingRef() + "       " + p.getName() + " " + p.getSurname() + "  							    "
		+ p.getBaggageVolume() + "m\u00B3 " + p.getBaggageWeight() + "kg");
			queue_arraylist.add(passenger_info);
		}
		}

		JList<String> queue_list = new JList<>(queue_arraylist.toArray(new String[0]));
		setViewportView(queue_list);
		/// pass queue and other values as parameters and call method which stores values into JList etc.
	}
}

