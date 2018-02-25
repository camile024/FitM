package engine;

import java.io.FileNotFoundException;
import java.util.Date;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public abstract class Locale {
	private static HashMap<String, String> stringBase;
	private static Date currentDate = new Date();
	private static boolean dateInitialized = false;
	private static Timer updateTimer;
	
	private static StringProperty dateProperty;
	
	/**
	 * Loads a language
	 * @param lang
	 * @throws FileNotFoundException 
	 */
	public static void load(Language lang) throws FileNotFoundException {
		stringBase = new HashMap<String, String>();
		FileReader fr;
		if (lang == Language.PL) {
		    
			fr = new FileReader(CONST.LANG_PL_PATH);
		} else {
			fr = new FileReader(CONST.LANG_EN_PATH);
		}
		fr.load();
		fr.filterFielded();
		stringBase = fr.getFilteredContents();
	}
	
	/**
	 * Returns a localized string based on the key
	 * @param key	string key
	 * @return	value in currently selected language
	 */
	public static String getString(String key) {
		if (stringBase.get(key) == null) {
			return key;
		} else {
			return stringBase.get(key);
		}
	}
	
	
	public static Date getCurrentDate() {
	    return currentDate;
	}
	
	public static StringProperty dateProperty() {
	    return dateProperty;
	}
	
	
	
	public static void initDate(int delay) {
	    if (!dateInitialized) {
	        dateInitialized = true;
	        currentDate = new Date();
	        updateTimer = new Timer();
	        TimerTask timerTask = (new TimerTask() {
	            @Override
	            public void run() {
	                updateDate();
	            }
	        });
	        dateProperty = new SimpleStringProperty("");
	        updateTimer.scheduleAtFixedRate(timerTask, 500, delay);
	    }
	    
	}
	
	private static void updateDate() {
	    currentDate = new Date();
	    /* Running javafx thread - regular behaviour */
	    try {
		    Platform.runLater(()-> {
		    	dateProperty.set(CustomerDB.getFullDateFormat().format(getCurrentDate()));
		    });
		/* Toolkit not initialised - behaviour expected only during test, update date in a
		 * regular way
		 */
	    } catch (IllegalStateException ex) {
	    	dateProperty.set(CustomerDB.getFullDateFormat().format(getCurrentDate()));
	    }
	    
	}
	
}
