package data.objects;

import java.util.ArrayList;
import java.util.Date;

import data.Attendance;
import data.Reservation;

public class GymDay {
	Date date;
	ArrayList<Attendance> attendees;
	ArrayList<Reservation> reservations;
	
	
	public GymDay(Date date) {
		this.date = date;
		attendees = new ArrayList<Attendance>();
		reservations = new ArrayList<Reservation>();
	}

	public void addAttendee(Attendance attendee) {
		attendees.add(attendee);
	}
	
	public void addReservation(Reservation reservation) {
		reservations.add(reservation);
	}
	
	public void removeAttendee(Attendance attendee) {
		attendees.remove(attendee);
	}
	
	public void removeReservation(Reservation reservation) {
		reservations.remove(reservation);
	}
	
	
	
	
	
	
	public ArrayList<Attendance> getAttendees() {
		return attendees;
	}
	
	public ArrayList<Attendance> getAttendees(Activity activity) {
		ArrayList<Attendance> subSet = new ArrayList<Attendance>();
		for (Attendance att : attendees) {
			if (att.getActivity() == activity) {
				subSet.add(att);
			}
		}
		return subSet;
	}

	public ArrayList<Reservation> getReservations() {
		return reservations;
	}
	
	public ArrayList<Reservation> getReservations(Activity activity) {
		ArrayList<Reservation> subSet = new ArrayList<Reservation>();
		for (Reservation res : reservations) {
			if (res.getActivity() == activity) {
				subSet.add(res);
			}
		}
		return subSet;
	}

	public Date getDate() {
		return date;
	}


}
