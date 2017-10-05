/**
 * Kristine Trinh
 * nlt895
 * 11190412
 */
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.opencsv.CSVReader;

import lib280.base.Pair280;
import lib280.hashtable.KeyedChainedHashTable280;
import lib280.tree.OrderedSimpleTree280;

// This project uses a JAR called opencsv which is a library for reading and
// writing CSV (comma-separated value) files.
// 
// You don't need to do this for this project, because it's already done, but
// if you want to use opencsv in other projects on your own, here's the process:
//
// 1. Download opencsv-3.1.jar from http://sourceforge.net/projects/opencsv/
// 2. Drag opencsv-3.1.jar into your project.
// 3. Right-click on the project in the package explorer, select "Properties" (at bottom of popup menu)
// 4. Choose the "Libraries" tab
// 5. Click "Add JARs"
// 6. Select the opencsv-3.1.jar from within your project from the list.
// 7. At the top of your .java file add the following imports:
//        import java.io.FileReader;
//        import com.opencsv.CSVReader;
//
// Reference documentation for opencsv is here:  
// http://opencsv.sourceforge.net/apidocs/overview-summary.html



public class QuestLog extends KeyedChainedHashTable280<String, QuestLogEntry> {

	public QuestLog() {
		super();
	}
	
	/**
	 * Obtain an array of the keys (quest names) from the quest log.  There is 
	 * no expectation of any particular ordering of the keys.
	 * 
	 * @return The array of keys (quest names) from the quest log.
	 */
	public String[] keys() {
		// TODO Implement this method.
		int length = this.count;
		String[] list = new String[length];
		goFirst();
		int index = 0;
		while(itemExists()) {
			list[index] = item().key();
			goForth();
			index = index + 1;
		}
		return list;
	}
	
	/**
	 * Format the quest log as a string which displays the quests in the log in 
	 * alphabetical order by name.
	 * 
	 * @return A nicely formatted quest log.
	 */
	public String toString() {
		// TODO Implement this method.
		String[] qNames = keys();
		String str = "";
		// Sort the array in an alphabetical order by name
		Arrays.sort(qNames);
		int size = qNames.length;
		for (int i = 0; i < size; i++) {
			str = str + obtain(qNames[i]).toString()+"\n";
		}
		return str;
	}
	
	/**
	 * Obtain the quest with name k, while simultaneously returning the number of
	 * items examined while searching for the quest.
	 * @param k Name of the quest to obtain.
	 * @return A pair in which the first item is the QuestLogEntry for the quest named k, and the
	 *         second item is the number of items examined during the search for the quest named k.
	 *         Note: if no quest named k is found, then the first item of the pair should be null.
	 */
	public Pair280<QuestLogEntry, Integer> obtainWithCount(String k) {
		// TODO Implement this method.
		
		// Write a method that returns a Pair280 which contains the quest log entry with name k, 
		// and the number QuestLogEntry objects that were examined in the process.  You need to write
		// this method from scratch without using any of the superclass methods (mostly because 
		// the superclass methods won't be terribly useful unless you can modify them, which you
		// aren't allowed to do!).
		
		int i = this.hashPos(k);
		hashArray[i].goFirst();
		int itemNum = 0;
		while(hashArray[i].itemExists()) {
			itemNum = itemNum + 1;
			if(hashArray[i].item().key() == k) {
				return new Pair280<QuestLogEntry, Integer>(hashArray[i].item(), itemNum); 
			}
			hashArray[i].goForth();
		}
		
		if( hashArray[i].isEmpty() ) {
			return new Pair280<QuestLogEntry, Integer>(null, 0);
		}
		return new Pair280<QuestLogEntry, Integer>(null, itemNum);	
	}
	
	
	public static void main(String args[])  {
		// Make a new Quest Log
		QuestLog hashQuestLog = new QuestLog();
		
		// Make a new ordered binary tree.
		OrderedSimpleTree280<QuestLogEntry> treeQuestLog =
				new OrderedSimpleTree280<QuestLogEntry>();
		
		
		// Read the quest data from a CSV (comma-separated value) file.
		// To change the file read in, edit the argument to the FileReader constructor.
		CSVReader inFile;
		try {
			inFile = new CSVReader(new FileReader("quests4.csv"));
		} catch (FileNotFoundException e) {
			System.out.println("Error: File not found.");
			return;
		}
		
		String[] nextQuest;
		try {
			// Read a row of data from the CSV file
			while ((nextQuest = inFile.readNext()) != null) {
				// If the read succeeded, nextQuest is an array of strings containing the data from
				// each field in a row of the CSV file.  The first field is the quest name,
				// the second field is the quest region, and the next two are the recommended
				// minimum and maximum level, which we convert to integers before passing them to the
				// constructor of a QuestLogEntry object.
				QuestLogEntry newEntry = new QuestLogEntry(nextQuest[0], nextQuest[1], 
						Integer.parseInt(nextQuest[2]), Integer.parseInt(nextQuest[3]));
				// Insert the new quest log entry into the quest log.
				hashQuestLog.insert(newEntry);
				treeQuestLog.insert(newEntry);
			}
		} catch (IOException e) {
			System.out.println("Something bad happened while reading the quest information.");
			e.printStackTrace();
		}
		
		// Print out the hashed quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
		System.out.println(hashQuestLog);
		
		// Print out the tree quest log's quests in alphabetical order.
		// COMMENT THIS OUT when you're testing the file with 100,000 quests.  It takes way too long.
	    System.out.println(treeQuestLog.toStringInorder());
		

		// TODO Determine the average number of elements examined during access for hashed quest log.
	    // (call hashQuestLog.obtainWithCount() for each quest in the log and find average # of access)
	    String[] strQ = hashQuestLog.keys();
	    
	    double hashAvg = 0;
	    for (String s : strQ){
	    	hashAvg += hashQuestLog.obtainWithCount(s).secondItem();
	    }
		System.out.println("Avg. # of items examined per query in the hashed quest log with " + hashQuestLog.count() + " entries: " + hashAvg/4);
		
		// TODO Determine the average number of elements examined during access for tree quest log.
	    // (call treeQuestLog.searchCount() for each quest in the log and find average # of access)		
	    double treeAvg = 0;
	    for (String s : strQ){
	    	treeAvg += treeQuestLog.searchCount(hashQuestLog.obtainWithCount(s).firstItem());
	    }
		System.out.println("Avg. # of items examined per query in the hashed quest log with " + hashQuestLog.count() + " entries: " + treeAvg/4);

	}
	
	
}
