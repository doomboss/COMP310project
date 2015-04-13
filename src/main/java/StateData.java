
public class StateData {
	int total = 0;
	int numOfTweets;

	public StateData(int valueToAdd){
		this.total = this.total+valueToAdd;
		numOfTweets = 1;
	}
	
	public void addData(int valueToAdd){
		this.total = this.total+valueToAdd;
		numOfTweets++;
	}
	
	public int getAverage(){
		return this.total/this.numOfTweets;
	}

}
