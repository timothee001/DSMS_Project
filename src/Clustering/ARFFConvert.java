package Clustering;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;

public class ARFFConvert {
	
	public static void ARFFWriteFile(Connection con, String dbName)
	
			
	
		    throws SQLException, IOException {
		    BufferedWriter writer = null;
		    writer = new BufferedWriter(new FileWriter("ARFFTweets.arff"));
		    Statement stmt = null;
		    String query = "select text " +
		                   
		                   "from " + dbName;
		    
		    //We respect the ARFF Syntax in order to be readable by the library
		    try {
		        stmt = con.createStatement();
		        ResultSet rs = stmt.executeQuery(query);
		        
		        //The prefixe takes in account all alphabet and numbers
		        
		        writer.write("@relation tweet\n"
		        		+ "@attribute '0' numeric\n"
		        		+ "@attribute '1' numeric\n"
		        		+ "@attribute '2' numeric\n"
		        		+ "@attribute '3' numeric\n"
		        		+ "@attribute '4' numeric\n"
		        		+ "@attribute '5' numeric\n"
		        		+ "@attribute '6' numeric\n"
		        		+ "@attribute '7' numeric\n"
		        		+ "@attribute '8' numeric\n"
		        		+ "@attribute '9' numeric\n"
		        		+ "@attribute 'a' numeric\n"
		        		+ "@attribute 'b' numeric\n"
		        		+ "@attribute 'c' numeric\n"
		        		+ "@attribute 'd' numeric\n"
		        		+ "@attribute 'e' numeric\n"
		        		+ "@attribute 'f' numeric\n"
		        		+ "@attribute 'g' numeric\n"
		        		+ "@attribute 'h' numeric\n"
		        		+ "@attribute 'i' numeric\n"
		        		+ "@attribute 'j' numeric\n"
		        		+ "@attribute 'k' numeric\n"
		        		+ "@attribute 'l' numeric\n"
		        		+ "@attribute 'm' numeric\n"
		        		+ "@attribute 'n' numeric\n"
		        		+ "@attribute 'o' numeric\n"
		        		+ "@attribute 'p' numeric\n"
		        		+ "@attribute 'q' numeric\n"
		        		+ "@attribute 'r' numeric\n"
		        		+ "@attribute 's' numeric\n"
		        		+ "@attribute 't' numeric\n"
		        		+ "@attribute 'u' numeric\n"
		        		+ "@attribute 'v' numeric\n"
		        		+ "@attribute 'w' numeric\n"
		        		+ "@attribute 'x' numeric\n"
		        		+ "@attribute 'y' numeric\n"
		        		+ "@attribute 'z' numeric\n"
		        		+ "@data\n");
		       
		        //this part of the code compute for every text of the tweets the number of occurence of each characters. We lower case everything
		        //to have the right count
		        while (rs.next()) {
		            String text = rs.getString("text");
		            if(!text.isEmpty()||!text.equals("\n"))
		            	System.out.println(text);  		         
		            try {
		            	
		            	TreeMap<String,Integer> m = new TreeMap<String,Integer>();
		            	
		            	for(char al = 'a'; al <='z';al++){
		            		m.put(""+al, 0);
		            	}
		            	
		            	for(int n = 0; n <=9;n++){
		            		m.put(""+n, 0);
		            	}
		            	
		            	for(int i =0;i<text.length();i++){
		            		
		            		String c = (""+text.charAt(i)).toLowerCase();
		            		if(m.containsKey(c)){
		            			
		            			m.put(c, m.get(c)+1);
		            			
		            		}
		            		
		            	}
		            	
		            	//System.out.println(m);
		            	
		            	String toWrite="";
		            	for(Entry<String, Integer> entry : m.entrySet()) {
		            		  String key = entry.getKey();
		            		  Integer value = entry.getValue();
		            		  toWrite+=value+",";
		            		  
		            		  //System.out.println(key + " => " + value);
		            		}	           
		                writer.write(toWrite.substring(0, toWrite.length()-1)+"\n");
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

	public static void main(String[] args) throws SQLException, IOException {
		// TODO Auto-generated method stub
		
		Connection conn=  DriverManager.getConnection("jdbc:"
					+ "mysql://localhost/twitter?useUnicode=yes&characterEncoding=UTF8&"
					+ "user=root&password=ilovemysql");
			 
		ARFFWriteFile(conn,"twitter.tweet");

	}

}
