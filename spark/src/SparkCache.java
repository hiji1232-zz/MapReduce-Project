import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import scala.Tuple2;

/**
 *  Main driver class, by using spark framework
 *  Please only apply 1 algorithm at a time
 */
public class SparkCache {
	
	public static void main(String[] args)  throws Exception {		
		TestDependence test = new TestDependence();
		// generate oracle
		test.createOracle(100);		
			
		// Algorithm 1: generate sequence
		List<ArrayList<Integer>> seq = test.generateRemoveSequence(100);		
		List<String> data = new ArrayList<String>();
		for (List<?> e : seq) {
			String str = e.toString();
			String str1 = str.replaceAll("]", "");
			String str2 = str1.replaceAll("\\[", "");
			data.add(str2);
		}
		// generate permuation sequence
		List<String> list = new ArrayList<String>();
		JohnsonTrotter.perm(8,list);
		// start counting
		long startTime = System.currentTimeMillis();
		System.out.println("Start timing");
		SparkConf sparkConf = new SparkConf().setAppName("SparkTestDependence");
		JavaSparkContext ctx = new JavaSparkContext(sparkConf);		
		JavaRDD<String> lines = ctx.parallelize(data, 4);
		JavaRDD<String> lines = ctx.parallelize(list, 4);
		
	    JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
	        @Override
	        public Iterable<String> call(String s) {
	        	List<String> str = Arrays.asList(s.split("\n"));

	        	return str;
	        }
	    });
	    
	    JavaPairRDD<String, Integer> ones = words.flatMapToPair
											(new PairFlatMapFunction<String, String, Integer>() {
	    	@Override
	        public Iterable<Tuple2<String, Integer>> call(String s) throws IOException 	        				 
				String[] lineToken = s.split(", ");			 
				MapSpace mapTest = new MapSpace();		 
				List<Tuple2<String, Integer>> result = mapTest.callSpace(lineToken);
				return result;
	        }
	     });
	    	    
	    JavaPairRDD<String, Integer> counts = ones.reduceByKey(new Function2<Integer, Integer, Integer>() {
	        @Override
	        public Integer call(Integer i1, Integer i2) {
	          return i1 + i2;
	        }
	    });

		List<Tuple2<String, Integer>> output = counts.collect();		
	    long endTime   = System.currentTimeMillis();
	    long totalTime = endTime - startTime;
	    System.out.println("totalTime: " + totalTime + " milliseconds");    
		// write to file
		File fout = new File("../data/result.txt");
		FileOutputStream fos = new FileOutputStream(fout);	 
		OutputStreamWriter writer = new OutputStreamWriter(fos);		
		for(Tuple2<String,Integer> tuple : output){
			String pair = tuple._1 + ": " + tuple._2.toString();
			writer.write(pair);
			writer.write("\n");
		}	 
		writer.close();
		ctx.stop();
		ctx.close();
	}
}
