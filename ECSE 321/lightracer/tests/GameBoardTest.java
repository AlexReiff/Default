import GamePackage.GameBoard;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameBoardTest {
	private GameBoard test;
	
	@Before
	public void setUp() throws Exception {
		test = new GameBoard();
	}

	@Test
	public void testRestart() {
		test.restart();
		assertTrue(test.P1.getPlayer() == 1);
		assertTrue(test.P2.getPlayer() == 2);

	}
	
	@Test
	public void testObstacleCollision() {
		//initially P1 should not be crashed
		assertFalse(test.P1.hasCrashed());
		
		/*
		 * Adding an obstacle in the way of P1
		 * after updating and checking for collision
		 * detection P1 should be crashed
		 */
		test.addObstacle(0,48,0);
		test.updateBoard();
		test.checkObstacleCollisions();
		
		assertTrue(test.P1.hasCrashed());
	}
	
	@Test
	public void testUpdateBoard() {
		test.updateBoard();		
		
		assertTrue(test.P1.getXPosition() == 0);
		assertTrue(test.P1.getYPosition() == 48);
		
		assertTrue(test.P2.getXPosition() == 74);
		assertTrue(test.P2.getYPosition() == 1);
		
		test.updateBoard();
		
		assertTrue(test.P1.getXPosition() == 0);
		assertTrue(test.P1.getYPosition() == 47);
		
		assertTrue(test.P2.getXPosition() == 74);
		assertTrue(test.P2.getYPosition() == 2);
	}

	@Test
	public void testAddObstacle() {
		test.addObstacle(3,4,1);
		assertTrue(test.obstacles[3][4] != null);
	}

}
