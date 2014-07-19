package preprocessing;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

import ontology.GetOntologyMap;

import ICTCLAS.I3S.AC.ICTCLAS50;
import JDBC.*;
import MODEL.TaggedWord;
import MODEL.Term;

public class Test0 {
	
	//初始化数据并进行频率统计
    public static void main(String[] srg) {
    	DBConnection dbcon=new DBConnection(); 
    	try {   
    		String sq="select * from test";   
    		ResultSet rs = dbcon.getRes2(sq);  
    	//	System.out.println(rs.getString("contents")); 
    	//---------------------------------------------------------	
    		try
    		{
    			ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
    			//分词所需库的路径
    			String argu = ".";
    			//初始化
    		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
    			{
    				System.out.println("Init Fail!");
    				return;
    			}
    			else { System.out.println("Init Succeed!"); }
    		String str = "userdict.txt";
			int nCount = testICTCLAS50.ICTCLAS_ImportUserDictFile(str.getBytes(), 0);
			
			System.out.println("Import "+nCount+" user words from userdict.txt");
    		ReadIn reader = new ReadIn();
    		ArrayList<String> stopWords = reader.readData("stopWords.txt");
    		ExtractWord extractWords = new ExtractWord();
    		TermStatistics statistics = new TermStatistics();
    		GetOntologyMap getOntology = new GetOntologyMap();
    		ArrayList<Term> oldTermList = new ArrayList<Term>();
    		ArrayList<String> titles = new ArrayList<String>();
    		HashMap<String, String> equalConcepts = getOntology.getEqualConcepts();
    		HashMap<String, String> superConcepts = getOntology.getSuperConcepts();
    		
    		int k=0;
    		
    			while (rs.next()){
    				k++;
        		String title = rs.getNString("title");
        		titles.add(title);
    			byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(rs.getString("contents").getBytes("GB2312"), 0, 1);
    			//System.out.println(nativeBytes.length);
    			String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
    			
         	    //System.out.println(nativeStr); 
         
        		ArrayList<TaggedWord> words = new ArrayList<TaggedWord>();
        		words = extractWords.extractTaggedWords(nativeStr, "n");//从分词结果中获取各词汇
    		
        		int sizeOfWords=words.size();
        		
                ArrayList<String> tempWords = new ArrayList<String>();
        		for (int i=0; i<sizeOfWords; i++){
        			if (! stopWords.contains(words.get(i).getSrcWord())) {
        				String wordText = words.get(i).getSrcWord();
        				if (equalConcepts.containsKey(wordText)){
        					wordText = equalConcepts.get(wordText);
        				}
        				if (superConcepts.containsKey(wordText)){
        					wordText = superConcepts.get(wordText);
        				}
        				tempWords.add(wordText);       					
        			}
        		}//用于过滤掉停用词
        		
        		ArrayList<Term> newTermList = statistics.updateTermList(oldTermList, tempWords, k);
        		oldTermList = newTermList;
    		}
    			
    			testICTCLAS50.ICTCLAS_Exit();
    			
        		WriteOut writer = new WriteOut();
        		writer.outputFrequency(oldTermList, "output.txt");
        		writer.outputTitle("outputTitles.txt", titles);
    			System.out.println("output ready...");
        			     		
    		}
    		catch (Exception ex)
    		{
    		}
       //-------------------------------------------------------------
    		rs.close(); 
    		} 
    	catch (Exception e) {  
    		e.printStackTrace(); 
    		}
    }
}
