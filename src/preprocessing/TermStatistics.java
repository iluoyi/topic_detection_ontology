package preprocessing;
import java.util.ArrayList;

import MODEL.*;

public class TermStatistics {

	//��ȡ��ƪ�ĵ��Ĳ��ظ���+Ƶ��ͳ��
	public ArrayList<Term> getNewTerms(ArrayList<String> newWords, int docNum){
		 ArrayList<Term> newTerms = new  ArrayList<Term>();
		 ArrayList<String> terms = new ArrayList<String>();
		 int sizeOfWords = newWords.size();
		 for (int i=0; i<sizeOfWords; i++){
			 String currentWord = newWords.get(i);
			 if (!terms.contains(currentWord)){
				 terms.add(currentWord);
				 Term newTerm = new Term(currentWord);
				 Record newRecord = new Record(docNum,1);
				 newTerm.getRecordList().add(newRecord);
				 newTerms.add(newTerm);
			 }else{
				 int position = terms.indexOf(currentWord);
				 newTerms.get(position).getRecordList().get(0).addFrequency();
			 }
		 }
		 return newTerms;
	}
	
	//���ڸ���term�б�
	public ArrayList<Term> updateTermList(ArrayList<Term> oldTermList, ArrayList<String> newWords, int docNum){
		ArrayList<Term> newTermList = oldTermList;
		ArrayList<Term> comingTerms = getNewTerms(newWords, docNum);
		
		for (Term comingTerm:comingTerms){
			Boolean flag=false;
			for (int j=0; j<newTermList.size();j++){
				if (newTermList.get(j).getTermText().equals(comingTerm.getTermText())){
					flag = true;
					newTermList.get(j).getRecordList().add(comingTerm.getRecordList().get(0));//ע�⣬�˴������������
					break;
				}
				}	if (!flag) {
					   newTermList.add(comingTerm);//ע�⣬�˴������������
			}
			
		}
		
		return newTermList;
	}
	
	
	
}
