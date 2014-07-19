package clustering;

import java.util.ArrayList;

import preprocessing.ReadIn;
import GUI.UIControl;
import MODEL.AbstractDoc;
import MODEL.Doc;

public class ClusteringCenters {

	public double getMeanDist(ArrayList<Doc> docList){
		double meanDist = 0;
		double distSum = 0;
		Kmeans cluster = new Kmeans();
		int count = 0;
		
		for (int i = 0; i < docList.size(); i++){
			for (int j = i+1; j < docList.size(); j++){
				Doc docA = docList.get(i);
				Doc docB = docList.get(j);
				distSum += cluster.similarity(docA, docB);
				count++;
			}
		}
		meanDist = distSum / count;
		return meanDist;
	}
	
	public ArrayList<AbstractDoc> getAbstractDoc(ArrayList<Doc> docList, double meanDist){
		ArrayList<AbstractDoc> abstractDocList = new ArrayList<AbstractDoc>();
		Kmeans cluster = new Kmeans();
		
		for (int i = 0; i < docList.size(); i++){
			AbstractDoc abstractDoc = new AbstractDoc();
			abstractDoc.setDocNum(i);//here the doc number is the same to the index number
			abstractDoc.setDeletedStatus(false);
			int count = 0;
			ArrayList<Integer> neighbors = new ArrayList<Integer>();
			for (int j = 0; j < docList.size(); j++){
				Doc docA = docList.get(i);
				Doc docB = docList.get(j);
				double simi = cluster.similarity(docA, docB);
				if (simi >= meanDist){
					neighbors.add(j);
					count++;
				}
			}
			abstractDoc.setNeighborList(neighbors);
			abstractDoc.setNeighborNum(count);
			
			abstractDocList.add(abstractDoc);
		}
		
		
		return abstractDocList;
	}
	
	public int getMaxDocIndex(ArrayList<AbstractDoc> abstractDocList){
		int max = 0;
		int index = 0;
		for (int i=0; i<abstractDocList.size(); i++){
			if (abstractDocList.get(i).getNeighborNum()>max && (!abstractDocList.get(i).getStatus())){
				max = abstractDocList.get(i).getNeighborNum();
				index = i;
			}
		}
		System.out.println(index+"\t"+max);
		return index;
	}
	
	public ArrayList<Integer> getCenters(ArrayList<AbstractDoc> abstractDocList, int k){
		ArrayList<Integer> centers = new ArrayList<Integer>();
		
		for (int i=0; i<k; i++){
			int index = getMaxDocIndex(abstractDocList);
			for (int j=0; j<abstractDocList.get(index).getNeighborList().size();j++){
				int docNum = abstractDocList.get(index).getNeighborList().get(j);
				abstractDocList.get(docNum).setDeletedStatus(true);
			}
			centers.add(index);
		}
		
		return centers;
	}
	
	public static void main(String args[]){
		ReadIn reader = new ReadIn();
		ArrayList<Doc> docList = reader.readDoc("outputDoc.txt");
		ArrayList<Doc> newList = reader.featureSelection(docList, 5);//截取前5个特征
		
		ClusteringCenters centers = new ClusteringCenters();
		double meanDist = centers.getMeanDist(newList);
		
		ArrayList<AbstractDoc> abstractDocList = centers.getAbstractDoc(newList, meanDist);
		ArrayList<Integer> centerList = centers.getCenters(abstractDocList, 45);
		for (int i =0; i<centerList.size(); i++){
			System.out.println(centerList.get(i));
		}		
	}
}
