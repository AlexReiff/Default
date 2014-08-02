package GamePackage;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;

/**
 * GamePanel is the class that displays the actual game play. It
 * contains a 2D array of cells which represent the squares on the board
 * and includes methods for updating the cells and for resetting the board
 * to its initial state.
 * 
 * @author Alex Reiff
 *
 */
public class GamePanel extends JPanel {
	private Cell[][] cells;
	private GridBagConstraints constraints;
	
    public GamePanel() {
        setLayout(new GridBagLayout());
        constraints = new GridBagConstraints();
        cells = new Cell[GameBoard.map.length][GameBoard.map[0].length];
        makeCells();
    }

    private void makeCells() {
        int cols = GameBoard.map[0].length; 
        int rows = GameBoard.map.length;
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                constraints.gridy = col;
                constraints.gridx = row + 2;
                Cell cell = new Cell();
              //creating the correct border for the cell, so that the edges
              //of the game board are black
                Border border = null;
                if(row == 0) {
                	if (col == 0) {
                        border = new MatteBorder(1, 1, 0, 0, Color.BLACK);
                	}
                	else if (col < cols-1) {
                        border = new MatteBorder(0, 1, 0, 0, Color.BLACK);
                    }
                    else {
                        border = new MatteBorder(0, 1, 1, 0, Color.BLACK);
                    }
                }
                else if (row < rows-1) {
                	if (col == 0){
                        border = new MatteBorder(1, 0, 0, 0, Color.BLACK);
                	}
                	else if (col < cols-1) {
                        border = new MatteBorder(0, 0, 0, 0, Color.BLACK);
                    }
                    else {
                        border = new MatteBorder(0, 0, 1, 0, Color.BLACK);
                    }
                }
                else {
                	if (col == 0) {
                        border = new MatteBorder(1, 0, 0, 1, Color.BLACK);
                	}
                	else if (col < cols-1) {
                        border = new MatteBorder(0, 0, 0, 1, Color.BLACK);
                    }
                    else {
                        border = new MatteBorder(0, 0, 1, 1, Color.BLACK);
                    }
                }
                cell.setBorder(border);
                
              //drawing initial obstacles onto the board  
                if(GameBoard.obstacles[row][col] != null) {
                	if(GameBoard.obstacles[row][col].getType() == Obstacle.Owner.BOARD) {
                    	cell.setBackground(Color.BLACK);
                	}
                }
                else {
                    cell.setBackground(Color.WHITE);
                }
                cells[row][col] = cell;
                add(cell, constraints);
            }
        }
    }
      
    /**
     * Draws the obstacles onto the board, whether they are a light trail
     * from one of the racers of part of the default obstacle of the current
     * map.
     */
    public void updateCells() {
    	for (int row = 0; row < GameBoard.map.length; row ++) {
		    for (int col = 0; col < GameBoard.map[0].length; col++) {
		    	Cell cell = cells[row][col];
                if(GameBoard.obstacles[row][col] != null) {
                	Obstacle curr = GameBoard.obstacles[row][col];	
                	if(curr.getType() == Obstacle.Owner.P1) {
                		cell.setBackground(Color.PINK);
                	}
                    else if(curr.getType() == Obstacle.Owner.P2) {
                    	cell.setBackground(Color.CYAN);
                    }
                    else if(curr.getType() == Obstacle.Owner.BOARD) {
                    	cell.setBackground(Color.BLACK);
                    }
                }
		    }
    	}
    }

    /**
     * Colors the square which corresponds to the racer's current
     * position with its corresponding color
     * 
     * @param racer   The racer whose position we are updating.
     */
    public void updateRacer(Racer racer) {
      	Cell racerCell = cells[racer.getXPosition()][racer.getYPosition()];
      	if(racer.getPlayer() == 1) {
      		racerCell.setBackground(Color.RED);
      	}
      	else if(racer.getPlayer() == 2) {
      		racerCell.setBackground(Color.BLUE);
      	}
    }
    
    /**
     * Erases everything that has been drawn onto the board and then
     * redraws the map's default obstacles and the racers.
     * 
     * @param r1   Player 1's racer
     * @param r2   Player 2's racer
     */
    public void clean(Racer r1, Racer r2) {
    	for(int row = 0; row < cells.length; row ++) {
    		for(int col = 0; col < cells[0].length; col++) {
    			if(GameBoard.obstacles[row][col] != null) {
    				if(GameBoard.obstacles[row][col].getType() == Obstacle.Owner.BOARD)
    					cells[row][col].setBackground(Color.BLACK);
    				}
    				else {
    					cells[row][col].setBackground(Color.WHITE);
    				}
    			}
    		}
    	updateRacer(r1);
    	updateRacer(r2);
    	}
	}
