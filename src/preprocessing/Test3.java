package preprocessing;

import java.util.ArrayList;
import clustering.Kmeans;

import MODEL.Doc;

public class Test3 {
	
	public static void main(String[] args){
		ReadIn reader = new ReadIn();
		ArrayList<Doc> docList = reader.readDoc("outputDoc.txt");
		ArrayList<Doc> newList = reader.featureSelection(docList, 5);//截取前10个特征
		ArrayList<String> titleList = reader.readTitle("outputTitles.txt");
		ArrayList<String> topicList = reader.getRealTopic(titleList);
		
		Kmeans cluster = new Kmeans();
		
		int k = 28;
		ArrayList<Doc> meanPoints = cluster.initialization(k, newList);
		int[] result = cluster.clustering(newList, k, meanPoints);
		int sumOfPuritySample = 0;
		int numOfTotal = newList.size();
		
	for (int clas=0; clas<k; clas++){
		System.out.println("========================================Class "+clas+"===========================================");
		ArrayList<String> purityList = new ArrayList<String>();
		ArrayList<Integer> purityFrequency = new ArrayList<Integer>();
		
		for (int i=0; i<result.length; i++){
			if (result[i]==clas){
					int size = newList.get(i).getWords().size();
					System.out.print(newList.get(i).getDocNum()+"\t"+topicList.get(i)+"\t"+titleList.get(i)+".txt\t");//doc number != i since doc number starts from 1 (rather than 0)
					for (int j=0; j<size; j++){
						System.out.print(newList.get(i).getWords().get(j).getWordText()+"\t");
					}
					System.out.print("\n");
					
					int index = purityList.indexOf(topicList.get(i));
					if (index != -1){	
						purityFrequency.set(index, purityFrequency.get(index).intValue()+1);
					} else {
						purityList.add(topicList.get(i));
						purityFrequency.add(1);
					}
				}
			}
		//System.out.print("\n");
		
		int max = 0;
			for (Integer index : purityFrequency){
				if (index.intValue() > max){
					max = index.intValue();
				}
			}
			//System.out.println(max);
			sumOfPuritySample+=max;
		}	
		System.out.println("Purity = "+(double) sumOfPuritySample/(double) numOfTotal);
	}
	
}
