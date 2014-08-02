

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import LoginPackage.LoginGUI;
import LoginPackage.MainGUI;
import PlayerPackage.StatisticsGUI;

public class IntegrationTests {
	private LoginGUI testLogin;
	private MainGUI testMain;
	private StatisticsGUI testStats;

	@Before
	public void setUp() throws Exception {
		testLogin = new LoginGUI();
		testMain = new MainGUI(testLogin);
	}

	
	@Test
	public void testMainGUIVisible() {
		assertFalse(testLogin.isVisible());
		assertTrue(testMain.isVisible());
		
		assertTrue(testMain.owner == testLogin);
		
		testStats = new StatisticsGUI(testMain);
		
		assertTrue(testStats.isVisible());
		assertFalse(testMain.isVisible());
	}



}
