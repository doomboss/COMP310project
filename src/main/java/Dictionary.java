import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public class Dictionary {
	
	private HashMap<String, ArrayList<String>> library = new HashMap<String, ArrayList<String>>();

	public HashMap<String, ArrayList<String>> getLibrary() {
		return library;
	}

	public void setLibrary(HashMap<String, ArrayList<String>> library) {
		this.library = library;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//filepath "resources/positivewords.txt"
		
		Dictionary dict = new Dictionary();
	}
	
	public Dictionary(){
		//filepath "resources/positivewords.txt"
		//loading the positive and negative library into arraylist
		try {
			Reader reader = new InputStreamReader(
					Dictionary.class.getResourceAsStream("dictionary/PositiveWords.txt"));			
			addDictionary(reader, "positive");
			reader = new InputStreamReader(
					Dictionary.class.getResourceAsStream("dictionary/NegativeWords.txt"));			
			addDictionary(reader, "negative");
//			addDictionary("resources/NegativeWords.txt", "negative");
			printAll();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(Exception e){
			e.printStackTrace();
		}
		 

	}
	
	//using inputstream instead of a file path
	public void addDictionary(Reader reader, String dictionaryKey) throws IOException, FileNotFoundException{
		BufferedReader br = new BufferedReader(reader);
		String tmp;
		ArrayList<String> tmplist = new ArrayList<String> ();
		while(( tmp = br.readLine())!=null ){
			tmplist.add(tmp);				
		}
		this.library.put(dictionaryKey, tmplist);
		reader.close();
	}
	
	public void addDictionary(String filePath, String dictionaryKey) throws IOException, FileNotFoundException{
		FileReader file = new FileReader(filePath);
		BufferedReader reader = new BufferedReader(file);
		String tmp;
		ArrayList<String> tmplist = new ArrayList<String> ();
		while(( tmp = reader.readLine())!=null ){
			tmplist.add(tmp);				
		}
		this.library.put(dictionaryKey, tmplist);
		reader.close();
	}
	
	public void printAll(){
		//loop through the hashmap
		Set set = this.library.entrySet();
        Iterator iterator = set.iterator();
        while(iterator.hasNext()) {
           Map.Entry mentry = (Map.Entry)iterator.next();
           System.out.print("key: "+ mentry.getKey() + " & Value: ");
           System.out.println(mentry.getValue());
        }
	}

}
