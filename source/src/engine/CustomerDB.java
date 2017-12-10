package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import data.objects.Activity;
import data.objects.Card;
import data.objects.Customer;

/**
 * To use:
 * 	1) Create an instance of CustomerDB with the path to app main directory
 * 	2) Call initActivities() to load activities from file
 *  3) Call initCustomers() to load Customers from data files
 *  4) Call initCards() to load Cards and associate them with Customers
 *  REMOVE/PUT METHODS + FILE UPDATING METHODS NEED REWORKING (to be able to manage 1-card/customer removal and properly
 *  overwrite only the data that's changed for performance purposes)
 * @author Kamil
 *
 */
public class CustomerDB {
	private HashMap<Integer, Activity> activities;
	private HashMap<Integer, Customer> customers;
	private HashMap<Integer, Card> cards;
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
		FileReader fr = new FileReader(dirName + CONST.ACT_LIST_PATH);
		fr.load();
		fr.filterAct();
		HashMap<Integer, String> contents = fr.getFilteredID();
		for (Entry<Integer, String> entry : contents.entrySet()) {
			Activity act = new Activity(entry.getKey(), entry.getValue());
			activities.put(entry.getKey(), act);
		}
	}
	
	/**
	 * Initializes Customers from data files
	 * @throws FileNotFoundException
	 * @throws ParseException		Thrown when date-parsing fails
	 */
	public void initCustomers() throws FileNotFoundException, ParseException {
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
			customer.setOpenDate(DateFormat.getInstance().parse(fr_customer.getField(CONST.CUSTOMER_OPEN)));
			customer.setPhone(fr_customer.getField(CONST.CUSTOMER_PHONE));
			customers.put(nextId, customer);
			
		}
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
	
	public Activity getActivity(int id) {
		return activities.get(id);
	}
	
	
	
	/**
	 * Creates an activity
	 * @param activity
	 * @throws Exception FileNotFoundException if file writing problem occurs,
	 *         Exception if activity already exists
	 */
	public void addActivity(Activity activity) throws Exception {
	    if (activities.get(activity.getId()) != null) {
	        throw new Exception("Activity already exists!");
	    } else {
	        activities.put(activity.getId(), activity);
	    }
		updateFiles();
	}
	
	/**
	 * Removes an Activity from the system
	 * @param activitiy
	 * @throws FileNotFoundException
	 */
	public void removeActivity(Activity activity) throws FileNotFoundException {
	    activities.remove(activity);
	    activity = null;
	    updateActivitiesFile();
	}
	
	/**
	 * Removes a Customer from the system
	 * @param customer
	 * @throws FileNotFoundException
	 */
	public void removeCustomer(Customer customer) throws FileNotFoundException {
	    customers.remove(customer);
	    File f = new File(dirName + CONST.CUSTOMER_DIR + customer.getId());
	    f.delete();
	    customer = null;
	    updateCustomerList();
	}
	
	/**
	 * Removes a card from the system
	 * @param card
	 * @throws FileNotFoundException
	 */
	public void removeCard(Card card) throws FileNotFoundException {
	    cards.remove(card);
	    File f = new File(dirName + CONST.CUSTOMER_DIR + card.getNumber());
        f.delete();
	    card = null;
	    updateFiles();
	}
	
	/**
	 * Updates all the data-related files
	 * @throws FileNotFoundException 
	 */
	private void updateFiles() throws FileNotFoundException {
		updateActivitiesFile();
		updateCustomerFiles();
		updateCardFiles();
	}
	
	
	private void updateCardFiles() throws FileNotFoundException {
		FileSaver fs_list = new FileSaver(dirName + CONST.CARD_LIST_PATH);
		FileSaver fs;
		String numList = "";
		HashMap<String, String> contents = new HashMap<String, String>();
		int customerId;
		
		/* Fill with fields */
		for (Card card : cards.values()) {
			/* number */
			contents.put(CONST.CARD_NUMBER, String.valueOf(card.getNumber()));
			numList += card.getNumber() + '\n';
			
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
		fs_list.saveRawString(numList);
	}
	
	/**
	 * Updates just the customer list file
	 * @throws FileNotFoundException
	 */
	private void updateCustomerList() throws FileNotFoundException {
	    FileSaver fs_list = new FileSaver(dirName + CONST.CUSTOMER_LIST_PATH);
	    String idList = "";
	    for (Customer customer : customers.values()) {
	        idList += customer.getId() + '\n';
	    }
	    /* Save list */
        fs_list.saveRawString(idList);
	}
	
	
	/**
	 * 
	 * @throws FileNotFoundException
	 */
	private void updateCustomerFiles() throws FileNotFoundException {
		FileSaver fs_list = new FileSaver(dirName + CONST.CUSTOMER_LIST_PATH);
		String idList = "";
		FileSaver fs;
		HashMap<String, String> contents = new HashMap<String, String>();
		int cardNum;
		/* Fill with fields */
		for (Customer customer : customers.values()) {
			/* id */
			contents.put(CONST.CUSTOMER_ID, String.valueOf(customer.getId())); 
			idList += customer.getId() + '\n';
			
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
			contents.put(CONST.CUSTOMER_CARD_NUMBER, String.valueOf(customer.getEntries()));
			
			/* open date */
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(customer.getOpenDate());
			contents.put(CONST.CUSTOMER_OPEN, "" + calendar.get(Calendar.YEAR) + '-' + calendar.get(Calendar.MONTH) + '-' + calendar.get(Calendar.DAY_OF_MONTH));
			
			/* SAVE */
			fs = new FileSaver(dirName + CONST.CUSTOMER_DIR + customer.getId());
			fs.saveFilteredData(contents);
		}
		/* Save list */
		fs_list.saveRawString(idList);
	}
	
	/**
	 * 
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
	
	public HashMap<Integer, Activity> getActivities() {
		return activities;
	}
	
	
}
