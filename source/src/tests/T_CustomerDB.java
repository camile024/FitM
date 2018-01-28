package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.text.ParseException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.objects.Activity;
import data.objects.Card;
import data.objects.Customer;

import engine.CONST;
import engine.CustomerDB;
import engine.FileReader;
import engine.FileSaver;

/**
 * @author Kamil
 *
 */
public class T_CustomerDB {
    
    /* Paths */
    private final String pathAct = CONST.TESTS_DATA_DIR + CONST.ACT_LIST_FILENAME;
    private final String pathCardList = CONST.TESTS_DIR + CONST.CARD_DIR + CONST.CARD_LIST_FILENAME;
    private final String pathCustomList = CONST.TESTS_DIR + CONST.CUSTOMER_DIR + CONST.CUSTOMER_LIST_FILENAME;
    private final String pathCustom2 = CONST.TESTS_DIR + CONST.CUSTOMER_DIR + "2";
    private final String pathCustom5 = CONST.TESTS_DIR + CONST.CUSTOMER_DIR + "5";
    private final String pathCard2225 = CONST.TESTS_DIR + CONST.CARD_DIR + "2225";
    private final String pathCard2228 = CONST.TESTS_DIR + CONST.CARD_DIR + "2228";
    
	private CustomerDB db;
	
	@Before
	public void setUp() throws Exception {
		db = new CustomerDB(CONST.TESTS_DIR);
	}

	@After
	public void tearDown() throws Exception {
	    /* load defaults */
	    FileReader defaultActivities = new FileReader(pathAct + CONST.DEFAULT_TEST_SUFFIX);
	    FileReader defaultCardList = new FileReader(pathCardList + CONST.DEFAULT_TEST_SUFFIX);
	    FileReader defaultCustomerList = new FileReader(pathCustomList + CONST.DEFAULT_TEST_SUFFIX);
	    FileReader defaultCustomer2 = new FileReader(pathCustom2 + CONST.DEFAULT_TEST_SUFFIX);
	    FileReader defaultCustomer5 = new FileReader(pathCustom5 + CONST.DEFAULT_TEST_SUFFIX);
        FileReader defaultCard2225 = new FileReader(pathCard2225 + CONST.DEFAULT_TEST_SUFFIX);
	    FileReader defaultCard2228 = new FileReader(pathCard2228 + CONST.DEFAULT_TEST_SUFFIX);
        
	    defaultActivities.load();
	    defaultCardList.load();
	    defaultCustomerList.load();
	    defaultCustomer2.load();
	    defaultCustomer5.load();
	    defaultCard2225.load();
	    defaultCard2228.load();
	    
	    /* Clear 'em */
        FileSaver fs = new FileSaver(pathAct);
        fs.saveRawString(defaultActivities.getContents());
        
        fs = new FileSaver(CONST.TESTS_FILESAVER2_PATH);
        fs.saveRawString("");
        
        fs = new FileSaver(pathCardList);
        fs.saveRawString(defaultCardList.getContents());
        
        fs = new FileSaver(pathCustomList);
        fs.saveRawString(defaultCustomerList.getContents());
        
        fs = new FileSaver(pathCustom2);
        fs.saveRawString(defaultCustomer2.getContents());
        
        fs = new FileSaver(pathCustom5);
        fs.saveRawString(defaultCustomer5.getContents());
        
        fs = new FileSaver(pathCard2225);
        fs.saveRawString(defaultCard2225.getContents());
        
        fs = new FileSaver(pathCard2228);
        fs.saveRawString(defaultCard2228.getContents());
	}

	@Test
	public void testActivities() throws FileNotFoundException {
		db.initActivities();
		assertEquals("Check first id", 0, db.getActivity(0).getId());
		assertEquals("Check second id", 5, db.getActivity(5).getId());
		assertEquals("Check third id", 3, db.getActivity(3).getId());
		assertEquals("Check first name", "test1", db.getActivity(0).getName());
		assertEquals("Check second name", "test2", db.getActivity(5).getName());
		assertEquals("Check third name", "test3", db.getActivity(3).getName());
	}
	
	@Test
	public void testUpdatingActivities() throws FileNotFoundException {
	    db.initActivities();
	    Activity act = db.getActivity(5);
	    act.setName("changedName");
	    act = db.getActivity(5);
	    assertEquals("Name should've changed.", "changedName", act.getName());
	    db.updateSingleObject(act, false);
	    db.initActivities();
	    act = db.getActivity(5);
	    assertEquals("Name after reloading the files should still be changed.", "changedName", act.getName());
	}
	
	@Test
	public void testCustomers() throws FileNotFoundException, ParseException {
	    db.initActivities();
	    db.initCustomers();
	    Customer cust = db.getCustomer(2);
	    assertEquals("Check name", "Jan", cust.getName());
	    assertEquals("Check surname", "Nowak", cust.getSurname());
	    assertEquals("Check DOB", "21/11/1995", cust.getDOB());
	    assertEquals("Check phone", "123456789", cust.getPhone());
	    assertEquals("Check open date", CustomerDB.getDateFormat().parse("2018-04-22"), cust.getOpenDate());
	    assertEquals("Check entries", 5, cust.getEntries());
	}
	
