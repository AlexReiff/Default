package GamePackage;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.JFrame;

import LoginPackage.LoginGUI;
import LoginPackage.MainGUI;

/**
 * The GameBoard class holds all of the components to play and display light racer.
 * It will play the game until one player has won twice and then it will update
 * player statistics and then it will stop updating. It contains all of the methods
 * that implement the game logic, such as collision detection and updating positions, 
 * as well as the methods that show the logic on the GUI.
 * 
 * @author Alex Reiff, Aidan Petit, Jungwan Kim, Syed Irtaza Raza
 */
public class GameBoard extends JFrame {
	public static Racer P1;
	public static Racer P2;
    private static GamePanel game;
    private static HeaderPanel header;
    private boolean gameOver = false;
    private boolean gamePaused = true;
    private static int p1RoundWins = 0;
    private static int p2RoundWins = 0;

    static String p1id;
    static String p2id;
    static int[][] map;
    public static Obstacle[][] obstacles;

	public GameBoard() {
		initializeMap();
		initializeBoard();
		game = new GamePanel();
		header = new HeaderPanel(p1RoundWins, p2RoundWins);		
		setLayout( new BorderLayout());	
        add(header,BorderLayout.PAGE_START);
        add(game,BorderLayout.CENTER);
        setSize(600,800);
        pack();
        setLocationRelativeTo(null);
	    setVisible(false);
	    updateDisplay(0.0);
	}

    /**
     * Resets all of the game's components to their default settings
     * so that a new game can be played
     */
    public static void restart() {
		initializeMap();
		p1RoundWins = 0;
		p2RoundWins = 0;
		p1id = LoginGUI.loginedPlayer1;
		p2id = LoginGUI.loginedPlayer2;
		P1 = new Racer(1,0,49,2);
		P2 = new Racer(2,74,0,0);
		game.clean(P1,P2);
		game.updateCells();
		header.reset();
	}
    
	/**
	 * Plays light racer until either play has won twice. Neither player's
	 * score will be increased in a tie and a new round will be started. 
	 */
	public void playThreeRoundMatch() {
		while(p1RoundWins < 2 && p2RoundWins < 2) {
			run();
		}
		if (p1RoundWins == 2) {
			//player 1 wins the match
			//display some message and update PvP record/personal records
			MainGUI.recordDriver.updateRecord(p1id, p2id, p1id);
			MainGUI.recordDriver.writeAllRecordsToFile();
		}
		else if (p2RoundWins == 2) {
			//player 2 wins the match, same steps as with p1
			MainGUI.recordDriver.updateRecord(p1id, p2id, p2id);
			MainGUI.recordDriver.writeAllRecordsToFile();
		}
	}
	
