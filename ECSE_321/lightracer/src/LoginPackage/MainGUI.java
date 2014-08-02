package LoginPackage;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import GamePackage.Map;
import PlayerPackage.RecordDriver;
import PlayerPackage.StatisticsGUI;
import GamePackage.GameBoard;

/**
 * The MainGUI class displays buttons that allows the user to
 * play a game, choose the map to play the game on, view statistics
 * or log out and return to the login screen.
 * 
 * @author Alex Reiff, Aidan Petit, Jungwan Kim, Syed Irtaza Raza
 */
public class MainGUI extends JFrame implements ActionListener {
	private JButton play;
	private JButton stats;
	private JButton pickMap;
	private JButton logOut;
	public LoginGUI owner;
	
	public static RecordDriver recordDriver;

	public MainGUI(LoginGUI l) {
		owner = l;
		recordDriver = new RecordDriver();
		
		setLayout(new GridLayout(2,2));
		
		play = new JButton("Play");
		play.addActionListener(this);
		
		stats = new JButton("View Stats");
		stats.addActionListener(this);
		
		pickMap = new JButton("Pick Map");
		pickMap.addActionListener(this);
		
		logOut = new JButton("Log Out");
		logOut.addActionListener(this);
		
		add(play);
		add(stats);
		add(pickMap);
		add(logOut);
		pack();
		
		setSize(800,600);
	    setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    setVisible(false);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == play) {
			GameBoard.restart();
		    owner.gameBoard.setVisible(true);
		}
		if (e.getSource() == stats) {
			new StatisticsGUI(this);
		}
		if (e.getSource() == pickMap) {
			Map.selectMapfile();
		}
		if (e.getSource() == logOut) {
			setVisible(false);
			owner.setVisible(true);
			owner.logout(3);
			this.dispose();		
		}
	}
}