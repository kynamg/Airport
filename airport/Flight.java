package airport;
/*
Flight Class
*/

import java.util.regex.Pattern;

public class Flight {
	
	private String flightCode;
	private String destination;
	private String carrier;
	private float maxWeight;
	private int maxPassengers;
	private float maxVolume;
	private float totalWeight;
	private float totalBaggageFees; 
	private float totalVol;
	private int totalPassengers;
	
	private float baggageFee;
	
	public Flight(String fC, String d, String c, float maxW, int maxP, float maxV) throws InvalidFlightCodeException, InvalidParameterException {
	
		Pattern flightPattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}");
		Pattern stringPattern = Pattern.compile("^[a-zA-Z]");
		
		boolean validFlight = flightPattern.matcher(fC).find();
		boolean validDestination = stringPattern.matcher(d).find();
		boolean validCarrier = stringPattern.matcher(c).find();
		
		double doubleWeight;
		double doubleVolume;
		
		if(validFlight == true && !fC.isEmpty()) {
			flightCode = fC;
		}
		else {
			throw new InvalidFlightCodeException(flightCode);
		}
		
		if(validDestination == true && !d.isEmpty()) {
			destination = d;
		}
		else {
			throw new InvalidParameterException(destination);
		}
		
		if(validCarrier == true && !c.isEmpty()) {
			carrier = c;
		}
		else {
			throw new InvalidParameterException(carrier);
		}
		
		String maximumW = Float.toString(maxW);
			
		if(!maximumW.isEmpty()) {
			try {
				doubleWeight = (int) (Double.parseDouble(maximumW));
			} catch (NumberFormatException exception) {
	
			}
			maxWeight = maxW;
		}
		
		try {
			if (maxP == (int)maxP) {
				maxPassengers = maxP;
			}
		} catch(NumberFormatException exception) {
			
		}
		
		String maximumV = Float.toString(maxV);
		if(!maximumV.isEmpty()) {
			try {
				doubleVolume = (int) (Double.parseDouble(maximumV));
			} catch (NumberFormatException exception) {
	
			}
			maxVolume = maxV;
		}
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
	
	public float calculateExcessBaggageFees(float weight) {
		if (weight>25.0) {
			baggageFee = (float) (10*(weight-25));
		}
		else {
			baggageFee = 0;
		}
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
