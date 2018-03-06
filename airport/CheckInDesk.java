package airport;

import java.util.ArrayList;

public class CheckInDesk implements Runnable {
	
	ArrayList<Passenger> passenger_queue;
	FlightList flight_list;
	
	public CheckInDesk(ArrayList<Passenger> passenger_queue, FlightList flight_list) {
		this.passenger_queue = passenger_queue;
		this.flight_list = flight_list;
	}
	
	public void run() {
		//while not dead - flag from main method
		while(!Thread.interrupted()) {
		//while(!passenger_queue.isEmpty()) {
			synchronized(passenger_queue) {
				if(!passenger_queue.isEmpty()) {
					System.out.println("Thread = "+Thread.currentThread().getName());
					Passenger next_passenger;
					Flight flight;
						next_passenger = get_passenger();
						remove_passenger(next_passenger);
						System.out.println("Passenger name = "+next_passenger.getName());
					
					try {
						flight = flight_list.findByCode(next_passenger.getFlightCode());
						flight.incrementPassengers();
						System.out.println("Flight list "+flight.getFlightCode()+" = "+flight.getTotalPassengers());
					} catch (NoMatchingFlightCodeException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
	
	public Passenger get_passenger() {
		Passenger next_passenger = passenger_queue.get(0);
		System.out.println("Passenger "+next_passenger.getName()+" gotten in");
		return next_passenger;
	}
	
	public void remove_passenger(Passenger passenger) {
		System.out.println("Passenger "+passenger.getName()+" being removed");
		passenger_queue.remove(passenger);
	}
}
