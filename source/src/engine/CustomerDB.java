package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import data.Attendance;
import data.Reservation;
import data.objects.Activity;
import data.objects.Card;
import data.objects.Customer;
import data.objects.GymDay;
import data.objects.WeekPlan;

/**
 * To use:
 * 	1) Create an instance of CustomerDB with the path to app main directory
 * 	2) Call initActivities() to load activities from file
 *  3) Call initCustomers() to load Customers from data files
 *  4) Call initCards() to load Cards and associate them with Customers
 *  5) Call initWeekPlan() to load WeekPlan's data and instantiate the object
 *  6) Call loadMonth(Date) to load a particular month when needed 
 * @author Kamil
 *
 */
public class CustomerDB {
	private HashMap<Integer, Activity> activities;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Card> cards;
	private HashMap<String, GymDay> currentMonth;
	private WeekPlan weekPlan;
	private String dirName;
	private static SimpleDateFormat dateFormat = new SimpleDateFormat(CONST.DATE_FORMAT_DEFAULT);
	private static SimpleDateFormat daylessDateFormat = new SimpleDateFormat(CONST.DATE_FORMAT_DAYLESS);
	private static SimpleDateFormat openDateFormat = new SimpleDateFormat(CONST.DATE_FORMAT_OPEN);
	private static SimpleDateFormat fullDateFormat = new SimpleDateFormat(CONST.DATE_FORMAT_FULL);
	
	public CustomerDB(String fileName) {
		this.dirName = fileName;
	}
	
	/**
	 * Initializes activities from data file
	 * @throws FileNotFoundException
	 */
	public void initActivities() throws FileNotFoundException {
		Activity.resetID();
		/* Turn off highestID for the duration of the initialisation */
		Activity.disableHighestID();
		activities = new HashMap<Integer, Activity>();
		FileReader fr = new FileReader(dirName + CONST.ACT_LIST_PATH);
		fr.load();
		fr.filterAct();
		HashMap<Integer, String> contents = fr.getFilteredID();
		for (Entry<Integer, String> entry : contents.entrySet()) {
			Activity act = new Activity(entry.getKey(), entry.getValue());
			activities.put(entry.getKey(), act);
		}
		/* Turn off highestID for the duration of the initialisation */
		Activity.enableHighestID();
	}
	
	/**
	 * Initializes Customers from data files
	 * @throws FileNotFoundException
	 * @throws ParseException		Thrown when date-parsing fails
	 */
	public void initCustomers() throws FileNotFoundException, ParseException {
		Customer.resetID();
		/* Turn off highestID for the duration of the initialisation */
		Customer.disableHighestID();
		customers = new HashMap<Integer, Customer>();
		FileReader fr = new FileReader(dirName + CONST.CUSTOMER_LIST_PATH);
		FileReader fr_customer;
		fr.load();
		String contents = fr.getContents();
		Scanner sc = new Scanner(contents);
		
		/* Read all IDs and find corresponding files containing customer data */
		while (sc.hasNextLine()) {
			int nextId = Integer.parseInt(sc.nextLine());
			fr_customer = new FileReader(dirName + CONST.CUSTOMER_DIR + nextId);
			fr_customer.load();
			fr_customer.filterFielded();
			
			/* Create Customer objects, fill them with data from files, add to the storage */
			Customer customer = new Customer(Integer.parseInt(fr_customer.getField(CONST.CUSTOMER_ID)));
			customer.setDOB(fr_customer.getField(CONST.CUSTOMER_DOB));
			customer.setEntries(Integer.parseInt(fr_customer.getField(CONST.CUSTOMER_ENTRIES)));
			customer.setName(fr_customer.getField(CONST.CUSTOMER_NAME));
			customer.setSurname(fr_customer.getField(CONST.CUSTOMER_SURNAME));
			customer.setOpenDate(openDateFormat.parse(fr_customer.getField(CONST.CUSTOMER_OPEN)));
			customer.setPhone(fr_customer.getField(CONST.CUSTOMER_PHONE));
			customers.put(nextId, customer);
			
		}
		/* re-enable highest id */
		Customer.enableHighestID();
		sc.close();
	}
	
