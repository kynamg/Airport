package views;

import interfaces.Observer;
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
import model.CheckIn;

public class CheckInDesksGUI extends JPanel implements Observer{

	private CheckIn checkindata;
	//private JPanel checkInDesks_panel;
	private JPanel checkInDesk1, checkInDesk2, checkInDesk3, checkInDesk4, checkInDesk5;
	private JTextArea desk1_label, desk2_label, desk3_label, desk4_label, desk5_label;
	
	public CheckInDesksGUI(CheckIn checkin) {
		this.checkindata = checkin;
		//checkin.registerObserver(this);
		this.setLayout(new GridLayout(1,5));
		// Check In Desk 1
		checkInDesk1 = new JPanel();
		checkInDesk1.setLayout(new BorderLayout());
		TitledBorder checkInDesk1_title = new TitledBorder("Check In Desk 1");
		checkInDesk1.setBorder(checkInDesk1_title);
		desk1_label = new JTextArea("CLOSED");
		desk1_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk1_label.setLineWrap(true);
	    desk1_label.setWrapStyleWord(true);
	    desk1_label.setOpaque(false);
	    desk1_label.setEditable(false);
		checkInDesk1.add(desk1_label);
		
		// Check In Desk 2
		checkInDesk2 = new JPanel();
		checkInDesk2.setLayout(new BorderLayout());
		TitledBorder checkInDesk2_title = new TitledBorder("Check In Desk 2");
		checkInDesk2.setBorder(checkInDesk2_title);
		desk2_label = new JTextArea("CLOSED");
		desk2_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk2_label.setLineWrap(true);
        desk2_label.setWrapStyleWord(true);
        desk2_label.setOpaque(false);
        desk2_label.setEditable(false);
		checkInDesk2.add(desk2_label);
		
		// Check In Desk 3
		checkInDesk3 = new JPanel();
		checkInDesk3.setLayout(new BorderLayout());
		TitledBorder checkInDesk3_title = new TitledBorder("Check In Desk 3");
		checkInDesk3.setBorder(checkInDesk3_title);
		desk3_label = new JTextArea("CLOSED");
		desk3_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk3_label.setLineWrap(true);
        desk3_label.setWrapStyleWord(true);
        desk3_label.setOpaque(false);
        desk3_label.setEditable(false);
		checkInDesk3.add(desk3_label);
		
		// Check In Desk 4
		checkInDesk4 = new JPanel();
		checkInDesk4.setLayout(new BorderLayout());
		TitledBorder checkInDesk4_title = new TitledBorder("Check In Desk 4");
		checkInDesk4.setBorder(checkInDesk4_title);
		desk4_label = new JTextArea("CLOSED");
		desk4_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk4_label.setLineWrap(true);
        desk4_label.setWrapStyleWord(true);
        desk4_label.setOpaque(false);
        desk4_label.setEditable(false);
		checkInDesk4.add(desk4_label);
		
		// Check In Desk 5
		checkInDesk5 = new JPanel();
		checkInDesk5.setLayout(new BorderLayout());
		TitledBorder checkInDesk5_title = new TitledBorder("Check In Desk 5");
		checkInDesk5.setBorder(checkInDesk5_title);
		desk5_label = new JTextArea("CLOSED");
		desk5_label.setFont(new Font("Serif", Font.ITALIC, 16));
		desk5_label.setLineWrap(true);
        desk5_label.setWrapStyleWord(true);
        desk5_label.setOpaque(false);
        desk5_label.setEditable(false);
		checkInDesk5.add(desk5_label);
		
		// Add check in desks to panel
		add(checkInDesk1);
		add(checkInDesk2);
		add(checkInDesk3);
		add(checkInDesk4);
		add(checkInDesk5);
	}
	
//	public JPanel getCheckInDeskPanel() {
//		return checkInDesks_panel;
//	}

	public void update() {
//		String t = checkindata.get_check_in_info();
//		switch(checkindata.get_desk_no()) {
//		case 1:  desk1_label.setText(t);;
//                 break;
//		case 2:  desk2_label.setText(t);;
//        			break;
//		case 3:  desk3_label.setText(t);;
//        			break;
//		case 4:  desk4_label.setText(t);;
//        			break;
//		case 5:  desk5_label.setText(t);;
//        			break;
//        default: break;
//	}
		
	}
}
