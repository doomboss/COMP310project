
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.*;

public class obamaTwitter {
	private final static String CONSUMER_KEY ="zU7A7rt17hmI1XuKTOXzcO0dN";
	private final static String CONSUMER_SECRET ="nCo01oLGJRZLhdp3PiOG4LjXKOwFBXIBK1MK91zs6qRRXyVnMQ";
	private final static String ACCESS_KEY ="96220631-FrkuXYCZxV9hO56KPfblTkZ9fDRVWwrvhcRpkMuBD";
	private final static String ACCESS_SECRET ="Q7sRFn8ApN3xRi9fsQQkoCB9bwtFD88Ldhpvo9ZR9qoM5";
	
	
	public static void main(String[] args) {
        Twitter twitter = auth();
    }
	
	public static Twitter auth(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_SECRET)
		  .setOAuthAccessToken(ACCESS_KEY)
		  .setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterFactory tf = new TwitterFactory( cb.build() );
		Twitter twitter = tf.getInstance();
	    
	    try {
			Query query = new Query("Obama");
			QueryResult result = twitter.search(query);
			for (Status status : result.getTweets()) {
			    System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	    
	    return twitter;
	}
	
}
