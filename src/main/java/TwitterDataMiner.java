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
			tdm.searchKeyword(twitter, "obama");
			System.out.println("");
			tdm.searchKeyword(twitter, "obama");
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
	    		statuses.get(1).getText() );
	    		
	    
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
		
		//determine opinion of tweet
		public int getValueOfTweet(String tweet){
			String[] words = getThreeWords(tweet);
			int val = 0;
			
			//determine if verb is pro or against
			/*if (words[0].in(goodWords){
				val = 1;
			}else if (words[0].in(badWords){
				val = -1;
			}else{
				return 0;
			}
			
			//determine if adverb negates verb
			if ( words[1].in(negatingTerms){
				if (val == 1){
					val = -1;
				}else{
					val = 1;
				}
			}
			
			//determine if modifier negates verb
			if ( words[2].in(negatingTerms){
				if (val == 1){
					val = -1;
				}else{
					val = 1;
				}
			}	
			*/
			
			return val;
		}
		
		//gets the noun,verb,and adverb of specific tweet
		public String [] getThreeWords (String keyword, String tweet){
			String[] words = new String[2];
			
			int keywordLoc = tweet.indexOf(keyword);
			int verbLoc = tweet.lastIndexOf( " ", keywordLoc);
			words[0] =tweet.substring(verbLoc+1, keywordLoc -1);
			
			int adverbLoc = tweet.lastIndexOf( " ", verbLoc);
			words[1] = tweet.substring(adverbLoc+1, verbLoc-1);
			
			int modifierLoc = tweet.indexOf(" ", adverbLoc);
			words[2] = tweet.substring(modifierLoc+1,adverbLoc-1);
			
			return words;
		}
	
	
	
}
