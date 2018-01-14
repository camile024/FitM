package tests;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.io.FileNotFoundException;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import engine.CONST;
import engine.Language;
import engine.Locale;

public class T_Locale {

	@Before
	public void setUp() throws Exception {
		
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testEN() throws FileNotFoundException {
		Locale.load(Language.EN);
		String test1 = Locale.getString(CONST.TXT_TEST);
		String test2 = Locale.getString(CONST.TXT_TEST2);
		assertEquals("TEST1 value in English", "Test text", test1);
		assertEquals("TEST2 value in English (mind the trailing whitespace)", "Test2 text ", test2);
	}
	
	@Test
	public void testPL() throws FileNotFoundException {
		Locale.load(Language.PL);
		String test1 = Locale.getString(CONST.TXT_TEST);
		String test2 = Locale.getString(CONST.TXT_TEST2);
		assertEquals("TEST1 value in Polish", "Tekst testowy", test1);
		assertEquals("TEST2 value in Polish", "Tekst testowy drugi", test2);
	}
	
	@Test
	public void testClock() throws InterruptedException {
	    Locale.initDate(5);
	    Date date1 = Locale.getCurrentDate();
	    Thread.sleep(1010);
	    Date date2 = Locale.getCurrentDate();
	    System.out.println(date1.toString());
	    System.out.println(date2.toString());
	    assertThat(date1,is(not(date2)));
	}

}
