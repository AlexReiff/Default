package GamePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * HeaderPanel is a class that displays relevant info to the
 * game of light racer currently running. It contains displays
 * for the number of wins of each player as well as a timer 
 * display that shows status messages when the timer is not active.
 * 
 * @author Alex Reiff
 *
 */
public class HeaderPanel extends JPanel {
	private JLabel p1;
	private JLabel p2;
	private int p1Wins;
	private int p2Wins;
	private JLabel timerDisplay;
	private double time = 0.0;
	
    public HeaderPanel(int p1RoundWins,int p2RoundWins) {
    	p1Wins = p1RoundWins;
    	p2Wins = p2RoundWins;
        setLayout(new GridLayout(1,3));
        init();
    }
    
    /**
     * Initializes the components of the display to their default
     * values: 0 wins for each player, 0 time elapsed, and shows
     * a welcome message instead of the timer.
     */
    private void init(){
    	p1 = new JLabel(Integer.toString(p1Wins));
    	p1.setFont(new Font("Times", Font.BOLD, 16));
    	p1.setForeground(Color.RED);
    	p1.setHorizontalAlignment(SwingConstants.CENTER);
    	add(p1);
    
    	timerDisplay = new JLabel("Welcome to Lightracer");
    	timerDisplay.setFont(new Font("Times", Font.BOLD, 16));
    	timerDisplay.setPreferredSize(new Dimension(200,10));
    	timerDisplay.setHorizontalAlignment(SwingConstants.CENTER);
    	add(timerDisplay);

        p2 = new JLabel(Integer.toString(p2Wins));
    	p2.setFont(new Font("Times", Font.BOLD, 16));
    	p2.setHorizontalAlignment(SwingConstants.CENTER);
    	p2.setForeground(Color.BLUE);
    	add(p2);
    }
    
    /**
     * Updates the timer display with the time elapsed of the
     * current game.
     * 
     * @param interval    the time elapsed since the last time the
     *                    timer was updated
     */
    public void updateTimer(double interval){
    	time += interval;
    	time = Math.floor(time * 100) / 100;
    	timerDisplay.setText(Double.toString(time));
    }
    
    /**
     *  Updates the display when player 1 wins a round.
     * 
     * @param p1win   the total number of rounds player 1 has won
     *                in the current game.
     */
    public void p1Wins(int p1win){
    	if (p1win <= 2) { 
    		p1.setText(Integer.toString(p1win));
    		timerDisplay.setText("Player 1 (" + GameBoard.p1id  +") Wins!");
    	}
    }
    
    /**
     * Updates the display when player 2 wins a round.
     * 
     * @param p2win   the total number of rounds player 2 has won
     *                in the current game.
     */
    public void p2Wins(int p2win){
    	if (p2win <= 2) {
    		p2.setText(Integer.toString(p2win));	
    		timerDisplay.setText("Player 2 (" + GameBoard.p2id + ") Wins!");
    	}
    }
    
    /**
     * Updates the display to show the default values for each field:
     * 0 wins for each player, 0 time elapsed, and a welcome message
     */
    public void reset() {
    	p1.setText("0");
    	p2.setText("0");
    	time = 0.0;
    	timerDisplay.setText("Welcome to Lightracer");
    }
}