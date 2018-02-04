package tests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
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
	
	@Test
	public void testSaveWeekPlan() throws FileNotFoundException {
		FileSaver fs = new FileSaver(CONST.TESTS_FILEREADER3_PATH); //same as FileReader's test
		ArrayList<Integer> expectedDay1 = new ArrayList<Integer>();
		expectedDay1.add(2);
		expectedDay1.add(3);
		ArrayList<Integer> expectedDay2 = new ArrayList<Integer>();
		expectedDay2.add(2);
		ArrayList<Integer> expectedDay5 = new ArrayList<Integer>();
		expectedDay5.add(6);
		expectedDay5.add(3);
		
		HashMap<Integer, ArrayList<Integer>> data = new HashMap<Integer, ArrayList<Integer>>();
		data.put(1, expectedDay1);
		data.put(2, expectedDay2);
		data.put(5, expectedDay5);
		fs.saveWeekPlan(data);
		
		
		
		/* Copied from FileReader corresponding test case thus depends on it to succeed */
		FileReader reader = new FileReader(CONST.TESTS_FILEREADER3_PATH);
		reader.load();
		HashMap<Integer, ArrayList<Integer>> result = reader.filterWeekPlan();
		
		assertEquals("day 1 | id: 2,3", expectedDay1, result.get(1));
		
		assertEquals("day 2 | id: 2", expectedDay2, result.get(2));
		
		assertEquals("day 5 | id: 6, 3", expectedDay5, result.get(5));
	}
	
	@Test
	public void testSaveGymDay() throws FileNotFoundException {
		/* prep */
		ArrayList<Integer> expectedRes2 = new ArrayList<Integer>();
		ArrayList<Integer> expectedRes3 = new ArrayList<Integer>();
		ArrayList<Integer> expectedAtt2 = new ArrayList<Integer>();
		ArrayList<Integer> expectedAtt3 = new ArrayList<Integer>();
		ArrayList<Integer> expectedAtt5 = new ArrayList<Integer>();
		expectedRes2.add(2);
		expectedRes2.add(5);
		
		expectedRes3.add(2);
		expectedRes3.add(1);
		
		expectedAtt2.add(3);
		expectedAtt2.add(5);
		
		expectedAtt3.add(2);
		
		expectedAtt5.add(3);
		expectedAtt5.add(6);
		/**/
		
		FileSaver fs = new FileSaver(CONST.TESTS_FILEREADER4_PATH); //same as FileReader's test
		
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> data = new HashMap<Integer, HashMap<Integer, ArrayList<Integer>>>();
		data.put(CONST.INDEX_ATTENDANTS, new HashMap<Integer, ArrayList<Integer>>());
		data.put(CONST.INDEX_RESERVATIONS, new HashMap<Integer, ArrayList<Integer>>());
		data.get(CONST.INDEX_ATTENDANTS).put(2, expectedAtt2);
		data.get(CONST.INDEX_ATTENDANTS).put(3, expectedAtt3);
		data.get(CONST.INDEX_ATTENDANTS).put(5, expectedAtt5);
		data.get(CONST.INDEX_RESERVATIONS).put(2, expectedRes2);
		data.get(CONST.INDEX_RESERVATIONS).put(3, expectedRes3);
		fs.saveGymDay(data);
		
		
		/* Copied from FileReader corresponding test case thus depends on it to succeed */
		FileReader reader = new FileReader(CONST.TESTS_FILEREADER4_PATH);
		reader.load();
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> result = reader.filterGymDay();
		HashMap<Integer, ArrayList<Integer>> resultAtt = result.get(CONST.INDEX_ATTENDANTS);
		HashMap<Integer, ArrayList<Integer>> resultRes = result.get(CONST.INDEX_RESERVATIONS);
		assertEquals("act 2 | res_id: 2, 5", expectedRes2, resultRes.get(2));
		assertEquals("act 3 | res_id: 2, 1", expectedRes3, resultRes.get(3));
		assertEquals("act 2 | att_id: 3, 5", expectedAtt2, resultAtt.get(2));
		assertEquals("act 3 | att_id: 2", expectedAtt3, resultAtt.get(3));
		assertEquals("act 5 | att_id: 3, 6", expectedAtt5, resultAtt.get(5));
	}

}
