package ecp.bigdata.Tutorial1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.TreeSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.TextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.TextOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

public class tweet_matrix extends Configured implements Tool {
   public static void main(String[] args) throws Exception {
      System.out.println(Arrays.toString(args));
      int res = ToolRunner.run(new Configuration(), new tweet_matrix(), args);
      
      System.exit(res);
   }
   
   @Override
   public int run(String[] args) throws Exception {
      System.out.println(Arrays.toString(args));
      Configuration conf = new Configuration();
      Job job = new Job(conf);
      job.setJarByClass(tweet_matrix.class);
      job.setOutputKeyClass(Text.class);
      job.setOutputValueClass(IntWritable.class);

      job.setMapperClass(Map.class);
      job.setReducerClass(Reduce.class);
      
      job.getConfiguration().set("mapreduce.output.textoutputformat.separator", ","); // getting a csv formatted output

      job.setInputFormatClass(TextInputFormat.class); // each line is considered a separate file
      job.setOutputFormatClass(TextOutputFormat.class);   

      //FileInputFormat.addInputPath(job, new Path(args[0]));
      //FileOutputFormat.setOutputPath(job, new Path(args[1]));
      
      FileInputFormat.addInputPath(job,new Path("input"));
      FileOutputFormat.setOutputPath(job, new Path("output"));
      
      FileSystem file_out = FileSystem.newInstance(getConf()); 

		if (file_out.exists(new Path("output"))) {      // check if there already is an output file
			file_out.delete(new Path("output"), true);  // if yes deletes it automatically to avoid manual deletion... 
		}
      
      job.waitForCompletion(true);
      
      // System.out.println("Number of Comparisons : " + Reduce.numberOfComparisons);
      
      return 0;
   }
   
   public static class Map extends Mapper<LongWritable, Text, Text, IntWritable> {
	   private final static IntWritable ONE = new IntWritable(1);
           
      /*
       * The map function takes as input a line of the document, i.e. a tweet
       * It outputs each word pairs as key and 1 as value
       */
	   
      @Override
      public void map(LongWritable key, Text value, Context context)
              throws IOException, InterruptedException {
    	  
    	  String[] tweet = value.toString().split(" ");
    	  
    	  for (int i = 0; i < tweet.length - 1; ++i) {
    		  for  (int j = i + 1; j < tweet.length - 1; ++j) {
    			  if (tweet[0] != " " & tweet[1] != " ") {
    				  TreeSet<String> pair = new TreeSet<String>();
        			  pair.add(tweet[0].replaceAll("[^A-Za-z0-9]","").toLowerCase(Locale.ENGLISH));
        			  pair.add(tweet[1].replaceAll("[^A-Za-z0-9]","").toLowerCase(Locale.ENGLISH));
        			  context.write(new Text(pair.first() + "," + pair.last()), ONE);
    			  }		  
    		  }
    	  }    	      	     
      }
   }         
   
   public static class Reduce extends Reducer<Text, IntWritable, Text, IntWritable> { 	  
	  
	  /*
	   * The reducer counts each pair of words overall
	   */
	   
      @Override
      public void reduce(Text key, Iterable<IntWritable> values, Context context)
              throws IOException, InterruptedException {
    	  int sum = 0;
    	  for (IntWritable val: values) {
    		  sum += val.get();
    	  }
    	  
    	  if (sum > 10) {
    		  context.write(key, new IntWritable(sum));
        	  System.out.println(key);
    	  }	  
      }
   }
}