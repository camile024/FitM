package engine;

import java.io.FileNotFoundException;
import java.text.ParseException;

public class Application {
    CustomerDB database;
    
	public static void main(String[] args) {
		Application app = new Application();
		app.run();
	}
	
	public void run() {
	    /* Load settings, initialize language */
	    try {
            Settings.loadSettings();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
	    
	    /* Initialize the database */
	    database = new CustomerDB(CONST.APP_DIR);
	    try {
            database.initActivities();
            database.initCustomers();
            database.initCards();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
	    
	}

}
