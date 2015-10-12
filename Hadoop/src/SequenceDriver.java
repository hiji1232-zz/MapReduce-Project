import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.NLineInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * This is the driver class, which include 2 algorithms to generate test cases. 
 * Please select to run algorithm 1 or algorithm 2. Not both of them together
 */
public class SequenceDriver {
    
    public static void main(String[] args) throws IOException {		
        SequenceFile seqFile = new SequenceFile();		
        // generate expected result
        seqFile.createOracle(10);
        // Algorithm 1: generate remove sequence algorithm
        List<ArrayList<Integer>> data = seqFile.generateSequence(10);
        // write sequence to file
        seqFile.writeToFile(data);
        
        // Algorithm 2: generate permutation sequence
        File fout = new File("../input/permutationSeq.txt");
        FileOutputStream fos = new FileOutputStream(fout);	 
        OutputStreamWriter osw = new OutputStreamWriter(fos);
        JohnsonTrotter.perm(5, osw);
        osw.close();		
        System.out.println("Sequence generation Done");
        
        // Start Hadoop mapreduce configuration
        long startTime = System.currentTimeMillis();
    	// map reduce diver
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "Test Dependence");
        job.setJarByClass(SequenceDriver.class);
        job.setMapperClass(SequenceMapper.class);
        job.setCombinerClass(SequenceReducer.class);
        job.setReducerClass(SequenceReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);
        NLineInputFormat.addInputPath(job, new Path(args[0]));	    
        // read one line
        job.getConfiguration().setInt("mapreduce.input.lineinputformat.linespermap", 1);    
        FileSystem fs = FileSystem.get(conf);
    
        //Check if output path (args[1])exist or not, delete the output path if already exist		
        if(fs.exists(new Path(args[1]))){
           fs.delete(new Path(args[1]),true);
        }    
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
              
        try {
            int status = job.waitForCompletion(true) ? 0 : 1;
            long endTime   = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Total execution time: "+ totalTime + " seconds");		    
            System.exit(status);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }  
    }
}
