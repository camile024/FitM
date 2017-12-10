package data.objects;

import java.util.Date;

public class Customer {
	/* Highest ID loaded in the memory */
	private static int highestID = 0;
	
	private int id;
	private String name;
	private String surname;
	private String DOB;
	private String phone;
	private int entries;
	private Card card;
	private Date openDate;
	
	public Customer(int id) {
		this.id = id;
		if (id > highestID) {
			highestID = id;
		}
	}

	
	
	/* **************************** */
	/*								*/
	/* 		GETTERS/SETTERS 		*/
	/*								*/
	/* **************************** */
	public static int getHighestID() {
		return highestID;
	}

	public static void setHighestID(int highestID) {
		Customer.highestID = highestID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getDOB() {
		return DOB;
	}

	public void setDOB(String dOB) {
		DOB = dOB;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getEntries() {
		return entries;
	}

	public void setEntries(int entries) {
		this.entries = entries;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public int getId() {
		return id;
	}
}
