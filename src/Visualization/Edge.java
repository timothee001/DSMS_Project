package Visualization;


//Implementing Comparable interface allows us to sort the edges
public class Edge implements Comparable<Edge>{

	Integer count;
	String first;
	String second;


	@Override
	public int compareTo(Edge o) {
		// TODO Auto-generated method stub
		
		return this.count>o.count?1:-1;
	}
	
	public String toString(){
		return '['+first+';'+second+';'+count+']';
	}
	
	
}
