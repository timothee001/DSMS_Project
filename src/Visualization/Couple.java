package Visualization;

public class Couple implements Comparable<Couple>{

	Integer count;
	String first;
	String second;


	@Override
	public int compareTo(Couple o) {
		// TODO Auto-generated method stub
		
		return this.count>o.count?1:-1;
	}
	
	public String toString(){
		return '['+first+';'+second+';'+count+']';
	}
	
	
}
