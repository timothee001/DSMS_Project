package twitterExtractor;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.cybozu.labs.langdetect.DetectorFactory;
import com.cybozu.labs.langdetect.LangDetectException;

import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class FillDatabase {

	
	public static void writeTableOnFile(Connection con, String dbName)
			
		
		    throws SQLException, IOException {
		    BufferedWriter writer = null;
		    writer = new BufferedWriter(new FileWriter("output.txt"));
		    Statement stmt = null;
		    String query = "select text " +
		                   
		                   "from " + dbName;
		    try {
		        stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        while (rs.next()) {
		            String text = rs.getString("text");
		        
		            if(!text.isEmpty())
		            	System.out.println(text);
		            		         
		            try {
		                writer.write(text+"\n");
		            } catch (Exception e) {
		                e.printStackTrace();
		            } 
		            
		               
		        }
		    } catch (SQLException e ) {
		    	 System.out.println(e);
		    } finally {
		        if (stmt != null) { stmt.close(); }
		        try {
                    // Close the writer regardless of what happens...
                    writer.close();
                } catch (Exception e) {
                }
		    }
		}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
				 
		 
		Connection conn;
		
		try{	
			 conn = DriverManager.getConnection("jdbc:"
					+ "mysql://localhost/twitter?useUnicode=yes&characterEncoding=UTF8&"
					+ "user=root&password=ilovemysql");
			
			 //viewTable(conn,"twitter.tweet");	 
				ConfigurationBuilder cb = new ConfigurationBuilder();  
				
				
				cb.setDebugEnabled(true)
						.setOAuthConsumerKey("basXIvROD3J1BSAvWOWwxm2Wi")
						.setOAuthConsumerSecret("C1mApGdB2ZDbQzNZ7Sfsj4N6vX5CSPBXGtqAsLh6Q55TlQA0CE")
						.setOAuthAccessToken("109226669-77QGoI2Cqoir71GWJKRlq8RJEAqRC628FARdJ897")
						.setOAuthAccessTokenSecret("0I6DRWcmbz2oF76Socmg6OedRUoar6u0FcD9MtJaceK0j"); 
				 
				 TwitterStream twittFactory = new TwitterStreamFactory(cb.build()).getInstance();  
			
				 TweetListener2 list = new TweetListener2(conn);  
				 twittFactory.addListener(list);  
				 twittFactory.sample();
		}
		catch(Exception e){
			System.out.println(e.toString());
		}
		
					
		
		
	}

}
