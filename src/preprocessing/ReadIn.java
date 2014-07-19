package preprocessing;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import MODEL.Doc;
import MODEL.Keyword;
import MODEL.Record;
import MODEL.Term;

public class ReadIn {

	public String input(String fileName){
		   BufferedReader br = null;
		   String line;
		   String result=""; 
		   
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
				while ((line = br.readLine()) != null){
					result = result+line;
		    	}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
			return result;
		}
	
	public ArrayList<String> readData(String fileName){
       ArrayList<String> words = new ArrayList<String>();
	   BufferedReader br = null;
	   String line;
	 
	    try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
			while ((line = br.readLine()) != null){
				words.add(line);
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	
		return words;
	}
	
	public ArrayList<Term> readTerms(String fileName){
	       ArrayList<Term> termList = new ArrayList<Term>();
		   BufferedReader br = null;
		   String line;
		   String[] splitted;
		 
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
				int size = Integer.parseInt(br.readLine());
				
				for (int i=0; i<size; i++){
				   line = br.readLine();
				   splitted = line.split("\t");
				   Term tempTerm = new Term(splitted[0]);
				   ArrayList<Record> recordList = new ArrayList<Record>();
				   for (int j=1; j<=(splitted.length-1)/2; j++){
					   Record tempRecord = new Record(Integer.parseInt(splitted[j*2-1]),Integer.parseInt(splitted[j*2]));
				       recordList.add(tempRecord);
				   }
				   tempTerm.setRecordList(recordList);
				   termList.add(tempTerm);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
			return termList;
		}
		
	public ArrayList<Term> readTermsWeight(String fileName){
	       ArrayList<Term> termList = new ArrayList<Term>();
		   BufferedReader br = null;
		   String line;
		   String[] splitted;
		 
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
				int size = Integer.parseInt(br.readLine());
				
				for (int i=0; i<size; i++){
				   line = br.readLine();
				   splitted = line.split("\t");
				   Term tempTerm = new Term(splitted[0]);
				   ArrayList<Record> recordList = new ArrayList<Record>();
				   for (int j=1; j<=(splitted.length-1)/2; j++){
					   Record tempRecord = new Record(Integer.parseInt(splitted[j*2-1]),Double.parseDouble(splitted[j*2]));
				       recordList.add(tempRecord);
				   }
				   tempTerm.setRecordList(recordList);
				   termList.add(tempTerm);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	    	
			return termList;
		}
	
	public ArrayList<Doc> readDoc(String fileName){
	       ArrayList<Doc> docList = new ArrayList<Doc>();
		   BufferedReader br = null;
		   String line;
		   String[] splitted;
		 
		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
				int size = Integer.parseInt(br.readLine());
				
				for (int i=0; i<size; i++){
				   line = br.readLine();
				   splitted = line.split("\t");
				   Doc tempDoc = new Doc();
				   tempDoc.setDocNum(Integer.parseInt(splitted[0]));
				   ArrayList<Keyword> wordList = new ArrayList<Keyword>();
				   for (int j=1; j<=(splitted.length-1)/2; j++){
					   Keyword tempWord = new Keyword(splitted[j*2-1],Double.parseDouble(splitted[j*2]));
					   wordList.add(tempWord);
				   }
				   tempDoc.setWords(wordList);
				   docList.add(tempDoc);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return docList;
		}
	
	public ArrayList<String> readTitle(String fileName){
	       ArrayList<String> titleList = new ArrayList<String>();
		   BufferedReader br = null;
		   String title;

		    try {
				br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
				int size = Integer.parseInt(br.readLine());
				
				for (int i=0; i<size; i++){
				   title = br.readLine();				 
				   titleList.add(title);
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return titleList;
		}
	
	public ArrayList<String> getRealTopic(ArrayList<String> titleList){
		ArrayList<String> topicList = new ArrayList<String>();
		   
		for (String title : titleList){
			topicList.add(title.substring(0, title.indexOf("-")));
		}
	       
			return topicList;
		}
	
	
	public ArrayList<Doc> featureSelection(ArrayList<Doc> inputList, int topNum){		
		int size = inputList.size();
		for (int i=0;i<size;i++){
			int wordSize = inputList.get(i).getWords().size();
			if (wordSize>topNum) {
				for (int j=topNum;j<wordSize; j++){
					inputList.get(i).getWords().remove(topNum);
				}
			}
		}		
		return inputList;
	}
	
	
	public static void main(String[] args){
		ReadIn reader = new ReadIn();
		ArrayList<String> stopWords = reader.readData("stopWords.txt");
		for (int i=0;i<stopWords.size();i++){
		System.out.println(stopWords.get(i));
		}
	}
}
