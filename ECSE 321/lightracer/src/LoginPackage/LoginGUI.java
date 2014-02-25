package LoginPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import au.com.bytecode.opencsv.*;
import javax.swing.*;
import GamePackage.GameBoard;

/**
 * The LoginGUI class displays 
 * 
 * 
 * @author  Jungwan Kim, Syed Irtaza Raza
 */

/**
 * The LoginGUI class hold the GUI and logic for logging in players
 * and has access to the RegistrationGUI. When both players are successfully
 * logged in, it immediately goes to the MainGUI.
 * 
 * @author Jungwan Kim, Syed Irtaza Raza
 */
public class LoginGUI extends JFrame implements ActionListener {
	private JPanel loginPanel;
	private JPanel errorMssgPanel;
	private GridBagConstraints gbc;
	private JTextField playerID1text, playerID2text;
	private JPasswordField playerPW1text, playerPW2text;
	private JLabel title, playerID1, playerID2, playerPW1, playerPW2, mssg;
	private JButton loginPlayer1, loginPlayer2, logoutPlayer1, logoutPlayer2, registration;
	
	public GameBoard gameBoard;
	public static String loginedPlayer1, loginedPlayer2;
      
	public LoginGUI() {
	    initializeComponents();
	    setLayout();
	    gameBoard = new GameBoard();
		gameBoard.playThreeRoundMatch();
	}
	
	public static void main(String[] args) {
		new LoginGUI();
	}    

	private void initializeComponents() {
		this.setTitle("Light Racer");
    	gbc = new GridBagConstraints();	
    	loginPanel = new JPanel();
    	errorMssgPanel = new JPanel();      

    	playerID1text = new JTextField(10);
    	playerID2text = new JTextField(10);
    	
    	playerPW1text = new JPasswordField(10);
    	playerPW2text = new JPasswordField(10);  
    	
    	title = new JLabel("Light Racer");
    	title.setOpaque(false);
    	title.setForeground(Color.WHITE);
    	title.setFont(new Font("Serif", Font.BOLD, 40));
    	
    	playerID1 = new JLabel("player1 username:  ");
    	playerID2 = new JLabel("   player2 username:  ");
    	
    	playerPW1 = new JLabel("player1 password:  ");
    	playerPW2 = new JLabel("   player2 password:  ");	
    	
    	mssg = new JLabel("");
    	mssg.setOpaque(true);
    	mssg.setForeground(Color.RED);
        
    	loginPlayer1 = new JButton("loginP1");
        loginPlayer1.addActionListener(this);
        
        loginPlayer2 = new JButton("loginP2");
        loginPlayer2.addActionListener(this);
        
        logoutPlayer1 = new JButton("logoutP1");
        logoutPlayer1.addActionListener(this);
        logoutPlayer1.setEnabled(false);
        
        logoutPlayer2 = new JButton("logoutP2");
        logoutPlayer2.addActionListener(this);
        logoutPlayer2.setEnabled(false);
        
        registration = new JButton("registration");
        registration.addActionListener(this);
	}
	
