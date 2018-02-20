package serialtest;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import jpos.JposException;
import jpos.events.DataEvent;
import jpos.events.DataListener;

public class UI_Controller implements DataListener{

	private jpos.Scanner scanner = new jpos.Scanner();
	
	@FXML
	TextArea textbox;
	
	@FXML
	TextArea textbox2;
	
	@FXML
	public void buttonClick() {
			updateBox(textbox, "Initialising JavaPOS...\n");
			// Create scanner instance and register for data events.
			scanner = new jpos.Scanner();
			scanner.addDataListener(this);
			
	        // Initialize the scanner.  Exception thrown if a method fails.
	        try {
	        	updateBox(textbox, "Opening the device...\n");
	        	scanner.open("DLS-QW2120-USB-OEM");
	        	updateBox(textbox, "Claiming the device...\n");
	        	scanner.claim(1000);
	        	updateBox(textbox, "Adding DataListener...\n");
	        	scanner.addDataListener(this);
	        	updateBox(textbox, "Enabling the device...\n");
	        	scanner.setDeviceEnabled(true);
	        	updateBox(textbox, "Setting 'DataEventEnabled' property...\n");
	        	scanner.setDataEventEnabled(true);
	        	updateBox(textbox, "---SUCCESS!---\n");
	        	updateBox(textbox, "[ LISTENING MODE ]");
	        } catch (JposException ex) {
	        	updateBox(textbox, "=== JPOS EXCEPTION TRACE[1] ===: \n" + ex.toString());
	        	updateBox(textbox, "\n=== END OF EXCEPTION ===\n");
	        	updateBox(textbox, "\nClosing DLS-QW2120-USB-OEM\n");
	        	try {
					scanner.close();
				} catch (JposException e) {
					updateBox(textbox, "=== JPOS EXCEPTION TRACE[1] ===: \n" + e.toString());
					updateBox(textbox, "\n=== END OF EXCEPTION ===\n");
				}
	        	updateBox(textbox, "\nTrying DLS-QW2100-USB-OEM\n");
	        	 try {
	 	        	updateBox(textbox, "Opening the device...\n");
	 	        	scanner.open("DLS-QW2100-USB-OEM");
	 	        	updateBox(textbox, "Claiming the device...\n");
	 	        	scanner.claim(1000);
	 	        	updateBox(textbox, "Adding DataListener...\n");
	 	        	scanner.addDataListener(this);
	 	        	updateBox(textbox, "Enabling the device...\n");
	 	        	scanner.setDeviceEnabled(true);
	 	        	updateBox(textbox, "Setting 'DataEventEnabled' property...\n");
	 	        	scanner.setDataEventEnabled(true);
	 	        	updateBox(textbox, "---SUCCESS!---\n");
	 	        	updateBox(textbox, "[ LISTENING MODE ]");
	 	        } catch (JposException ex2) {
	 	        	updateBox(textbox, "=== JPOS EXCEPTION TRACE[1] ===: \n" + ex2.toString());
		        	updateBox(textbox, "\n=== END OF EXCEPTION ===\n");
	 	        	
	 	        }
	        }
	        //...Success! Continue doing work...
	}


	@Override
	public void dataOccurred(DataEvent e) {
		jpos.Scanner sc = (jpos.Scanner) e.getSource();
		try {
			updateBox(textbox2, "[" + e.getStatus() + "] " + scanner.getScanData().toString() + "\n");
            sc.setDataEventEnabled(true);
        } catch (JposException ex){
        	updateBox(textbox2, "=== JPOS EXCEPTION TRACE[2] ===: \n" + ex.toString());
        	updateBox(textbox2, "\n=== END OF EXCEPTION ===\n");
        }
		
	}
	
	private void updateBox(TextArea textbox, String text) {
		System.out.println(text);
		Platform.runLater(() -> {
			textbox.appendText(text);
		});
	}
	
}
