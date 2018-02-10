package data;

import data.objects.Activity;
import data.objects.Customer;

public class Attendance extends Reservation {

	public Attendance(Customer customer, Activity activity) {
		super(customer, activity);
	}
}
