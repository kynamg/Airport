package airport;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.*;

import model.CheckIn;

public class PassengerQueue implements Runnable {

	static ArrayList<Passenger> passenger_queue;
	PassengerList passengers;
	static CheckIn checkin;
	static boolean thread_killed = false;
	
	public PassengerQueue(PassengerList passengers, CheckIn checkin) {
		passenger_queue = new ArrayList<Passenger>();
		this.passengers = passengers;
		this.checkin = checkin;
	}
	
	protected synchronized static void add_passenger_to_queue(Passenger passenger) {
		
		passenger_queue.add(passenger);
		//LOG: passenger joins queue
		String q_passenger = "Passenger joined queue " + passenger.getName() + " " + passenger.getSurname();
		AirportLog.log(Level.INFO,q_passenger);
		
		CheckInDemo.open_close_check_in_desks(passenger_queue.size());
		//checkin.notifyObservers();
		checkin.update_passenger_queue(passenger_queue);
		
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			thread_killed = true;
		}
	}

	protected synchronized static void remove_passenger_from_queue(Passenger passenger) {
		passenger_queue.remove(passenger);
		//LOG: passenger checked in when removed from queue
		String checkin_passenger = "Passenger checked in " + passenger.getName() + " " + passenger.getSurname();
		AirportLog.log(Level.INFO,checkin_passenger) ;
		CheckInDemo.open_close_check_in_desks(passenger_queue.size());
		checkin.update_passenger_queue(passenger_queue);
		
		try {
			Thread.sleep(200);
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
			Passenger passenger = it.next();
			add_passenger_to_queue(passenger);
		}
	}
}
