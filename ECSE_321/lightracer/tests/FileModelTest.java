

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import PlayerPackage.FileModel;
import PlayerPackage.Record;

public class FileModelTest {
	private FileModel test;
	private ArrayList<Record> testRecords;

	
	@Before
	public void setUp() throws Exception {
		test = new FileModel("testFile");
		testRecords = new ArrayList<Record>();
		testRecords.add(new Record("Player2","Player3"));
		testRecords.add(new Record("Player42","Player1"));
	
	}

	@Test
	public void testReadFile() throws IOException {
		assertTrue(test.readRecordList() != null);
	}

	@Test
	public void testWriteRecordList() throws IOException {
		test.writeRecordList(testRecords);	
		assertFalse(test.readRecordList().isEmpty());
	}

}