	@Test
	public void testUpdatingCustomers() throws FileNotFoundException, ParseException {
	    db.initActivities();
        db.initCustomers();
        Customer cust = db.getCustomer(2);
        cust.setSurname("Brzeczyszczykiewicz");
        assertEquals("Name should've changed.", "Brzeczyszczykiewicz", cust.getSurname());
        db.updateSingleObject(cust, false);
        db.initActivities();
        db.initCustomers();
        cust = db.getCustomer(2);
        assertEquals("Name after reloading the files should still be changed.", "Brzeczyszczykiewicz", cust.getSurname());
	}
	
	@Test
	public void testCards() throws FileNotFoundException, ParseException {
	    db.initActivities();
	    db.initCustomers();
	    db.initCards();
	    Card card = db.getCard(2225);
	    Customer cust = db.getCustomer(2);
	    assertEquals("Check number", 2225, card.getNumber());
	    assertEquals("Check customer", cust, card.getCustomer());
	    assertEquals("Check card", card, cust.getCard());
	}
	
	/**
	 * Tests clearing a customer from a relationship,
	 * @throws FileNotFoundException
	 * @throws ParseException
	 */
	@Test
	public void testUpdatingCards1() throws FileNotFoundException, ParseException {
	    db.initActivities();
        db.initCustomers();
        db.initCards();
        Card card = db.getCard(2225);
        Customer cust = db.getCustomer(2);
        assertEquals("Reference from card should be not null", cust, card.getCustomer());
        assertEquals("Reference from customer should be not null", card, cust.getCard());
        db.assignCard(null, card);
        
        db.initActivities();
        db.initCustomers();
        db.initCards();
        card = db.getCard(2225);
        cust = db.getCustomer(2);
        assertEquals("Reference from card should be cleared", null, card.getCustomer());
        assertEquals("Reference from customer should be cleared", null, cust.getCard());
	}
	
	   /**
     * Tests clearing a card from a relationship,
     * @throws FileNotFoundException
     * @throws ParseException
     */
    @Test
    public void testUpdatingCards2() throws FileNotFoundException, ParseException {
        db.initActivities();
        db.initCustomers();
        db.initCards();
        Card card = db.getCard(2225);
        Customer cust = db.getCustomer(2);
        assertEquals("Reference from card should be not null", cust, card.getCustomer());
        assertEquals("Reference from customer should be not null", card, cust.getCard());
        db.assignCard(cust, null);
        
        db.initActivities();
        db.initCustomers();
        db.initCards();
        card = db.getCard(2225);
        cust = db.getCustomer(2);
        assertEquals("Reference from card should be cleared", null, card.getCustomer());
        assertEquals("Reference from customer should be cleared", null, cust.getCard());
    }
    
      /**
      * Tests updating card's owner
      * @throws FileNotFoundException
      * @throws ParseException
      */
     @Test
     public void testUpdatingCards3() throws FileNotFoundException, ParseException {
         db.initActivities();
         db.initCustomers();
         db.initCards();
         Card card = db.getCard(2225);
         Customer cust = db.getCustomer(2);
         Customer cust2 = db.getCustomer(5);
         assertEquals("Reference from card should be not null", cust, card.getCustomer());
         assertEquals("Reference from customer 2 should be not null", card, cust.getCard());
         assertEquals("Reference from customer 5 should be null", null, cust2.getCard());
         db.assignCard(cust2, card);
         
         db.initActivities();
         db.initCustomers();
         db.initCards();
         card = db.getCard(2225);
         cust = db.getCustomer(2);
         cust2 = db.getCustomer(5);
         assertEquals("Reference from card should be new", cust2, card.getCustomer());
         assertEquals("Reference from customer 2 should be cleared", null, cust.getCard());
         assertEquals("Reference from customer 5 should be updated", card, cust2.getCard());
     }
     
     /**
     * Tests updating customer's card
     * @throws FileNotFoundException
     * @throws ParseException
     */
    @Test
    public void testUpdatingCards4() throws FileNotFoundException, ParseException {
        db.initActivities();
        db.initCustomers();
        db.initCards();
        Card card = db.getCard(2225);
        Card card2 = db.getCard(2228);
        Customer cust = db.getCustomer(2);
        assertEquals("Reference from card 2225 should be not null", cust, card.getCustomer());
        assertEquals("Reference from card 2228 should be null", null, card2.getCustomer());
        assertEquals("Reference from customer should be not null", card, cust.getCard());
        db.assignCard(cust, card2);
        
        db.initActivities();
        db.initCustomers();
        db.initCards();
        card = db.getCard(2225);
        card2 = db.getCard(2228);
        cust = db.getCustomer(2);
        assertEquals("Reference from card 2225 should be null", null, card.getCustomer());
        assertEquals("Reference from card 2228 should be updated", cust, card2.getCustomer());
        assertEquals("Reference from customer should be updated", card2, cust.getCard());
    }
    

}
