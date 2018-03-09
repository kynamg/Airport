package airport;

import java.util.ArrayList;

import views.CheckInGUI;

public class CheckInDesk implements Runnable {
	
	ArrayList<Passenger> passenger_queue;
	FlightList flight_list;
	CheckInGUI gui;
	int desk_number;
	
	public CheckInDesk(ArrayList<Passenger> passenger_queue, FlightList flight_list, CheckInGUI gui, int desk_number) {
		this.passenger_queue = passenger_queue;
		this.flight_list = flight_list;
		this.gui = gui;
		this.desk_number = desk_number;
	}
	
	public void run() {
		//while not dead - flag from main method
		while(!Thread.interrupted()) {
			gui.update_checkInDesk(desk_number, "OPEN");
			synchronized(passenger_queue) {
				if(!passenger_queue.isEmpty()) {
					Passenger next_passenger;
					Flight flight;
					next_passenger = get_passenger();
					
					gui.update_checkInDesk(desk_number, "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname());
					
					if(CheckInDemo.has_flight_departed(next_passenger) == false) {
						try {
							flight = flight_list.findByCode(next_passenger.getFlightCode());
							flight.incrementPassengers();
							System.out.println("Flight list "+flight.getFlightCode()+" = "+flight.getTotalPassengers());
						} catch (NoMatchingFlightCodeException e) {
							System.out.println(e.getMessage());
						}
					}
					else {
						System.out.println("Sorry, your plane has departed");
					}
					
					PassengerQueue.remove_passenger_from_queue(next_passenger);
				}
			}
		}
		gui.update_checkInDesk(desk_number, "CLOSED");
		System.out.println("CheckInDesk "+Thread.currentThread()+" has been closed");
	}
	
	public Passenger get_passenger() {
		Passenger next_passenger = passenger_queue.get(0);
		return next_passenger;
	}
	
	public ArrayList<Passenger> getQueue(){
		return passenger_queue;
	}
}
