package data.objects;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class GymDay {
	Date date;
	HashMap<Activity, ArrayList<Customer>> attendees;
	HashMap<Activity, ArrayList<Customer>> reservations;
	
	
	public GymDay(Date date) {
		this.date = date;
		attendees = new HashMap<Activity, ArrayList<Customer>>();
		reservations = new HashMap<Activity, ArrayList<Customer>>();
	}

	public void addAttendee(Activity activity, Customer customer) {
		addToList(activity, customer, attendees);
	}
	
	public void addReservation(Activity activity, Customer customer) {
		addToList(activity, customer, reservations);
	}
	
	public void removeAttendee(Activity activity, Customer customer) {
		removeFromList(activity, customer, attendees);
	}
	
	public void removeReservation(Activity activity, Customer customer) {
		removeFromList(activity, customer, reservations);
	}
	
	
	
	


	private void removeFromList(Activity activity, Customer customer, HashMap<Activity, ArrayList<Customer>> map) {
		/* Check if any lists present */
		ArrayList<Customer> aList = map.get(activity);
		if (aList != null) {
			aList.remove(customer);
		}
	}
	
	private void addToList(Activity activity, Customer customer, HashMap<Activity, ArrayList<Customer>> map) {
		/* Check if any lists present */
		if (map.get(activity) == null) {
			/* if not we have to create initial one */
			map.put(activity, new ArrayList<Customer>());
		}
		/* Add the customer */
		map.get(activity).add(customer);
	}
	
	
	
	public Date getDate() {
		return date;
	}

	public HashMap<Activity, ArrayList<Customer>> getAttendees() {
		return attendees;
	}

	public HashMap<Activity, ArrayList<Customer>> getReservations() {
		return reservations;
	}
}
