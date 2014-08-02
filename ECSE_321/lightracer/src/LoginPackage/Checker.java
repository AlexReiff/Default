package LoginPackage;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.bytecode.opencsv.CSVReader;

/**
 * The Checker class contains methods for making sure that passwords follow
 * the security guidelines of the requirement specification and that new usernames
 * are distinct from already existing usernames.
 * 
 * @author Jungwan Kim
 */
public class Checker {
	
	public static String errorMsg;

	/**
	 * Checks that the input password matches all of the criteria specified
	 * in the requirement specification.
	 * 
	 * @param pass   The password to be checked.
	 * @return   <code>true</code> The password is valid<p>
	 *           <code>false</code>   The password is invalid
	 */
	public static boolean checkPassword(String pass) {	
		errorMsg = "<html><div style=\"text-align: center;\">";
		boolean isLongEnough = false;
    	boolean containsDigit = false;
    	boolean containsLower = false;
    	boolean containsUpper = false;
    	boolean containsSpecial = false;
    	Pattern nonalphanumericPattern = Pattern.compile(".*\\W+.*");
    	Matcher patternMatcher = null;
    	
    	if (pass.length() >= 8) {
    		isLongEnough = true;
    	}
    	for (char character: pass.toCharArray()) {
    		if(Character.isDigit(character)) {
    			containsDigit = true;
    		}
    		if(Character.isLowerCase(character)) {
    			containsLower = true;
    			}
    		if(Character.isUpperCase(character)) {
    			containsUpper = true;
    		}
    		patternMatcher = nonalphanumericPattern.matcher(Character.toString(character));
    		if(patternMatcher.find()) {
    			containsSpecial = true;
    		}
       }
    	
    	if (isLongEnough && containsDigit && containsLower && containsUpper && containsSpecial) {
    		errorMsg = errorMsg + "</html>";
    		return true;
    	}
    	
    	//append error message
    	if(!isLongEnough) {
    		errorMsg =  errorMsg + "<br> password is less than 8 characters";
    	}
    	if(!containsDigit) {
    		errorMsg =  errorMsg + "<br> password does not contain a number";
    	}
    	if(!containsLower) {
    		errorMsg = errorMsg + "<br> passsword does not contain a lowercase letter";
    	}
    	if(!containsUpper) {
    		errorMsg = errorMsg + "<br> passsword does not contain an uppercase letter";
    	}
    	if(!containsSpecial) {
    		errorMsg = errorMsg + "<br> passsword does not contain a non-alphanumeric character";
    	}
    	errorMsg = errorMsg + "</html>";
    	return false;	
    }
    
    /**
     * When a new user tries to create a user name, checks to make sure that the user name does
     * not already exist.
     * 
     * @param userName   The user name to check.
     * @return   <code>true</code> This is a new user name<p>
     *           <code>false</code> This user name is already taken
     * @throws IOException   The CSV file does not exist
     */
    public static boolean checkDuplicateID(String userName) throws IOException{
    	errorMsg = "<html><div style=\"text-align: center;\">";
    	boolean iscontainsID = false;
    	int userNamecolumn = 0;
    	CSVReader reader = new CSVReader(new FileReader(new File("").getAbsolutePath() + "/res/data/accountData.csv"), '\t');
		String [] existingUserList;
	    while ((existingUserList = reader.readNext()) != null) {
	    	if (existingUserList[userNamecolumn].equals(userName)) {
	    		iscontainsID = true;
	    	}
	    }
	    errorMsg = errorMsg + userName + "  is already in use. <br> Try another user name </html>";
	    reader.close();
    	return (!iscontainsID);
    }
     
    /**
     * When a user tries to log in, checks that their username has been created and that
     * the password that they entered is the correct password for the username.
     * 
     * @param userName   The username of the user trying to login
     * @param passWord   The corresponding password entry
     * @return <code>true</code> This is a successful login combination<p>
     *         <code>false</code> This is an invalid login combination
     * @throws IOException   The CSV file does not exist
     */
    public static boolean findUser(String userName, String passWord) throws IOException{
    	errorMsg = "<html><div style=\"text-align: center;\">";
    	boolean foundUser = false;
    	boolean isUserInData = false;
    	int userNamecolumn = 0;
    	int passWordcolumn = 1;
    	CSVReader reader = new CSVReader(new FileReader(new File("").getAbsolutePath() + "/res/data/accountData.csv"), '\t');
		String [] existingUserList;
	    while ((existingUserList = reader.readNext()) != null) {
	    	if (existingUserList[userNamecolumn].equals(userName)) {
	    		isUserInData = true;
	    		if (existingUserList[passWordcolumn].equals(passWord)) {
	    			foundUser = true;
	    		}
	    	}
	    }
	    reader.close();
	    if(isUserInData&&!foundUser) {
	    	 errorMsg = errorMsg + "Login Unsuccessful<br>check your password</html>";
	    }
	    if(!isUserInData) {
	    	errorMsg = errorMsg + userName + "  is not found <br> </html>";
	    }
    	return (foundUser);
    }
}