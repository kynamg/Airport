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
	
	public Flight(String fC, String d, String c, float maxW, int maxP, float maxV) {
		flightCode = fC;
		destination = d;
		carrier = c;
		maxWeight = maxW;
		maxPassengers = maxP;
		maxVolume = maxV;
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
	
	
}
