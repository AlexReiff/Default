

import static org.junit.Assert.*;

import java.io.EOFException;

import org.junit.Before;
import org.junit.Test;

import PlayerPackage.Record;
import PlayerPackage.RecordDriver;

public class RecordDriverTest {
	private RecordDriver testDriver;
	private Record testRecord;
	private Record testRecord2;

	@Before
	public void setUp() throws Exception {
		testDriver = new RecordDriver();
		testRecord = new Record("Player1","Player2");
		testRecord2 = new Record("Batman","Test42");
	}

	@Test
	public void testGetAllRecords() {
		assertTrue(testDriver.allRecords.size() > 0);
		assertFalse(testDriver.allRecords.contains(testRecord));
	}

	@Test
	public void testUpdateRecord() {
		testDriver.updateRecord("Player1", "Player2", "Player1");
		testDriver.updateRecord("Batman", "Test42", "Batman");
		
		try {
		assertTrue(testDriver.getPvPRecord("Player1","Player2").getP1Wins() != 0);
	
		assertTrue(testDriver.getPvPRecord("Batman","Test42").getP1Wins() != 0);
	
		}catch(EOFException exc){exc.printStackTrace();}
	
	}
	

	@Test
	public void testcheckPvPRecord() {
		assertTrue(testDriver.checkForRecord("Player1","Player2"));
		assertTrue(testDriver.checkForRecord("Batman","Test42"));

	}

	@Test
	public void testGetLifeTimeWins() {
		assertTrue(testDriver.getLifeTimeWins("Player1") > 0);
		assertFalse(testDriver.getLifeTimeWins("Player2") == 0);
	}

}
