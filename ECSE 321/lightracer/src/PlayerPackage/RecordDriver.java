package PlayerPackage;

import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Set;

import au.com.bytecode.opencsv.CSVReader;

/**
 * The RecordDriver class contains methods for accessing both Record objects and
 * CareerRecord objects. It can update records as well as read from them and sum
 * up total records for any given player.
 * 
 * @author Aidan Petit
 */
public class RecordDriver {
	private static CSVReader reader;
	private String[] topTen = new String[10];
	private static FileModel fm;
	public ArrayList<Record> allRecords = new ArrayList<Record>();
	
	public RecordDriver() {
		try {
			fm = new FileModel(new File("").getAbsolutePath() + "/res/data/RecordList.txt");
			allRecords = fm.readRecordList();
		}
		catch (EOFException f) {
			f.printStackTrace();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private ArrayList<Record> getAllRecords() {
		return this.allRecords;
	}
	
	/**
	 * After a game finishes, updates the players' records with the result
	 * 
	 * @param p1   player 1
	 * @param p2   player 2
	 * @param winner   which player won the game
	 */
	public void updateRecord(String p1, String p2, String winner) {
		if (this.allRecords == null) {
			Record newRecord = new Record(p1,p2);
			if (p1.equals(winner)) {
				newRecord.addP1Win();
			}
			else if (p2.equals(winner)) {
				newRecord.addP2Win();
			}
			this.allRecords = new ArrayList<Record>();
			this.allRecords.add(newRecord);	
		}
		else{	
			if (checkForRecord(p1,p2) == true) { //the 2 players have a record
				Record theOld = null;
				try{
					theOld = getPvPRecord(p1,p2);
				} catch (EOFException exc) {
					System.out.println("File is not null and contains no record for: ");}
				if (p1 == winner) {
					theOld.addP1Win();
					this.allRecords.add(theOld);
				}
				if (p2 == winner) {
					theOld.addP2Win();
					this.allRecords.add(theOld);
				}
			}
			else{		
				Record theNew = new Record(p1,p2);
				if (p1 == winner) {
					theNew.addP1Win();
					this.allRecords.add(theNew);
				}
				if (p2 == winner) {
					theNew.addP2Win();
					this.allRecords.add(theNew);
				}
			}		
			fm.writeRecordList(this.allRecords);
		}
	}
		
	/**
	 * Saves all of the records to a persistent file.
	 */
	public void writeAllRecordsToFile() {
		fm.writeRecordList(this.allRecords);
	}
		
	/**
	 * Finds the record object which contains the match history between the
	 * two input players.
	 * 
	 * @param p1   Player 1
	 * @param p2   Player 2
	 * @return     The record object corresponding to their game history
	 * @throws     EOFException   There is no history between the two players
	 */
	public Record getPvPRecord(String p1, String p2) throws EOFException{
		String currentP1;
		String currentP2;
		Record currentRecord = null;
	
		getAllRecords();
		
		for(int i=0; i<allRecords.size(); i++) {
			currentP1 = allRecords.get(i).getPlayer1();
			currentP2 = allRecords.get(i).getPlayer2();
			if (p1.equals(currentP1) && p2.equals(currentP2) ) {
				currentRecord = allRecords.get(i);
				return currentRecord;
			}
			if (p1.equals(currentP2) && p2.equals(currentP1)) {
				currentRecord = allRecords.get(i);
				return currentRecord;
			}
		}
		return currentRecord;
	}
	
	/**
	 * Sums up all of the wins this player has had with every opponent
	 * 
	 * @param player   The selected player
	 * @return         The total wins of this player across all games
	 */
	public int getLifeTimeWins(String player) {
		int wins = 0;	
		/*
		*	By storing the list of all records as a LinkedHashSet, clearing ArrayList, then 
		* 	re-adding the Records to the ArrayList we eliminate all duplicate entries.
		*/
		Set<Record> consolidateRecords = new LinkedHashSet<Record>(allRecords);
		allRecords.clear();
		allRecords.addAll(consolidateRecords);	
		if(this.allRecords == null) {
			System.out.println("Player has no career record");
			return wins;
		}
		for(int i=0; i<this.allRecords.size(); i++) {	
			Record current = this.allRecords.get(i);
			System.out.println("GetLife - p1: "+current.getPlayer1()+" - Current p2: "+current.getPlayer2());

			if (current.getPlayer1().equals(player)) {
				wins += current.getP1Wins();
			}
			if (current.getPlayer2().equals(player)) {
				wins += current.getP2Wins();
			}
		}
		return wins;
	}
	
	/**
	 * Sums up all of the losses this player has had with every opponent
	 * 
	 * @param player   The selected player
	 * @return         The total losses of this player across all games
	 */
	public int getLifeTimeLosses(String player) {
		int losses = 0;	
		if(this.allRecords == null) {
			System.out.println("Player has no career record");
			return 0;
		}
		for(int i=0; i<allRecords.size(); i++) {	
			Record current = allRecords.get(i);
			if (current.getPlayer1().equals(player)) {
				losses += current.getP2Wins();
			}
			else if (current.getPlayer2().equals(player)) {
				losses += current.getP1Wins();
			}
		}		
		return losses;
	}
	
	/**
	 * Finds the 10 players with the most wins
	 * 
	 * @return   the usernames of the 10 best players
	 */
	public String[] getTopTen() {
		ArrayList<CareerRecord> allPlayerCareerRecords = new ArrayList<CareerRecord>();
		try {
			reader = new CSVReader(new FileReader(new File("").getAbsolutePath() + "/res/data/accountData.csv"),'\t');
			String[] current;
			while((current = reader.readNext()) != null) {
				String currentID = current[0];
				int currentWins = getLifeTimeWins(currentID);
				int currentLosses = getLifeTimeLosses(currentID);
				CareerRecord currentRecord = new CareerRecord(currentID,currentWins,currentLosses);
				allPlayerCareerRecords.add(currentRecord);
			}	
		}
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		catch (IOException f) {
			f.printStackTrace();
		}						
		Collections.sort(allPlayerCareerRecords, new Comparator<CareerRecord>() {
			@Override
			public int compare(CareerRecord p1, CareerRecord p2) {
				int difference = p1.getCareerWins() - p2.getCareerWins();
				if(difference > 0) {
					return 1;
				}
				else if(difference == 0) {
					return 0;
				}
				else {return -1;				
				}
			}
		});

		/* allPlayers is now sorted in reverse numerical order
		 * use a for loop to add the last ten elements of 
		 * allPlayers to results
		 */
		for (int j=0; j<10 ; j++) {
			int place = allPlayerCareerRecords.size() - 1 - j;
			topTen[j] = allPlayerCareerRecords.get(place).toString() ;
		}
		
		if (allPlayerCareerRecords.size() < 10) {
			String[] result = {"No Top 10 List Avalable"};
			return result;
		}
		return topTen;
	}
	
	/**
	 * Checks that the players have played before to avoid reading from
	 * a null file
	 * 
	 * @param p1   Player 1
	 * @param p2   Player 1
	 * @return     <code>true</code> The two players have played each other before
	 *             <code>false</code> The two players have not played each other before
	 */
	public boolean checkForRecord(String p1, String p2)  {
		String inputP1 = p1;
		String inputP2 = p2;
		String currentP1;
		String currentP2;
		if (this.allRecords == null) {
			return false;
		}		
		for(int index = 0; index < this.allRecords.size() ; index++) {		
			Record current = this.allRecords.get(index);	
			currentP1 = current.getPlayer1();
			currentP2 = current.getPlayer2();			
			if (inputP1.equals(currentP1) && inputP2.equals(currentP2 )) {
				return true;
			}
			if (inputP1.equals(currentP2) && inputP2.equals(currentP1)) {
				return true;
			}	
		}
		return false;
	}
}