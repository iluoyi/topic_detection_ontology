package preprocessing;

import java.util.ArrayList;

import ontology.GetOntologyMap;

import MODEL.Term;

public class Test1 {

	//将频率统计转化为TFIDF统计
	public static void main(String[] args){
		ReadIn reader = new ReadIn();
		WriteOut writer = new WriteOut();
		ArrayList<Term> termList = reader.readTerms("output.txt");
		
		GetOntologyMap getOntology = new GetOntologyMap();
		ArrayList<String> conceptsList = getOntology.getOntoConcepts();
   		
		int termSize = termList.size();
		for (int i=0; i<termSize; i++){       			
			int docNum=termList.get(i).getRecordList().size();
			for (int j=0; j<docNum; j++){
				double frequency = (double)termList.get(i).getRecordList().get(j).getFrequency();
				double weight =frequency *Math.log10((double) termSize/docNum);
				
	//----test------to increase weights of ontoloy concepts here------------							
				//if (conceptsList.contains(termList.get(i).getTermText())){
					//termList.get(i).getRecordList().get(j).setWeight(weight*2);	
				//}else{
					termList.get(i).getRecordList().get(j).setWeight(weight);
				//}
					//事实证明，不提高ontology的权值结果表现得更好！！！
			}
		}
		
		writer.outputWeight(termList, "outputTFIDF.txt");
		System.out.println("outputTFIDF ready...");
	}
}
