package views;

import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;

import interfaces.Observer;
import model.CheckIn;

public class FlightsGUI extends JPanel implements Observer{
	private CheckIn checkindata;
	
	private JPanel flight1, flight2, flight3;
	private JTextArea flight1_code, flight2_code, flight3_code;
	private JTextArea flight1_info, flight2_info, flight3_info;

	public FlightsGUI(CheckIn checkin) {
		super();
		this.checkindata = checkin;
		checkin.registerObserver(this);
		
		setLayout(new GridLayout(1,3));
		
		// flight panel 1
		flight1 = new JPanel();
		flight1.setLayout(new GridLayout(2,1));
		TitledBorder flight1_title = new TitledBorder("Flight 1");
		flight1.setBorder(flight1_title);
		flight1_code = new JTextArea("Flight Code here");
		flight1_info = new JTextArea("Flight info here");
		flight1_code.setFont(new Font("Calibri", Font.BOLD, 14));
		flight1_code.setLineWrap(true);
        flight1_code.setWrapStyleWord(true);
        flight1_code.setOpaque(false);
        flight1_code.setEditable(false);
        flight1_info.setWrapStyleWord(true);
        flight1_info.setOpaque(false);
        flight1_info.setEditable(false);
		flight1.add(flight1_code);
		flight1.add(flight1_info);
		
		// flight panel 2
		flight2 = new JPanel();
		flight2.setLayout(new GridLayout(2,1));
		TitledBorder flight2_title = new TitledBorder("Flight 2");
		flight2.setBorder(flight2_title);
		flight2_code = new JTextArea("Flight Code here");
		flight2_info = new JTextArea("Flight info here");
		flight2_code.setFont(new Font("Calibri", Font.BOLD, 14));
		flight2_code.setLineWrap(true);
        flight2_code.setWrapStyleWord(true);
        flight2_code.setOpaque(false);
        flight2_code.setEditable(false);
        flight2_info.setWrapStyleWord(true);
        flight2_info.setOpaque(false);
        flight2_info.setEditable(false);
		flight2.add(flight2_code);
		flight2.add(flight2_info);
		
		//flight panel 3
		flight3 = new JPanel();
		flight3.setLayout(new GridLayout(2,1));
		TitledBorder flight3_title = new TitledBorder("Flight 3");
		flight3.setBorder(flight3_title);
		flight3_code = new JTextArea("Flight Code here");
		flight3_info = new JTextArea("Flight info here");
		flight3_code.setFont(new Font("Calibri", Font.BOLD, 14));
		flight3_code.setLineWrap(true);
        flight3_code.setWrapStyleWord(true);
        flight3_code.setOpaque(false);
        flight3_code.setEditable(false);
        flight3_code.setLineWrap(true);
        flight3_info.setWrapStyleWord(true);
        flight3_info.setOpaque(false);
        flight3_info.setEditable(false);
		flight3.add(flight3_code);
		flight3.add(flight3_info);
		
		add(flight1);
		add(flight2);
		add(flight3);
	}

	public synchronized void update() {	
		// update flight panels
		if(checkindata.get_flight_code_destination() != null) {
			if(checkindata.get_flight_code_destination().contains("LG2212")) {
				flight1_code.setText(checkindata.get_flight_code_destination());
				flight1_info.setText(checkindata.get_flight_info());
			}
			else if(checkindata.get_flight_code_destination().contains("LG1254")) {
				flight2_code.setText(checkindata.get_flight_code_destination());
				flight2_info.setText(checkindata.get_flight_info());
			}
			else if(checkindata.get_flight_code_destination().contains("AB1234")) {
				flight3_code.setText(checkindata.get_flight_code_destination());
				flight3_info.setText(checkindata.get_flight_info());
			}
		}
	}

}
