package engine;

import java.io.File;
import java.io.FileNotFoundException;
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
