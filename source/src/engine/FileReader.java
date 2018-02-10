package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * To use:
 * 	1) Create an instance with fileName()
 * 	2) Call load to load the contents (this is where FileNotFoundException can pop up)
 * 	3) Call corresponding filter()
 * 	4) Use getters to get a particular field and/or whole collection
 * @author Kamil
 *
 */
public class FileReader {
	private final String delimiter = "=|\r?\n|\r";
	private final String delimiterCustomerList = ",|;|\r?\n|\r";
	private String fileName = "";
	private String contents;
	private HashMap<String, String> filteredContents;
	private HashMap<Integer, String> filteredID;
	
	/**
	 * Creates an instance of cusom FileReader for a given fileName
	 * @param fileName path to the file that's to be read
	 */
	public FileReader(String fileName) {
		this.fileName = fileName;
		initMaps();
	}
	
	/**
	 * Loads contents of the file to the buffer
	 * @throws FileNotFoundException fileName not specified or wrong
	 */
	public void load() throws FileNotFoundException {
		initMaps();
		File file = new File(fileName);
		//java.util.Locale loc = new java.util.Locale("pl", "PL");
		Scanner sc = new Scanner(file, "UTF-8");
		contents = "";
		
		while (sc.hasNextLine()) {
			contents += sc.nextLine() + '\n';
		}
		sc.close();
	}
	
	/**
	 * Filters the contents of the file containing Activity entries
	 */
	public void filterAct() {
		initMaps();
		Scanner sc = new Scanner(contents);
		sc.useDelimiter(delimiter);
		int id = -1;
		while (sc.hasNext()) {
			//get next 2 fields
			String field = sc.next();
			String value = sc.next();
			
			if (field.equals("id")) {
				id = Integer.parseInt(value);
				
			} else if (field.equals("name")) {
				filteredID.put(id, value);
			}
			
		}
		sc.close();
	}
	
	/**
	 * Filters the contents of the file containing regular fields (each line with FIELD=VALUE..)
	 */
	public void filterFielded() {
		initMaps();
		Scanner sc = new Scanner(contents);
		sc.useDelimiter(delimiter);
		while (sc.hasNext()) {
			//get next field
			String field = sc.next();
			String value = sc.next();
			filteredContents.put(field, value);
		}
		sc.close();
	}
	
	/**
	 * Filters the contents of the file containing weekplan entries
	 */
	public HashMap<Integer, ArrayList<Integer>> filterWeekPlan() {
		/* initialise hashmap */
		HashMap<Integer, ArrayList<Integer>> filtered = new HashMap<Integer, ArrayList<Integer>>();
		for (int i = 1; i <= 7; i++ ) {
			filtered.put(i, new ArrayList<Integer>());
		}
		
		Scanner sc = new Scanner(contents);
		sc.useDelimiter(delimiter);
		int day = -1;
		while (sc.hasNext()) {
			//get next 2 fields
			String field = sc.next();
			String value = sc.next();
			
			if (field.equals("day")) {
				day = Integer.parseInt(value);
				
			} else if (field.equals("id")) {
				filtered.get(day).add(Integer.parseInt(value));
			}
			
		}
		sc.close();
		return filtered;
	}
	
	public HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> filterGymDay() {
		/* initialise hashmap */
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> filtered = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		filtered.put(CONST.INDEX_RESERVATIONS, new HashMap<Integer, ArrayList<Integer>>());
		filtered.put(CONST.INDEX_ATTENDANTS, new HashMap<Integer, ArrayList<Integer>>());
		
		Scanner sc = new Scanner(contents);
		sc.useDelimiter(delimiter);
		int actId = -1;
		
		while (sc.hasNext()) {
			//get next 2 fields
			String field = sc.next();
			String value = sc.next();
			
			if (field.equals("activity")) {
				actId = Integer.parseInt(value);
				
			} else if (field.equals("cs_res")) {
				if (filtered.get(CONST.INDEX_RESERVATIONS).get(actId) == null) {
					filtered.get(CONST.INDEX_RESERVATIONS).put(actId, new ArrayList<Integer>());
				}
				filtered.get(CONST.INDEX_RESERVATIONS).get(actId).addAll(getCustomerList(value));
			} else if (field.equals("cs_att")) {
				if (filtered.get(CONST.INDEX_ATTENDANTS).get(actId) == null) {
					filtered.get(CONST.INDEX_ATTENDANTS).put(actId, new ArrayList<Integer>());
				}
				filtered.get(CONST.INDEX_ATTENDANTS).get(actId).addAll(getCustomerList(value));
			}
		}
		
		sc.close();
		return filtered;
		
	}
	
	/**
	 * Takes a String with customer IDs listed and splits it up into an ArrayList of ints
	 * @param value
	 * @return
	 */
	private ArrayList<Integer> getCustomerList(String value) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Scanner sc = new Scanner(value);
		sc.useDelimiter(delimiterCustomerList);
		while (sc.hasNext()) {
			String next = sc.next();
			if (!next.trim().equals(""))
				result.add(Integer.valueOf(next));
		}
		sc.close();
		return result;
		
	}
	
   private void initMaps() {
        filteredContents = new HashMap<String, String>();
        filteredID = new HashMap<Integer, String>();
    }
	   
	/**
	 * Returns a field value of a field identified by a string
	 * @param key	name of the field
	 * @return	string value
	 */
	public String getField(String key) {
		return filteredContents.get(key);
	}
	
	/**
	 * Returns value of a field identified by an ID (needs to be pre-loaded by a function
	 * that loads to filteredID)
	 * @param id	id of the object
	 * @return	string value
	 */
	public String getField(int id) {
		return filteredID.get(id);
	}
	
	
	/**
	 * 
	 * @return unfiltered contents loaded in the memory
	 */
	public String getContents() {
		return contents;
	}
	
	/**
	 * returns filtered HashMap (can be null if 
	 * the FileReader instance not filtered before!)
	 * @return
	 */
	public HashMap<String, String> getFilteredContents() {
		return filteredContents;
	}
	
	/**
	 * returns filtered HashMap (can be null if 
	 * the FileReader instance not filtered before!)
	 * @return
	 */
	public HashMap<Integer, String> getFilteredID() {
		return filteredID;
	}
	
	/**
	 * (Re-)set the fileName for this FileReader instance
	 * @param fileName new fileName
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

	
}
