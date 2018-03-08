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
					Passenger next_passenger;
					Flight flight;
						next_passenger = get_passenger();
						CheckInDemo.remove_passenger_from_queue(next_passenger);
					
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
		System.out.println("CheckInDesk "+Thread.currentThread()+" has been closed");
	}
	
	public Passenger get_passenger() {
		Passenger next_passenger = passenger_queue.get(0);
		System.out.println("Passenger "+next_passenger.getName()+" gotten in");
		return next_passenger;
	}
	
	public ArrayList<Passenger> getQueue(){
		return passenger_queue;
	}
}
