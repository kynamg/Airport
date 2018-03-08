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
		
		/*if(passenger_queue.size()>5) {
			while(check_in_desks.size()<3) {
				check_in_desks.add(new Thread(new CheckInDesk(passenger_queue, flights)));
			}
		}
		else if(passenger_queue.size()<2) {
			while(check_in_desks.size()>1) {
				check_in_desks.remove(0);
			}
		}*/
		System.out.println("GUI = "+gui.getName());
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
		while(it.hasNext()) {
			add_passenger_to_queue(it.next());
			/*try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
		}
	}
}
