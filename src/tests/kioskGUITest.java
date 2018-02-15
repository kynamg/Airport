package tests;

import airport.*;

import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class kioskGUITest {
	
	private KioskGUI gui_test;
	
	@Test
	void test_last_name() {		
		PassengerList passengerlist_mocked = mock(PassengerList.class);
		FlightList flightlist_mocked = mock(FlightList.class);

		gui_test = new KioskGUI(passengerlist_mocked, flightlist_mocked);
		
		boolean actual_valid = gui_test.check_last_name("Jones");
		boolean expected_valid = true;
		String message_valid = "Failed at Jones";
		assertEquals(message_valid, expected_valid, actual_valid);
		
		boolean actual_numbers = gui_test.check_last_name("123456");
		boolean expected_numbers = false;
		String message_numbers = "Failed at 123456";
		assertEquals(message_numbers, expected_numbers, actual_numbers);
		
		boolean actual_letters_numbers = gui_test.check_last_name("BA12345");
		boolean expected_letters_numbers = false;
		String message_letters_numbers = "Failed at BA12345";
		assertEquals(message_letters_numbers, expected_letters_numbers, actual_letters_numbers);
		
		boolean actual_empty = gui_test.check_last_name("");
		boolean expected_empty = false;
		String message_empty = "Failed at empty";
		assertEquals(message_empty, expected_empty, actual_empty);
		
		boolean actual_spaces = gui_test.check_last_name("Yola Jones");
		boolean expected_spaces = false;
		String message_spaces = "Failed at spaces";
		assertEquals(message_spaces, expected_spaces, actual_spaces);
		
		boolean actual_really_long = gui_test.check_last_name("ReeeeeeeeeeeaaaaaalllllyLooooooonnnngg");
		boolean expected_really_long = true;
		String message_really_long = "Failed at really long";
		assertEquals(message_really_long, expected_really_long, actual_really_long);
	}
	
	@Test
	void test_booking_ref() {
		PassengerList passengerlist_mocked = mock(PassengerList.class);
		FlightList flightlist_mocked = mock(FlightList.class);

		gui_test = new KioskGUI(passengerlist_mocked, flightlist_mocked);
		
		boolean actual_valid = gui_test.check_booking_ref("BA12345");
		boolean expected_valid = true;
		String message_valid = "Failed at valid";
		assertEquals(message_valid, expected_valid, actual_valid);
		
		boolean actual_all_numbers = gui_test.check_booking_ref("1234567");
		boolean expected_all_numbers = true;
		String message_all_numbers = "Failed at all numbers";
		assertEquals(message_all_numbers, expected_all_numbers, actual_all_numbers);
		
		boolean actual_all_letters = gui_test.check_booking_ref("ABCDEFG");
		boolean expected_all_letters = true;
		String message_all_letters = "Failed at all letters";
		assertEquals(message_all_letters, expected_all_letters, actual_all_letters);
		
		boolean actual_symbols = gui_test.check_booking_ref("BA1234!");
		boolean expected_symbols = false;
		String message_symbols = "Failed at symbols";
		assertEquals(message_symbols, expected_symbols, actual_symbols);
		
		boolean actual_too_short = gui_test.check_booking_ref("BA123");
		boolean expected_too_short = false;
		String message_too_short = "Failed at too short";
		assertEquals(message_too_short, expected_too_short, actual_too_short);
		
		boolean actual_too_long = gui_test.check_booking_ref("BA1234567");
		boolean expected_too_long = false;
		String message_too_long = "Failed at too long";
		assertEquals(message_too_long, expected_too_long, actual_too_long);
	}
	
	@Test
	void test_weight_calculation() {
		
		PassengerList passengerlist_mocked = mock(PassengerList.class);
		FlightList flightlist_mocked = mock(FlightList.class);

		gui_test = new KioskGUI(passengerlist_mocked, flightlist_mocked);
		
		JTextField weight_entry = new JTextField();
		JComboBox<String> weight_units = new JComboBox<String>();
		weight_units.addItem("kg");
		weight_units.addItem("lbs");
		
		Object[] returned_1 = new Object[2];
		weight_entry.setText("1");
		returned_1 = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_1 = true;
		int expected_weight_1 = 1;
		String message_1 = "Failed at 1";
		assertEquals(message_1, expected_bool_1, returned_1[0]);
		assertEquals(message_1, expected_weight_1, returned_1[1]);
		
		Object[] returned_exclimation = new Object[2];
		weight_entry.setText("!");
		returned_exclimation = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_exclimation = false;
		String message_exclimation = "Failed at !";
		assertEquals(message_exclimation, expected_bool_exclimation, returned_exclimation[0]);
		
		Object[] returned_empty = new Object[2];
		weight_entry.setText("");
		returned_empty = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_empty = false;
		String message_empty = "Failed at empty";
		assertEquals(message_empty, expected_bool_empty, returned_empty[0]);
		
		Object[] returned_2_2 = new Object[2];
		weight_entry.setText("2.2");
		returned_2_2 = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_2_2 = true;
		int expected_weight_2_2 = 2;
		String message_2_2 = "Failed at 2_2";
		assertEquals(message_2_2, expected_bool_2_2, returned_2_2[0]);
		assertEquals(message_2_2, expected_weight_2_2, returned_2_2[1]);
		
		Object[] returned_2_8 = new Object[2];
		weight_entry.setText("2.8");
		returned_2_8 = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_2_8 = true;
		int expected_weight_2_8 = 3;
		String message_2_8 = "Failed at 2_8";
		assertEquals(message_2_8, expected_bool_2_8, returned_2_8[0]);
		assertEquals(message_2_8, expected_weight_2_8, returned_2_8[1]);
		
		Object[] returned_2_8_8 = new Object[2];
		weight_entry.setText("2.8.8");
		returned_2_8_8 = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_2_8_8 = false;
		String message_2_8_8 = "Failed at 2_8";
		assertEquals(message_2_8_8, expected_bool_2_8_8, returned_2_8_8[0]);
		
		Object[] returned_negative = new Object[2];
		weight_entry.setText("-1");
		returned_negative = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_negative = true;
		String message_negative = "Failed at negative";
		assertEquals(message_negative, expected_bool_negative, returned_negative[0]);
		assertEquals(message_negative, 0, returned_negative[1]);
		
		Object[] returned_0 = new Object[2];
		weight_entry.setText("0");
		returned_0 = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_0 = true;
		String message_0 = "Failed at zero";
		assertEquals(message_0, expected_bool_0, returned_0[0]);
		assertEquals(message_0, 0, returned_0[1]);
		
		weight_units.removeItem("kg");
		
		Object[] returned_1_lbs = new Object[2];
		weight_entry.setText("1");
		returned_1_lbs = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_1_lbs = true;
		int expected_weight_1_lbs = 2;
		String message_1_lbs = "Failed at 1 lbs";
		assertEquals(message_1_lbs, expected_bool_1_lbs, returned_1_lbs[0]);
		assertEquals(message_1_lbs, expected_weight_1_lbs, returned_1_lbs[1]);
		
		Object[] returned_2_2_lbs = new Object[2];
		weight_entry.setText("2.2");
		returned_2_2_lbs = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_2_2_lbs = true;
		int expected_weight_2_2_lbs = 4;
		String message_2_2_lbs = "Failed at 2.2 lbs";
		assertEquals(message_2_2_lbs, expected_bool_2_2_lbs, returned_2_2_lbs[0]);
		assertEquals(message_2_2_lbs, expected_weight_2_2_lbs, returned_2_2_lbs[1]);
		
		Object[] returned_10_8_lbs = new Object[2];
		weight_entry.setText("10.8");
		returned_10_8_lbs = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_10_8_lbs = true;
		int expected_weight_10_8_lbs = 24;
		String message_10_8_lbs = "Failed at 2.2 lbs";
		assertEquals(message_10_8_lbs, expected_bool_10_8_lbs, returned_10_8_lbs[0]);
		assertEquals(message_10_8_lbs, expected_weight_10_8_lbs, returned_10_8_lbs[1]);
		
		Object[] returned_0_lbs = new Object[2];
		weight_entry.setText("0");
		returned_0_lbs = gui_test.calculate_weight(weight_entry, weight_units);
		boolean expected_bool_0_lbs = true;
		String message_0_lbs = "Failed at zero_lbs";
		assertEquals(message_0_lbs, expected_bool_0_lbs, returned_0_lbs[0]);
		assertEquals(message_0_lbs, 0, returned_0_lbs[1]);
		
	}

}
