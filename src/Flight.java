/*
Flight Class
*/

public class Flight {
	
	private String flightCode;
	private String destination;
	private String carrier;
	private float maxWeight;
	private int maxPassengers;
	private float maxVolume;
	private int numPassengers;
	private float totalWeight;
	private float totalBaggageFees; 
	private float totalVol;
	
	private float baggageFee;
	
	public Flight(String fC, String d, String c, float maxW, int maxP, float maxV) {
		flightCode = fC;
		destination = d;
		carrier = c;
		maxWeight = maxW;
		maxPassengers = maxP;
		maxVolume = maxV;
	}
	
	public String getCarrier() {
		return carrier;
	}
	
	public String getFlightCode() {
		return flightCode;
	}
	
	public float getMaxWeight() {
		return maxWeight;
	}
	
	public float getMaxVol() {
		return maxVolume;
	}
	
	public int getMaxPassengers() {
		return maxPassengers;
	}
	
	public float getTotalBaggageFees() {
		return totalBaggageFees;
	}
	
	public float getTotalWeight() {
		return totalWeight;
	}
	
	public void setTotalWeight(float totalW) {
		totalWeight = totalW;
	}
	
	public void setTotalBaggageFees(float totalB) {
		totalBaggageFees = totalB;
	}
	
	public void setTotalVol(float totalV) {
		totalVol = totalV;
	}
	
	public boolean equals (Object other) {
		if (other instanceof Flight) {
			Flight otherFlight = (Flight) other;
			if(flightCode.equals(otherFlight.flightCode))
				return true;
		}
		return false;
	}
	
	public float calculateExcessBaggageFees(float weight) {
		if (weight>25.0) {
			baggageFee = (float) (10*(weight-25));
		}
		else {
			baggageFee = 0;
		}
//		totalBaggageFees += baggageFee;
		return baggageFee;	
	}
	
	public void incrementWeight(float weight) {
		totalWeight += weight;
	}
	
	public void incrementVolume(float volume) {
		totalVol += volume;
	}
	
	public void incrementBaggageFees(float baggageFee) {
		totalBaggageFees += baggageFee;
	}
	
}
