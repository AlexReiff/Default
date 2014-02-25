package PlayerPackage;

import java.io.Serializable;

/**
 * The Record class keeps track of the match history between two
 * specific players. It can update the record and return the match
 * history between the two players.
 * 
 * @author Aidan Petit
 */
public class Record implements Serializable {
	private String p1;
	private String p2;
	private int p1Wins;
	private int p2Wins;

	public Record(String player1, String player2) {
		this.p1 = player1;
		this.p2 = player2;
		this.p1Wins = 0;
		this.p2Wins = 0;
	}
	
    public Record(String player1, String player2, int p1Wins, int p2Wins) {
            this.p1 = player1;
            this.p2 = player2;
            this.p1Wins = p1Wins;
            this.p2Wins = p2Wins;
    }
	
	/**
	 * Returns the number of times player 1 has won
	 * 
	 * @return   Number of times player 1 has won
	 */
	public int getP1Wins() {
		return this.p1Wins;
	}
	
	/**
	 * Returns the number of times player 2 has won
	 * 
	 * @return   Number of times player 2 has won
	 */
	public int getP2Wins() {
		return this.p2Wins;
	}
	
	/**
	 * Increment the number of times player 1 has won
	 */
	public void addP1Win() {
		this.p1Wins ++;
	}
	
	/**
	 * Increment the number of times player 2 has won
	 */
	public void addP2Win() {
		this.p2Wins ++;
	}
	
	/**
	 * Return the username of player 1
	 * 
	 * @return   Username of player 1 as a string
	 */
	public String getPlayer1() {
		return this.p1;
	}
	
	/**
	 * Return the username of player 2
	 * 
	 * @return   Username of player 2 as a string
	 */
	public String getPlayer2() {
		return this.p2;
	}
	

    /**
     * Assigns a specific player 1 to this record
     * 
     * @param player1   The user who is player 1
     */
    public void setPlayer1(Player player1) {
    	this.p1 = player1.getUserID();
    }

    /**
     * @param player2   The user who is player 2
     */
    public void setPlayer2(Player player2) {
    	this.p2 = player2.getUserID();
    }
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new StringBuffer("All time record for ")
	 	.append(p1)
	 	.append(" versus ")
	 	.append(p2)
	 	.append("   ")
	 	.append(this.p1Wins)
	 	.append(" - ")
	 	.append(this.p2Wins)
	 	.toString();
	}
}
