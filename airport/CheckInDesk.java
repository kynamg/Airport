package airport;

import java.util.ArrayList;

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
	
	public CheckInDesk(ArrayList<Passenger> passenger_queue, FlightList flight_list, CheckInGUI gui, CheckIn checkin, int desk_number) {
		this.passenger_queue = passenger_queue;
		this.flight_list = flight_list;
		this.gui = gui;
		this.desk_number = desk_number;
		this.checkin = checkin;
	}
	
	public void run() {
		//while not dead - flag from main method
		//set_index();
		while(!Thread.interrupted()) {
			//Tell the GUI this check in desk is open
			//System.out.println("Desk number : " + desk_number);
			//checkin.update_check_in_desk(desk_number, "OPEN");
			gui.update_checkInDesk(desk_number, "OPEN"); //logs check in desk open
			boolean passenger_to_get = false;
			//if there's a passenger left to check in
			synchronized(passenger_queue) {
				passenger_to_get = get_next_passenger();
			}
			
			if(passenger_to_get == true) {
				//set_index();
				System.out.println("here and info : " + "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname()+"\nWeight = "+next_passenger.getBaggageWeight()+"\nVolume = "+next_passenger.getBaggageVolume()+"\nBaggage fee = "+next_passenger.getBaggageFee());
				//Update the GUI with what the GUI is currently doing
				gui.update_checkInDesk(desk_number, "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname()+"\nWeight = "+next_passenger.getBaggageWeight()+"\nVolume = "+next_passenger.getBaggageVolume()+"\nBaggage fee = "+next_passenger.getBaggageFee());
				//checkin.update_check_in_desk(desk_number, "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname()+"\nWeight = "+next_passenger.getBaggageWeight()+"\nVolume = "+next_passenger.getBaggageVolume()+"\nBaggage fee = "+next_passenger.getBaggageFee());
				Flight flight;
				//Check if that passenger's flight has departed, and if not, increment flight details
				if(CheckInDemo.has_flight_departed(next_passenger) == false) {
					try {
						//Get Flight object from passengers' flight code
						flight = flight_list.findByCode(next_passenger.getFlightCode());
						//Increment flight details
						flight.incrementPassengers();
						flight.incrementVolume(next_passenger.getBaggageVolume());
						flight.incrementWeight(next_passenger.getBaggageWeight());
						float baggageFee = flight.calculateExcessBaggageFees(next_passenger.getBaggageWeight());
						flight.incrementBaggageFees(baggageFee);
						System.out.println();
						CheckInDemo.get_current_flight_capacity_info(flight);
						//System.out.println("Flight list "+flight.getFlightCode()+" = "+flight.getTotalPassengers());
					} catch (NoMatchingFlightCodeException e) {
						System.out.println(e.getMessage());
					}
					
					//Potentially remove for final thing - this just makes a list
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				else {
					//Could this go on the GUI somewhere?
					System.out.println("Sorry, your plane has departed");
				}
			}
		}
		//Update GUI and close when interrupted
		gui.update_checkInDesk(desk_number, "CLOSED"); //log check in desk closed after this line
		//set_index();
		//checkin.update_check_in_desk(desk_number, "CLOSED");
		//System.out.println("CheckInDesk "+Thread.currentThread()+" has been closed");
	}
//	public void set_index() {
//		desk_number = CheckInDemo.get_index(Thread.currentThread());
//		System.out.println("desk number NOW: " + desk_number);
//	}
	
	private synchronized boolean get_next_passenger() {
		if(passenger_queue.isEmpty()) {
			return false;
		}
		else {
			next_passenger = passenger_queue.get(0);
			PassengerQueue.remove_passenger_from_queue(next_passenger);
			System.out.println("Thread out with passenger = "+next_passenger.getName());
			return true;
		}

	}
}
