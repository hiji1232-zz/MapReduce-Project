import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 *  Reduce class, to collect the value from mapper 
 */
public class SequenceReducer extends Reducer<Text,IntWritable,Text,IntWritable> {
     private final static IntWritable zero = new IntWritable(0);
     private IntWritable result = new IntWritable();
     public Set<String> dependenceTest = new HashSet<String>();
        
     public void reduce (Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {
        int sum = 0;
        for (IntWritable val : values) {
            sum += val.get();	
        }   	
        result.set(sum);
        context.write(key, result);   	
        dependenceTest = getDependenceTest(key);
     }
        
     public Set<String> getDependenceTest(Text key) {
        Set<String> resultSet = new HashSet<String>();
        if (result.compareTo(zero) != 0) {
            resultSet.add(key.toString());	
        }    
        return resultSet;
     }
}
