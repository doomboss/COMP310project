import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

public class SentimentDictionary {
	private HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

	public SentimentDictionary(String filePath) throws IOException {
		FileReader file = new FileReader(filePath);
		BufferedReader reader = new BufferedReader(file);
		String tmp;
		String [] tempArray = new String[1];
		while(( tmp = reader.readLine() )!=null ){
			tempArray = tmp.split(",");
			dictionary.put(
				tempArray[0].trim(),
				Math.round( Float.parseFloat(tempArray[1].trim() ) * ( (float) 100) ) // 
			);
		}
		reader.close();
	}
	
	public HashMap<String, Integer> getDictionary(){
		return dictionary;
	}
}

