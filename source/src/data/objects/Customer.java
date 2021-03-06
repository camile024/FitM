package data.objects;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import engine.CONST;
import engine.CustomerDB;
import engine.Locale;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Customer {
	/* Highest ID loaded in the memory */
	private static int highestID = -1;
	private static boolean highestIDEnabled = false;
	
	private int id;
	private String name;
	private String surname;
	private String DOB;
	private String phone;
	private int entries;
	private Card card;
	private Date openDate;
	
	private SimpleIntegerProperty idProperty = new SimpleIntegerProperty();
	private SimpleIntegerProperty entriesProperty = new SimpleIntegerProperty();
	private SimpleStringProperty nameProperty = new SimpleStringProperty();
	private SimpleStringProperty surnameProperty = new SimpleStringProperty();
	private SimpleStringProperty DOBProperty = new SimpleStringProperty();
	private SimpleStringProperty phoneProperty = new SimpleStringProperty();
	private SimpleStringProperty cardProperty = new SimpleStringProperty();
	private SimpleStringProperty openDateProperty = new SimpleStringProperty();
	
	public Customer() {
		this(highestID + 1);
	}
	
	public Customer(int id) {
		this.id = id;
		/* update highestID */
		if (id > highestID) {
			highestID = id;
		} else if (highestIDEnabled == true){
			this.id = ++highestID;
		}
		openDate = new Date();
	}
	
	public static void resetID() {
		highestID = -1;
	}

	public static void disableHighestID() {
		highestIDEnabled = false;
	}
	
	public static void enableHighestID() {
		highestIDEnabled = true;
	}
	
	public static void resetHighestID() {
		highestID = CONST.DEFAULT_HIGHEST_ID;
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
	
	public SimpleIntegerProperty idProperty() {
	    idProperty.set(id);
	    return  idProperty;
	}
	
	public SimpleStringProperty nameProperty() {
        nameProperty.set(name);
        return nameProperty;
    }
	
	public SimpleStringProperty surnameProperty() {
	    surnameProperty.set(surname);
        return surnameProperty;
    }
	
	public SimpleStringProperty phoneProperty() {
	    phoneProperty.set(phone);
        return phoneProperty;
    }
	
	public SimpleStringProperty DOBProperty() {
	    DOBProperty.set(DOB);
        return DOBProperty;
    }
	
	public SimpleIntegerProperty entriesProperty() {
	    entriesProperty.set(entries);
        return entriesProperty;
    }
	
	public SimpleStringProperty cardProperty() {
	    if (card == null) {
	        cardProperty.set(Locale.getString(CONST.TXT_NONE));
	    } else {
	        cardProperty.set(String.valueOf(card.getNumber()));
	    }
        return cardProperty;
    }
	
	public SimpleStringProperty openDateProperty() {
		long millisPerDay = TimeUnit.DAYS.toMillis(1);
	    long difference = (openDate.getTime() / millisPerDay) - (Locale.getCurrentDate().getTime() / millisPerDay);
	    if (difference < 0) {
	        difference = -1;
	    }
	    
	    /* The string property is: X Days (DATE) */
	    openDateProperty.set((difference + 1) + " "
	            + Locale.getString(CONST.TXT_DAYS) + " (" + CustomerDB.getOpenDateFormat().format(openDate)
	            + ")");
	    
	    return openDateProperty;
	}
}
