import twitter4j.GeoLocation;

public class TwitterData {
	private int opinion;
	private String location;
	private String tweet;
	
	public TwitterData(int opinion, String tweet, String location){
		this.opinion = opinion;
		this.tweet = tweet;
		this.location = location;
	}
	
	public TwitterData(String tweet, String location){
		this.tweet = tweet;
		this.location = location;
		this.generateOpinion();
	}
	
	public void generateOpinion(){
		//TODO: generate opinion
	}
	
	public int getOpinion(){
		return this.opinion;
	}
	
	public GeoLocation getLocation(){
		return this.location;
	}
	
	public String getTweet(){
		return this.tweet;
	}
	
}
