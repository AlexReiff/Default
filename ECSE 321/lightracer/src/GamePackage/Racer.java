package GamePackage;

/**
 * The Racer class represents the two objects that are controlled by the
 * players on the game board. It also sends light trail objects to the
 * game board when the racers move. 
 * 
 * @author Aidan Petit
 *
 */
public class Racer {
	private int racerID;
	private int xPosition;
	private int yPosition; 
	private boolean hasCrashed = false;
	private int direction; 

	public Racer(int racerID, int xPosition, int yPosition, int direction) {
		this.racerID = racerID;
		this.xPosition = xPosition;
		this.yPosition = yPosition;
		this.direction = direction;
	}	
	
	/**
	 * Creates a light trail object at the racer's current position.
	 */
	public void addLightTrail() {
	    GameBoard.addObstacle(xPosition, yPosition, racerID);
	}
	
	/**
	 * Returns whether the racer has collided with another object or not.
	 * 
	 * @return   <code>true</code> The racer has collided with another object <p>
	 *           <code>false</code> The racer has not collided with another object
	 */
	public boolean hasCrashed() {
		return hasCrashed;
	}
	
	/**
	 * Tells the racer that it has collided with another object
	 * 
	 * @param hasCrashed   Legacy implementation. Value does not matter.
	 */
	public void setHasCrashed(boolean hasCrashed) {
		this.hasCrashed = true;
	}
	
	/**
	 * Returns if this racer is player 1 or player 2
	 * 
	 * @return   <code>1</code> This is player 1's racer <p>
	 *           <code>2</code> This is player 2's racer
	 */
	public int getPlayer() {
		return this.racerID;
	}
	
	/**
	 * Returns the x-coordinate of the racer on the game board.
	 * 
	 * @return   The current x-position of the racer
	 */
	public int getXPosition() {
		return xPosition;
	}
	/**
	 * Adjusts the x-coordinate of the racer on the game board.
	 * 
	 * @param xPosition   The new x-position of the racer
	 */
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	/**
	 * Returns the y-coordinate of the racer on the game board.
	 * 
	 * @return   The current y-position of the racer
	 */
	public int getYPosition() {
		return yPosition;
	}
	
	/**
	 * Adjusts the y-coordinate of the racer on the game board.
	 * 
	 * @param yPosition   The new y-position of the racer
	 */
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	/**
	 * Returns the current direction that the racer is facing.
	 * 
	 * @return   <code>0</code>   The racer is facing up <p>
	 *           <code>1</code>   The racer is facing right <p>
	 *           <code>2</code>   The racer is facing down <p>
	 *           <code>3</code>   The racer is facing left <p>
	 */
	public int getDirection() {
		return direction;
	}
	
	/**
	 * Updates the racer to face the direction of the parameter.
	 * 
	 * @param direction   The new direction of the racer
	 */
	public void setDirection(int direction) {
		this.direction = direction;
	}
}