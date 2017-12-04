package engine;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map.Entry;

import data.objects.Activity;

/**
 * To use:
 * 	1) Create an instance of CustomerDB with the path to /DATA/ directory
 * 	2) Call initActivities() to load activities from file
 * @author Kamil
 *
 */
public class CustomerDB {
	private HashMap<Integer, Activity> activities;
	private String dirName;
	
	public CustomerDB(String fileName) {
		this.dirName = fileName;
	}
	
	/**
	 * Initializes activities from data file
	 * @throws FileNotFoundException
	 */
	public void initActivities() throws FileNotFoundException {
		activities = new HashMap<Integer, Activity>();
		FileReader fr = new FileReader(dirName + CONST.ACT_LIST_FILENAME);
		fr.load();
		fr.filterAct();
		HashMap<Integer, String> contents = fr.getFilteredID();
		for (Entry<Integer, String> entry : contents.entrySet()) {
			Activity act = new Activity(entry.getKey(), entry.getValue());
			activities.put(entry.getKey(), act);
		}
	}
	
	public Activity getActivity(int id) {
		return activities.get(id);
	}
	
	
	
	/**
	 * Replaces activity with a new one (updates the object) - for it to work
	 * the new activity has to have the same ID (otherwise it'll just be added)
	 * @param activity
	 * @throws FileNotFoundException 
	 */
	public void updateActivity(Activity activity) throws FileNotFoundException {
		activities.put(activity.getId(), activity);
		updateFiles();
	}
	
	/**
	 * Updates all the data-related files
	 * @throws FileNotFoundException 
	 */
	private void updateFiles() throws FileNotFoundException {
		/* Activities */
		FileSaver fs = new FileSaver(dirName + CONST.ACT_LIST_FILENAME);
		HashMap<Integer, String> contents = new HashMap<Integer, String>();
		for (Entry<Integer, Activity> entry : activities.entrySet()) {
			contents.put(entry.getKey(), entry.getValue().getName());
		}
		fs.saveAct(contents);
	}
	
	public HashMap<Integer, Activity> getActivities() {
		return activities;
	}
	
	
}
