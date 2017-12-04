package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import engine.CONST;
import engine.CustomerDB;

/**
 * NEEDS MORE TESTS - confirming updated activities are saved to file properly and so on
 * @author Kamil
 *
 */
public class T_CustomerDB {
	private CustomerDB db;
	
	@Before
	public void setUp() throws Exception {
		db = new CustomerDB(CONST.TESTS_DIR);
	}

	@After
	public void tearDown() throws Exception {
		
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

}
