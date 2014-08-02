

import static org.junit.Assert.*;
import GamePackage.Map;
import GamePackage.Obstacle;

import java.io.*;
import java.util.Scanner;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.junit.Test;

public class MapTest {

	public int[][] testedMap;
	public Obstacle[][] testedObstacles;
	public File testedMapFile;
	public int[][] expectedMap2;
	public Obstacle[][] expectedObstacles2;
	
	public void generateExpectedMap() {
		expectedMap2 = new int[75][50];
		expectedObstacles2 = new Obstacle[75][50];
		File mapPath = new File("").getAbsoluteFile();
		try {
			Scanner lineReader = new Scanner(new File(mapPath + "/res/data/map/basicmap2.txt"));
			String[] temparyArray = new String[50];
			while (lineReader.hasNext()) {
				for (int line = 0; line < 50; line++) {
					temparyArray[line] = lineReader.nextLine();
				}
			}
			for (int yPixel = 0; yPixel < 50; yPixel++) {
				for (int xPixel = 0; xPixel < 75; xPixel++) {
					int valueinPosition = Integer.parseInt(temparyArray[yPixel].substring(xPixel, xPixel+1));
					if (valueinPosition == 1) {
						expectedObstacles2[xPixel][yPixel] = new Obstacle('0');
					}
					else { expectedObstacles2[xPixel][yPixel] = null; }
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testIsDefaultMapCreated() {
		Map map = new Map();
		testedMap = map.getBoardMap();
		testedObstacles = map.getBoardObstacle();
		for (int x = 0; x < testedMap.length; x++) {
			for (int y = 0; y < testedMap[0].length; y++) {
				assertEquals(testedMap[x][y], 0);
				assertEquals(testedObstacles[x][y], null);
			}
		}
	}
	
	@Test
	public void testForWrongFileNoFile() {
		testedMapFile = new File("");
		Map.mapchoser = new JFileChooser();
		Map.mapchoser.setSelectedFile(testedMapFile); 
		Map.isMapSelected = true;
		Map map = new Map();
		testedMap = map.getBoardMap();
		testedObstacles = map.getBoardObstacle();
		for (int x = 0; x < testedMap.length; x++) {
			for (int y = 0; y < testedMap[0].length; y++) {
				assertEquals(testedMap[x][y], 0);
				assertEquals(testedObstacles[x][y], null);
			}
		}
	}
	
	@Test
	public void testIsSelectedMapCreated() {
		testedMapFile = new File("").getAbsoluteFile();
		testedMapFile = new File(testedMapFile + "/res/data/map/basicmap2.txt");
		Map.mapchoser = new JFileChooser();
		Map.mapchoser.setSelectedFile(testedMapFile); 
		Map.isMapSelected = true;
		Map map = new Map();
		
		generateExpectedMap();
		testedMap = map.getBoardMap();
		testedObstacles = map.getBoardObstacle();
		
		for (int x = 0; x < testedMap.length; x++) {
			for (int y = 0; y < testedMap[0].length; y++) {
				assertEquals(testedMap[x][y], 0);
				if(testedObstacles[x][y] == null) {
					assertEquals(testedObstacles[x][y], expectedObstacles2[x][y]);
				}
				else {
					assertEquals(testedObstacles[x][y].getType(), expectedObstacles2[x][y].getType());
				}
			}
		}
	}
	
	
}
