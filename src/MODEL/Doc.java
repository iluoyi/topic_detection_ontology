package MODEL;

import java.util.ArrayList;

public class Doc {
	
	int docNum = 0;
	ArrayList<Keyword> words = new ArrayList<Keyword>();
	
	
	public void setDocNum(int num){
		docNum = num;
	}

	public int getDocNum(){
		return docNum;
	}
	
	public void setWords(ArrayList<Keyword> newWords){
		words = newWords;
	}
	
	public ArrayList<Keyword> getWords(){
		return words;
	}
	
	public String wordsToString(){
		String toString = "";
		int size = getWords().size();
	
		for (int i =0; i < size; i++){
			toString = toString + getWords().get(i).getWordText() +", ";
		}
		return toString;
	}
}
