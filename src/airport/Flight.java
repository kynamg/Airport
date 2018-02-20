package airport;
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
	private int totalPassengers;
	
	private float baggageFee;
	
	public Flight(String fC, String d, String c, float maxW, int maxP, float maxV) {
		flightCode = fC;
		destination = d;
		carrier = c;
		maxWeight = maxW;
		maxPassengers = maxP;
		maxVolume = maxV;
	}
	
	protected String getCarrier() {
		return carrier;
	}
	
	public String getFlightCode() {
		return flightCode;
	}
	
	protected float getMaxWeight() {
		return maxWeight;
	}
	
	protected float getMaxVol() {
		return maxVolume;
	}
	
	protected int getMaxPassengers() {
		return maxPassengers;
	}
	
	protected float getTotalBaggageFees() {
		return totalBaggageFees;
	}
	
	protected float getTotalWeight() {
		return totalWeight;
	}
	
	protected float getTotalVolume() {
		return totalVol;
	}
	
	protected int getTotalPassengers() {
		return totalPassengers;
	}
	
	protected void setTotalWeight(float totalW) {
		totalWeight = totalW;
	}
	
	protected void setTotalBaggageFees(float totalB) {
		totalBaggageFees = totalB;
	}
	
	protected void setTotalVol(float totalV) {
		totalVol = totalV;
	}
	
	/*public boolean equals (Object other) {
		if (other instanceof Flight) {
			Flight otherFlight = (Flight) other;
			if(flightCode.equals(otherFlight.flightCode))
				return true;
		}
		return false;
	}*/
	
	protected float calculateExcessBaggageFees(float weight) {
		if (weight>25.0) {
			baggageFee = (float) (10*(weight-25));
		}
		else {
			baggageFee = 0;
		}
//		totalBaggageFees += baggageFee;
		return baggageFee;	
	}
	
	protected void incrementPassengers() {
		totalPassengers ++;
	}
	protected void incrementWeight(float weight) {
		totalWeight += weight;
	}
	
	protected void incrementVolume(float volume) {
		totalVol += volume;
	}
	
	protected void incrementBaggageFees(float baggageFee) {
		totalBaggageFees += baggageFee;
	}
	
}
