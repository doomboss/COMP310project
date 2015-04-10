import java.util.ArrayList;
import twitter4j.TwitterException;

class Main {
	public static void main(String[] args) throws TwitterException {
		Streaming tStream = new Streaming("obama",100);
		ArrayList<TwitterData> twitterDataCollection = tStream.run();
	}
}
