package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import engine.CONST;
import engine.FileReader;
import engine.FileSaver;

public class T_FileSaver {
	HashMap<Integer, String> idData;
	HashMap<String, String> data;
	
	@Before
	public void setUp() throws Exception {
		idData = new HashMap<Integer, String>();
		data = new HashMap<String, String>();
		/* prepare data */
		data.put("FIELD1", "VAL1");
		data.put("FIELD2", "VAL2");
		
		idData.put(1, "NAME1");
		idData.put(2, "NAME2");
	}

	@After
	public void tearDown() throws Exception {
		/* clear the files */
		FileSaver fs = new FileSaver(CONST.TESTS_FILESAVER1_PATH);
		fs.saveRawString("");
		fs = new FileSaver(CONST.TESTS_FILESAVER2_PATH);
		fs.saveRawString("");
	}

	@Test
	public void test() throws FileNotFoundException {
		/* Save data to files */
		FileSaver fs = new FileSaver(CONST.TESTS_FILESAVER1_PATH);
		FileSaver fs2 = new FileSaver(CONST.TESTS_FILESAVER2_PATH);
		FileReader fr = new FileReader(CONST.TESTS_FILESAVER1_PATH);
		FileReader fr2 = new FileReader(CONST.TESTS_FILESAVER2_PATH);
		fs.saveFilteredData(data);
		fs2.saveAct(idData);
		fr.load();
		fr.filterFielded();
		fr2.load();
		fr2.filterAct();
		assertEquals("Check FIELD1", "VAL1", fr.getField("FIELD1"));
		assertEquals("Check FIELD2", "VAL2", fr.getField("FIELD2"));
		assertEquals("Check 1", "NAME1", fr2.getField(1));
		assertEquals("Check 2", "NAME2", fr2.getField(2));
		assertNull("Check invalid", fr2.getField(0));
		assertNull("Check invalid2", fr.getField("FIELD3"));
		
	}

}
