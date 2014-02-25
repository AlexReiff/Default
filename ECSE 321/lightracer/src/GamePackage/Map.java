package GamePackage;

import java.io.*;

import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 * The Map class creates a representation of a map file in a format 
 * that the game can play on. It populates the map with the starting
 * obstacles and can change the map.
 * 
 * @author Jungwan Kim
 */
public class Map {
	public static boolean isMapSelected = false;
	private static Scanner lineReader;
	public static JFileChooser mapchoser;
	private int heightMap = 50;
	private int widthMap = 75;
	private int[][] boardMap;
	private Obstacle[][] boardObstacle;
	private File mapPath;

	public Map() {
		if(!isMapSelected) {
			openMapfile();
		}
		else { 
			openSelectedMapFile();
		}
		readMapfile();
		closeMapfile();
	}
	
	/**
	 * Returns the map of the game board
	 * 
	 * @return   The map of the game board.
	 */
	public int[][] getBoardMap() {
		return boardMap;
	}
	
	/**
	 * Returns the set of default obstacles and their
	 * positions on this map.
	 * 
	 * @return   2D array of the default obstacles.
	 */
	public Obstacle[][] getBoardObstacle() {
		return boardObstacle;
	}
	
	private void initializeSize() {
		 boardMap = new int[widthMap][heightMap];
		 boardObstacle = new Obstacle[widthMap][heightMap];		 
	}
	
	private void openMapfile() {
		try {
			mapPath = new File("").getAbsoluteFile();
			lineReader = new Scanner(new File(mapPath + "/res/data/map/basicmap1.txt"));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void openSelectedMapFile() {
		try {
			lineReader = new Scanner(new File(mapchoser.getSelectedFile().getAbsolutePath()));
		}
		catch (Exception e) {
			openMapfile();
		}
	}
	
	private void readMapfile() {
		try {
			String[] temparyArray = new String[heightMap];
			while (lineReader.hasNext()) {
				for (int line = 0; line < heightMap; line++) {
					temparyArray[line] = lineReader.nextLine();
				}
			}
			
		    initializeSize();
		    
			for (int yPixel = 0; yPixel < heightMap; yPixel++) {
				for (int xPixel = 0; xPixel < widthMap; xPixel++) {
					int valueinPosition = Integer.parseInt(temparyArray[yPixel].substring(xPixel, xPixel+1));
					boardMap[xPixel][yPixel] = 0;
					if (valueinPosition == 1) {
						boardObstacle[xPixel][yPixel] = new Obstacle('0');
					}
					else { boardObstacle[xPixel][yPixel] = null; }
				}
			}
		}
		catch (Exception e) {
			//if user selects file that is not a map structure, it loads default map.
			openMapfile();
			readMapfile();
		}
	}
	
	private void closeMapfile() {
		lineReader.close();
	}
	
	/**
	 * Creates a pop-up display that allows the user to select a map from the list
	 * and will load it into the game upon selection.
	 */
	public static void selectMapfile() {
		mapchoser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("txt", "txt");
	    mapchoser.setFileFilter(filter);
	    mapchoser.setCurrentDirectory(new File(new File("").getAbsoluteFile() + "/res/data/map"));
	    int returnVal = mapchoser.showOpenDialog(mapchoser);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	isMapSelected = true;			
	    }
	}
	
}