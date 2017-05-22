package Visualization;
import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeSet;

import javax.swing.JFrame;

import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationImageServer;

public class CircleViz {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		
		//We read and load the tweets we obtained in the hadoop job
		TreeSet<Edge> allCouplesSort = new TreeSet<Edge>();		
		BufferedReader br = new BufferedReader(new FileReader("inputGraph.txt"));
		try {
		    StringBuilder sb = new StringBuilder();
		    String line = br.readLine();
		    while (line != null) {
		        sb.append(line);
		        sb.append(System.lineSeparator());
		        line = br.readLine();		  
		        if(line!=null){
		        	 
				        String [] tab = line.split(",");
				        Edge c= new Edge();
				        c.first=tab[0];
				        c.second = tab[1];
				        c.count = Integer.parseInt(tab[2]);
				       //System.out.println(c);
				        allCouplesSort.add(c);
		        }
		      
		        
		    }
		    String everything = sb.toString();
		   
		} finally {
		    br.close();
		}
			
		
		UndirectedSparseGraph<String,String> g = new UndirectedSparseGraph<String,String>();
	    
		
		for(int i =0;i<allCouplesSort.size();i++){
			
	    		Edge c=allCouplesSort.pollLast();
	    		g.addEdge(c.first+"*"+c.second, c.first,c.second);
	    	
		}
		
		
		System.out.println(g.getVertices().toArray()[3]);
	    

	      
	    
	    VisualizationImageServer vs =
	      new VisualizationImageServer(
	        new CircleLayout(g), new Dimension(2000, 2000));
	 
	     JFrame frame = new JFrame();
	    frame.getContentPane().add(vs);
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.pack();
	    frame.setVisible(true);
		
		
		
	}

}