	/**
	 * Initializes member Cards from data files
	 * and assigns them to existing Customers
	 * @throws FileNotFoundException
	 */
	public void initCards() throws FileNotFoundException {
		cards = new HashMap<Integer, Card>();
		FileReader fr = new FileReader(dirName + CONST.CARD_LIST_PATH);
		FileReader fr_card;
		fr.load();
		String contents = fr.getContents();
		Scanner sc = new Scanner(contents);
		
		/* Read all numbers and find corresponding files containing card data */
		while (sc.hasNextLine()) {
			int nextNum = Integer.parseInt(sc.nextLine());
			fr_card = new FileReader(dirName + CONST.CARD_DIR + nextNum);
			fr_card.load();
			fr_card.filterFielded();
			
			/* Create a Card instance - assign to Customer (if possible), add to HashMap */
			Card card = new Card(nextNum);
			int customerId = Integer.parseInt(fr_card.getField(CONST.CARD_CUSTOMER_ID));
			Customer customer = customers.get(customerId);
			if (customer != null) {
				customer.setCard(card);
				card.setCustomer(customer);
			}
			cards.put(nextNum, card);
			
		}
		sc.close();
	}
	
	/**
	 * Initializes the WeekPlan object from data file
	 * @throws FileNotFoundException
	 */
	public void initWeekPlan() throws FileNotFoundException {
		weekPlan = new WeekPlan();
		FileReader fr = new FileReader(dirName + CONST.WEEKPLAN_PATH);
		fr.load();
		HashMap<Integer, ArrayList<Integer>> contents = fr.filterWeekPlan();
		for (Entry<Integer, ArrayList<Integer>> entry : contents.entrySet()) {
			for (Integer act : entry.getValue()) {
				if (getActivity(act) != null)
					weekPlan.addActivity(entry.getKey(), getActivity(act));
			}
		}
	}
	
	
	public HashMap<String, GymDay> loadMonth(Date date) throws ParseException {
		currentMonth = new HashMap<String, GymDay>();
		FileReader fr;
		for (int day = 0; day <= 31; day++) {
			try {
				String parsedDay;
				if (day < 10) {
					parsedDay = "0" + day;
				} else {
					parsedDay = "" + day;
				}
				String fileName = daylessDateFormat.format(date) + "-" + parsedDay;
				fr = new FileReader(dirName + CONST.CALENDAR_DIR + fileName);
				fr.load();
				Date gymDayDate = dateFormat.parse(fileName);
				GymDay gymDay = new GymDay(gymDayDate);
				HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> data = fr.filterGymDay();
				fillGymDay(data, gymDay);
				currentMonth.put(fileName, gymDay);
				
			} catch (FileNotFoundException ex) {
				//do nothing - not all dates have representing dates
			}
		}
		return currentMonth;
	}
	
	
	private void fillGymDay(HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> data, GymDay gymDay) throws FileNotFoundException {
		HashMap<Integer, ArrayList<Integer>> attendees = data.get(CONST.INDEX_ATTENDANTS);
		HashMap<Integer, ArrayList<Integer>> reservations = data.get(CONST.INDEX_RESERVATIONS);
		boolean overwriteDay = false;
		/* Attendees */
		for (Entry<Integer, ArrayList<Integer>> entry : attendees.entrySet()) {
			if (getActivity(entry.getKey()) != null) { //make sure to check activity still exists (gymday file can be outdated)
				for (Integer customer : entry.getValue()) {
					if (getCustomer(customer) != null) { //make sure customer exists too
						gymDay.addAttendee(new Attendance(getCustomer(customer), getActivity(entry.getKey())));
					} else {
						overwriteDay = true;
					}
				}
			} else {
				overwriteDay = true;
			}
		}
		/* Reservations*/
		for (Entry<Integer, ArrayList<Integer>> entry : reservations.entrySet()) {
			if (getActivity(entry.getKey()) != null) { //as above - make sure there's an activity related
				for (Integer customer : entry.getValue()) {
					if (getCustomer(customer) != null) { //as above - make sure customer exists
						gymDay.addReservation(new Reservation(getCustomer(customer), getActivity(entry.getKey())));
					} else {
						overwriteDay = true;
					}
				}
			} else {
				overwriteDay = true;
			}
		}
		if (overwriteDay) { //if any customer/activity listed in the file doesn't exist, make sure to update the file
			updateGymDayFile(gymDay);
		}
	}
	