	private void run() {
		initializeMap();
		resetBoard();
			while (!gameOver) {
				if(!gamePaused) {
					updateBoard();
				}
				try {
					Thread.sleep(100);
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
	}
	
	/**
	 * Moves both racers and adds light trails in their previous position.
	 * If either racer has crashed, it ends the current round.
	 */
	public void updateBoard() {
		if (checkObstacleCollisions() == false) { //no collision, add light trails, update position of Racers, draw map
			addObstacle(P1.getXPosition(),P1.getYPosition(), '1');
			addObstacle(P2.getXPosition(),P2.getYPosition(), '2');
			updateRacerPosition(P1);
			updateRacerPosition(P2);
			updateDisplay(0.1);
		}
		else {
			announceWinner();
			gamePaused = true;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			playThreeRoundMatch();
		}
	}
	
	/**
	 * Checks if either of the racers has collided with anything
	 * 
	 * @return <code>true</code> One of the racers has crashed <p>
	 *         <code>false</code> Neither racer has collided with anything
	 */
	public boolean checkObstacleCollisions() {
		boolean collision = false;
		for( int x = 0; x< obstacles.length; x++) {
			for (int y =0; y<obstacles[0].length; y++) {			
				if (x == P1.getXPosition() && y == P1.getYPosition()) {
					if (obstacles[x][y] != null) {
						collision = true;
						P1.setHasCrashed(true);
					}	
				}
				if (x == P2.getXPosition() && y == P2.getYPosition()) {
					if(obstacles[x][y] != null) {
						collision = true;
						P2.setHasCrashed(true);
					}
				}
			}
		}
		if (P1.getXPosition()==P2.getXPosition() && P1.getYPosition()==P2.getYPosition()) {
			collision = true;
			P1.setHasCrashed(true);
			P2.setHasCrashed(true);
		}
			return collision;
		}
	
	private void updateRacerPosition(Racer racer) {
		int currDirection = racer.getDirection();
		int currXPos = racer.getXPosition();
		int currYPos = racer.getYPosition();
		if (currDirection == 0 ) {//moving down
			currYPos = currYPos + 1; 
			if (currYPos > map[0].length -1) {
				racer.setHasCrashed(true);
			}
			else {
				racer.setYPosition(currYPos); //
			}
		}
		else if( currDirection == 1) { //moving right
			currXPos = currXPos + 1; 
			if (currXPos > map.length -1) {
				racer.setHasCrashed(true);
			}
			else {
				racer.setXPosition(currXPos);
			}
		}
		else if( currDirection == 2) { //moving up
			currYPos = currYPos - 1; 
			if (currYPos < 0) {
				racer.setHasCrashed(true);
			}
			else {
				racer.setYPosition(currYPos);
			}
		}
		else if( currDirection == 3) { //moving left
			currXPos = currXPos - 1; 
			if (currXPos < 0) {
				racer.setHasCrashed(true);
			}
			else {
				racer.setXPosition(currXPos);
			}
		}
	}
	
    private void updateDisplay(double timeElapsed) {
    	game.updateRacer(P1);
    	game.updateRacer(P2);
    	header.updateTimer(timeElapsed);
    	game.updateCells();  	
    }
	
	private void toggleClock() {
		gamePaused = !gamePaused;
	}
	
	private void announceWinner() {
		if (P1.hasCrashed() == true && P2.hasCrashed() == true) {
			//tie
		}
		else if (P1.hasCrashed() == true) {
			p2RoundWins ++;
			header.p2Wins(p2RoundWins);
		}
		else {
			p1RoundWins ++;
			header.p1Wins(p1RoundWins);
		}
	}
	
	private static void initializeMap() {
		Map boardmap = new Map();
		map = boardmap.getBoardMap();
		obstacles = boardmap.getBoardObstacle();
	}
	
	
	private void resetBoard() {
		P1 = new Racer(1,0,49,2);
		P2 = new Racer(2,74,0,0);
		game.clean(P1,P2);
	}
	
	private void initializeBoard() {
		P1 = new Racer(1,0,49,2);
		P2 = new Racer(2,74,0,0);
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				int key = e.getKeyCode();
				switch(key) {
					case KeyEvent.VK_LEFT:
						if (P2.getDirection()!=1 && !gamePaused)
						P2.setDirection(3);
						break;
					case KeyEvent.VK_UP:
						if (P2.getDirection()!=0 && !gamePaused)
						P2.setDirection(2);
						break;
					case KeyEvent.VK_RIGHT:
						if (P2.getDirection()!=3 && !gamePaused)
						P2.setDirection(1);
						break;
					case KeyEvent.VK_DOWN:
						if (P2.getDirection()!=2 && !gamePaused)
						P2.setDirection(0);
						break;
					case KeyEvent.VK_A:
						if (P1.getDirection()!=1 && !gamePaused)
						P1.setDirection(3);
						break;
					case KeyEvent.VK_W:
						if (P1.getDirection()!=0 && !gamePaused)
						P1.setDirection(2);
						break;
					case KeyEvent.VK_D:
						if (P1.getDirection()!=3 && !gamePaused)
						P1.setDirection(1);
						break;
					case KeyEvent.VK_S:
						if (P1.getDirection()!=2 && !gamePaused)
						P1.setDirection(0);
						break;
					case KeyEvent.VK_SPACE:
						toggleClock();
						break;
				}
			}
			@Override
			public void keyReleased(KeyEvent arg0) {
			}
			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
	}
	
	/**
	 * Adds an obstacle corresponding the given racer to the board
	 * at the given location.
	 * 
	 * @param x   The x-coordinate of the obstacle to be placed.
	 * @param y   The y-coordinate of the obstacle to be placed.
	 * @param racerID   If the racer is player 1 or player 2.
	 */
	public static void addObstacle(int x, int y, int racerID) {
		obstacles[x][y] = new Obstacle(((char) racerID));	
	}
}