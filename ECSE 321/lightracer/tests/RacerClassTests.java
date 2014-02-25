

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import GamePackage.Racer;

public class RacerClassTests {
	private Racer test1;
	private Racer test2;
	
	@Before
	public void init(){
		test1 = new Racer(1,10,10,2);
		test2 = new Racer(2,20,40,0);
	}

	@Test
	public void testCrash() {
		assertFalse(test1.hasCrashed());
		test1.setHasCrashed(true);
		test2.setHasCrashed(true);
		assertTrue(test1.hasCrashed());
		assertTrue(test2.hasCrashed());
	}

	@Test
	public void testGetPlayer() {
		int player1 = test1.getPlayer();
		int player2 = test2.getPlayer();
		
		System.out.println(player1);
		System.out.println(player2);

		assertTrue(player1 == 1 || player1 == 2);
		assertTrue(player2 ==1 || player2 == 2);
		
	}

	@Test
	public void testGetandSetDirection() {
		int direction1 = test1.getDirection();
		int direction2 = test2.getDirection();

		assertTrue(direction1 >= 0 && direction1 < 4);
		assertTrue(direction2 >= 0 && direction2 < 4);
	
		test1.setDirection(1);
		test2.setDirection(3);
		
		int newDirection1 = test1.getDirection();
		int newDirection2 = test2.getDirection();

		assertTrue(newDirection1>=0 && newDirection1 <4);
		assertTrue(newDirection2>=0 && newDirection2 <4);	
		
		
		}
}