	public Activity getActivity(int id) {
		return activities.get(id);
	}
	
   
   /**
    * Assigns a card to a customer and vice-versa (overwrites
    * previous card/customer, can be set to null in either)
    * @param customer
    * @param card
 * @throws FileNotFoundException 
    */
   public void assignCard(Customer customer, Card card) throws FileNotFoundException {
       Customer prevCustomer = null;
       Card prevCard = null;
       
       /* Get previous customer/card */
       if (card != null) {
           prevCustomer = card.getCustomer();
       }
       if (customer != null) {
           prevCard = customer.getCard();
       }
       
       /* Clear any previous references */
       if (prevCustomer != null) {
           prevCustomer.setCard(null);
       }
       if (prevCard != null) {
           prevCard.setCustomer(null);
       } 
       
       /* Update destinations (has to be after everything else in case
        * new destination = old destination)
        */
       if (customer != null) {
           customer.setCard(card);
       }
       if (card != null) {
           card.setCustomer(customer);
       }
       
       /* Any changes have to be saved here, otherwise the info might be lost */
       updateSingleObject(prevCard, true);
       updateSingleObject(prevCustomer, true);
       updateSingleObject(card, true);
       updateSingleObject(customer, true);
   }
  
   /**
    * Adds a single object (must be of one of the data.objects classes) to the database
    * and filesystem
    * @param obj
    * @throws Exception 
    */
   public void addSingleObject(Object obj) throws Exception {
	   /* CUSTOMER */
	    if (obj instanceof Customer) {
	        addCustomer((Customer) obj);
	    /* CARD */
	    } else if (obj instanceof Card) {
	        addCard((Card) obj);
	    /* ACTIVITY */
	    } else if (obj instanceof Activity) {
	    	addActivity((Activity) obj);
	    /* WEEKPLAN */
	    } else if (obj instanceof WeekPlan) {
	    	/* WeekPlan cannot be added - it's always there, do nothing */
	    /* GYMDAY */
	    } else if (obj instanceof GymDay) {
	    	GymDay newDay = (GymDay) obj;
	    	currentMonth.put(dateFormat.format(newDay.getDate()), newDay);
	    	updateGymDayFile(newDay);
	    }
   }
   
   /**
    * Adds a single object (must be of one of the data.objects classes) to the database
    * and filesystem
    * @param obj
    * @throws FileNotFoundException 
    */
   public void removeSingleObject(Object obj) throws FileNotFoundException {
	   /* CUSTOMER */
	    if (obj instanceof Customer) {
	        removeCustomer((Customer) obj);
	    /* CARD */
	    } else if (obj instanceof Card) {
	        removeCard((Card) obj);
	    /* ACTIVITY */
	    } else if (obj instanceof Activity) {
	    	removeActivity((Activity) obj);
	    /* WEEKPLAN */
	    } else if (obj instanceof WeekPlan) {
	    	/* WeekPlan cannot be removed - do nothing */
	    /* GYMDAY */
	    } else if (obj instanceof GymDay) {
	    	removeGymDay((GymDay) obj);
	    }
   }
	
