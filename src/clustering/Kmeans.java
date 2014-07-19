package clustering;

import java.util.ArrayList;
import java.util.Random;

import MODEL.Doc;
import MODEL.Keyword;

public class Kmeans {

	int[] assignment;
	int meanPointSize = 1000;
	
	//to judge if centers have been initialized
	public boolean isInitialized(){
		if (assignment == null) return false;
		
		for (int i=0; i<assignment.length; i++){
			if (assignment[i] == -1){
				return true;
			}
		}
		return false;
	}
	
	// to initialize clustering centers randomly
	public ArrayList<Doc> initialization(int k, ArrayList<Doc> docList){
		ArrayList<Doc> meanPoints = new ArrayList<Doc>();
		int docSize = docList.size();
		Random rand = new Random();
		int index;
		
		assignment = new int[docSize];
		for (int i=0; i<docSize; i++){
			assignment[i] = -1;
		}//any one be selected as a cluster will be assigned a value=-1
		
		for (int i=0; i<k; i++){
		    index = rand.nextInt(docSize);
			while (assignment[index]!=-1){
			 index = rand.nextInt(docSize);
			}			
			assignment[index] = i;
			meanPoints.add(docList.get(index));
		}
		return meanPoints;
	}
	
	//to initialize centers acquiring from manually set or user-defined algorithm
	public ArrayList<Doc> newIni(ArrayList<Integer> centerList, ArrayList<Doc> docList){
		ArrayList<Doc> meanPoints = new ArrayList<Doc>();
		int docSize = docList.size();
		assignment = new int[docSize];
		int centerListSize = centerList.size();
		int index;
		
		for (int i=0; i<centerListSize; i++){
			index = centerList.get(i);
			assignment[index] = i;
			meanPoints.add(docList.get(index));
		}
		return meanPoints;
	}
	
	public int[] clustering(ArrayList<Doc> docList, int k, ArrayList<Doc> meanPoints){
		int docSize = docList.size();	
		int[] newlabel = new int[docSize];
		Boolean flag;
		
		for (int i=0; i<docSize; i++){
			newlabel[i] = -1;
		}//any one be selected as a cluster will be assigned a value=-1
		
		int num=0;
		while (true){
			num++;
			System.out.println("--Iteration "+num);
			System.out.println("  --Assigning new labels start...");
			newlabel = getNewLabel(docList, meanPoints);
			System.out.println("  --Assigning new labels end...");	
			
			int[] record = new int[docSize];
			 for (int i=0; i<docSize; i++){
				  record[i] = assignment[i]; 
				 }
			
			//to judge whether centers are changed
			flag = true;
			for (int i=0; i<docSize; i++){
			 if (newlabel[i] != assignment[i]){
				 flag = false;
				 break;
			 }
			}
			 if (flag){
				 //centers are not changed so the iteration can be terminated
				 break;
			 } else {
				 for (int i=0; i<docSize; i++){
				 assignment[i] = newlabel[i]; 
				 }
			 }
			//---------------------------------------
			 System.out.println("  --Changing the center start...");
			 meanPoints = resetMeanPoints(k, docList, newlabel);
			 System.out.println("  --Changing the center end...");
			
		}
		
		return assignment;
	}
	
	
	
	//to recalculate clustering centers
	private ArrayList<Doc> resetMeanPoints(int k, ArrayList<Doc> docList, int[] newlabel){
		int docSize = docList.size();
		ArrayList<Doc> meanPoints = new ArrayList<Doc>();

		for (int i=0; i<k; i++){
			meanPoints.add(new Doc());
		}
		
		for (int item=0; item<docSize; item++){
		
			meanPoints.get(newlabel[item]).setDocNum(meanPoints.get(newlabel[item]).getDocNum()+1);
			ArrayList<Keyword> wordList = docList.get(item).getWords();
			ArrayList<Keyword> meanPointsList = meanPoints.get(newlabel[item]).getWords();
			addIn(wordList, meanPointsList);
				
		}
		
					
		for (int i=0; i<k; i++){
			if (meanPoints.get(i).getWords().size()>meanPointSize){
				pruneMeanPoints(meanPoints.get(i));
			}//truncate centers
			int size = meanPoints.get(i).getWords().size();
			System.out.println("    --Current vector size of mean point " +i+ " is: "+size);
			for (int j=0; j<size; j++){
				meanPoints.get(i).getWords().get(j).divideWeight((double) meanPoints.get(i).getDocNum());
			}
		}
		
		return meanPoints;
	}
	
