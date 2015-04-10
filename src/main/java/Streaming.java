import java.util.ArrayList;
import java.util.List;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Streaming {
	private final static String CONSUMER_KEY ="piFIsLBu5EcjojzcVDacX1RzI";
	private final static String CONSUMER_SECRET ="3k7ODgDWNuDh2ausIqr00XCSoGAn3Aq3l23rv8iPTjSSjAtsQa";
	private final static String ACCESS_KEY ="96220631-wcBzyV6XutakQCo2EwnlIY0Ag5aVR5ofYSPgaKQme";
	private final static String ACCESS_SECRET ="nsKDhCMEky76NO2sIf91oRZbWdZHloPgyTQjWK5F27h09";
	private ArrayList<TwitterData> twitterDataCollection = new ArrayList<TwitterData>();
	private String keyword;
	private int interval;
	
	public Streaming(String keyword, int interval){
		super();
		this.keyword = keyword;
		this.interval = interval;
	}
	
	
	public ArrayList<TwitterData> run() throws TwitterException {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_SECRET)
		  .setOAuthAccessToken(ACCESS_KEY)
		  .setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build() ).getInstance();
		
		ArrayList<TwitterData> twitterDataCollection = new ArrayList<TwitterData>();
		
		StatusListener listener = new StatusListener() {
	
			public void onStatus(Status status) {
				if (status.getGeoLocation() != null){
					twitterDataCollection.add(new TwitterData(status.getText(), placeToString(status.getPlace() ) ));
					System.out.println(status.getText() + ", STATE= " + placeToString(status.getPlace() ) );
				}
			}
		
			public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {
				System.out.println("Got a status deletion notice id:" + statusDeletionNotice.getStatusId());
			}
		
			 public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				 System.out.println("Got track limitation notice:" + numberOfLimitedStatuses);
			 }
		
			 public void onScrubGeo(long userId, long upToStatusId) {
				 	System.out.println("Got scrub_geo event userId:" + userId + " upToStatusId:" + upToStatusId);
			 }
		
			 public void onStallWarning(StallWarning warning) {
				 System.out.println("Got stall warning:" + warning);
			 }
		
			 public void onException(Exception ex) {
				 ex.printStackTrace();
			 }
			 
		 };
		 
		FilterQuery filter = new FilterQuery();
		String[] keywordsArray = { keyword };
		String[] language = { "en" };
		filter.language(language);
		filter.track(keywordsArray);
		twitterStream.addListener(listener);
		
		twitterStream.filter(filter);
		
		while (twitterDataCollection.size() < interval ){
			//makes sure we have enough stuff first. This might be poor practice.\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
			
		}
		twitterStream.shutdown();
		return twitterDataCollection;
		 
	}
	
	private String placeToString(Place place){
		String [] split = new String [1];
		String fullName = place.getFullName();
		split = fullName.split(",");
		return split[1].trim();
	}

}

