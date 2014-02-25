package PlayerPackage;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.io.EOFException;

import LoginPackage.LoginGUI;
import LoginPackage.MainGUI;


/**
 * The StatisticsGUI class displays the head to head record of the two
 * currently logged in users and a list of the ten players with the most
 * wins.
 * 
 * @author Aidan Petit
 */
public class StatisticsGUI extends JPanel implements ActionListener {
	private static String p1ID; 
	private static String p2ID;
	private static String[] topTen = new String[10];
	private JFrame frame;
	private JPanel topTenPanel;
	private JPanel versusPanel;
	private JPanel buttonPanel;
	private JPanel topTenLabelPanel;
	private JButton backToMainMenu;
	private JLabel PvPRecord;
	private JLabel topTenLabel;
	private JList topTenPlayers;
	private GridBagConstraints gbc;	
	
	static MainGUI owner;

	public StatisticsGUI(MainGUI main) {		
		owner = main;
		p1ID = LoginGUI.loginedPlayer1;
		p2ID = LoginGUI.loginedPlayer2;
		initializeComponents();
		setLayout();
	}
	
	private void initializeComponents() {	
		frame = new JFrame("LightRacer Statistics");
		
		topTenLabelPanel = new JPanel();
		topTenPanel = new JPanel();		
		versusPanel = new JPanel();
		buttonPanel = new JPanel();
		gbc = new GridBagConstraints();
		
		backToMainMenu = new JButton("Main Menu");
		backToMainMenu.addActionListener(this);
		
		String record= "";
		if (MainGUI.recordDriver.checkForRecord(p1ID, p2ID) == true) {
			try {
				record = MainGUI.recordDriver.getPvPRecord(p1ID, p2ID).toString();
			} catch(EOFException exc) {exc.printStackTrace();} 			
		}
		else {
			record = "No Lifetime Record";
		}
		
		topTen = MainGUI.recordDriver.getTopTen();

		PvPRecord = new JLabel(record,SwingConstants.CENTER);
		PvPRecord.setForeground(Color.WHITE);
		PvPRecord.setFont(new Font("Serif", Font.BOLD, 28));
			
		topTenLabel = new JLabel("Top Ten Players");
		topTenLabel.setForeground(Color.BLACK);
		topTenLabel.setAlignmentX(SwingConstants.CENTER);
			
		topTenPlayers = new JList(topTen); 
		topTenPlayers.setFixedCellHeight(40);
		topTenPlayers.setFixedCellWidth(400);
		topTenPlayers.setFont(new Font("Serif", Font.ITALIC, 16));
		DefaultListCellRenderer renderer =  (DefaultListCellRenderer)topTenPlayers.getCellRenderer();  
		renderer.setHorizontalAlignment(JLabel.CENTER);
			
		buttonPanel.setOpaque(true);
	}
	
	private void setLayout() {
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setSize(600, 800);
		frame.setResizable(false);
		frame.setLocation(0,0);
		setLayout(new GridBagLayout());
		
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.weighty = 0.5;
		add(versusPanel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.weighty = 0.0;
		add(topTenLabelPanel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 5;
		gbc.weighty = 0.2;
		add(topTenPanel, gbc);
		
		gbc.gridx = 2;
		gbc.gridy = 10;
		gbc.weighty = 0.5;
		add(buttonPanel,gbc);
		
		versusPanel.setLayout(new GridLayout(1,1));
		versusPanel.setBackground(new Color(10,0,0,150));
		versusPanel.setOpaque(true);		
		versusPanel.setAlignmentX(Component.TOP_ALIGNMENT);
		versusPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		versusPanel.setVisible(true);
		versusPanel.add(PvPRecord);

		topTenPanel.setBackground(new Color(102,200,255,100));
		topTenPanel.setAlignmentY(CENTER_ALIGNMENT);
		topTenPanel.setAlignmentX(CENTER_ALIGNMENT);
		topTenPanel.setMinimumSize(new Dimension(600,50));
		topTenPanel.setMaximumSize(new Dimension(600,200));
		topTenPanel.setSize(600,100);
		topTenPanel.setVisible(true);
		topTenLabelPanel.add(topTenLabel);
		
		topTenLabel.setAlignmentY(CENTER_ALIGNMENT);
		topTenLabel.setFont(new Font("Serif", Font.BOLD, 20));
		topTenPanel.add(topTenPlayers);
		
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.setVisible(true);
		buttonPanel.add(backToMainMenu);
		
		PvPRecord.setVisible(true);			
		backToMainMenu.setVisible(true);
		topTenPlayers.setVisible(true);
		
		frame.add(this);		
		frame.setVisible(true);
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
    	if(e.getSource() == backToMainMenu) {
    		frame.dispose();
    	}		  
	}
}

