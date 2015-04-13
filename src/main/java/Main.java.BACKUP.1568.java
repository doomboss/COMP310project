import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import twitter4j.TwitterException;

class Main {
	public static void main(String[] args) throws TwitterException, IOException {
		//build dictionary
		SentimentDictionary dictionary = new SentimentDictionary("C:\\Users\\chevalierc\\git\\Project 310\\COMP310project\\src\\main\\java\\dictionary\\sentiments.csv");
		
		//grab twitter data from stream
		Streaming tStream = new Streaming("I",10);
		ArrayList<TwitterData> twitterDataCollection = tStream.run();
		
		//generate opinions for each twitter element
		twitterDataCollection = generateOpinnions(twitterDataCollection, dictionary);
		
		//print averages of each state
		printData(twitterDataCollection);	
	}
	
	public static ArrayList<TwitterData> generateOpinnions(ArrayList<TwitterData> twitterDataCollection,SentimentDictionary dictionary){
		System.out.println("[About to generate oppinions]");
		TwitterData tempData = null;
		for (int i = 0; i < twitterDataCollection.size(); i++) {
			tempData = twitterDataCollection.get(i);
			tempData.generateOpinion(dictionary);
			twitterDataCollection.set(i, tempData);
		}
		return twitterDataCollection;
	}
	
	public static void printData(ArrayList<TwitterData> twitterDataCollection){
		System.out.println("");
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