import java.util.HashMap;

public class TwitterData {
	private int opinion;
	private String location;
	private String tweet;
	final int NUMOFWORDS = 12;// how many words it looks through in tweet. affects speed of program
	
	public TwitterData(int opinion, String tweet, String location){
		this.opinion = opinion;
		this.tweet = tweet;
		this.location = location;
	}
	
	public TwitterData(String tweet, String location){
		this.tweet = tweet;
		this.location = location;
	}
	
	public void generateOpinion(SentimentDictionary sentimentdictionary){
		HashMap<String, Integer> dictionary  = sentimentdictionary.getDictionary();
		int count = 0;
		int total = 0;
		String [] wordsFromTweet = new String [NUMOFWORDS];
		wordsFromTweet = tweet.split(" ", NUMOFWORDS);
		for(int i = 0; i < wordsFromTweet.length; i++){
			if (wordsFromTweet[i] != null){
				if (dictionary.containsKey(wordsFromTweet[i])){
					count++;
					total = total + dictionary.get(wordsFromTweet[i]);
				}
			}else{
				break;
			}
		}
		
		if (count > 0){
			this.opinion = total/count;
		}else{
			this.opinion = 0;
		}
	}
	
	public int getOpinion(){
		return this.opinion;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public String getTweet(){
		return this.tweet;
	}
	
}
