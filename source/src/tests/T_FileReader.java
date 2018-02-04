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
	
	@Test
	public void testFilterWeekPlan() throws FileNotFoundException {
		reader = new FileReader(CONST.TESTS_FILEREADER3_PATH);
		reader.load();
		HashMap<Integer, ArrayList<Integer>> result = reader.filterWeekPlan();
		ArrayList<Integer> expectedDay1 = new ArrayList<Integer>();
		expectedDay1.add(2);
		expectedDay1.add(3);
		assertEquals("day 1 | id: 2,3", expectedDay1, result.get(1));
		ArrayList<Integer> expectedDay2 = new ArrayList<Integer>();
		expectedDay2.add(2);
		assertEquals("day 2 | id: 2", expectedDay2, result.get(2));
		ArrayList<Integer> expectedDay5 = new ArrayList<Integer>();
		expectedDay5.add(6);
		expectedDay5.add(3);
		assertEquals("day 5 | id: 6, 3", expectedDay5, result.get(5));
	}
	
	@Test
	public void testFilterGymDay() throws FileNotFoundException {
		reader = new FileReader(CONST.TESTS_FILEREADER4_PATH);
		reader.load();
		HashMap<Integer, HashMap<Integer, ArrayList<Integer>>> result = reader.filterGymDay();
		HashMap<Integer, ArrayList<Integer>> resultAtt = result.get(CONST.INDEX_ATTENDANTS);
		HashMap<Integer, ArrayList<Integer>> resultRes = result.get(CONST.INDEX_RESERVATIONS);
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
		
		assertEquals("act 2 | res_id: 2, 5", expectedRes2, resultRes.get(2));
		assertEquals("act 3 | res_id: 2, 1", expectedRes3, resultRes.get(3));
		assertEquals("act 2 | att_id: 3, 5", expectedAtt2, resultAtt.get(2));
		assertEquals("act 3 | att_id: 2", expectedAtt3, resultAtt.get(3));
		assertEquals("act 5 | att_id: 3, 6", expectedAtt5, resultAtt.get(5));
	}

}
