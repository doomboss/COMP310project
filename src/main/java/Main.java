import java.io.IOException;
import java.util.ArrayList;

import twitter4j.TwitterException;

class Main {
	public static void main(String[] args) throws TwitterException, IOException {
		//build dictionary
		SentimentDictionary dictionary = new SentimentDictionary(); //I removed the hardcoding path
		System.out.println("Dictionary built:" + dictionary.getDictionary().size() );
		
		//grab twitter data from stream
		System.out.println("[About to start Sreaming]");
		Streaming tStream = new Streaming("I",100);
		ArrayList<TwitterData> twitterDataCollection = tStream.run();
		TweetsDatabase td = new TweetsDatabase();
		td.setTweets(twitterDataCollection);
		
		//generate opinions for each twitter element
		System.out.println("[About to generate oppinions]");
		TwitterData tempData = null;
		for (int i = 0; i < twitterDataCollection.size(); i++) {
			tempData = twitterDataCollection.get(i);
			tempData.generateOpinion(dictionary);
			twitterDataCollection.set(i, tempData);
		}
		
		//testing
		for (int i = 0; i < twitterDataCollection.size(); i++) {
			tempData = twitterDataCollection.get(i);
			System.out.println(tempData.getLocation() + ": " + tempData.getOpinion() );
		}
		
		
		
	}
}
