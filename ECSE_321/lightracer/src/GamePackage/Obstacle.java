package GamePackage;

/**
 * The Obstacle class is the representation of anything that, when
 * it overlaps with a racer, will cause the round to end. It can either
 * be a default obstacle of the map or a light trail of a racer.
 * 
 * @author Alex Reiff, Syed Irtaza Raza
 */
public class Obstacle {
	private Owner type;
	
	public enum Owner {BOARD, P1, P2};
	
	/**
	 * Converts the code for a given obstacle into that obstacle.
	 * 
	 * @param obstacleCode   the char representation of an obstacle
	 */
	public Obstacle(char obstacleCode) {
		switch (obstacleCode) {
			case '0':
				type = Owner.BOARD;
				break;
			case '1':
				type = Owner.P1;
				break;
			case '2':
				type = Owner.P2;
				break;
		}
	}

	/**
	 * Returns the type of the obstacle, which is a player for
	 * a light trail or the board for a default obstacle.
	 * 
	 * @return the type of the obstacle
	 */
	public Owner getType() {
		return type;
	}
}