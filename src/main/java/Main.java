import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.Map;


import twitter4j.TwitterException;

class Main {
	public static void main(String[] args) throws TwitterException, IOException, InterruptedException {
		//build dictionary
		SentimentDictionary dictionary = new SentimentDictionary();
		System.out.println("Dictionary built. Entries contained: " + dictionary.getDictionary().size() );
		
		//grab twitter data from stream
		System.out.println("[About to start Sreaming]");
		Streaming tStream = new Streaming("I",10);
		Thread thread = new Thread(tStream);
		thread.start();
//		ArrayList<TwitterData> twitterDataCollection = tStream.run();
		ArrayList<TwitterData> twitterDataCollection = tStream.getTwitterDataCollection();
		
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
		
		
		
		//
		printData(twitterDataCollection);
		
			
	}
	
	public static void printData(ArrayList<TwitterData> twitterDataCollection){
		System.out.println("-+-+-+- AVERAGES -+-+-+-");
		//Compiling averages
		String [] states = new String[] {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC",
				"DL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME",
				"MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "HV", "NH", "NJ",
				"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD",
				"TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
		
		HashMap<String,StateData> stateAverageCollection = new HashMap<String,StateData>();
		for (int i = 0; i < twitterDataCollection.size(); i++) {
			TwitterData tempData = twitterDataCollection.get(i);
			String tempLocation = tempData.getLocation() ;
			if (Arrays.asList(states).contains(tempLocation)){
				if (stateAverageCollection.containsKey( tempLocation ) ){
					StateData temp = stateAverageCollection.get(tempLocation );
					temp.addData(tempData.getOpinion() );
					stateAverageCollection.replace(tempLocation , temp);
				}else{
					stateAverageCollection.put(tempLocation, new StateData(tempData.getOpinion() ));	
				}
			}
		}
		for (int i = 0; i < states.length; i++){
			if(stateAverageCollection.containsKey(states[i]) ){
				StateData temp = stateAverageCollection.get(states[i]);
				System.out.println(states[i]+": " + temp.getAverage() );
			}else{
				System.out.println(states[i]+": No Data For This State" );
			}
		}
	}
		
		
		
		
}