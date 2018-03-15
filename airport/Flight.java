package airport;
/*
Flight Class
*/

import java.util.Random;
import java.util.logging.Level;
import java.util.regex.Pattern;

public class Flight implements Runnable {
	
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
	private int departureTime;
	
	private float baggageFee;
	
	public Flight(String fC, String d, String c, float maxW, int maxP, float maxV) throws InvalidFlightCodeException, InvalidParameterException {
	
		// RegEx patterns for flight code and strings
		Pattern flightPattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}$");
		Pattern stringPattern = Pattern.compile("^[a-zA-Z]");
		
		// Compare RegEx patterns to parameters
		boolean validFlight = flightPattern.matcher(fC).find();
		boolean validDestination = stringPattern.matcher(d).find();
		boolean validCarrier = stringPattern.matcher(c).find();
		
		double doubleWeight;
		double doubleVolume;
		
		// Check that flightCode is of the right format
		if(validFlight == true && !fC.isEmpty()) {
			flightCode = fC;
		}
		else {
			throw new InvalidFlightCodeException(fC);
		}
		
		// Check destination is a string
		if(validDestination == true && !d.isEmpty()) {
			destination = d;
		}
		else {
			throw new InvalidParameterException(d);
		}
		
		// Check carrier is a string
		if(validCarrier == true && !c.isEmpty()) {
			carrier = c;
		}
		else {
			throw new InvalidParameterException(c);
		}
		
		// Check weight is a float
		String maximumW = Float.toString(maxW);
			
		if(!maximumW.isEmpty()) {
			try {
				doubleWeight = (int) (Double.parseDouble(maximumW));
			} catch (NumberFormatException exception) {
			}
			maxWeight = maxW;
		}
		
		// Check passengers is an integer
		try {
			if (maxP == (int)maxP) {
				maxPassengers = maxP;
			}
		} catch(NumberFormatException exception) {
			
		}
		
		// Check volume is a float
		String maximumV = Float.toString(maxV);
		if(!maximumV.isEmpty()) {
			try {
				doubleVolume = (int) (Double.parseDouble(maximumV));
			} catch (NumberFormatException exception) {
	
			}
			maxVolume = maxV;
		}
		
		getDepartureTime();
	}
	
	// Get Methods
	protected String getCarrier() {
		return carrier;
	}
	
	protected String getDestination() {
		return destination;
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
	
	// Set methods
	protected void setTotalWeight(float totalW) {
		totalWeight = totalW;
	}
	
	protected void setTotalBaggageFees(float totalB) {
		totalBaggageFees = totalB;
	}
	
	protected void setTotalVol(float totalV) {
		totalVol = totalV;
	}
	
	// Calculate excess baggage fees if weight is over 25kg
	public float calculateExcessBaggageFees(float weight) {
		if (weight>25.0) {
			baggageFee = (float) (10*(weight-25));
		}
		else {
			baggageFee = 0;
		}
		return baggageFee;	
	}
	
	// Increment methods to update total passengers, weight, volume and baggage fees
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
	
	private void getDepartureTime() {
		Random random = new Random();
		//Generate a random departure time between 1 second and 10 seconds
		departureTime = random.nextInt(9000)+1000;
	}
	
	public void setDepartureTime(int time) {
		departureTime = time;
	}

	public void run() {
		try {
			Thread.sleep(departureTime);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//LOG: flights departing
		String flight_departing = "Flight Departing: " + flightCode;
		AirportLog.log(Level.INFO,flight_departing);
		CheckInDemo.flight_depart(Thread.currentThread(), flightCode);
	}
	
}
