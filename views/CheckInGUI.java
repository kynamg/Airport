package views;

import interfaces.Observer;
import model.CheckIn;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

import airport.CheckInDesk;
import airport.InvalidBookingRefException;
import airport.InvalidFlightCodeException;
import airport.InvalidParameterException;
import airport.Passenger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// GUI frame which contains three GUI displays: queue, desks and flights
public class CheckInGUI extends JFrame{
	private CheckIn checkin;
	
	public CheckInGUI(CheckIn model) throws InvalidFlightCodeException, InvalidBookingRefException, InvalidParameterException {
		this.checkin = model;
		
		JPanel airport_panel = new JPanel(new GridLayout(3,1));
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Airport");
		setSize(800,500);
				
		// Panel with three parts
		airport_panel.add(new QueueGUI(model));
		airport_panel.add(new CheckInDesksGUI(model));
		airport_panel.add(new FlightsGUI(model));
		
		add(airport_panel);
		setVisible(true);
		
	}
	
}