	/**
	 * Updates all the data-related files
	 * @throws FileNotFoundException 
	 */
	public void updateFiles() throws FileNotFoundException {
		updateActivitiesFile();
		updateCustomerFiles();
		updateCardFiles();
		updateWeekPlanFile();
	}
	
	
	/**
	 * Updates all the files that are relevant for the particular object
	 * Saves time by not overwriting unrelated files.
	 * @param updatedObj   Object that has been updated in software
	 * @param ignoreLists  Whether or not to ignore list files (in case they are updated once outside this function)
	 * @throws FileNotFoundException
	 */
	public void updateSingleObject(Object updatedObj, boolean ignoreLists) throws FileNotFoundException {
	    /* CUSTOMER */
	    if (updatedObj instanceof Customer) {
	        Customer customer = (Customer)updatedObj;
	        if (!ignoreLists)
	            updateCustomerList();
	        updateSingleCustomer(customer);
	        if (customer.getCard() != null)
	            updateSingleCard(customer.getCard());
	    /* CARD */
	    } else if (updatedObj instanceof Card) {
	        Card card = (Card)updatedObj;
	        if (!ignoreLists)
	            updateCardList();
	        updateSingleCard(card);
	        if (card.getCustomer() != null) 
	            updateSingleCustomer(card.getCustomer());
	    /* ACTIVITY */
	    } else if (updatedObj instanceof Activity) {
	        updateActivitiesFile();
	    /* WEEKPLAN */
	    } else if (updatedObj instanceof WeekPlan) {
	    	updateWeekPlanFile();
	    /* GYMDAY */
	    } else if (updatedObj instanceof GymDay) {
	    	updateGymDayFile((GymDay)updatedObj);
	    }
	}
	
	
	   /**
	    * Creates a card
	    * @param card
	    * @throws Exception FileNotFoundException if file writing problem occurs,
	    * @throws Exception if card already exists
	    */
	  private void addCard(Card card) throws Exception {
	       if (cards.get(card.getNumber()) != null) {
	           throw new Exception("Card already exists!");
	       } else {
	           cards.put(card.getNumber(), card);
	       }
	       updateSingleObject(card, false);
	   }
	  
	  
	/**
	 * Creates an activity
	 * @param activity
	 * @throws Exception FileNotFoundException if file writing problem occurs,
	 * @throws Exception if activity already exists
	 */
	private void addActivity(Activity activity) throws Exception {
	    if (activities.get(activity.getId()) != null) {
	        throw new Exception("Activity already exists!");
	    } else {
	        activities.put(activity.getId(), activity);
	    }
	    updateSingleObject(activity, false);
	}
	
	/**
	 * Creates a customer
	 * @param customer
	 * @throws Exception FileNotFoundException if file writing problem occurs,
	 * @throws Exception if customer already exists
	 */
	private void addCustomer(Customer customer) throws Exception {
        if (customers.get(customer.getId()) != null) {
            throw new Exception("Customer already exists!");
        } else {
            customers.put(customer.getId(), customer);
        }
        updateSingleObject(customer,false);
    }
	
	
	/**
	 * Removes an Activity from the system
	 * @param activitiy
	 * @throws FileNotFoundException
	 */
  	private void removeActivity(Activity activity) throws FileNotFoundException {
	    activities.remove(activity.getId());
	    /* Remove any Attendances/Reservations currently in memory */
	    if (currentMonth != null) {
	    	for (Entry<String, GymDay> entry : currentMonth.entrySet()) {
	    		boolean wasRemoved = false;
	    		ArrayList<Reservation> toRemove = entry.getValue().getReservations(activity);
	    		for (Reservation r : toRemove) {
	    			entry.getValue().removeReservation(r);
	    			wasRemoved = true;
	    		}
	    		ArrayList<Attendance> toRemove2 = entry.getValue().getAttendees(activity);
	    		for (Attendance a : toRemove2) {
	    			entry.getValue().removeReservation(a);
	    			wasRemoved = true;
	    		}
	    		if (wasRemoved) {
	    			updateGymDayFile(entry.getValue());
	    		}
	    	}
	    }
	    /* */
	    /* Update WeekPlan */
	    for (int i=1; i <= 7; i++) {
	    	weekPlan.removeActivity(i, activity);
	    }
	    updateWeekPlanFile();
	    /* */
	    updateActivitiesFile();
	}
  	
  	private void removeGymDay(GymDay gymDay) throws FileNotFoundException {
	    currentMonth.remove(dateFormat.format(gymDay.getDate()));
	    File f = new File(dirName + CONST.CALENDAR_DIR + dateFormat.format(gymDay.getDate()));
	    f.delete();
	}
	
	/**
	 * Removes a Customer from the system
	 * @param customer
	 * @throws FileNotFoundException
	 */
	private void removeCustomer(Customer customer) throws FileNotFoundException {
	    customers.remove(customer.getId());
	    assignCard(null, customer.getCard());
	    File f = new File(dirName + CONST.CUSTOMER_DIR + customer.getId());
	    f.delete();
	    updateCustomerList();
	}
	
	/**
	 * Removes a card from the system
	 * @param card
	 * @throws FileNotFoundException
	 */
	private void removeCard(Card card) throws FileNotFoundException {
	    cards.remove(card.getNumber());
	    File f = new File(dirName + CONST.CUSTOMER_DIR + card.getNumber());
        f.delete();
        assignCard(card.getCustomer(), null);
	    updateFiles();
	}
	
