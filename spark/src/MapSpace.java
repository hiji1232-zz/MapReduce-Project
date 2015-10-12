package sparkMapReduce;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import scala.Tuple2;

/**
 * Map class
 */
public class MapSpace {		
	public List<Tuple2<String, Integer>> tupleList = new ArrayList<Tuple2<String, Integer>>();
	// use space program to compare result
	public List<Tuple2<String, Integer>> callSpace(String[] lineToken) throws IOException {			
		for (String e : lineToken) {				 
			String fileID = convertToFile(e);
			BufferedReader result = runSpace(fileID);
			BufferedReader oracle = readOracle(fileID);				
			String oracleLine = null;
			String resultLine = null;
					 
			// compare result
			while ((oracleLine = oracle.readLine()) != null && (resultLine = result.readLine()) != null) {
				// if same, collect 0
				if (oracleLine.equals(resultLine)) {
					Tuple2<String, Integer> tuple = new Tuple2<String, Integer>(fileID, 0);
					tupleList.add(tuple);
				// if not same, collect 1
				} else {
					Tuple2<String, Integer> tuple = new Tuple2<String, Integer>(fileID, 1);
					tupleList.add(tuple);
				}			    
			}			 
		}	 		 
		return tupleList;
	}
	
	/**
	 * Convert sequence number to file
	 * @param num
	 */
	public String convertToFile(String num) {
		String fileName = "gr" + num;
		return fileName;
	}
	   	   
	/**
	 * Run each space program
	 * @throws IOException 
	 */
	public BufferedReader runSpace(String fileID) throws IOException{
		Process p;
		String cmd = "data/space testInput/" + fileID;
		String[] shell = {"/bin/sh", "-c", cmd};					
		p = Runtime.getRuntime().exec(shell);
		// get the output data
		String line;             
		BufferedReader read = new BufferedReader(new InputStreamReader(p.getInputStream()));   
		return read;
	}
	   
	/**
	 * Read data from oracle
	 * @param fileID
	 * @return
	 * @throws FileNotFoundException
	 */
	public BufferedReader readOracle(String fileID) throws FileNotFoundException {
		// get the oracle data
		String path = "testOracle/" + fileID;
		BufferedReader read = new BufferedReader(new FileReader(path));
		return read;
	}	
}
