package tests;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import engine.CONST;
import engine.Locale;
import engine.Settings;

public class T_Settings {

	@Before
	public void setUp() throws Exception {
		Settings.setFileName(CONST.TEST_CFG_PATH);
		Settings.loadSettings();
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void test() {
		assertEquals("Check locale settings", "Tekst testowy drugi", Locale.getString("TEST2"));
		assertTrue("Check proper firstRun", Settings.getFirstRun());
		Settings.markFirstRun();
		assertFalse("Check firstRun marked", Settings.getFirstRun());
	}

}
