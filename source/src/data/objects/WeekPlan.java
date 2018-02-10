package data.objects;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * DAYS IN WEEKPLAN ARE COUNTED 1-7
 * @author Kamil
 *
 */
public class WeekPlan {

	private HashMap<Integer, ArrayList<Activity>> activities;
	
	public WeekPlan() {
		activities = new HashMap<Integer, ArrayList<Activity>>();
		for (int i = 1; i <= 7; i++) {
			activities.put(i, new ArrayList<Activity>());
		}
	}
	
	public void addActivity(int dayOfTheWeek, Activity activity) {
		activities.get(dayOfTheWeek).add(activity);
	}
	
	public void removeActivity(int dayOfTheWeek, Activity activity) {
		activities.get(dayOfTheWeek).remove(activity);
	}

	public HashMap<Integer, ArrayList<Activity>> getActivities() {
		return activities;
	}
	
}
