package PlayerPackage;

/**
 * The CareerRecord class stores the lifetime wins and losses of a given user.
 * 
 * @author Alex Reiff, Aidan Petit
 */
public class CareerRecord {
	private String playerID;
	private int careerWins;
	private int careerLosses;
	
	public CareerRecord(String playerID, int careerWins, int careerLosses) {
		this.playerID = playerID;
		this.careerWins = careerWins;
		this.careerLosses = careerLosses;
	}

	/**
	 * Returns the number of games this player has ever won.
	 * 
	 * @return   The number of games this player has ever won
	 */
	public int getCareerWins() {
		return careerWins;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
	 	   return new StringBuffer("Lifetime record for ")
	 	   .append(this.playerID)
	 	   .append(": ")
	 	   .append(this.careerWins)
	 	   .append(" - ")
	 	   .append(this.careerLosses)
	 	   .toString();
	}
}