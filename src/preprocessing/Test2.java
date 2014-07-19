package preprocessing;

import java.util.ArrayList;

import MODEL.*;

public class Test2 {
    public static void main(String[] srg) {
    	
    	//��TFIDFͳ��ת��Ϊ�ĵ�������ʽ
    		ReadIn reader = new ReadIn();
    		ArrayList<Term> termList = reader.readTermsWeight("outputTFIDF.txt");
    		int docNum=0;
    		ArrayList<Doc> docList = new ArrayList<Doc>();
    		ArrayList<Integer> removeList = new ArrayList<Integer>();
    		
    		while (!termList.isEmpty()){
    			docNum++;
    			Doc newDoc = new Doc();
    			newDoc.setDocNum(docNum);
    			ArrayList<Keyword> keywords = new ArrayList<Keyword>();
    			removeList.clear();
    			
    			int currentSize = termList.size();
    			for (int i=0; i<currentSize; i++){
      			//	System.out.println(docNum+"\t"+termList.get(i).getTermText());
    				if (termList.get(i).getRecordList().get(0).getDocNum()==docNum){
    				Keyword tempWord = new Keyword(termList.get(i).getTermText(),termList.get(i).getRecordList().get(0).getWeight());
    					int j;
    					for (j=0; j<keywords.size(); j++){
    						if (tempWord.getWeight()>keywords.get(j).getWeight()){
    							break;
    						}
    					}
						keywords.add(j, tempWord);   	
						termList.get(i).getRecordList().remove(0);
    				}//�����i��term�ڵ�docNum���ĵ��У����ս������doc��keywords�б��У���ɾ��ԭ��¼
    				if (termList.get(i).getRecordList().isEmpty()){
    					removeList.add(i);
    				}   			
    			}
    			
    			int delta=0;
    			for (int index:removeList){
    				termList.remove(index-delta);
    				delta++;
    			}//ɾ��record listΪ�ռ�¼��term
    			
    			newDoc.setWords(keywords);
    			docList.add(newDoc);
    	}
    	
    		WriteOut writer = new WriteOut();
    		writer.outputDoc(docList, "outputOntoDoc.txt");
    		System.out.println("outputDoc ready...");
    } 
}
