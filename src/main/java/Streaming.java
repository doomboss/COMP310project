import java.util.ArrayList;

import javax.swing.JTextArea;

import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Streaming implements Runnable{
	private final static String CONSUMER_KEY ="piFIsLBu5EcjojzcVDacX1RzI";
	private final static String CONSUMER_SECRET ="3k7ODgDWNuDh2ausIqr00XCSoGAn3Aq3l23rv8iPTjSSjAtsQa";
	private final static String ACCESS_KEY ="96220631-wcBzyV6XutakQCo2EwnlIY0Ag5aVR5ofYSPgaKQme";
	private final static String ACCESS_SECRET ="nsKDhCMEky76NO2sIf91oRZbWdZHloPgyTQjWK5F27h09";
	private ArrayList<TwitterData> twitterDataCollection = new ArrayList<TwitterData>();
	public ArrayList<TwitterData> getTwitterDataCollection() {
		return twitterDataCollection;
	}


	private String keyword;
	private int interval;
	private final Object lock = new Object();
	private JTextArea output;
	
	public Streaming(String keyword, int interval){
		super();
		this.keyword = keyword;
		this.interval = interval;
	}
	
	protected void setOutput(JTextArea output){
		this.output = output;
	}
	
	
	
	public void run() {
		
		//configure
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
					twitterDataCollection.add(new TwitterData(status.getText(), placeToString(status.getPlace() ) ));
					System.out.println(status.getText() + ", STATE= " + placeToString(status.getPlace() ) );
					output.append(status.getText() + ", STATE= " + placeToString(status.getPlace() ) +"\n");
					if (twitterDataCollection.size() > interval) {
				          synchronized (lock) {
				            lock.notify();
				          }
				          System.out.println("unlocked");
				        }
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
		try {
		      synchronized (lock) {
		        lock.wait();
		      }
		    } catch (InterruptedException e) {
		      // TODO Auto-generated catch block
		      e.printStackTrace();
		    }
		
		twitterStream.shutdown();
		
		
		
		 
	}
	}
	
	private String placeToString(Place place){
		String [] split = new String [1];
		String fullName = place.getFullName();
		split = fullName.split(",");
		try {
			return split[1].trim();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return split[0];
		}
	}

}

