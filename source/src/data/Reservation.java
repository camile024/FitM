package data;

import data.objects.Activity;
import data.objects.Customer;

public class Reservation {

	Customer customer;
	Activity activity;
	
	public Reservation(Customer customer, Activity activity) {
		this.customer = customer;
		this.activity = activity;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}
	
	
}
