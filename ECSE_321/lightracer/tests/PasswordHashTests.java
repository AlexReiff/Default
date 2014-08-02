import static org.junit.Assert.*;

import org.junit.Test;

import LoginPackage.PasswordHash;


public class PasswordHashTests {
	String password = "123456aA!";
	
	/*
	 * test checks whether hashed password is different from input password
	 */
	@Test
	public void testPassowrdHash() {
		
		assertFalse((PasswordHash.byteArrayToHexString(PasswordHash.computeHash(password))).equals("123456aA!"));
		
		
	}

}