	//add terms of a document vector into the center vector
	private void addIn(ArrayList<Keyword> wordList, ArrayList<Keyword> meanPointList){
		int listSize = wordList.size();
		Boolean flag;
		
		for (int i=0; i<listSize; i++){
			flag = false;
			for (int j=0; j<meanPointList.size(); j++){
				if (wordList.get(i).getWordText().equals(meanPointList.get(j).getWordText())){
					meanPointList.get(j).addWeight(wordList.get(i).getWeight());
					flag = true;
					break;
				}
			}
			if (!flag){
				Keyword tempWord = new Keyword(wordList.get(i).getWordText(),wordList.get(i).getWeight());				
				meanPointList.add(tempWord);
			}
		}
		
	}
	
	private void pruneMeanPoints(Doc meanPoint){
		int listSize = meanPoint.getWords().size();
		for (int i=0; i<listSize; i++){
			for (int j=i; j<listSize; j++){
				if (meanPoint.getWords().get(i).getWeight()<meanPoint.getWords().get(j).getWeight()){
					String tempText = meanPoint.getWords().get(i).getWordText();
					double tempWeight = meanPoint.getWords().get(i).getWeight();
					meanPoint.getWords().get(i).setWordText(meanPoint.getWords().get(j).getWordText());
					meanPoint.getWords().get(i).setWeight(meanPoint.getWords().get(j).getWeight());
					meanPoint.getWords().get(j).setWordText(tempText);
					meanPoint.getWords().get(i).setWeight(tempWeight);
				}
			}
		}
		for (int i=0; i<listSize-meanPointSize;i++){
			meanPoint.getWords().remove(meanPointSize);
		}
	}
	

	//to figure out the nearest cluster center as target cluster, and assign label to newLabelList
	private int[] getNewLabel(ArrayList<Doc> docList, ArrayList<Doc> meanPoints){
		int docSize = docList.size();
		int meanSize = meanPoints.size();
		int[] newlabel = new int[docSize];
		int index;//Àà±ð
		double maxSimi;
		double simi;
		
		for (int i=0; i<docSize; i++){
			maxSimi = 0;
			index = 0;
			for (int j=0; j<meanSize; j++){
				Doc docA = docList.get(i);
				Doc docB = meanPoints.get(j);
				simi = similarity(docA, docB);
				if (simi > maxSimi){
					maxSimi = simi;
					index = j;
				}
			}
			newlabel[i] = index;
		}
		
		return newlabel;
	}
	
	//to compute the similarity
	public double similarity(Doc docA, Doc docB){
		double simi = 0;
		double up = innerProduct(docA, docB);
		double downA = Math.sqrt(innerProduct(docA, docA));
		double downB = Math.sqrt(innerProduct(docB, docB));
		simi = up /(downA*downB);
		return simi;
	}
	
	//inner product
	private double innerProduct(Doc docA, Doc docB){
		double product = 0;
		int docASize = docA.getWords().size();
		int docBSize = docB.getWords().size();
		
			for (int i=0; i<docASize; i++){
				for (int j=0; j<docBSize; j++){
					if (docA.getWords().get(i).getWordText().equals(docB.getWords().get(j).getWordText())){
						product = product+docA.getWords().get(i).getWeight()*docB.getWords().get(j).getWeight();
						break;
					}
				}
			}
		return product;	
	}
}
