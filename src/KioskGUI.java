
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.event.*;
import java.util.*;

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
	
	public void detailsScreen(JFrame guiFrame) {
		
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
		
		//Add panels to GUI
		guiContainer.add(details_panel);
		guiContainer.add(name_panel);
		guiContainer.add(booking_ref_panel);
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);
		
		//Action listener for entry button
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if((booking_ref_entry.getText().isEmpty() == false) && (name_entry.getText().isEmpty() == false)) {
					//Checking details goes in here!!
					System.out.println("Continue");
					guiContainer.remove(name_panel);
					guiContainer.remove(booking_ref_panel);
					guiContainer.remove(confirm_panel);
					menu_location.setText("BAGGAGE");
					//Go onto baggage entry interface
					baggage_entry_screen(guiFrame, guiContainer);
				}
				
				//Check for valid name
				if(name_entry.getText().isEmpty()){
					name_entry.setText("Please enter your last name");
				}
				//Check for valid booking reference
				if(booking_ref_entry.getText().isEmpty()) {
					booking_ref_entry.setText("Please enter your booking ref");
				}
			}
		});		
	}
	
	public void baggage_entry_screen(JFrame guiFrame, Container guiContainer) {
		
		//Increase size of frame for added information
		guiFrame.setSize(400, 220);
		
		//Panel allowing users to input the weight
		JTextField weight_entry = new JTextField();
		JComboBox<String> weight_units = new JComboBox<String>();
		weight_units.addItem("kg");
		weight_units.addItem("lbs");
		JPanel weight_panel = add_selection_entry("Enter Weight:", guiContainer, weight_entry, weight_units);
		
		//Panel allowing users to input the height (height/length/width order does not matter)
		JTextField dimension_entry1 = new JTextField();
		JComboBox<String> dimension_units = new JComboBox<String>();
		dimension_units.addItem("cm");
		dimension_units.addItem("inches");
		JPanel dimension_panel1 = add_selection_entry("Enter Dimensions:", guiContainer, dimension_entry1, dimension_units);

		//Panel allowing users to input width
		JTextField dimension_entry2 = new JTextField();
		JComboBox<String> filler2 = new JComboBox<String>();
		JPanel dimension_panel2 = add_selection_entry("", guiContainer, dimension_entry2, filler2);
		filler2.setVisible(false);
		
		//Panel allowing users to input length
		JTextField dimension_entry3 = new JTextField();
		JComboBox<String> filler3 = new JComboBox<String>();
		JPanel dimension_panel3 = add_selection_entry("", guiContainer, dimension_entry3, filler3);
		filler3.setVisible(false);
		
		//Panel allowing users to input the volume
		JTextField volume_entry = new JTextField();
		JComboBox<String> volume_units = new JComboBox<String>();
		//\u00B3 unicode for cubed
		volume_units.addItem("cm\u00B3");
		volume_units.addItem("inches\u00B3");
		JPanel volume_panel = add_selection_entry("OR Enter Volume:", guiContainer, volume_entry, volume_units);
		
		//Confirm button
		JButton enter_button = new JButton();
		JPanel confirm_panel = enter_button(enter_button);
		
		//Grey out volume box if dimension is entered
		dimension_entry1.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				volume_entry.setEditable(false);
			}

			public void insertUpdate(DocumentEvent e) {
				volume_entry.setEditable(false);
			}

			public void removeUpdate(DocumentEvent e) {
				if(dimension_entry1.getText().isEmpty() && dimension_entry2.getText().isEmpty() && dimension_entry3.getText().isEmpty()) {
				volume_entry.setEditable(true);
				}
			}
		});
		
		//Grey out volume box if dimension is entered
		dimension_entry2.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				volume_entry.setEditable(false);
			}

			public void insertUpdate(DocumentEvent e) {
				volume_entry.setEditable(false);
			}

			public void removeUpdate(DocumentEvent e) {
				if(dimension_entry1.getText().isEmpty() && dimension_entry2.getText().isEmpty() && dimension_entry3.getText().isEmpty()) {
				volume_entry.setEditable(true);
				}
			}
		});
		
		//Grey out volume box if dimension is entered
		dimension_entry3.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				volume_entry.setEditable(false);
			}

			public void insertUpdate(DocumentEvent e) {
				volume_entry.setEditable(false);
			}

			public void removeUpdate(DocumentEvent e) {
				if(dimension_entry1.getText().isEmpty() && dimension_entry2.getText().isEmpty() && dimension_entry3.getText().isEmpty()) {
				volume_entry.setEditable(true);
				}
			}
		});
		
		//Grey out dimension boxes if volume is entered
		volume_entry.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent arg0) {
				dimension_entry1.setEditable(false);
				dimension_entry2.setEditable(false);
				dimension_entry3.setEditable(false);
			}

			public void insertUpdate(DocumentEvent e) {
				dimension_entry1.setEditable(false);
				dimension_entry2.setEditable(false);
				dimension_entry3.setEditable(false);
			}

			public void removeUpdate(DocumentEvent e) {
				if(volume_entry.getText().isEmpty()) {
				dimension_entry1.setEditable(true);
				dimension_entry2.setEditable(true);
				dimension_entry3.setEditable(true);
				}
			}
		});
		
		//Action listener for entry button, check everything and then send across
		enter_button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				boolean valid_volume = false;
				boolean valid_weight = false;
				
				try {
					baggage_weight = (int) (Double.parseDouble(weight_entry.getText()));
					valid_weight = true;
					//NOTE TO SELF: THIS DOESN'T ROUND PROPERLY
				} catch (NumberFormatException exception) {
					weight_entry.setText("Invalid number");
				}
				if(weight_units.getSelectedItem() == "lbs") {
					baggage_weight = (int) (2.2*baggage_weight);
				}
				if(volume_entry.getText().isEmpty()) {
					try {
						baggage_volume = (int) (Double.parseDouble(dimension_entry1.getText()) * Double.parseDouble(dimension_entry2.getText()) * Double.parseDouble(dimension_entry3.getText())); 
						valid_volume = true;
					} catch (NumberFormatException exception) {
						dimension_entry1.setText("Invalid entries");
					}
					if(dimension_units.getSelectedItem() == "inches") {
						baggage_volume = (int) (16.39*baggage_volume);
					}
				}
				else {
					try {
						baggage_volume = (int) (Double.parseDouble(volume_entry.getText()));
						valid_volume = true;
					} catch (NumberFormatException exception) {
						volume_entry.setText("Invalid number");
					}
					if(volume_units.getSelectedItem() == "inches\u00B3") {
						baggage_volume = (int) (16.39*baggage_volume);
					}
				}
				System.out.println("Baggage weight = " + baggage_weight);
				System.out.println("Baggage volume = " + baggage_volume);
			
				//If everything is entered correctly
				if((valid_volume == true) && (valid_weight == true)) {
					System.out.println("Yes. One can proceed");
					//Send things to the various places here
				}
			}
		});
		
		guiContainer.add(weight_panel);
		guiContainer.add(dimension_panel1);
		guiContainer.add(dimension_panel2);
		guiContainer.add(dimension_panel3);
		guiContainer.add(volume_panel);
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
		guiFrame.setSize(400, 150);
		guiFrame.setVisible(true);
		
		detailsScreen(guiFrame);
	}
}
