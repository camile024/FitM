package engine;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * To use:
 * 	1) Create an instance with a given FileName
 * 	2) Call one of the save.. methods
 * @author Kamil
 *
 */
public class FileSaver {
	private String fileName;
	private String contents;
	private PrintWriter file;
	
	/**
	 * Creates a FileSaver for a given filename, also checks if the file actually exists
	 * (when creating new file it's the caller's responsibility to ensure this function can
	 * do its job)
	 * @param fileName	path + filename + extension
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException 
	 */
	public FileSaver(String fileName) throws FileNotFoundException {
		this.fileName = fileName;
		try {
			file = new PrintWriter(fileName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Saves given string as-is
	 * @param data	String to be saved in the file
	 */
	public void saveRawString(String data) {
		contents = data;
		saveContents();
	}
	
	/**
	 * Saves data in regular 'field=value' format
	 * @param data	HashMap with fields and corresponding values
	 */
	public void saveFilteredData(HashMap<String, String> data) {
		contents = "";
		for (Entry<String, String> entry : data.entrySet()) {
			contents += entry.getKey() + '=';
			contents += entry.getValue() + '\n';
		}
		saveContents();
	}
	
	/**
	 * Writes to the file with Activities-list format
	 * @param data	HashMap with activities and their ids
	 */
	public void saveAct(HashMap<Integer, String> data) {
		contents = "";
		for (Entry<Integer, String> entry : data.entrySet()) {
			contents += "id=";
			contents += String.valueOf(entry.getKey());
			contents += "\nname=";
			contents += String.valueOf(entry.getValue());
			contents += '\n';
		}
		saveContents();
	}
	
	public void saveWeekPlan(HashMap<Integer, ArrayList<Integer>> data) {
		contents = "";
		for (Entry<Integer, ArrayList<Integer>> entry : data.entrySet()) {
			for (Integer oneDay : entry.getValue()) {
				contents += "day=";
				contents += String.valueOf(entry.getKey());
				contents += "\nid=";
				contents += String.valueOf(oneDay);
				contents += '\n';
			}
		}
		saveContents();
	}
	
	
	public void saveGymDay(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> data) {
		contents = "";
		HashMap<Integer, ArrayList<Integer>> attendees = data.get(CONST.INDEX_ATTENDANTS);
		HashMap<Integer, ArrayList<Integer>> reservations = data.get(CONST.INDEX_RESERVATIONS);
		
		for (Entry<Integer, ArrayList<Integer>> entry : reservations.entrySet()) { //for every list of customers
			contents += "activity=";
			contents += String.valueOf(entry.getKey()); //add the id of the activity
			contents += "\ncs_res=";
			for (Integer customer : entry.getValue()) { //get the list of customers
				contents += String.valueOf(customer) + ','; //add each one, separated by comma
			}
			contents += ";\n"; //end of the list
		}
		
		for (Entry<Integer, ArrayList<Integer>> entry : attendees.entrySet()) { //for every list of customers
			contents += "activity=";
			contents += String.valueOf(entry.getKey()); //add the id of the activity
			contents += "\ncs_att=";
			for (Integer customer : entry.getValue()) { //get the list of customers
				contents += String.valueOf(customer) + ','; //add each one, separated by comma
			}
			contents += ";\n"; //end of the list
		}
		saveContents();
	}
	
	/**
	 * Saves prepared 'contents' string to the file
	 */
	private void saveContents() {
		file.print(contents);
		file.close();
		
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
