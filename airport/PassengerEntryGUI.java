package airport;

import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class PassengerEntryGUI {
	
	JTextField menu_location = new JTextField();
	JFrame passenger_entry_frame = new JFrame();
	Container guiContainer = new Container();
	KioskGUI kioskGUI;
	
	Flight flight;
	Pattern flightPattern = Pattern.compile("^[a-zA-Z]{2}[0-9]{4}$");
	
	private void details_screen(JFrame guiFrame, Container guiContainer, PassengerList passenger_list, FlightList flight_list) {
				
		//Heading to tell user what view they're in (details/baggage)
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		menu_location.setEditable(false);
		menu_location.setText("Welcome to KYC Airport");
		menu_location.setFont(font1);
		
		//Panel for allowing user to enter last name
		JPanel first_name_panel = new JPanel();
		JTextField first_name_entry = data_entry_panel(first_name_panel, "First Name:");
		
		//Panel for allowing user to enter last name
		JPanel last_name_panel = new JPanel();
		JTextField last_name_entry = data_entry_panel(last_name_panel, "Last Name:");
		
		JPanel booking_ref_panel = new JPanel();
		JTextField booking_ref_entry = data_entry_panel(booking_ref_panel, "Booking Reference:");
		
		JPanel flight_code_panel = new JPanel();
		JTextField flight_code_entry = data_entry_panel(flight_code_panel, "Flight Code:");
		
		JPanel checked_in_panel = new JPanel();
		checked_in_panel.setLayout(new GridLayout());
		
		JTextField checked_in_prompt = new JTextField();
		checked_in_prompt.setVisible(true);
		checked_in_prompt.setEditable(false);
		checked_in_prompt.setText("Checked In?");
		JCheckBox checked_in_entry = new JCheckBox();
		checked_in_entry.setVisible(true);
		
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
							baggage_entry_screen(guiFrame, guiContainer, flight, passenger_list, flight_list);
						}
						else {
							first_name_entry.setText("");
							last_name_entry.setText("");
							booking_ref_entry.setText("");
							flight_code_entry.setText("");
							checked_in_entry.setSelected(false);
							JOptionPane.showMessageDialog(passenger_entry_frame,"Success - Go To Kiosk");  
						}
					} catch (InvalidFlightCodeException | InvalidBookingRefException | InvalidParameterException invalid_parameter_exception) {
						System.out.println("Error in parameters - should already be checked!!");
					}
				}
			}
		});		
	}
	
	//Screen that allows users to input baggage weight and baggage volume
	protected void baggage_entry_screen(JFrame guiFrame, Container guiContainer, Flight flight, PassengerList passenger_list, FlightList flight_list) {
		//Increase size of frame for added information
		guiFrame.setSize(400, 140);
		
		//List storying data entry panels (weight, dimensions[3] (height, length, width), volume)
		JPanel weight_panel = new JPanel();
		JTextField weight_entry = data_entry_panel(weight_panel, "Enter Weight (kg):");
		
		//Set of text fields allowing user to input height/length/width
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
					//Increment flight details
					//Verify flight code with passenger details
					float baggage_fees = flight.calculateExcessBaggageFees(baggage_weight);
					//Display baggage fees if they exist and ask the user to confirm
					//Increment flight details
					flight.incrementBaggageFees(baggage_fees);
					flight.incrementVolume(baggage_volume);
					flight.incrementWeight(baggage_weight);
					flight.incrementPassengers();
					JOptionPane.showMessageDialog(passenger_entry_frame,"Success - Go To Boarding Zone");
								
					guiFrame.setSize(400, 200);
					
					remove_panel(weight_panel);
					remove_panel(volume_panel);
					remove_panel(confirm_panel);
					
					details_screen(guiFrame, guiContainer, passenger_list, flight_list);					
				}
				else {
					
				}
			}
		});

		//Add panels to GUI
		guiContainer.add(weight_panel);
		guiContainer.add(volume_panel);
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);		
	}
	
	
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
	
	private void remove_panel(JPanel panel) {
		panel.setVisible(false);
		guiContainer.remove(panel);
	}

	public PassengerEntryGUI(PassengerList passenger_list, FlightList flight_list) {
		//start_gui(passenger_list, flight_list);
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
				SwingUtilities.updateComponentTreeUI(guiFrame);*/
				
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
	
	public static void main(String[] args) {
		
		PassengerList passenger_list = new PassengerList();
		FlightList flight_list = new FlightList();
		
		try {
			Flight flight = new Flight("AB1234", "Japan", "BA", 12, 4, 3);
			flight_list.add(flight);
		} catch (InvalidFlightCodeException | InvalidParameterException e) {
			e.printStackTrace();
		}
		
		PassengerEntryGUI passenger_entry_gui = new PassengerEntryGUI(passenger_list, flight_list);		
	}
}
