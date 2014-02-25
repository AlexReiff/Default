//Alexander Reiff
//260504962

package a4;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import a2.LanguageParser;
import a2.StringSplitter;

public class A2Panel extends JPanel implements ActionListener{	
	JLabel title;
	JLabel name;
	JTextField input;
	JButton calc;
	JLabel result;
	JLabel validity;
	JLabel numStrings;
	
	A2Panel(){
		makeComponents();
		makeLayout();
	}
	
	private void makeComponents(){
		//creates each object along with specifics, nothing fancy
		title = new JLabel("Assignment 2");
		title.setFont(new Font("Times", Font.BOLD, 16));
		title.setOpaque(true);
		title.setBackground(Color.lightGray);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.TOP);
		
		name = new JLabel("Enter string below:");
		name.setFont(new Font("Times", Font.BOLD, 12));
		name.setOpaque(true);
		name.setHorizontalAlignment(SwingConstants.LEFT);
		name.setVerticalAlignment(SwingConstants.CENTER);
		
		input = new JTextField("");
		input.setFont(new Font("Times", Font.PLAIN, 12));
		input.setOpaque(true);
		input.setBorder(BorderFactory.createLoweredBevelBorder());
		
		calc = new JButton("evaluate");
		calc.addActionListener(this);
		calc.setHorizontalAlignment(SwingConstants.CENTER);
		calc.setVerticalAlignment(SwingConstants.CENTER);

		result = new JLabel("");
		result.setFont(new Font("Times", Font.BOLD, 12));
		result.setOpaque(true);
		result.setHorizontalAlignment(SwingConstants.LEFT);
		result.setVerticalAlignment(SwingConstants.CENTER);
		
		validity = new JLabel("");
		validity.setFont(new Font("Times", Font.BOLD, 12));
		validity.setOpaque(true);
		validity.setHorizontalAlignment(SwingConstants.LEFT);
		validity.setVerticalAlignment(SwingConstants.CENTER);
		
		numStrings = new JLabel("");
		numStrings.setFont(new Font("Times", Font.PLAIN, 12));
		numStrings.setOpaque(true);
		numStrings.setHorizontalAlignment(SwingConstants.LEFT);
		numStrings.setVerticalAlignment(SwingConstants.CENTER);
	}
	
	private void makeLayout(){
		//makes the layout look nice
		//the window would also immediately shrink, so this fixed it
		setPreferredSize(new Dimension(200, 320));
		setLayout( new GridLayout(11, 1));
		
		add(title);
		add(name);
		add(input);
		add(calc);
		add(result);
		add(validity);
		add(numStrings);
	}
	
	public void actionPerformed(ActionEvent e) {
		//there is only one event possible in this panel
		result.setText("Result:");
		//reads the input and puts it through LanguageParser
		StringSplitter splitter = new StringSplitter(input.getText());
		Integer numString = splitter.countTokens();
		//set the background color and print statement based on result
		if(LanguageParser.parse(splitter)){
			validity.setText("valid");
			validity.setBackground(Color.green);
			numStrings.setBackground(Color.green);
		}
		else{
			validity.setText("invalid");
			validity.setBackground(Color.pink);
			numStrings.setBackground(Color.pink);
			
		}
		//prints the number of tokens, and catches the 1 token exception
		if(numString == 1){
			numStrings.setText("There is 1 token");
		}
		else{
			numStrings.setText("There are " + numString.toString() + " tokens");
		}
	}
}