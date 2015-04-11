import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


//put a word to a hashmap and indicates the amount of time it appears
public class TweetsDatabase {
	
	private ArrayList<TwitterData> data = new ArrayList<TwitterData>();
	
	public ArrayList<TwitterData> getTweets() {
		return data;
	}

	public void setTweets(ArrayList<TwitterData> data) {
		this.data = data;
	}

	public TweetsDatabase(){
		super();
	}
	
	public void saveTweetsData() throws IOException{
		FileOutputStream fos = new FileOutputStream("tweets.ser");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this.data);
        oos.close();
        fos.close();
        System.out.printf("Serialized HashMap data is saved in tweets.ser");
	}
	
	public void loadTweetsData() throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("tweets.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setTweets((ArrayList<TwitterData>) ois.readObject());
        ois.close();
        fis.close();
        System.out.println("Deserialized HashMap and load it into the system!...");
        
        
	}
	
	
	
	
}
