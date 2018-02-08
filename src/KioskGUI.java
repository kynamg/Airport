
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.util.*;
import java.util.regex.Pattern;

public class KioskGUI {
	
	JFrame guiFrame;
	int baggage_weight = 0;
	int baggage_volume = 0;	
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new KioskGUI();
			}
		});
	}
	
	public void detailsScreen(JFrame guiFrame, PassengerList passenger_list, FlightList flight_list) {
		
		Container guiContainer = new Container();
		guiContainer.setLayout(new BoxLayout(guiContainer, BoxLayout.Y_AXIS));
		
		//Heading to tell user what view they're in (details/baggage)
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		JTextField menu_location = new JTextField();
		menu_location.setEditable(false);
		menu_location.setText("DETAILS");
		menu_location.setFont(font1);
		
		Panel details_panel = new Panel();
		details_panel.setLayout(new GridLayout());
		details_panel.setBackground(null);;
		
		details_panel.add(menu_location);
		
		//Panel for allowing user to enter last name
		Panel name_panel = new Panel();
		name_panel.setLayout(new GridLayout());
		
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
		
		JPanel error_message_name_panel = new JPanel();
		error_message_name_panel.setLayout(new GridLayout());
		error_message_name_panel.setVisible(true);
		
		JTextField error_message_name = new JTextField();
		error_message_name.setEditable(false);
		error_message_name.setVisible(false);
		error_message_name_panel.add(error_message_name);
		
		JPanel error_message_booking_ref_panel = new JPanel();
		error_message_booking_ref_panel.setLayout(new GridLayout());
		error_message_booking_ref_panel.setVisible(true);
		
		JTextField error_message_booking_ref = new JTextField();
		error_message_booking_ref.setEditable(false);
		error_message_booking_ref.setVisible(false);
		error_message_booking_ref_panel.add(error_message_booking_ref);
		
		//Add panels to GUI
		guiContainer.add(details_panel);
		guiContainer.add(name_panel);
		guiContainer.add(booking_ref_panel);
		guiContainer.add(confirm_panel);
		guiContainer.add(error_message_name_panel);
		guiContainer.add(error_message_booking_ref_panel);
		
		guiFrame.add(guiContainer);
		
		//Action listener for entry button
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((check_booking_ref(booking_ref_entry.getText()) == true) && (check_last_name(name_entry.getText()) == true)){
					Passenger passenger = passenger_list.findBookingRef(booking_ref_entry.getText());
					if(passenger != null) {
						if(passenger.getSurname().equals(name_entry.getText())) {
							guiContainer.remove(name_panel);
							guiContainer.remove(booking_ref_panel);
							guiContainer.remove(confirm_panel);
							guiContainer.remove(error_message_name_panel);
							guiContainer.remove(error_message_booking_ref_panel);
							menu_location.setText("BAGGAGE");
							//Go onto baggage entry interface
							baggage_entry_screen(guiFrame, guiContainer, passenger, flight_list);
						}
						else {
							error_message_name.setVisible(true);
							error_message_name.setText("Incorrect last name - please retry");;
						}
					}
					else {
						error_message_booking_ref.setVisible(true);
						error_message_booking_ref.setText("Booking reference not recognised");
					}
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
	
	public void baggage_entry_screen(JFrame guiFrame, Container guiContainer, Passenger passenger, FlightList flight_list) {
		
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
		
		//Panel allowing users to input the height (height/length/width order does not matter)
		JTextField dimension_entry[] = new JTextField[3];
		
		for(int i = 0; i<dimension_entry.length; i++)
		{
			dimension_entry[i] = new JTextField();
		}
		
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
				boolean valid_volume = false;
				boolean valid_weight = false;
				
				baggage_weight = calculate_weight(weight_entry, weight_units, valid_weight);
				
				baggage_volume = calculate_volume(dimension_entry, dimension_units, volume_entry, volume_units, valid_volume);
				
				System.out.println("Baggage weight = " + baggage_weight);
				System.out.println("Baggage volume = " + baggage_volume);
			
				Flight flight = flight_list.findByCode(passenger.getFlightCode());
				float baggage_fees = flight.calculateExcessBaggageFees(baggage_weight);
				flight.incrementBaggageFees(baggage_fees);
				flight.incrementVolume(baggage_volume);
				flight.incrementWeight(baggage_weight);
				
				//If everything is entered correctly
				if((valid_volume == true) && (valid_weight == true)) {
					System.out.println("Yes. One can proceed");
					//Send things to the various places here
				}
			}
		});
		
		for(int i=0; i<data_entry.length; i++) {
			guiContainer.add(data_entry[i]);
		}
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);		
	}
	
	//Function that creates a panel with a text label, a text field and drop-down menu and returns the panel
	public JPanel add_selection_entry(String tag, Container guiContainer, JTextField entry, JComboBox<String> units) {
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
	
	public int calculate_weight(JTextField weight_entry, JComboBox<String> weight_units, boolean valid_weight) {
		int baggage_weight_current = 0;
		
		try {
			baggage_weight_current = (int) (Double.parseDouble(weight_entry.getText()));
			valid_weight = true;
			//NOTE TO SELF: THIS DOESN'T ROUND PROPERLY
		} catch (NumberFormatException exception) {
			weight_entry.setText("Invalid number");
		}
		if(weight_units.getSelectedItem() == "lbs") {
			baggage_weight_current = (int) (2.2*baggage_weight_current);
		}
		
		return baggage_weight_current;
	}
	
	public int calculate_volume(JTextField dimension_entry[], JComboBox<String> dimension_units, JTextField volume_entry, JComboBox<String> volume_units, boolean valid_volume) {
		int baggage_volume_current = 0;		
		
		if(volume_entry.getText().isEmpty()) {
			try {
				baggage_volume_current = (int) (Double.parseDouble(dimension_entry[0].getText()) * Double.parseDouble(dimension_entry[1].getText()) * Double.parseDouble(dimension_entry[2].getText())); 
				valid_volume = true;
			} catch (NumberFormatException exception) {
				dimension_entry[0].setText("Invalid entries");
			}
			if(dimension_units.getSelectedItem() == "inches") {
				baggage_volume_current = (int) (16.39*baggage_volume_current);
			}
		}
		else {
			try {
				baggage_volume_current = (int) (Double.parseDouble(volume_entry.getText()));
				valid_volume = true;
			} catch (NumberFormatException exception) {
				volume_entry.setText("Invalid number");
			}
			if(volume_units.getSelectedItem() == "inches\u00B3") {
				baggage_volume_current = (int) (16.39*baggage_volume_current);
			}
		}
		
		return baggage_volume_current;
	}
	
	//Function that creates an enter button and puts it at the far right of the screen
	public JPanel enter_button(JButton enter) {
		
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
	
	public KioskGUI() {
		
		guiFrame = new JFrame();
		Passenger passenger_test = new Passenger("Yola", "Jones", "BA12345", "123456", false);
		PassengerList passenger_list = new PassengerList();
		passenger_list.addPassenger(passenger_test);
		
		Flight flight_test = new Flight("123456", "New Zealand", "BA", 100, 100, 100);
		FlightList flight_list = new FlightList();
		flight_list.add(flight_test);
		
		
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
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Check In Kiosk");
		guiFrame.setSize(400, 200);
		guiFrame.setVisible(true);
		
		detailsScreen(guiFrame, passenger_list, flight_list);
	}
}
