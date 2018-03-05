package airport;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class PassengerEntryGUI {
	
	JTextField menu_location = new JTextField();
	JFrame passenger_entry_frame = new JFrame();
	Container guiContainer = new Container();
	KioskGUI kioskGUI;
	
	ArrayList<Passenger> passenger_queue;
	
	//The one parameter check not able to be used from kioskGUI
	Flight flight;
	Pattern flightPattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}$");
	
	//Screen allowing user to input first name, last name, booking reference, flight code and whether they've checked in or not
	private void details_screen(JFrame guiFrame, Container guiContainer, PassengerList passenger_list, FlightList flight_list) {
				
		//Heading to tell user what view they're in (details/baggage)
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		menu_location.setEditable(false);
		menu_location.setText("Welcome to KYC Airport");
		menu_location.setFont(font1);
		
		//Panel for allowing user to enter first name
		JPanel first_name_panel = new JPanel();
		JTextField first_name_entry = data_entry_panel(first_name_panel, "First Name:");
		
		//Panel for allowing user to enter last name
		JPanel last_name_panel = new JPanel();
		JTextField last_name_entry = data_entry_panel(last_name_panel, "Last Name:");
		
		//Booking reference panel
		JPanel booking_ref_panel = new JPanel();
		JTextField booking_ref_entry = data_entry_panel(booking_ref_panel, "Booking Reference:");
		
		//Flight code panel
		JPanel flight_code_panel = new JPanel();
		JTextField flight_code_entry = data_entry_panel(flight_code_panel, "Flight Code:");
		
		//Checked in panel
		JPanel checked_in_panel = new JPanel();
		checked_in_panel.setLayout(new GridLayout());
		
		JTextField checked_in_prompt = new JTextField();
		checked_in_prompt.setVisible(true);
		checked_in_prompt.setEditable(false);
		checked_in_prompt.setText("Checked In?");
		//Checkbox allows user to tick if they've checked in or not
		JCheckBox checked_in_entry = new JCheckBox();
		checked_in_entry.setVisible(true);
		//If they have, they get taken to a screen which allows them to enter baggage info
		//Else get redirected to kioskGUI
		
		checked_in_panel.add(checked_in_prompt);
		checked_in_panel.add(checked_in_entry);
		
		//Panel for "confirm" button
		JButton enter_button = new JButton();
		JPanel confirm_panel = kioskGUI.enter_button(enter_button);
		
		//Add panels to GUI
		guiContainer.add(first_name_panel);
		guiContainer.add(last_name_panel);
		guiContainer.add(booking_ref_panel);
		guiContainer.add(flight_code_panel);
		guiContainer.add(checked_in_panel);
		guiContainer.add(confirm_panel);
		
		//Add container to window
		guiFrame.add(guiContainer);
		
		//ActionListener changing text on button when checkbox is selected/deselected
		checked_in_entry.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(checked_in_entry.isSelected() == true) {
					enter_button.setText("Next Screen");
				}
				else {
					enter_button.setText("Confirm");
				}
			}
		});
		
		//Action listener for entry button
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valid_data = true;
				
				//Check user input
				if(kioskGUI.check_name(first_name_entry.getText()) == false) {
					first_name_entry.setText("Invalid first name");
					valid_data = false;
				}
				if(kioskGUI.check_name(last_name_entry.getText()) == false) {
					last_name_entry.setText("Invalid last name");
					valid_data = false;
				}
				if(kioskGUI.check_booking_ref(booking_ref_entry.getText()) == false) {
					booking_ref_entry.setText("Invalid booking reference");
					valid_data = false;
				}
				if(flightPattern.matcher(flight_code_entry.getText()).find() == false) {
					flight_code_entry.setText("Invalid flight code");
					valid_data = false;
				}
				else {
					try {
						flight = flight_list.findByCode(flight_code_entry.getText());
					} catch (NoMatchingFlightCodeException no_matching_flight_code) {
						flight_code_entry.setText("Unrecognised flight code");
						valid_data = false;
					}
				}
				
				//If user has entered valid data
				if(valid_data == true){
					Passenger new_passenger;
					try {
						new_passenger = new Passenger(first_name_entry.getText(), last_name_entry.getText(), booking_ref_entry.getText(), flight_code_entry.getText(), String.valueOf(checked_in_entry.isSelected()));
						if(checked_in_entry.isSelected() == true) {
							passenger_list.addPassenger(new_passenger);
							remove_panel(first_name_panel);
							remove_panel(last_name_panel);
							remove_panel(booking_ref_panel);
							remove_panel(flight_code_panel);
							remove_panel(checked_in_panel);
							remove_panel(confirm_panel);
							//Change menu location label from "DETAILS" to "BAGGAGE"
							menu_location.setText("BAGGAGE");
							//Go onto baggage entry interface
							baggage_entry_screen(guiFrame, guiContainer, flight, new_passenger, passenger_list, flight_list);
						}
						else {
							first_name_entry.setText("");
							last_name_entry.setText("");
							booking_ref_entry.setText("");
							flight_code_entry.setText("");
							checked_in_entry.setSelected(false);
							JOptionPane.showMessageDialog(passenger_entry_frame,"Success - Go To Kiosk");
							kioskGUI.start_gui(passenger_list, flight_list);
						}
					} catch (InvalidFlightCodeException | InvalidBookingRefException | InvalidParameterException invalid_parameter_exception) {
						//This error should never be called - the parameters are already checked above
						System.out.println("Error in parameters");
					}
				}
			}
		});		
	}
	
	//A very basic version of the one in KioskGUI
	//Designed assuming the user has a sheet with weight and volume printed on ticket
	//Only gets shown when user has already checked in
	//Usually this would be shown when the user is checking in online, this is just for the purposes of flight
	//In an actual airport this screen wouldn't be shown
	private void baggage_entry_screen(JFrame guiFrame, Container guiContainer, Flight flight, Passenger passenger, PassengerList passenger_list, FlightList flight_list) {
		//Set new size of screen
		guiFrame.setSize(400, 140);
		
		//Allows user to enter weight information
		JPanel weight_panel = new JPanel();
		JTextField weight_entry = data_entry_panel(weight_panel, "Enter Weight (kg):");
		
		//Allows user to enter volume information
		JPanel volume_panel = new JPanel();
		JTextField volume_entry = data_entry_panel(volume_panel, "Enter Volume (m\u00B3):");
		
		//Confirm button
		JButton enter_button = new JButton();
		JPanel confirm_panel = kioskGUI.enter_button(enter_button);
		
		//Action listener for entry button, check everything and then send across
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				int baggage_weight = 0;
				int baggage_volume = 0;
				boolean valid_entry = true;
				
				//Check weight
				try {
					baggage_weight = (int) (Double.parseDouble(weight_entry.getText()));
					if(baggage_weight > flight.getMaxWeight()) {
						weight_entry.setText("Weight too large!");
						valid_entry = false;
					}
				}
				catch (NullPointerException | NumberFormatException invalid_weight) {
					weight_entry.setText("Invalid weight");
					valid_entry = false;
				}
				//Check volume
				try {
					baggage_volume = (int) (Double.parseDouble(volume_entry.getText()));
					if(baggage_volume > flight.getMaxVol()) {
						volume_entry.setText("Volume too large!");
						valid_entry = false;
					}
				}
				catch (NullPointerException | NumberFormatException invalid_volume) {
					volume_entry.setText("Invalid volume");
					valid_entry = false;
				}

				//If everything is entered correctly
				if(valid_entry == true) {
					//Calculate excess baggage fees - user should already know these, it's just for use with flight
					float baggage_fees = flight.calculateExcessBaggageFees(baggage_weight);
					//Increment flight details
					flight.incrementBaggageFees(baggage_fees);
					flight.incrementVolume(baggage_volume);
					flight.incrementWeight(baggage_weight);
					flight.incrementPassengers();
					JOptionPane.showMessageDialog(passenger_entry_frame,"Success - Go To Boarding Zone");
					
					//Update GUI components to prepare for next screen
					guiFrame.setSize(400, 200);
					
					remove_panel(weight_panel);
					remove_panel(volume_panel);
					remove_panel(confirm_panel);
					
					passenger_queue.add(passenger);
					//Go back to details screen
					details_screen(guiFrame, guiContainer, passenger_list, flight_list);					
				}
			}
		});

		//Add panels to GUI
		guiContainer.add(weight_panel);
		guiContainer.add(volume_panel);
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);		
	}
	
	//Method to create panel with prompt and text entry
	private JTextField data_entry_panel(JPanel panel, String prompt_text) {
		panel.setLayout(new GridLayout());
		
		JTextField prompt = new JTextField();
		prompt.setEditable(false);
		prompt.setText(prompt_text);
		JTextField data_entry = new JTextField();
		data_entry.setEditable(true);
		panel.add(prompt);
		panel.add(data_entry);
		
		return data_entry;
	}
	
	//Remove panel from container. Used for when screens are switching
	private void remove_panel(JPanel panel) {
		panel.setVisible(false);
		guiContainer.remove(panel);
	}

	public PassengerEntryGUI(PassengerList passenger_list, FlightList flight_list, ArrayList<Passenger> passenger_queue) {
		//start_gui(passenger_list, flight_list);
		this.passenger_queue = passenger_queue;
		
		kioskGUI = new KioskGUI();
		EventQueue.invokeLater(new Runnable() {
			public void run() {
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
				SwingUtilities.updateComponentTreeUI(passenger_entry_frame);*/
				
				passenger_entry_frame.setTitle("New Passenger");
				passenger_entry_frame.setSize(400, 200);
				passenger_entry_frame.setVisible(true);
				
				//Container for details entry screen
				guiContainer.setLayout(new BoxLayout(guiContainer, BoxLayout.Y_AXIS));
				
				//Panel for allowing user to enter their details
				Panel details_panel = new Panel();
				details_panel.setLayout(new GridLayout());
				details_panel.setBackground(null);
				
				//Add banner which tells the user which menu they're in
				details_panel.add(menu_location);
				
				//Add this to the container
				guiContainer.add(details_panel);
				
				//Start up user entry with the GUI
				details_screen(passenger_entry_frame, guiContainer, passenger_list, flight_list);
			}
		});
	}
}
