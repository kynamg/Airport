package airport;

import java.util.ArrayList;

public class CheckInDesk implements Runnable {
	
	private ArrayList<Passenger> passenger_queue;

	public CheckInDesk(ArrayList<Passenger> passenger_queue) {
		this.passenger_queue = passenger_queue;
	}
	
	public void run() {
		Passenger next_passenger = get_passenger();
		try {
			System.out.println("Thread sleeping");
			Thread.sleep(1000);
			//To simulate a person checking in a passenger - time must pass!!
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}
		remove_passenger(next_passenger);
	}
	
	public synchronized Passenger get_passenger() {
		System.out.println("Got passenger");
		Passenger next_passenger = passenger_queue.get(0);
		return next_passenger;
	}
	
	public synchronized void remove_passenger(Passenger passenger) {
		System.out.println("Returned passenger");
		passenger_queue.remove(passenger);
	}
}
