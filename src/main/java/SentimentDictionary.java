import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public class SentimentDictionary {
	private HashMap<String, Integer> dictionary = new HashMap<String, Integer>();

	public SentimentDictionary() throws IOException {
		Reader reader = new InputStreamReader(SentimentDictionary.class.getResourceAsStream("dictionary/sentiments.csv"));
//		FileReader file = new FileReader(filePath);
		BufferedReader br = new BufferedReader(reader);
		String tmp;
		String [] tempArray = new String[1];
		while(( tmp = br.readLine() )!=null ){
			tempArray = tmp.split(",");
			dictionary.put(
				tempArray[0].trim(),
				Math.round( Float.parseFloat(tempArray[1].trim() ) * ( (float) 100) ) // 
			);
		}
		br.close();
	}
	
	public HashMap<String, Integer> getDictionary(){
		return dictionary;
	}
}