	private void setLayout() { 
		setSize(800, 600);
	    setResizable(false);
	    setLocationRelativeTo(null);	
	    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    
	    setLayout(new BorderLayout());
	    setContentPane(new JLabel(new ImageIcon(new File("").getAbsolutePath() + "/res/image/test.png")));
	    
	    loginPanel.setLayout(new GridBagLayout());
	    errorMssgPanel.setLayout(new FlowLayout());
	    
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 4;
	    gbc.insets = new Insets(10,10,30,10);
	    loginPanel.add(title, gbc);
	    
	    gbc.insets = new Insets(0,0,0,0);
	    gbc.gridwidth = 1;
        gbc.gridx=0;
        gbc.gridy=1;
        loginPanel.add(playerID1, gbc);  
        
        gbc.gridx=1;
        gbc.gridy=1;
        loginPanel.add(playerID1text, gbc);
                   
        gbc.gridx=2;
        gbc.gridy=1;
        loginPanel.add(playerID2,gbc);
    
        gbc.gridx=3;
        gbc.gridy=1;
        loginPanel.add(playerID2text,gbc);
        
        gbc.gridx=0;
        gbc.gridy=2;
        loginPanel.add(playerPW1,gbc);
          
        gbc.gridx=1;
        gbc.gridy=2;
        loginPanel.add(playerPW1text, gbc);
          
        gbc.gridx=2;
        gbc.gridy=2;
        loginPanel.add(playerPW2, gbc);
          
        gbc.gridx=3;
        gbc.gridy=2;
        loginPanel.add(playerPW2text,gbc);
        
        gbc.gridx=0;
        gbc.gridy=3;
        loginPanel.add(loginPlayer1, gbc);
        
        gbc.gridx=1;
        gbc.gridy=3;
        loginPanel.add(logoutPlayer1, gbc);
        
        gbc.gridx=2;
        gbc.gridy=3;
        loginPanel.add(loginPlayer2, gbc);
        
        gbc.gridx=3;
        gbc.gridy=3;
        loginPanel.add(logoutPlayer2, gbc);
     	
        gbc.insets = new Insets(10,0,0,0);
        gbc.gridx=3;
        gbc.gridy=4;
        loginPanel.add(registration,gbc);
     
     	errorMssgPanel.add(mssg);
     	
     	loginPanel.setBackground(new Color (0,0,0,0));
     	errorMssgPanel.setBackground(new Color (0,0,0,0));
     
     	setSize(799,599);
     	setSize(800,600);
     
     	loginPanel.setVisible(true); 
     	errorMssgPanel.setVisible(true);
     	
     	setLayout(new GridLayout(2,1));
     	add(loginPanel);
     	add(errorMssgPanel);
     	
     	setVisible(true);  	
      }

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	    	if (e.getSource() == registration) {
	    		new RegistrationGUI();
	    	}
	    	if (e.getSource() == loginPlayer1) {
	    		String player1id = playerID1text.getText();
	    		String player1pw = new String(playerPW1text.getPassword());
	    		try {
					if (Checker.findUser(player1id, PasswordHash.byteArrayToHexString(PasswordHash.computeHash(player1pw)))) {
						if(!loginPlayer2.isEnabled()) {
							if(player1id.equals(playerID2text.getText())) { 
								mssg.setText( player1id + " is already logged in");
							}
							else {
								login(1, player1id);
								isPlayable();
							}
						}
						else {
							login(1, player1id);
						}
					}
					else { 
						String errormessg = Checker.errorMsg;
						mssg.setText(errormessg);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	    	}
	    	if (e.getSource() == loginPlayer2) {
	    		String player2id = playerID2text.getText();
	    		String player2pw = new String(playerPW2text.getPassword());
	    		try {
					if (Checker.findUser(player2id,PasswordHash.byteArrayToHexString(PasswordHash.computeHash(player2pw)))) {
						if(!loginPlayer1.isEnabled()) {
							if(player2id.equals(playerID1text.getText())) { 
								mssg.setText( player2id + " is already logged in");
							}
							else {	
								login(2, player2id);
								isPlayable();
							}
						}
						else{
							login(2, player2id);
						}	
					}
					else { 
						String errormessg = Checker.errorMsg;
						mssg.setText(errormessg);
						}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
	    	}
	    	if (e.getSource() == logoutPlayer1) {
	    		logout(1);
	    	}
	    	if (e.getSource() == logoutPlayer2) {
	    		logout(2);
	    	}
	    }
	  
	 private void isPlayable() { 
		 if(!loginPlayer1.isEnabled() && !loginPlayer2.isEnabled()) {
			 new MainGUI(this).setVisible(true);
			 this.setVisible(false);
		 }
	 }
	 
	 private void login(int player, String id) {
		 if (player == 1) {
			 loginedPlayer1 = id;
			 playerID1text.setEnabled(false);
			 playerPW1text.setEnabled(false);
			 loginPlayer1.setEnabled(false);
			 logoutPlayer1.setEnabled(true);
			
			 mssg.setText("");
		 }
		 if (player == 2) {
			 loginedPlayer2 = id;
			 playerID2text.setEnabled(false);
			 playerPW2text.setEnabled(false);
			 loginPlayer2.setEnabled(false);
			 logoutPlayer2.setEnabled(true);
			
			 mssg.setText("");
		 }
	 }
	 
	/**
	 * Logs out the player specified by the input parameter, with
	 * an input of <code>3</code> corresponding to logging both
	 * players out
	 * 
	 * @param player   the player to be logged out
	 */
	public void logout(int player) {
		 if (player == 1) {
			 loginedPlayer1 = "";
			 playerID1text.setEnabled(true);
			 playerPW1text.setEnabled(true);
			 loginPlayer1.setEnabled(true);
			 logoutPlayer1.setEnabled(false);
			 
			 mssg.setText("");
		 }
		 if (player == 2) {
			 loginedPlayer2 = "";
			 playerID2text.setEnabled(true);
			 playerPW2text.setEnabled(true);
			 loginPlayer2.setEnabled(true);
			 logoutPlayer2.setEnabled(false);
			 
			 mssg.setText("");
		 }
		 //log out both players
		 if (player == 3) {
			 loginedPlayer1 = "";
			 loginedPlayer2 = "";
			 playerID1text.setEnabled(true);
			 playerID1text.setText("");
			 playerPW1text.setEnabled(true);
			 playerPW1text.setText("");
			 loginPlayer1.setEnabled(true);
			 logoutPlayer1.setEnabled(false);
			 
			 playerID2text.setEnabled(true);
			 playerID2text.setText("");
			 playerPW2text.setEnabled(true);
			 playerPW2text.setText("");
			 loginPlayer2.setEnabled(true);
			 logoutPlayer2.setEnabled(false);
			 
			 mssg.setText("");
		 }
	 }
}