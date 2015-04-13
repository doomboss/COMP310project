import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DataHandler {
	private ArrayList<TwitterData> twitterDataCollection = null;
	private final String [] states = new String[] {"AL", "AK", "AZ", "AR", "CA", "CO", "CT", "DE", "DC",
			"DL", "GA", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA", "ME",
			"MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "HV", "NH", "NJ",
			"NM", "NY", "NC", "ND", "OH", "OK", "OR", "PA", "RI", "SC", "SD",
			"TN", "TX", "UT", "VT", "VA", "WA", "WV", "WI", "WY"};
	
	public  DataHandler (ArrayList<TwitterData> twitterDataCollection) throws IOException{
		this.twitterDataCollection = twitterDataCollection;	
	}
	
	public ArrayList<TwitterData> generateOpinnions(ArrayList<TwitterData> twitterDataCollection) throws IOException{
		//build dictionary
		SentimentDictionary dictionary = new SentimentDictionary();
		//generate opinions
		TwitterData tempData = null;
		for (int i = 0; i < twitterDataCollection.size(); i++) {
			tempData = twitterDataCollection.get(i);
			tempData.generateOpinion(dictionary);
			twitterDataCollection.set(i, tempData);
		}
		return twitterDataCollection;
	}
	
	public HashMap<String,StateData> generateStateAverageCollection(){
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
		return stateAverageCollection;
	}
	
	public void printData() throws IOException{
		twitterDataCollection = generateOpinnions(twitterDataCollection);
		HashMap<String,StateData> stateAverageCollection = generateStateAverageCollection();
		
		for (int i = 0; i < states.length; i++){
			if(stateAverageCollection.containsKey(states[i]) ){
				StateData temp = stateAverageCollection.get(states[i]);
				System.out.println(states[i]+": " + temp.getAverage() );
			}else{
				System.out.println(states[i]+": No Data For This State" );
			}
		}
	}
	
	public String compileDataAsString () throws IOException{
		String data = "";
		twitterDataCollection = generateOpinnions(twitterDataCollection);
		HashMap<String,StateData> stateAverageCollection = generateStateAverageCollection();
		
		for (int i = 0; i < states.length; i++){
			if(stateAverageCollection.containsKey(states[i]) ){
				StateData temp = stateAverageCollection.get(states[i]);
				data = data.concat(states[i]+": " + temp.getAverage() + " \n" );
			}else{
				data = data.concat(states[i]+": No Data For This State \n" );
			}
		}
		
		return data;
	}
				
}