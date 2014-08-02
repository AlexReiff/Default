//Alexander Reiff
//260504962

package a4;

import java.math.BigInteger;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;


public class A1Panel extends JPanel implements ActionListener {

	JLabel title;
	JLabel name1;
	JTextField arg1;
	JLabel name2;
	JTextField arg2;
	JRadioButton add;
	JRadioButton sub;
	JRadioButton mult;
	JRadioButton div;
	JButton calc;
	JLabel result;
	int operator = 0;

	A1Panel() {
		makeComponents();
		makeLayout();
	}

	private void makeComponents() {
		//creates each object along with specifics, nothing fancy
		title = new JLabel("Assignment 1");
		title.setFont(new Font("Times", Font.BOLD, 16));
		title.setOpaque(true);
		title.setBackground(Color.lightGray);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setVerticalAlignment(SwingConstants.TOP);

		name1 = new JLabel("First argument:");
		name1.setFont(new Font("Times", Font.BOLD, 12));
		name1.setOpaque(true);
		name1.setHorizontalAlignment(SwingConstants.LEFT);
		name1.setVerticalAlignment(SwingConstants.CENTER);
		
		arg1 = new JTextField("0");
		arg1.setFont(new Font("Times", Font.PLAIN, 12));
		arg1.setOpaque(true);
		arg1.setBorder(BorderFactory.createLoweredBevelBorder());

		name2 = new JLabel("Second Argument:");
		name2.setFont(new Font("Times", Font.BOLD, 12));
		name2.setOpaque(true);
		name2.setHorizontalAlignment(SwingConstants.LEFT);
		name2.setVerticalAlignment(SwingConstants.CENTER);
		
		arg2 = new JTextField("0");
		arg2.setFont(new Font("Times", Font.PLAIN, 12));
		arg2.setOpaque(true);
		arg2.setBorder(BorderFactory.createLoweredBevelBorder());

		add = new JRadioButton("Add");
		add.addActionListener(this);
		
		sub = new JRadioButton("Subtract");
		sub.addActionListener(this);
		
		mult = new JRadioButton("Multiply");
		mult.addActionListener(this);
		
		div = new JRadioButton("Divide");
		div.addActionListener(this);

		calc = new JButton("compute result");
		calc.addActionListener(this);
		calc.setHorizontalAlignment(SwingConstants.CENTER);
		calc.setVerticalAlignment(SwingConstants.CENTER);

		result = new JLabel("");
		result.setFont(new Font("Times", Font.PLAIN, 16));
		result.setOpaque(true);
		result.setBackground(Color.white);
		result.setHorizontalAlignment(SwingConstants.CENTER);
		result.setVerticalAlignment(SwingConstants.CENTER);
	}

	private void makeLayout() {	
		//makes the layout look nice
		//the window would also immediately shrink, so this fixed it
		setPreferredSize(new Dimension(200, 320));
		setLayout(new GridLayout(11, 1));
		
		//sorts the four operators into a button group
		ButtonGroup group = new ButtonGroup();
		group.add(add);
		group.add(sub);
		group.add(mult);
		group.add(div);
		
		add(title);
		add(name1);
		add(arg1);
		add(name2);
		add(arg2);
		add(add);
		add(sub);
		add(mult);
		add(div);
		add(calc);
		add(result);
	}
	/*
	 * I set up this method so that clicking an operator changed the value for the switch statement
	 * Upon pressing the compute button, it uses BigInteger to compute the result.
	 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			operator = 1;
		}
		if (e.getSource() == sub) {
			operator = 2;
		}
		if (e.getSource() == mult) {
			operator = 3;
		}
		if (e.getSource() == div) {
			operator = 4;
		}
		if (e.getSource() == calc) {
			BigInteger firstNum = new BigInteger(arg1.getText());
			BigInteger secondNum = new BigInteger(arg2.getText());
			switch (operator) {
			case 1:
				result.setText(firstNum.add(secondNum).toString());
				break;
			case 2:
				result.setText(firstNum.subtract(secondNum).toString());
				break;
			case 3:
				result.setText(firstNum.multiply(secondNum).toString());
				break;
			case 4:
				if (secondNum.toString() == "0") {
					result.setText("undefined");
				} else {
					result.setText(firstNum.divide(secondNum).toString());
				}
				break;
			}
		}
	}
}