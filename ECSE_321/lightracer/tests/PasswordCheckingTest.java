import static org.junit.Assert.*;
import LoginPackage.Checker;
import org.junit.Test;


public class PasswordCheckingTest {
	String[] pwText = {"12345!aA", "HelloWor1d!", "ECSE321Fin@l", "g0OdP@ss", "Wor1d!Hello", "Fin@lD3lieval"};	
	String[] failpwText = {"1234567", "1aA!", "!12345aaa", "nonumbeR!!", "1CAPS!!!!", "noupp3r!etter"};
	String[] shortpwText = {"1!aA", "2bB@", "#3Cc" };
	String[] nolowerText = {"1111AAA!", "2BBC!@BBC", "THI#3ESEE" };
	String[] noupperText = {"123whyn@upper", "n0cap!tal"};
	String[] nospecialText = {"HelloWorld1", "ECSE321somuchFun", "hmm0utofIdea"};
	String[] nonumberText = {"HelloWorld!", "Abstr@ctAlgebra"};
	String[] mixedText = {"nonumber+upper", "1"};
	/*
	 * test checker.checkPassword returns correct boolean expression for given password
	 */
	@Test
	public void testPasswordRequirement() {
		for (int i = 0; i < pwText.length; i++) {
			assertEquals(Checker.checkPassword(pwText[i]), true);
			assertEquals(Checker.checkPassword(failpwText[i]), false);
		}
	}
	
	/*
	 * test checker.mssg is correctly updated after it checks password
	 */
	@Test
	public void testdisplayMssgforShorPassword() {
		for (int i = 0; i < shortpwText.length; i++) {
			Checker.checkPassword(shortpwText[i]);
			assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
					+ "<br> password is less than 8 characters</html>");
		}
		for (int i = 0; i < nolowerText.length; i++) {
			Checker.checkPassword(nolowerText[i]);
			assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
					+ "<br> passsword does not contain a lowercase letter</html>");
		}
		for (int i = 0; i < noupperText.length; i++) {
			Checker.checkPassword(noupperText[i]);
			assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
					+ "<br> passsword does not contain an uppercase letter</html>");
		}
		for (int i = 0; i < nospecialText.length; i++) {
			Checker.checkPassword(nospecialText[i]);
			assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
					+ "<br> passsword does not contain a non-alphanumeric character</html>");
		}
		for (int i = 0; i < nonumberText.length; i++) {
			Checker.checkPassword(nonumberText[i]);
			assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
					+ "<br> password does not contain a number</html>");
		}
	}
	
	/*
	 * diplay multiple error mssg for password requirement
	 */
	@Test
	public void testmixederrorMssgforPassword() {
		Checker.checkPassword(mixedText[0]);
		assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
				+ "<br> password does not contain a number"
				+ "<br> passsword does not contain an uppercase letter</html>");
		Checker.checkPassword(mixedText[1]);
		assertEquals(Checker.errorMsg, "<html><div style=\"text-align: center;\">"
				+ "<br> password is less than 8 characters"
				+ "<br> passsword does not contain a lowercase letter"
				+ "<br> passsword does not contain an uppercase letter"
				+ "<br> passsword does not contain a non-alphanumeric character</html>");
	}
	
}
