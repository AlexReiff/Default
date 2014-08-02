package PlayerPackage;

/**
 * The Player class stores the username of a player.
 * 
 * @author Aidan Petit
 */
public class Player {
        private String userID;
            
        public Player(String userID, String password) {
                this.userID = userID;
        }
        
        /**
         * Returns the user name of this player
         * 
         * @return   Username of this player
         */
        public String getUserID() {
                return userID;
        }
}