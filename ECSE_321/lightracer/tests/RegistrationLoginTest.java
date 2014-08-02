
import static org.junit.Assert.*;
import au.com.bytecode.opencsv.*;

import java.io.*;

import org.junit.Test;

public class RegistrationLoginTest {

	String[] existingUser = {"Test01", "Test02", "Test03"};
	String[] existingUserPW = {"123456!aA", "Dem@Us3R02", "Dem@Us3R03"};
	String[] expectedUser = {"Test01", "Test02", "Test03"};
	String[] expectedUserPW = {"123456!aA", "Dem@Us3R02", "Dem@Us3R03"};
	CSVWriter writer;
	CSVReader reader;
	
	@Test
	public void createAccountTest() {
		try {
			writer = new CSVWriter(new FileWriter(new File("")), '\t');
			for (int i = 0; i < existingUser.length; i++) {
				String[] entries = {existingUser[i], existingUserPW[i]};
				writer.writeNext(entries);
				writer.close();
			}
			
			reader = new CSVReader(new FileReader(new File("")));
			for (int i = 0; i < existingUser.length; i++) {
				String [] existingUserList;
			    while ((existingUserList = reader.readNext()) != null) {
			    	assertEquals(existingUserList[0],expectedUser[i]);
			    }
			}
		} catch(Exception e) {
			
		}		
	}
	
	@Test
	public void loginFindAccountTest() throws IOException {
		try {
			writer = new CSVWriter(new FileWriter(new File("")), '\t');
			reader = new CSVReader(new FileReader(new File("")));
			for (int i = 0; i < existingUser.length; i++) {
				String [] existingUserList;
				try {
					while ((existingUserList = reader.readNext()) != null) {
						assertEquals(existingUserList[0],expectedUser[i]);
						assertEquals(existingUserList[1],expectedUserPW[i]);
					}
				} catch (IOException e) {
	
				}
			}
		}catch (FileNotFoundException e) {
			
		}
	}
}
	
	
	

