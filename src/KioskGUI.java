
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;

public class KioskGUI {
	
	JFrame guiFrame;
	JPanel buttonPanel;
	JTextField numberCalc;
	int calcOperation = 0;
	int currentCalc;
	
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
		
		Font font1 = new Font("SansSerif", Font.BOLD, 18);
		JTextField menu_location = new JTextField();
		menu_location.setEditable(false);
		menu_location.setBackground(null);
		menu_location.setText("DETAILS");
		menu_location.setFont(font1);
		
		Panel details_panel = new Panel();
		details_panel.setLayout(new GridLayout());
		details_panel.setBackground(null);;
		
		details_panel.add(menu_location);
		
		Panel name_panel = new Panel();
		name_panel.setLayout(new GridLayout());
		
		JTextField name_prompt = new JTextField();
		name_prompt.setEditable(false);
		name_prompt.setText("Last Name:");
		JTextField name_entry = new JTextField();
		name_entry.setEditable(true);
		
		name_panel.add(name_prompt);
		name_panel.add(name_entry);		
		
		Panel booking_ref_panel = new Panel();
		booking_ref_panel.setLayout(new GridLayout());
		
		JTextField booking_ref_prompt = new JTextField();
		booking_ref_prompt.setEditable(false);
		booking_ref_prompt.setText("Booking Reference:");
		JTextField booking_ref_entry = new JTextField();
		booking_ref_entry.setEditable(true);

		booking_ref_panel.add(booking_ref_prompt);
		booking_ref_panel.add(booking_ref_entry);
		
		JButton enter = new JButton();
		JPanel confirm_panel = enter_button(enter);
		
		guiContainer.add(details_panel);
		guiContainer.add(name_panel);
		guiContainer.add(booking_ref_panel);
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);
		
		enter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(name_entry.getText().isEmpty()){
					name_entry.setText("Please enter your last name");
				}
				if(booking_ref_entry.getText().isEmpty()) {
					booking_ref_entry.setText("Please enter your booking ref");
				}
				
				if((booking_ref_entry.getText().isEmpty() == false) && (name_entry.getText().isEmpty() == false)) {
					System.out.println("Continue");
					guiContainer.remove(name_panel);
					guiContainer.remove(booking_ref_panel);
					guiContainer.remove(confirm_panel);
					menu_location.setText("BAGGAGE");
					baggage_entry_screen(guiFrame, guiContainer);
				}
			}
		});		
	}
	
	public void baggage_entry_screen(JFrame guiFrame, Container guiContainer) {
		
		guiFrame.setSize(400, 220);
		
		ArrayList<String> weight_selection = new ArrayList<String>();
		weight_selection.add("kg");
		weight_selection.add("lbs");

		JPanel weight_panel = add_selection_entry("Enter Weight:", guiContainer, weight_selection);
		
		ArrayList<String> dimension_selection = new ArrayList<String>();
		dimension_selection.add("metres");
		dimension_selection.add("inches");
		
		JPanel dimension_panel1 = add_selection_entry("Enter Dimensions:", guiContainer, dimension_selection);
		JPanel dimension_panel2 = add_selection_entry("", guiContainer, dimension_selection);
		JPanel dimension_panel3 = add_selection_entry("", guiContainer, dimension_selection);
		
		ArrayList<String> volume_selection = new ArrayList<String>();
		volume_selection.add("metres\u00B3");
		volume_selection.add("inches\u00B3");
		
		JPanel volume_panel = add_selection_entry("OR Enter Volume:", guiContainer, volume_selection);
		
		JButton enter = new JButton();
		JPanel confirm_panel = enter_button(enter);
		
		guiContainer.add(weight_panel);
		guiContainer.add(dimension_panel1);
		guiContainer.add(dimension_panel2);
		guiContainer.add(dimension_panel3);
		guiContainer.add(volume_panel);
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);		
	}
	
	public JPanel add_selection_entry(String tag, Container guiContainer, ArrayList<String> options) {
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
		JTextField entry = new JTextField();
		entry.setEditable(true);
		JComboBox units = new JComboBox();
		
		Iterator iter = options.iterator();
	      while (iter.hasNext()) {
	         units.addItem(iter.next());
	      }
		
		
		panel.add(prompt);
		panel.add(entry);
		panel.add(units);
		
		return panel;
		
	}
	
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
		
	/*public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		numberCalc.setText(action);
	}*/
}
