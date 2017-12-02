package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import engine.CONST;
import engine.FileReader;

public class T_FileReader {
	FileReader reader;
	
	@Before
	public void setUp() throws Exception {
		reader = null;
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testLoad() throws FileNotFoundException {
		reader = new FileReader(CONST.TESTS_FILEREADER1_PATH);
		reader.load();
		assertEquals("Contents should be equal", "FIELD1=VALUE1\nFIELD2=VALUE 2\nFIELD3=VALUE 3 3\nFIELD4=VALUE !@#$%^ \\x /r 4;\n\n\n", reader.getContents());

	}
	
	@Test
	public void testFilter() throws FileNotFoundException {
		reader = new FileReader(CONST.TESTS_FILEREADER1_PATH);
		reader.load();
		reader.filterFielded();
		assertEquals("FIELD1 should be VALUE1", "VALUE1", reader.getField("FIELD1"));
		assertEquals("FIELD2 should be VALUE 2", "VALUE 2", reader.getField("FIELD2"));
		assertEquals("FIELD3 should be VALUE 3 3", "VALUE 3 3", reader.getField("FIELD3"));
		assertEquals("FIELD4 should be VALUE 3 3", "VALUE !@#$%^ \\x /r 4;", reader.getField("FIELD4"));
	}
	
	@Test
	public void testFilterAct() throws FileNotFoundException {
		reader = new FileReader(CONST.TESTS_FILEREADER2_PATH);
		reader.load();
		reader.filterAct();
		assertEquals("0: Zumba 16:00", "Zumba 16:00", reader.getField(0));
		assertEquals("1: Taniec Towarzyski 17:00", "Taniec Towarzyski 17:00", reader.getField(1));
		assertEquals("2: Null", null, reader.getField(2));
		assertEquals("5: Tango 20:00", "Tango 20:00", reader.getField(5));
	}

}
