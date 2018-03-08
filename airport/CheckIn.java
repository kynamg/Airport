package airport;

import java.util.*;

import interfaces.Observer;
import interfaces.Subject;

public class CheckIn implements Subject {
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
}
