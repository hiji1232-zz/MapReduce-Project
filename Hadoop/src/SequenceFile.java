import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  This class is to implement two different algorithm and generate corrspondent sequence
 */
public class SequenceFile {
    
    /**
     * generate sequence with remove each element
     * @param n
     */
    public List<ArrayList<Integer>> generateSequence(int n) {	
        List<ArrayList<Integer>> removeSequence = new ArrayList<ArrayList<Integer>>();		
        for (int i = 0; i < n; i++) {
            ArrayList<Integer> updatedSequence = initialSequence(n);
            updatedSequence.remove(i);
            removeSequence.add(updatedSequence);
        }
        return removeSequence;
    }
    
    /**
     * Get the sequence
     * @param n
     */
    private ArrayList<Integer> initialSequence(int n) {
        ArrayList<Integer> sequence = new ArrayList<Integer>();		
        // initilize list
        for (int i = 1; i <= n; i++) {
            sequence.add(i);
        }
        return sequence;
    }
    	
    /**
     * Convert Sequence to String
     * @param n
     * @return
     */
    public String seqToString (int n) {
        String SeqToStr = initialSequence(n).toString();
        String str1 = SeqToStr.replaceAll("[],]", "");
        String str2 = str1.replaceAll("\\[", "");
        String strSeq = str2.replaceAll(" ", "");
        System.out.println("Seq to str: " +strSeq);
        return strSeq;
    }
    	
    /**
     * generate permutation sequence
     */
    public Set<String> generatePermutation(String strSeq) {		
        Set<String> permutationSeq = new HashSet<String>();
        if(strSeq == null) {
            return null;
        } else if (strSeq.length() == 0) {
            permutationSeq.add("");
            return permutationSeq;
        }
        
        char first = strSeq.charAt(0);
        String rem = strSeq.substring(1);
        Set<String> restStr = generatePermutation(rem);		
        for (String str : restStr) {
            for (int i = 0; i <= str.length(); i++) {
                permutationSeq.add(str.substring(0, i) + first + str.substring(i));
            }
        }
    	return permutationSeq;		
    }
        
    /**
     *  Write the sequence into file
     */
    public void writeToFile(List<ArrayList<Integer>> data) throws IOException {
        File fout = new File("input/removeSequence.txt");
        FileOutputStream fos = new FileOutputStream(fout);	 
        OutputStreamWriter osw = new OutputStreamWriter(fos);		
        for(int i = 0; i < data.size(); i++){
            String arr = data.get(i).toString();
            String str = arr.replaceAll("]", "");
            String str2 = str.replaceAll("\\[", "");
            osw.write(str2);
            osw.write("\n");
        } 
        osw.close();
    }
    
    /**
     *  Write permutation into file
     */
    public void writePermutation(Set<String> data) throws IOException {
        File fout = new File("../input/permutationSeq.txt");
        FileOutputStream fos = new FileOutputStream(fout);	 
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        for (String str : data) {			
            for (int i = 0; i < str.length(); i++) {
                if (i != str.length() - 1) {
                    osw.append(str.charAt(i));
                    osw.append(", ");	
                } else {
                    osw.append(str.charAt(i));
                    osw.append("\n");
                }
            }
        }	 
        osw.close();
    }
    
    /**
     * Create the expected output
     */
    public void createOracle(int n) throws IOException {		
        Process p;
        for (int i = 1; i <= n; i++) {
            String cmd = "../input/space testInput/gr" + i + " > ../testOracle/gr"+ i;
            String[] shell = {"/bin/sh", "-c", cmd};
            p = Runtime.getRuntime().exec(shell);		
    	}
    }
}
