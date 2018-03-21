package model;

import java.util.*;

import airport.Passenger;
import interfaces.Observer;
import interfaces.Subject;
import views.CheckInGUI;

public class CheckIn implements Subject {
	
	static ArrayList<Passenger> passenger_queue;
	
	String flight_code_destination;
	String flight_info;
	
	String check_in_info;
	
	private List<Observer> registeredObservers = new LinkedList<Observer>();
	
	public void registerObserver(Observer obs) {
		registeredObservers.add(obs);
	}

	public void removeObserver(Observer obs) {
		registeredObservers.remove(obs);
	}
	
	public void notifyObservers() {
		for(Observer obs : registeredObservers)
			obs.update();
	}
	
	public ArrayList<Passenger> get_passenger_queue() {
		return passenger_queue;
	}
	
	public String get_flight_code_destination() {
		return flight_code_destination;
	}
	
	public String get_flight_info() {
		return flight_info;
	}
	
	protected String get_check_in_info() {
		return check_in_info;
	}
	
	public void update_check_in_desk(String check_in_info) {
		this.check_in_info = check_in_info;
		//notifyObservers();
	}
	
	public void update_flight_info(String flight_code_destination, String flight_info) {
		this.flight_code_destination = flight_code_destination;
		this.flight_info = flight_info;
		notifyObservers();
		
	}
	
	public void update_passenger_queue(ArrayList<Passenger> passenger_queue) {
		this.passenger_queue = passenger_queue;
		notifyObservers();
		//update_observers();
	}
	
	public CheckIn() {
		
	}
}
