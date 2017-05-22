package Clustering;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Enumeration;

import weka.clusterers.SimpleKMeans;
import weka.core.DistanceFunction;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.ManhattanDistance;
import weka.core.Option;
import weka.core.neighboursearch.PerformanceStats;
 
public class Cluster {
 
	public static BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
 
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
 
		return inputReader;
	}
 
	public static void main(String[] args) throws Exception {
		
		int clustNumber = 10;
		SimpleKMeans kmeans = new SimpleKMeans();
		ManhattanDistance m = new ManhattanDistance();
		kmeans.setDistanceFunction(m);
		
 
		kmeans.setSeed(10);
 
		//important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(clustNumber);
 
		BufferedReader datafile = readDataFile("C://Users//Timothee//workspace//DSMS//ARFFTweets.arff"); 
		Instances data = new Instances(datafile);

		kmeans.buildClusterer(data);
 
		// This array returns the cluster number (starting with 0) for each instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
 
		int i=0;
		
		 BufferedWriter writer = null;
		    writer = new BufferedWriter(new FileWriter("Kmean "+clustNumber+" clusters.txt"));
		
		for(int clusterNum : assignments) {
			 writer.write("Tweet "+i+" -> Cluster "+clusterNum+" \n");
		   
		    i++;
		}
	
        try {
            // Close the writer regardless of what happens...
            writer.close();
        } catch (Exception e) {
        }
		
		
		
		
		
	}
}