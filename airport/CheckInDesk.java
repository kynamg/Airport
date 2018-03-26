package airport;

import java.util.ArrayList;
import java.util.logging.*;

import model.CheckIn;
import views.CheckInGUI;

public class CheckInDesk implements Runnable {
	
	ArrayList<Passenger> passenger_queue;
	FlightList flight_list;
	CheckInGUI gui;
	static CheckIn checkin;
	//What desk index this desk is - used by GUI
	int desk_number;
	Passenger next_passenger;
	
	public CheckInDesk(ArrayList<Passenger> passenger_queue, FlightList flight_list, CheckInGUI gui, int desk_number, CheckIn model) {
		this.passenger_queue = passenger_queue;
		this.flight_list = flight_list;
		this.gui = gui;
		this.desk_number = desk_number;
		this.checkin = model;
	}
	
	public void run() {
		//while not dead - flag from main method
		while(!Thread.interrupted()) {
			//Tell the GUI this check in desk is open
			//gui.update_checkInDesk(desk_number, "OPEN");
			checkin.update_check_in_desk(desk_number, "OPEN");
			boolean passenger_to_get = false;
			//if there's a passenger left to check in
			synchronized(passenger_queue) {
				passenger_to_get = get_next_passenger();
			}
			
			if(passenger_to_get == true) {
	
				//Update the GUI with what the GUI is currently doing
				//gui.update_checkInDesk(desk_number, "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname()+"\nWeight = "+next_passenger.getBaggageWeight()+"\nVolume = "+next_passenger.getBaggageVolume()+"\nBaggage fee = "+next_passenger.getBaggageFee());
				checkin.update_check_in_desk(desk_number, "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname()+"\nWeight = "+next_passenger.getBaggageWeight()+"\nVolume = "+next_passenger.getBaggageVolume()+"\nBaggage fee = "+next_passenger.getBaggageFee());
				Flight flight;
				//Check if that passenger's flight has departed, and if not, increment flight details
				if(CheckInDemo.has_flight_departed(next_passenger) == false) {
					try {
						//Get Flight object from passengers' flight code
						flight = flight_list.findByCode(next_passenger.getFlightCode());
						//LOG: Passengers Boarding flights
						String board_p =  next_passenger.getName() + " " + next_passenger.getSurname() + " boarding flight: " + next_passenger.getFlightCode();
						AirportLog.log(Level.INFO,board_p);
						//Increment flight details
						flight.incrementPassengers();
						flight.incrementVolume(next_passenger.getBaggageVolume());
						flight.incrementWeight(next_passenger.getBaggageWeight());
						float baggageFee = flight.calculateExcessBaggageFees(next_passenger.getBaggageWeight());
						flight.incrementBaggageFees(baggageFee);
						CheckInDemo.get_current_flight_capacity_info(flight);
					} catch (NoMatchingFlightCodeException e) {
						System.out.println(e.getMessage());
					}
					
					//Potentially remove for final thing - this just makes a list
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				else {
					//LOG: Passengers Missed Flight
					String missed =  next_passenger.getName() + " " + next_passenger.getSurname() + " missed flight: " + next_passenger.getFlightCode();
					AirportLog.log(Level.INFO,missed);
				}
			}
		}
	}
	
	private synchronized boolean get_next_passenger() {
		if(passenger_queue.isEmpty()) {
			return false;
		}
		else {
			next_passenger = passenger_queue.get(0);
			PassengerQueue.remove_passenger_from_queue(next_passenger);
			return true;
		}
	}
}
