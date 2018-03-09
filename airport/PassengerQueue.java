package airport;

import java.util.ArrayList;
import java.util.Iterator;

import views.CheckInGUI;

public class PassengerQueue implements Runnable {

	static ArrayList<Passenger> passenger_queue;
	static FlightList flights;
	PassengerList passengers;
	static CheckInGUI gui;
	
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
		
		//System.out.println("Number of threads = "+check_in_desks.size());
		System.out.println("Passenger queue = "+passenger_queue.size());
		
		try { //Just for show, don't actually need this
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	protected synchronized static void remove_passenger_from_queue(Passenger passenger) {
		System.out.println("Passenger "+passenger.getName()+" being removed");
		
		passenger_queue.remove(passenger);
		CheckInDemo.open_close_check_in_desks(passenger_queue.size());
		
		gui.update_values(passenger_queue);
		
		try { //Just for show, don't actually need this
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Passenger> get_passenger_queue() {
		return passenger_queue;
	}

	public void run() {
		Iterator<Passenger> it = passengers.getIterator();
		while(it.hasNext() && !Thread.interrupted()) {
			add_passenger_to_queue(it.next());
		}
	}
}
