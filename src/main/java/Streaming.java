import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Streaming{
	private final static String CONSUMER_KEY ="piFIsLBu5EcjojzcVDacX1RzI";
	private final static String CONSUMER_SECRET ="3k7ODgDWNuDh2ausIqr00XCSoGAn3Aq3l23rv8iPTjSSjAtsQa";
	private final static String ACCESS_KEY ="96220631-wcBzyV6XutakQCo2EwnlIY0Ag5aVR5ofYSPgaKQme";
	private final static String ACCESS_SECRET ="nsKDhCMEky76NO2sIf91oRZbWdZHloPgyTQjWK5F27h09";
	static int hasLoc = 0;
	static int dontHazLoc = 0;
	static double percent = 0;
	private String keyword;
	private int interval;
	
	public Streaming(String keyword, int interval){
		super();
		this.keyword = keyword;
		this.interval = interval;
	}
	

	public static void main(String[] args) throws TwitterException {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_SECRET)
		  .setOAuthAccessToken(ACCESS_KEY)
		  .setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterStream twitterStream = new TwitterStreamFactory(cb.build() ).getInstance();
		
		
		
		StatusListener listener = new StatusListener() {
	
			public void onStatus(Status status) {
				if (status.getGeoLocation() != null){
					hasLoc++;
					System.out.println("@" + status.getUser().getScreenName() + " - " + status.getText() + status.getGeoLocation() + status.getCreatedAt() );
				}else{
					dontHazLoc++;
				}
				percent = ((double) hasLoc/ (double) dontHazLoc)*100;
				System.out.println(hasLoc + " / " + dontHazLoc + ":" + percent + "%");
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
		String[] keywordsArray = { "a" };
		String[] language = { "en" };
		filter.language(language);
		filter.track(keywordsArray);
		twitterStream.addListener(listener);
		
		twitterStream.filter(filter);
		 
	}

}

