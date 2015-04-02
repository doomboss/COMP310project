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


public class CommonWordDatabase {
	
	private HashMap<String, Integer> words = new HashMap<String, Integer>();
	
	public HashMap<String, Integer> getWords() {
		return words;
	}

	public void setWords(HashMap<String, Integer> words) {
		this.words = words;
	}

	public CommonWordDatabase(){
		super();
		try{
			loadWordsData();
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		
	}
	
	public void saveWordsData() throws IOException{
		FileOutputStream fos =
                new FileOutputStream("words.ser");
             ObjectOutputStream oos = new ObjectOutputStream(fos);
             oos.writeObject(this.words);
             oos.close();
             fos.close();
             System.out.printf("Serialized HashMap data is saved in words.ser");
	}
	
	public void loadWordsData() throws IOException, ClassNotFoundException{
		FileInputStream fis = new FileInputStream("words.ser");
        ObjectInputStream ois = new ObjectInputStream(fis);
        setWords((HashMap) ois.readObject());
        ois.close();
        fis.close();
        System.out.println("Deserialized HashMap and load it into the system!...");
        
        //loop through the hashmap
        Set set = this.words.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
           Map.Entry mentry = (Map.Entry)iterator.next();
           System.out.print("key: "+ mentry.getKey() + " & Value: ");
           System.out.println(mentry.getValue());
        }
        
        
	}
	
	
	
	
}
