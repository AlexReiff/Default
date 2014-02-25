package PlayerPackage;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

import java.util.ArrayList;

/**
 * The FileModel class contains methods for reading and writing records
 * from a persistent file.
 * 
 * @author Aidan Petit
 */
public class FileModel {
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private String filename;

	public FileModel(String filename) throws IOException {
		this.filename = filename;
	}

	/**
	 * Reads all of the records from the file where they are persistently stored.
	 * 
	 * @return   An ArrayList of all records
	 * @throws IOException   The record file doesn't exist
	 */
	public ArrayList<Record> readRecordList() throws IOException{
		try {
			ois = new ObjectInputStream(new FileInputStream(filename));
			ArrayList<Record> retrieved = ((ArrayList<Record>) ois.readObject());
			ois.close();
			return retrieved;
		}
		//no arraylist found, returning new arraylist
		catch (EOFException EOF) {
			return (new ArrayList<Record>());
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (StreamCorruptedException f) {
			System.out.println("Unable to read file");	
		}
		return null;
	}

	/**
	 * Writes the ArrayList of all records to a persistent file
	 * 
	 * @param records   The ArrayList of records to be written to file
	 */
	public void writeRecordList(ArrayList<Record> records) {		
		try {
			oos = new ObjectOutputStream(new FileOutputStream(filename));
			oos.writeObject(records);
			oos.close();
		}
		catch (IOException e) {
			System.out.println("Unable to write file");
			e.printStackTrace();
		} 		
	}
}