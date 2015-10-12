import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 *  Mapper class, using Hadoop map reduce framework
 */
public class SequenceMapper extends Mapper<Object, Text, Text, IntWritable> {	
    private final static IntWritable one = new IntWritable(1);
    private final static IntWritable zero = new IntWritable(0);
    public Text fileName = new Text();
    
    /**
     *	Map function
     */
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {	     
         String line = value.toString();
         String[] lineToken = line.split(", ");		 
         // Iterator each file 
         for (int i = 0; i < lineToken.length; i++) {
             String str = lineToken[i];
             String fileID = convertToFile(str);
             BufferedReader result = runSpace(fileID);
             BufferedReader oracle = readOracle(fileID);			 
             String oracleLine = null;
             String resultLine = null;			 
             // compare result
            while ((oracleLine = oracle.readLine()) != null && (resultLine = result.readLine()) != null) {
                // if same, collect 0
                if (oracleLine.equals(resultLine)) {		
                    fileName.set(fileID);
                    context.write(fileName, zero);
                // if not same, collect 1
                } else {
                    System.out.println("oracle: " + oracleLine);
                    System.out.println("result: " + resultLine);
                    fileName.set(fileID);
                    context.write(fileName, one);					
                }    
            }
         }
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
        String cmd = "../input/space ../../testInput/" + fileID;            	
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
        String path = "../../testOracle/" + fileID;
        BufferedReader read = new BufferedReader(new FileReader(path));
        return read;
    }
}