	/**
	 * Updates a single Customer file
	 * @param customer customer to update the file for
	 * @throws FileNotFoundException
	 */
	private void updateSingleCustomer(Customer customer) throws FileNotFoundException {
	    FileSaver fs;
        HashMap<String, String> contents = new HashMap<String, String>();
        int cardNum;
        /* id */
        contents.put(CONST.CUSTOMER_ID, String.valueOf(customer.getId())); 
        
        /* card number */
        if (customer.getCard() != null) {
            cardNum = customer.getCard().getNumber();
        } else {
            cardNum = CONST.DEFAULT_CUSTOMER_CARD_NUMBER;
        }
        contents.put(CONST.CUSTOMER_CARD_NUMBER, String.valueOf(cardNum));
        
        /* name + surname + DOB + phone */
        contents.put(CONST.CUSTOMER_DOB, customer.getDOB()); 
        contents.put(CONST.CUSTOMER_NAME, customer.getName()); 
        contents.put(CONST.CUSTOMER_SURNAME, customer.getSurname()); 
        contents.put(CONST.CUSTOMER_PHONE, customer.getPhone()); 
        
        /* entries */
        contents.put(CONST.CUSTOMER_ENTRIES, String.valueOf(customer.getEntries()));
        
        /* open date */
        contents.put(CONST.CUSTOMER_OPEN, "" + openDateFormat.format(customer.getOpenDate()));
        
        /* SAVE */
        fs = new FileSaver(dirName + CONST.CUSTOMER_DIR + customer.getId());
        fs.saveFilteredData(contents);
	}
	
	/**
	 * Updates a single Card file
	 * @param card Card in memory to update the file for
	 * @throws FileNotFoundException
	 */
	private void updateSingleCard(Card card) throws FileNotFoundException {
	    FileSaver fs;
        HashMap<String, String> contents = new HashMap<String, String>();
        int customerId;
	    /* number */
        contents.put(CONST.CARD_NUMBER, String.valueOf(card.getNumber()));
        /* customer id */
        if (card.getCustomer() != null) {
            customerId = card.getCustomer().getId();
        } else {
            customerId = CONST.DEFAULT_CARD_CUSTOMER_ID;
        }
        contents.put(CONST.CARD_CUSTOMER_ID, String.valueOf(customerId));
        
        /* SAVE */
        fs = new FileSaver(dirName + CONST.CARD_DIR + card.getNumber());
        fs.saveFilteredData(contents);
	}
	
	/**
	 * Updates all files relevant to Card objects
	 * @throws FileNotFoundException
	 */
	private void updateCardFiles() throws FileNotFoundException {
	    updateCardList();
	    for (Card card : cards.values()) {
            updateSingleObject(card, true);
        }
	    
	}
	
	/**
	 * Updates CS_List file
	 * @throws FileNotFoundException
	 */
	private void updateCardList() throws FileNotFoundException {
	    FileSaver fs_list = new FileSaver(dirName + CONST.CARD_LIST_PATH);
        String idList = "";
        for (Card card : cards.values()) {
            idList += card.getNumber() + "\n";
        }
        /* Save list */
        fs_list.saveRawString(idList);
	}
	
	/**
	 * Updates just the customer list file
	 * @throws FileNotFoundException
	 */
	private void updateCustomerList() throws FileNotFoundException {
	    FileSaver fs_list = new FileSaver(dirName + CONST.CUSTOMER_LIST_PATH);
	    String idList = "";
	    for (Customer customer : customers.values()) {
	        idList += customer.getId() + "\n";
	    }
	    /* Save list */
        fs_list.saveRawString(idList);
	}
	
	
	/**
	 * Updates all Customer files
	 * @throws FileNotFoundException
	 */
	private void updateCustomerFiles() throws FileNotFoundException {
	    updateCustomerList();
		for (Customer customer : customers.values()) {
			updateSingleObject(customer, true);
		}
	}
	
	/**
	 * Updates ACT_List file
	 * @throws FileNotFoundException
	 */
	private void updateActivitiesFile() throws FileNotFoundException {
		FileSaver fs = new FileSaver(dirName + CONST.ACT_LIST_PATH);
		HashMap<Integer, String> contents_act = new HashMap<Integer, String>();
		for (Entry<Integer, Activity> entry : activities.entrySet()) {
			contents_act.put(entry.getKey(), entry.getValue().getName());
		}
		fs.saveAct(contents_act);
	}
	
