package airport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.*;

import views.CheckInGUI;

public class PassengerQueue implements Runnable {

	static ArrayList<Passenger> passenger_queue;
	static FlightList flights;
	PassengerList passengers;
	static CheckInGUI gui;
	static boolean thread_killed = false;
	
	public PassengerQueue(CheckInGUI gui, FlightList flights, PassengerList passengers) {
		passenger_queue = new ArrayList<Passenger>();
		this.passengers = passengers;
		this.flights = flights;
		this.gui = gui;
		
	}
	
	protected synchronized static void add_passenger_to_queue(Passenger passenger) {
		
		passenger_queue.add(passenger);
		
		CheckInDemo.open_close_check_in_desks(passenger_queue.size());
		
		gui.update_values(passenger_queue);
		
		System.out.println("Passenger queue = "+passenger_queue.size());
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			thread_killed = true;
			System.out.println("Thread killed");
		}
	}
	
	protected synchronized static void remove_passenger_from_queue(Passenger passenger) {
		System.out.println("Passenger "+passenger.getName()+" being removed");
		
		passenger_queue.remove(passenger);
		CheckInDemo.open_close_check_in_desks(passenger_queue.size());
		
		gui.update_values(passenger_queue);
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			thread_killed = true;
		}
	}
	
	public static ArrayList<Passenger> get_passenger_queue() {
		return passenger_queue;
	}

	public void run() {
		Iterator<Passenger> it = passengers.getIterator();
		while(it.hasNext() && !Thread.interrupted()) {
			Passenger p = it.next();
			add_passenger_to_queue(p);
			String q_passenger = "Passenger joined queue " + p.getName() + " " + p.getSurname();
			AirportLog.log(Level.INFO,q_passenger) ;
		}
	}
}
