
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
		
		Container container = new Container();
		//container.setLayout(new GridLayout());
		
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("Check In Kiosk");
		guiFrame.setSize(300, 300);
		guiFrame.setLocationRelativeTo(null);
		
		JTextField passengerFirstName = new JTextField();
		passengerFirstName.setEditable(true);
		
		JPanel passengerFirstNamePanel = new JPanel();
		passengerFirstNamePanel.setLayout(new FlowLayout());
		
		container.add(passengerFirstName);
		container.setVisible(true);
		
		guiFrame.add(container);
		guiFrame.setVisible(true);
		
		
	}
	
	private void addNumberButton(Container parent, String name) {
		JButton but = new JButton(name);
		but.setActionCommand(name);
		but.addActionListener(this);
		parent.add(but);
	}
	
	private void addActionButton(Container parent, int action, String text) {
		JButton but = new JButton(text);
		but.setActionCommand(text);
		OperatorAction addAction = new OperatorAction(1);
		but.addActionListener(addAction);
		parent.add(but);	
	}
		
	public void actionPerformed(ActionEvent event) {
		String action = event.getActionCommand();
		numberCalc.setText(action);
	}
	
	private class OperatorAction implements ActionListener {
		private int operator;
		
		public OperatorAction(int operation) {
			operator = operation;			
		}
		
		public void actionPerformed(ActionEvent event) {
			currentCalc = Integer.parseInt(numberCalc.getText());
			calcOperation = operator;
		}	
	}
}