	/**
	 * Gets a HashMap with lists of activities and creates based on it
	 * HashMap with lists of integers (activity IDs).
	 * Uses FileSaver to save the generated contents in a file
	 * @throws FileNotFoundException
	 */
	private void updateWeekPlanFile() throws FileNotFoundException {
		FileSaver fs = new FileSaver(dirName + CONST.WEEKPLAN_PATH);
		HashMap<Integer, ArrayList<Activity>> activities = weekPlan.getActivities();
		HashMap<Integer, ArrayList<Integer>> contents = new HashMap<Integer, ArrayList<Integer>>();
		for (Entry<Integer, ArrayList<Activity>> entry : activities.entrySet()) {
			if (contents.get(entry.getKey()) == null) {
				contents.put(entry.getKey(), new ArrayList<Integer>());
			}
			for (Activity act : entry.getValue()) {
				contents.get(entry.getKey()).add(act.getId());
			}
		}
		fs.saveWeekPlan(contents);
	}
	
	/**
	 * Updates a file relevant to given GymDay's date
	 * @param day
	 * @throws FileNotFoundException
	 */
	private void updateGymDayFile(GymDay day) throws FileNotFoundException {
		/* First translate all reservations
		 * and attendances to FileSaver-friendly format
		 */
		/* Prep maps */
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> data = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		data.put(CONST.INDEX_ATTENDANTS, new HashMap<Integer, ArrayList<Integer>>());
		data.put(CONST.INDEX_RESERVATIONS, new HashMap<Integer, ArrayList<Integer>>());
		/* Prep attendants */
		ArrayList<Attendance> attendances = day.getAttendees();
		for (Attendance a : attendances) { //for every attendance
			if (data.get(CONST.INDEX_ATTENDANTS).get(a.getActivity().getId()) == null) { //check if no custID AList is present yet for this actID
				data.get(CONST.INDEX_ATTENDANTS).put(a.getActivity().getId(), new ArrayList<Integer>()); //no AList, create one		
			}
			data.get(CONST.INDEX_ATTENDANTS).get(a.getActivity().getId()).add(a.getCustomer().getId()); //add a customerID to it
		}
		/* Prep reservations */
		ArrayList<Reservation> reservations = day.getReservations();
		for (Reservation r : reservations) { //for every attendance
			if (data.get(CONST.INDEX_RESERVATIONS).get(r.getActivity().getId()) == null) { //check if no custID AList is present yet for this actID
				data.get(CONST.INDEX_RESERVATIONS).put(r.getActivity().getId(), new ArrayList<Integer>()); //no AList, create one		
			}
			data.get(CONST.INDEX_RESERVATIONS).get(r.getActivity().getId()).add(r.getCustomer().getId()); //add a customerID to it
		}
		/* Save all the data */
		FileSaver fs = new FileSaver(dirName + CONST.CALENDAR_DIR + dateFormat.format(day.getDate()));
		fs.saveGymDay(data);
	}
	
	public Card getCard(int cardNum) {
	    return cards.get(cardNum);
	}
	
	
	public Customer getCustomerByCardId(int cardNum) {
	    for (Customer customer : customers.values()) {
	        if (customer.getCard().getNumber() == cardNum)
	            return customer;
	    }
	    return null;
	}
	
	public Customer getCustomer(int id) {
	    return customers.get(id);
	}
	
	public HashMap<Integer, Activity> getActivities() {
		return activities;
	}

    public HashMap<Integer, Customer> getCustomers() {
        return customers;
    }

    public HashMap<Integer, Card> getCards() {
        return cards;
    }

    public String getDirName() {
        return dirName;
    }

    public static SimpleDateFormat getDateFormat() {
        return dateFormat;
    }
    
    public static SimpleDateFormat getOpenDateFormat() {
        return openDateFormat;
    }
    
    public static SimpleDateFormat getFullDateFormat() {
        return fullDateFormat;
    }

	public WeekPlan getWeekPlan() {
		return weekPlan;
	}
    
    
	
	
	
	
}
