package preprocessing;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import MODEL.Doc;
import MODEL.Term;

public class WriteOut {

	public void output(String fileName, String content)
	{
		PrintWriter bw = null;
		try {	  	
			bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF-8"))); 
			bw.print(content);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (Exception ioe2) {
				// just ignore it
			}
		}
	}
	
	public void outputFrequency(ArrayList<Term> termList, String fileName)
	{
		PrintWriter bw = null;
		try {	  	
			bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"))); 
			
			int termSize = termList.size();
    		bw.println(termSize);
    		for (int i=0; i<termSize; i++){
    			bw.print(termList.get(i).getTermText()+"\t");
    			int docNum=termList.get(i).getRecordList().size();
    			for (int j=0; j<docNum; j++){
    				bw.print(termList.get(i).getRecordList().get(j).getDocNum()+"\t"+termList.get(i).getRecordList().get(j).getFrequency()+"\t");
    			}
    				bw.print("\n");
    		}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (Exception ioe2) {
				// just ignore it
			}
		}
	}
	
	public void outputWeight(ArrayList<Term> termList, String fileName)
	{
		PrintWriter bw = null;
		try {	  	
			bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"))); 
			
			int termSize = termList.size();
    		bw.println(termSize);
    		for (int i=0; i<termSize; i++){
    			bw.print(termList.get(i).getTermText()+"\t");
    			int docNum=termList.get(i).getRecordList().size();
    			for (int j=0; j<docNum; j++){
    				bw.print(termList.get(i).getRecordList().get(j).getDocNum()+"\t"+termList.get(i).getRecordList().get(j).getWeight()+"\t");
    			}
    				bw.print("\n");
    		}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (Exception ioe2) {
				// just ignore it
			}
		}
	}
		
	public void outputDoc(ArrayList<Doc> docList, String fileName)
	{
		PrintWriter bw = null;
		try {	  	
			bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"))); 
			
			int docSize = docList.size();
    		bw.println(docSize);
    		for (int i=0; i<docSize; i++){
    			bw.print(docList.get(i).getDocNum()+"\t");
    			int termSize=docList.get(i).getWords().size();

    			for (int j=0; j<termSize; j++){
    				bw.print(docList.get(i).getWords().get(j).getWordText()+"\t"+docList.get(i).getWords().get(j).getWeight()+"\t");
    			}
    				bw.print("\n");
    		}
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (Exception ioe2) {
				// just ignore it
			}
		}
	}
	
	public void outputTitle(String fileName, ArrayList<String> titles)
	{
		PrintWriter bw = null;
		try {	  	
			bw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),"UTF-8"))); 
			
			int titleSize = titles.size();
    		bw.println(titleSize);
    		for (int i=0; i<titleSize; i++){
    			bw.println(titles.get(i));
    		}		
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} finally {                       // always close the file
			if (bw != null) try {
				bw.close();
			} catch (Exception ioe2) {
				// just ignore it
			}
		}
	}
}
