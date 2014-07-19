package preprocessing;

import java.io.File;
import java.util.ArrayList;

public class Pretreatment {

	public void TopicTag(String folderName, String tag){
		File dirPath=new File("./News_data/"+folderName);
		File[] files = dirPath.listFiles();
		File newDirPath=new File("./TestCorpus-28/"+tag+folderName.substring(folderName.indexOf('-')));
		newDirPath.mkdir();
		
		ReadIn reader = new ReadIn();
		WriteOut writer = new WriteOut();
		
		for (File f : files){
    		String title;  		
    		title = "./News_data/"+folderName+"/"+f.getName();
    		
    		String contents = reader.input(title);  
    		contents.replace('\'', ' ');
    		
    		String fileName = f.getName().substring(f.getName().indexOf('-'));
    		title = "./TestCorpus-28/"+tag+folderName.substring(folderName.indexOf('-'))+"/"+tag+fileName;
    		writer.output(title, contents);
		}
		System.out.println("Topic "+folderName+" is tagged ready...");
	}
	
	
	public void deleteDuplication(String fileName){
		ReadIn reader = new ReadIn();
		ArrayList<String> dupliWords = reader.readData(fileName);
		
		ArrayList<String> newWords = new ArrayList<String>();
		
		for (String word : dupliWords){
			if (!newWords.contains(word)){
				newWords.add(word);
			}
		}
		
		WriteOut writer = new WriteOut();
		writer.outputTitle("new_"+fileName, newWords);
	}
	
	public static void main(String[] args){
		Pretreatment pretreat = new Pretreatment();
		pretreat.deleteDuplication("userdict.txt");
	}
}
