
import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.*;

public class TwitterDataMiner {
	private final static String CONSUMER_KEY ="piFIsLBu5EcjojzcVDacX1RzI";
	private final static String CONSUMER_SECRET ="3k7ODgDWNuDh2ausIqr00XCSoGAn3Aq3l23rv8iPTjSSjAtsQa";
	private final static String ACCESS_KEY ="96220631-wcBzyV6XutakQCo2EwnlIY0Ag5aVR5ofYSPgaKQme";
	private final static String ACCESS_SECRET ="nsKDhCMEky76NO2sIf91oRZbWdZHloPgyTQjWK5F27h09";
	
	
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
