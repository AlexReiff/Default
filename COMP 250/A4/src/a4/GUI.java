package a4;

//  COMP 250 - Introduction to Computer Science - Fall 2012
//  Assignment #3 - Question 1

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;

import a3.AutoComplete;
import a3.Trie;

public class GUI
{
	public static void main(String[] args)
	{	   
		JFrame frame = new JFrame("Assignment 4"); 
		frame.setLayout( new GridLayout(1,3));
		
		
		JPanel panel = new A1Panel();
		panel.setBorder( BorderFactory.createRaisedBevelBorder()  );
		frame.add( panel);

		panel = new A2Panel();
		panel.setBorder( BorderFactory.createRaisedBevelBorder()  );
		frame.add( panel);

	//  You will need to change the following since it is the path on Prof's computer.
		
		String fileName = "C:\\Program Files\\eclipse\\workspace\\2012\\src\\a3\\inputFile.txt";
		panel = new A3Panel(fileName);
		panel.setBorder( BorderFactory.createRaisedBevelBorder()  );
		frame.add( panel);
	
		frame.setSize(800,600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.pack();
	}
}