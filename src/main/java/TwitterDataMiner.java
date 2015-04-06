<<<<<<< HEAD

import twitter4j.*;
import twitter4j.conf.*;

public class TwitterDataMiner {
	private final static String CONSUMER_KEY ="piFIsLBu5EcjojzcVDacX1RzI";
	private final static String CONSUMER_SECRET ="3k7ODgDWNuDh2ausIqr00XCSoGAn3Aq3l23rv8iPTjSSjAtsQa";
	private final static String ACCESS_KEY ="96220631-wcBzyV6XutakQCo2EwnlIY0Ag5aVR5ofYSPgaKQme";
	private final static String ACCESS_SECRET ="nsKDhCMEky76NO2sIf91oRZbWdZHloPgyTQjWK5F27h09";
	private static String keyword;//"noun"
	
	
	public static void main(String[] args) {
        Twitter twitter = auth();
        keyword = getNoun();
        
    }
	
	//returns authenticated twitter instance for use
	public static Twitter auth(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true)
		  .setOAuthConsumerKey(CONSUMER_KEY)
		  .setOAuthConsumerSecret(CONSUMER_SECRET)
		  .setOAuthAccessToken(ACCESS_KEY)
		  .setOAuthAccessTokenSecret(ACCESS_SECRET);
		TwitterFactory tf = new TwitterFactory( cb.build() );
		Twitter twitter = tf.getInstance();
	    return twitter;
	}
	public static String getNoun(){
		//TODO: retrieves keyword from user
		return "";
	}	
	
	public static QueryResult DoQuery(Twitter twitter){
		try {
			Query query = new Query("Obama");
			QueryResult result = twitter.search(query);
			return result;
		} catch (TwitterException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static TwitterData[] getTwitterData(QueryResult results){
		//TODO:convert query into array of twitterData
		return null;
	}
	
	public static int StringToOpinion(String tweet,String keyWord){
		//TODO: takes string and keyword and determine opinion of it
		return 0;
	}
		
}
=======

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import twitter4j.*;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.*;

public class TwitterDataMiner {
	private final static String CONSUMER_KEY ="piFIsLBu5EcjojzcVDacX1RzI";
	private final static String CONSUMER_SECRET ="3k7ODgDWNuDh2ausIqr00XCSoGAn3Aq3l23rv8iPTjSSjAtsQa";
	private final static String ACCESS_KEY ="96220631-wcBzyV6XutakQCo2EwnlIY0Ag5aVR5ofYSPgaKQme";
	private final static String ACCESS_SECRET ="nsKDhCMEky76NO2sIf91oRZbWdZHloPgyTQjWK5F27h09";
	
	
	public static void main(String[] args){
		TwitterDataMiner tdm = new TwitterDataMiner();
		try{
			Twitter twitter = tdm.auth();
//			tdm.searchKeyword(twitter, "obama");
			tdm.getCommonWord(twitter, "obama");
		}
		catch(IOException ioe){
			ioe.printStackTrace();
		}
		catch(TwitterException te){
			te.printStackTrace();
		}
    }
	
	public Twitter auth() throws IOException, TwitterException{
		
		//a way to read from twitter4j.properties manually
//		Properties prop = new Properties();
//		String propFileName = "twitter4j.properties";
//		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
//		if (inputStream != null) {
//			prop.load(inputStream);
//		} else {
//			throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
//		}
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		//uncomment this to use the key inside code 
//		cb.setDebugEnabled(true)
//		  .setOAuthConsumerKey(CONSUMER_KEY)
//		  .setOAuthConsumerSecret(CONSUMER_SECRET)
//		  .setOAuthAccessToken(ACCESS_KEY)
//		  .setOAuthAccessTokenSecret(ACCESS_SECRET);
		
		//read the key from properties file, doesnt seem to be needed and the application handle it automatically...
//		cb.setDebugEnabled(true)
//		  .setOAuthConsumerKey(prop.getProperty("oauth.consumerKey"))
//		  .setOAuthConsumerSecret(prop.getProperty("oauth.consumerSecret"))
//		  .setOAuthAccessToken(prop.getProperty("oauth.accessToken"))
//		  .setOAuthAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret"));
		
		
		
		TwitterFactory tf = new TwitterFactory( cb.build() );
		Twitter twitter = tf.getInstance();
		
		//getting the latest tweets from user's home timeline, just to see if everything is working correctly...
		List<Status> statuses = twitter.getHomeTimeline();
	    System.out.println("Showing home timeline.");
	    System.out.println("@"+statuses.get(1).getUser() + ":" +
	    		statuses.get(1).getText());
	    
//	    for (Status status : statuses) {
//	        System.out.println("@"+status.getUser().getName() + ":" +
//	                           status.getText());
//	    }
	    
	    
	    return twitter;
	}
		
	
		//a separate method to use the Twitter element to search a specific word, and print it out without storing anything
		public void searchKeyword(Twitter twitter, String keyword) throws TwitterException{
			
		    Query query = new Query(keyword);
			QueryResult result = twitter.search(query);
				
			for (Status status : result.getTweets()) {
				if(status.getLang().contains("en"))
					System.out.println("@" + status.getUser().getScreenName() + ":" + status.getText());
//			    System.out.println("Language: "+status.getLang());
			}
			
		}
		
		//get all the words that involves a specific word and put it into hashmap...
		public void getCommonWord(Twitter twitter, String keyword) throws TwitterException{
			
			CommonWordDatabase cwd = new CommonWordDatabase();
			
			Query query = new Query(keyword);
			QueryResult result = twitter.search(query);
				
			for (Status status : result.getTweets()) {
				if(status.getLang().contains("en")){
					System.out.println(status.getText());
					for(String tmp : status.getText().split("\\s")){
						if(!tmp.contains("@") && !tmp.contains("http")){
//							System.out.println(tmp.replaceAll("[<>\\[\\],|\":.-]\t", ""));
							//replaceAll("[^a-zA-Z0-9'#\\t\\n\\s]", "") method get rid of non letter and numbers, also tap and enter and spaces
							cwd.add(tmp.replaceAll("[^a-zA-Z0-9'#\\t\\n\\s]", ""));
							System.out.println(tmp.replaceAll("[^a-zA-Z0-9#\\t\\n\\s]", ""));

							//example of replace all: replaceAll("[<>\\[\\],-]", "");
							//replace everything beside letters and numbers: replace(/[^\w\s!?]/g,'');
						}
					}
				}
						
//			    System.out.println("Language: "+status.getLang());
			}
			try{
				cwd.saveWordsData();
			}
			catch(IOException ioe){
				ioe.printStackTrace();
			}
//			cwd.printAll();
			
		}
	
	
	
}
>>>>>>> 270c2d7a66e8598bacb9abf6ec5d828d8858b69b
