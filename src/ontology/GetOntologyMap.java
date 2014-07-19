package ontology;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

public class GetOntologyMap {

	public ArrayList<String> getOntoConcepts(){
		ArrayList<String> conceptList = new ArrayList<String>();
		 BufferedReader br = null;
		   String line;
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("ontoConcepts.txt"),"UTF-8"));
				while ((line = br.readLine()) != null){
					conceptList.add(line);
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
		
		return conceptList;
	}
	
	public HashMap<String, String> getEqualConcepts(){
		HashMap<String, String> equalConcepts = new HashMap<String, String>();
		   BufferedReader br = null;
		   String line;
		   String[] words;
		   
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("equalConcepts.txt"),"UTF-8"));
				while ((line = br.readLine()) != null){
					words = line.split(" ");
					equalConcepts.put(words[0], words[1]);
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
		return equalConcepts;
	}
	
	public HashMap<String, String> getSuperConcepts(){
		HashMap<String, String> equalConcepts = new HashMap<String, String>();
		   BufferedReader br = null;
		   String line;
		   String[] words;
		   
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream("superConcepts.txt"),"UTF-8"));
				while ((line = br.readLine()) != null){
					words = line.split(" ");
					equalConcepts.put(words[0], words[1]);
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
		return equalConcepts;
	}
}
