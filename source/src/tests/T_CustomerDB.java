package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import engine.CONST;
import engine.CustomerDB;
import engine.FileReader;
import engine.FileSaver;

/**
 * NEEDS MORE TESTS - confirming updated activities are saved to file properly and so on
 * - need to test initCustomers()
 * - need to test initCards()
 * - need to test customer/card file saving
 * @author Kamil
 *
 */
public class T_CustomerDB {
    
    /* Paths */
    private final String pathAct = CONST.TESTS_DATA_DIR + CONST.ACT_LIST_FILENAME + CONST.DEFAULT_TEST_SUFFIX;
    private final String pathCardList = CONST.TESTS_DIR + CONST.CARD_DIR + CONST.CARD_LIST_FILENAME + CONST.DEFAULT_TEST_SUFFIX;
    private final String pathCustomList = CONST.TESTS_DIR + CONST.CUSTOMER_DIR + CONST.CUSTOMER_LIST_FILENAME + CONST.DEFAULT_TEST_SUFFIX;
    private final String pathCustom2 = CONST.TESTS_DIR + CONST.CUSTOMER_DIR + "2" + CONST.DEFAULT_TEST_SUFFIX;
    private final String pathCard2225 = CONST.TESTS_DIR + CONST.CARD_DIR + "2225" + CONST.DEFAULT_TEST_SUFFIX;
    
	private CustomerDB db;
	
	@Before
	public void setUp() throws Exception {
		db = new CustomerDB(CONST.TESTS_DIR);
	}

	@After
	public void tearDown() throws Exception {
	    
	    
	    /* load defaults */
	    FileReader defaultActivities = new FileReader(pathAct);
	    FileReader defaultCardList = new FileReader(pathCardList);
	    FileReader defaultCustomerList = new FileReader(pathCustomList);
	    FileReader defaultCustomer2 = new FileReader(pathCustom2);
	    FileReader defaultCard2225 = new FileReader(pathCard2225);
	    
	    defaultActivities.load();
	    defaultCardList.load();
	    defaultCustomerList.load();
	    defaultCustomer2.load();
	    defaultCard2225.load();
	    
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
        
        fs = new FileSaver(pathCard2225);
        fs.saveRawString(defaultCard2225.getContents());
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
	    //Activity activity = new Act
	}

}
