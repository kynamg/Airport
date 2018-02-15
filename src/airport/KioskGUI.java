package airport;

import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.EventListenerList;

import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;

public class KioskGUI {
	
	JFrame guiFrame;
	JTextField menu_location = new JTextField();
	int baggage_weight = 0;
	int baggage_volume = 0;
	
	//Screen which allows users to input last name and booking reference
	//Method calls baggage_entry_screen once details have been checked
	private void details_screen(JFrame guiFrame, Container guiContainer, PassengerList passenger_list, FlightList flight_list) {
		
		//Heading to tell user what view they're in (details/baggage)
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		menu_location.setEditable(false);
		menu_location.setText("DETAILS");
		menu_location.setFont(font1);
		
		//Panel showing DETAILS heading
		
		//Panel for allowing user to enter last name
		Panel name_panel = new Panel();
		name_panel.setLayout(new GridLayout());
		
		//Last name: and name entry text fields
		JTextField name_prompt = new JTextField();
		name_prompt.setEditable(false);
		name_prompt.setText("Last Name:");
		JTextField name_entry = new JTextField();
		name_entry.setEditable(true);
		
		name_panel.add(name_prompt);
		name_panel.add(name_entry);		
		
		//Panel for allowing user to enter booking reference
		Panel booking_ref_panel = new Panel();
		booking_ref_panel.setLayout(new GridLayout());
		
		//Booking Reference: and booking reference entry text fields
		JTextField booking_ref_prompt = new JTextField();
		booking_ref_prompt.setEditable(false);
		booking_ref_prompt.setText("Booking Reference:");
		JTextField booking_ref_entry = new JTextField();
		booking_ref_entry.setEditable(true);

		booking_ref_panel.add(booking_ref_prompt);
		booking_ref_panel.add(booking_ref_entry);
		
		//Panel for "confirm" button
		JButton enter_button = new JButton();
		JPanel confirm_panel = enter_button(enter_button);
		
		//Panel for an error message if something has gone wrong with name
		JPanel error_message_name_panel = new JPanel();
		error_message_name_panel.setLayout(new GridLayout());
		error_message_name_panel.setVisible(true);
		
		//Text field for error message if something wrong with name
		JTextField error_message_name = new JTextField();
		error_message_name.setEditable(false);
		error_message_name.setVisible(false);
		error_message_name_panel.add(error_message_name);
		
		//Panel for error message if something has gone wrong with booking reference
		JPanel error_message_booking_ref_panel = new JPanel();
		error_message_booking_ref_panel.setLayout(new GridLayout());
		error_message_booking_ref_panel.setVisible(true);
		
		//Text field for error message if something wrong with booking reference
		JTextField error_message_booking_ref = new JTextField();
		error_message_booking_ref.setEditable(false);
		error_message_booking_ref.setVisible(false);
		error_message_booking_ref_panel.add(error_message_booking_ref);
		
		//Add panels to GUI
		guiContainer.add(name_panel);
		guiContainer.add(booking_ref_panel);
		guiContainer.add(confirm_panel);
		guiContainer.add(error_message_name_panel);
		guiContainer.add(error_message_booking_ref_panel);
		
		//Add container to window
		guiFrame.add(guiContainer);
		
		guiFrame.addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				System.out.println("I have closed");
			}
			
		});
		
		//Action listener for entry button
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//If user has entered valid data
				if((check_booking_ref(booking_ref_entry.getText()) == true) && (check_last_name(name_entry.getText()) == true)){
					Passenger passenger;
					try {
						passenger = passenger_list.findBookingRef(booking_ref_entry.getText());
						if(passenger.getCheckIn() == false) {
							if(passenger.getSurname().equals(name_entry.getText())) {
								//Remove un-needed panels
								name_panel.setVisible(false);
								guiContainer.remove(name_panel);
								booking_ref_panel.setVisible(false);
								guiContainer.remove(booking_ref_panel);
								confirm_panel.setVisible(false);
								guiContainer.remove(confirm_panel);
								error_message_name_panel.setVisible(false);
								guiContainer.remove(error_message_name_panel);
								error_message_booking_ref_panel.setVisible(false);
								guiContainer.remove(error_message_booking_ref_panel);
								//Change menu location label from "DETAILS" to "BAGGAGE"
								menu_location.setText("BAGGAGE");
								//Go onto baggage entry interface
								baggage_entry_screen(guiFrame, guiContainer, passenger, passenger_list, flight_list);
							}
							else {
								error_message_name.setVisible(true);
								error_message_name.setText("Incorrect last name - please retry");
							}
						}
						else {
							error_message_name.setVisible(true);
							error_message_name.setText("Sorry, you have already checked in");
						}
					} catch (NoMatchingBookingRefException no_matching_booking_reference) {
						error_message_booking_ref.setVisible(true);
						error_message_booking_ref.setText("Booking reference not recognised");
					}
					//If passenger booking reference and last name match up
				}
				
				if(check_booking_ref(booking_ref_entry.getText()) == false) {
					error_message_booking_ref.setVisible(true);
					error_message_booking_ref.setText("Invalid booking ref, please try again");
				}

				if(check_last_name(name_entry.getText()) == false) {
					error_message_name.setVisible(true);
					error_message_name.setText("Invalid name, please try again");
				}
			}
		});		
	}
	
	//Booking reference must be an alphanumeric 7-character long string
	public boolean check_booking_ref(String booking_ref) {
		Pattern p = Pattern.compile("[^a-zA-Z0-9]");
		boolean invalid = p.matcher(booking_ref).find();
		if(invalid == true || booking_ref.isEmpty() || booking_ref.length()!=7) {
			return false;
		}
		else {
			return true;
		}
	}
	
	//Last name must only contain letters (no spaces or numbers allowed)
	public boolean check_last_name(String last_name) {
		Pattern p = Pattern.compile("[^a-zA-Z]");
		boolean invalid = p.matcher(last_name).find();
		if(invalid == true || last_name.isEmpty()) {
			return false;
		}
		else {
			return true;
		}
	}	
	
	//Screen that allows users to input baggage weight and baggage volume
	private void baggage_entry_screen(JFrame guiFrame, Container guiContainer, Passenger passenger, PassengerList passenger_list, FlightList flight_list) {
		
		//Increase size of frame for added information
		guiFrame.setSize(400, 220);
		
		//List storying data entry panels (weight, dimensions[3], volume)
		JPanel data_entry[] = new JPanel[5];
		for(int i = 0; i<data_entry.length; i++)
		{
			data_entry[i] = new JPanel();
		}
		
		//Panel allowing users to input the weight
		JTextField weight_entry = new JTextField();
		JComboBox<String> weight_units = new JComboBox<String>();
		weight_units.addItem("kg");
		weight_units.addItem("lbs");
		data_entry[0] = add_selection_entry("Enter Weight:", guiContainer, weight_entry, weight_units);
		
		JTextField dimension_entry[] = new JTextField[3];
		
		for(int i = 0; i<dimension_entry.length; i++)
		{
			dimension_entry[i] = new JTextField();
		}
		
		//Panel allowing users to input the height (height/length/width order does not matter)
		JComboBox<String> dimension_units = new JComboBox<String>();
		dimension_units.addItem("cm");
		dimension_units.addItem("inches");
		data_entry[1] = add_selection_entry("Enter Dimensions:", guiContainer, dimension_entry[0], dimension_units);

		//Panel allowing users to input width
		JComboBox<String> filler2 = new JComboBox<String>();
		data_entry[2] = add_selection_entry("", guiContainer, dimension_entry[1], filler2);
		filler2.setVisible(false);
		
		//Panel allowing users to input length
		JComboBox<String> filler3 = new JComboBox<String>();
		data_entry[3] = add_selection_entry("", guiContainer, dimension_entry[2], filler3);
		filler3.setVisible(false);
		
		//Panel allowing users to input the volume
		JTextField volume_entry = new JTextField();
		JComboBox<String> volume_units = new JComboBox<String>();
		//\u00B3 unicode for cubed
		volume_units.addItem("cm\u00B3");
		volume_units.addItem("inches\u00B3");
		data_entry[4] = add_selection_entry("OR Enter Volume:", guiContainer, volume_entry, volume_units);
		
		//Confirm button
		JButton enter_button = new JButton();
		JPanel confirm_panel = enter_button(enter_button);
		
		for(int i=0; i<dimension_entry.length; i++) {
		//Grey out volume box if dimension is entered
			dimension_entry[i].getDocument().addDocumentListener(new DocumentListener() {
				public void changedUpdate(DocumentEvent arg0) {
					volume_entry.setEditable(false);
				}
	
				public void insertUpdate(DocumentEvent e) {
					volume_entry.setEditable(false);
				}
	
				public void removeUpdate(DocumentEvent e) {
					if(dimension_entry[0].getText().isEmpty() && dimension_entry[1].getText().isEmpty() && dimension_entry[2].getText().isEmpty()) {
					volume_entry.setEditable(true);
					}
				}
			});
		}
		
		//Grey out dimension boxes if volume is entered
		volume_entry.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				for(int i=0; i<dimension_entry.length; i++) {
					dimension_entry[i].setEditable(false);
				}
			}

			public void insertUpdate(DocumentEvent e) {
				for(int i=0; i<dimension_entry.length; i++) {
					dimension_entry[i].setEditable(false);
				}
			}

			public void removeUpdate(DocumentEvent e) {
				if(volume_entry.getText().isEmpty()) {
					for(int i=0; i<dimension_entry.length; i++) {
						dimension_entry[i].setEditable(true);
					}
				}
			}
		});
		
		//Action listener for entry button, check everything and then send across
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				Object[] baggage_weight_object = new Object[2];
				Object[] baggage_volume_object = new Object[2];
				
				baggage_weight_object = calculate_weight(weight_entry, weight_units);
				baggage_weight = (int)baggage_weight_object[1];
				baggage_volume_object = calculate_volume(dimension_entry, dimension_units, volume_entry, volume_units);
				baggage_volume = (int)baggage_volume_object[1];
				
				//If everything is entered correctly
				if((baggage_volume_object[0].equals(true)) && (baggage_volume_object[0].equals(true))) {
					
					//Increment flight details
					try {
						Flight flight = flight_list.findByCode(passenger.getFlightCode());
						float baggage_fees = flight.calculateExcessBaggageFees(baggage_weight);
						flight.incrementBaggageFees(baggage_fees);
						flight.incrementVolume(baggage_volume);
						flight.incrementWeight(baggage_weight);
						flight.incrementPassengers();
						passenger.setCheckIn();
						CheckInDemo.check_in_passenger();
					}
					catch (NoMatchingFlightCodeException no_matching_flight_code_exception) {
						System.out.println("No matching flight code");
					}
					
					for(int i=0; i<data_entry.length; i++) {
						data_entry[i].setVisible(false);
						guiContainer.remove(data_entry[i]);
					}
					confirm_panel.setVisible(false);
					guiContainer.remove(confirm_panel);
					details_screen(guiFrame, guiContainer, passenger_list, flight_list);
				}
				else {
					System.out.println("Invalid details");
				}
			}
		});

		//Add panels to GUI
		for(int i=0; i<data_entry.length; i++) {
			guiContainer.add(data_entry[i]);
		}
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);		
	}
	
	//Function that creates a panel with a text label, a text field and drop-down menu and returns the panel
	private JPanel add_selection_entry(String tag, Container guiContainer, JTextField entry, JComboBox<String> units) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		
		panel.setLayout(new GridLayout());
		
		JTextField prompt = new JTextField();
		prompt.setEditable(false);
		if(tag == "") {
			prompt.setVisible(false);
		}
		else {
			prompt.setText(tag);
		}
		entry.setEditable(true);
		
		panel.add(prompt);
		panel.add(entry);
		panel.add(units);
		
		return panel;
	}
	
	public Object[] calculate_weight(JTextField weight_entry, JComboBox<String> weight_units) {
		
		Object[] return_object = new Object[2];
		
		boolean valid_weight = false;
		int baggage_weight_current = 0;
		
		try {
			//+0.5 to prevent rounding issues
			baggage_weight_current = (int) (Double.parseDouble(weight_entry.getText()) + 0.5);
			valid_weight = true;
		} catch (NumberFormatException exception) {
			weight_entry.setText("Invalid number");
		}
		if(weight_units.getSelectedItem() == "lbs") {
			baggage_weight_current = (int) ((2.2*baggage_weight_current) + 0.5);
		}
		
		return_object[0] = valid_weight;
		return_object[1] = baggage_weight_current;
		
		return return_object;
	}
	
	private Object[] calculate_volume(JTextField dimension_entry[], JComboBox<String> dimension_units, JTextField volume_entry, JComboBox<String> volume_units) {
		
		Object[] return_object = new Object[2];
		
		boolean valid_volume = false;
		int baggage_volume_current = 0;		
		
		try {
			//+0.5 to round properly
			baggage_volume_current = (int) (Double.parseDouble(dimension_entry[0].getText()) * Double.parseDouble(dimension_entry[1].getText()) * Double.parseDouble(dimension_entry[2].getText()) + 0.5); 
			valid_volume = true;
		} catch (NumberFormatException exception) {
			dimension_entry[0].setText("Invalid entries");
		}
		if(dimension_units.getSelectedItem() == "inches") {
			baggage_volume_current = (int) ((16.39*baggage_volume_current) + 0.5);
		}
		else {
			try {
				baggage_volume_current = (int) (Double.parseDouble(volume_entry.getText()) + 0.5);
				valid_volume = true;
			} catch (NumberFormatException exception) {
				volume_entry.setText("Invalid number");
			}
			if(volume_units.getSelectedItem() == "inches\u00B3") {
				baggage_volume_current = (int) ((16.39*baggage_volume_current) + 0.5);
			}
		}
		
		return_object[0] = valid_volume;
		return_object[1] = baggage_volume_current;
		return return_object;
	}
	
	//Function that creates an enter button and puts it at the far right of the screen
	private JPanel enter_button(JButton enter) {
		
		JPanel confirm_panel = new JPanel();
		confirm_panel.setLayout(new GridLayout());
		
		JButton filler_1 = new JButton();
		filler_1.setText("");
		filler_1.setEnabled(false);
		filler_1.setVisible(false);
		JButton filler_2 = new JButton();
		filler_2.setText("");
		filler_2.setEnabled(false);
		filler_2.setVisible(false);
		
		enter.setText("Confirm");
		enter.setEnabled(true);
		
		confirm_panel.add(filler_1);
		confirm_panel.add(filler_2);
		confirm_panel.add(enter);
		
		return confirm_panel;
	}
	
	public void close_gui() {
		guiFrame.setVisible(false);
	}
	
	public KioskGUI(PassengerList passenger_list, FlightList flight_list) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				guiFrame = new JFrame();
				
				/*try {
				UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
				} catch (UnsupportedLookAndFeelException e) {
					System.out.println("Invalid look and feel");
				} catch (IllegalAccessException e) {
					System.out.println("Disallowed access");
				} catch (InstantiationException e) {
					System.out.println("Instantiation Exception");
				} catch (ClassNotFoundException e) {
					System.out.println("Class not found exception");
				}
				SwingUtilities.updateComponentTreeUI(guiFrame);*/
				
				
				guiFrame.setTitle("Check In Kiosk");
				guiFrame.setSize(400, 200);
				guiFrame.setVisible(true);
				
				//Container for details entry screen
				Container guiContainer = new Container();
				guiContainer.setLayout(new BoxLayout(guiContainer, BoxLayout.Y_AXIS));
				
				Panel details_panel = new Panel();
				details_panel.setLayout(new GridLayout());
				details_panel.setBackground(null);
				
				details_panel.add(menu_location);
				
				guiContainer.add(details_panel);
				
				details_screen(guiFrame, guiContainer, passenger_list, flight_list);
			}
		});
	}
}
