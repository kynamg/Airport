package airport;

import java.util.ArrayList;

import views.CheckInGUI;

public class CheckInDesk implements Runnable {
	
	ArrayList<Passenger> passenger_queue;
	FlightList flight_list;
	CheckInGUI gui;
	//What desk index this desk is - used by GUI
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
			//Tell the GUI this check in desk is open
			gui.update_checkInDesk(desk_number, "OPEN"); //logs check in desk open
			synchronized(passenger_queue) {
				//if there's a passenger left to check in
				if(!passenger_queue.isEmpty()) {
					Passenger next_passenger;
					Flight flight;
					//Get next passenger in queue
					next_passenger = passenger_queue.get(0);
					
					//Potentially remove for final thing - this just makes a list
					/*try {
						Thread.sleep(2000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}*/
					
					//Update the GUI with what the GUI is currently doing
					gui.update_checkInDesk(desk_number, "Passenger "+next_passenger.getName()+" "+next_passenger.getSurname()+"\nWeight = "+next_passenger.getBaggageWeight()+"\nVolume = "+next_passenger.getBaggageVolume()+"\nBaggage fee = "+next_passenger.getBaggageFee());
					
					//Check if that passenger's flight has departed, and if not, increment flight details
					if(CheckInDemo.has_flight_departed(next_passenger) == false) {
						try {
							//Get Flight object from passengers' flight code
							flight = flight_list.findByCode(next_passenger.getFlightCode());
							//Increment flight details
							flight.incrementPassengers();
							flight.incrementVolume(next_passenger.getBaggageVolume());
							flight.incrementWeight(next_passenger.getBaggageWeight());
							CheckInDemo.get_current_flight_capacity_info(flight);
							System.out.println("Flight list "+flight.getFlightCode()+" = "+flight.getTotalPassengers());
						} catch (NoMatchingFlightCodeException e) {
							System.out.println(e.getMessage());
						}
					}
					else {
						//Could this go on the GUI somewhere?
						System.out.println("Sorry, your plane has departed");
					}
					
					//Remove that passenger from the queue
					PassengerQueue.remove_passenger_from_queue(next_passenger);
				}
			}
		}
		//Update GUI and close when interrupted
		gui.update_checkInDesk(desk_number, "CLOSED"); //log check in desk closed after this line
		System.out.println("CheckInDesk "+Thread.currentThread()+" has been closed");
	}
}
