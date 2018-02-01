
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class KioskGUI implements ActionListener {
	
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
	
	public KioskGUI() {
		
		guiFrame = new JFrame();
		Container guiContainer = new Container();
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Check In Kiosk");
		guiFrame.setSize(400, 150);
		guiContainer.setLayout(new BoxLayout(guiContainer, BoxLayout.Y_AXIS));
		
		Font font1 = new Font("SansSerif", Font.BOLD, 20);
		JTextField menu_location = new JTextField();
		menu_location.setEditable(false);
		menu_location.setText("DETAILS");
		menu_location.setFont(font1);
		
		Panel details_panel = new Panel();
		details_panel.setLayout(new GridLayout());
		
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
		
		Panel confirm_panel = new Panel();
		confirm_panel.setLayout(new GridLayout(1,3));
		
		JButton filler_1 = new JButton();
		filler_1.setText("");
		filler_1.setEnabled(false);
		filler_1.setVisible(false);
		JButton filler_2 = new JButton();
		filler_2.setText("");
		filler_2.setEnabled(false);
		filler_2.setVisible(false);
		
		JButton enter = new JButton();
		enter.setText("Confirm");
		enter.setEnabled(true);
		
		confirm_panel.add(filler_1);
		confirm_panel.add(filler_2);
		confirm_panel.add(enter);
		
		booking_ref_panel.add(booking_ref_prompt);
		booking_ref_panel.add(booking_ref_entry);
		
		guiContainer.add(details_panel);
		guiContainer.add(name_panel);
		guiContainer.add(booking_ref_panel);
		guiContainer.add(confirm_panel);
		
		guiFrame.add(guiContainer);
		
		guiFrame.setVisible(true);
		
		/*Container container = new Container();
		container.setLayout(new GridLayout());
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Check In Kiosk");
		guiFrame.setSize(300, 300);
		guiFrame.setLocationRelativeTo(null);
		
		JTextField passengerFirstName = new JTextField();
		passengerFirstName.setEditable(true);
		JButton passengerSubmit = new JButton();
		passengerSubmit.setText("enter");
		
		JPanel passengerFirstNamePanel = new JPanel();
		passengerFirstNamePanel.setLayout(new FlowLayout());
		passengerFirstNamePanel.setVisible(true);
		
		container.add(passengerFirstName);
		container.add(passengerSubmit);
		container.setVisible(true);
		
		guiFrame.add(container);
		guiFrame.setVisible(true);*/
	}
		
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		numberCalc.setText(action);
	}
}
