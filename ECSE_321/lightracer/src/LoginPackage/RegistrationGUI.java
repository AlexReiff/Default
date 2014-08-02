package LoginPackage;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.*;

import au.com.bytecode.opencsv.*;

import javax.swing.*;
    
/**
 * The Registration class displays the form necessary for a user to create
 * a new user account and calls the logic that creates the account.
 * 
 * @author  Jungwan Kim, Syed Irtaza Raza
 */

public class RegistrationGUI extends JPanel implements ActionListener {
	private JFrame frame;
	private JPanel errorMsgPanel;
	private JTextField idText;
	private JPasswordField passWordText, passWordCheckText;
	private JLabel title, playerID, playerPassWord, playerPassWordcheck, msg;
	private JButton close, register;
	private GridBagConstraints gbc;
	private CSVWriter writer;
	   
	public RegistrationGUI() {
		initializeComponents();
	   	setLayout();
	}
	   
	private void initializeComponents() {
	   	frame = new JFrame("LightRacer Registration");
	   	
	   	errorMsgPanel = new JPanel();
	   	
	   	gbc = new GridBagConstraints();
	   	
	   	idText = new JTextField(10);
    
	   	passWordText = new JPasswordField(10);
    
	   	passWordCheckText = new JPasswordField(10);
    
	   	title = new JLabel("Registration Page");
	    title.setFont(new Font("Serif", Font.BOLD, 20));
	    title.setForeground(Color.DARK_GRAY);
	   
	    playerID = new JLabel("username:  ");
	   
	    playerPassWord = new JLabel("password:  ");
	   
	    msg = new JLabel("");
	    msg.setForeground(Color.RED);
	   
	    playerPassWordcheck = new JLabel("re-enter password:  ");
	   
	    close = new JButton("close");
	    close.addActionListener(this);
    
	    register = new JButton("register");
	    register.addActionListener(this);
	}
	   
	private void setLayout() {
	  	frame.setLayout(new GridLayout(2,1));
	   	errorMsgPanel.setLayout(new FlowLayout());
	    setLayout(new GridBagLayout());
	       
	    gbc.gridx = 0;
	    gbc.gridy = 0;
	    gbc.gridwidth = 2;
	    gbc.insets = new Insets(10, 30, 30, 10);
	    add(title, gbc);
	      
	    gbc.insets = new Insets(0, 0, 0, 0);
	    gbc.gridx=0;
	    gbc.gridy=1;
	    gbc.gridwidth = 1;
	    add(playerID, gbc);         
	     
	    gbc.gridx=1;
	    gbc.gridy=1;
	    add(idText, gbc);
	                
	    gbc.gridx=0;
	    gbc.gridy=2;
	    add(playerPassWord,gbc);
 
	    gbc.gridx=1;
	    gbc.gridy=2;
	    add(passWordText,gbc);
	       
	    gbc.gridx=0;
	    gbc.gridy=3;
	    add(playerPassWordcheck,gbc);
	       
	    gbc.gridx=1;
	    gbc.gridy=3;
	    add(passWordCheckText, gbc);
	        	           
	    gbc.gridx=0;
	    gbc.gridy=4;
	    add(close,gbc);
	    	 
	    gbc.gridx=1;
	    gbc.gridy=4;
	    add(register, gbc);
	    	 
	    errorMsgPanel.add(msg);
	   	
	    frame.setSize(800, 600);
	    frame.setResizable(false);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLocation(0,0);
	   	 
	    frame.add(this);
	    frame.add(errorMsgPanel);

	    frame.setLocationRelativeTo(null);	  
	    frame.setVisible(true);
	}
	   
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
	  	if(e.getSource() == close) {   		
	   		this.frame.dispose();
	   	}
   	
    	if(e.getSource() == register) {
    		try{
    			String userid = idText.getText();
	    		String password = new String(passWordText.getPassword());
	    		String passwordCheck = new String(passWordCheckText.getPassword());
	    		writer = new CSVWriter(new FileWriter(new File("").getAbsolutePath() + "/res/data/accountData.csv", true), '\t');
	    		if (Checker.checkDuplicateID(userid)) {
    				if (Checker.checkPassword(password)&&password.equals(passwordCheck)) {
    					String[] entries = {userid, PasswordHash.byteArrayToHexString(PasswordHash.computeHash(password))};
	    				writer.writeNext(entries);
	    				writer.close();
	    				this.frame.dispose();
    				}
	    			else { 
			    		if (userid.length() == 0 || password.length() == 0 ||passwordCheck.length() == 0) {
			    			msg.setText("fill the provide fields");
			    		}
			    		else if (!password.equals(passwordCheck)) {
			    			msg.setText("password does not match"); 
			    		}
			    		else {
			    			msg.setText(Checker.errorMsg);
			    		}
			    	 }
	    		}
	    		else { 
	    			msg.setText("id is already in use");	
	    		}
    		}
    		catch (Exception error) {
    			error.printStackTrace();
    		}	
    	}
    }	      
}