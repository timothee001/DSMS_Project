import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.ConfigurationBuilder;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Salut");
		
		ConfigurationBuilder cb = new ConfigurationBuilder(); 
		cb.setDebugEnabled(true).setOAuthConsumerKey("basXIvROD3J1BSAvWOWwxm2Wi")
		.setOAuthConsumerSecret("C1mApGdB2ZDbQzNZ7Sfsj4N6vX5CSPBXGtqAsLh6Q55TlQA0CE")
		.setOAuthAccessToken("109226669-77QGoI2Cqoir71GWJKRlq8RJEAqRC628FARdJ897")
		.setOAuthAccessTokenSecret("0I6DRWcmbz2oF76Socmg6OedRUoar6u0FcD9MtJaceK0j"); 
		
		TwitterStream twittFactory = new TwitterStreamFactory(cb.build()).getInstance(); 
		TweetListener2 list = new TweetListener2(conn); twittFactory.addListener(list); twittFactory.sample();
		
		
	}

}
