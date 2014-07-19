package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.Map.Entry;

import javax.swing.SwingUtilities;

import preprocessing.ReadIn;
import MODEL.AbstractDoc;
import MODEL.Doc;
import MODEL.StringAndFreq;
import clustering.ClusteringCenters;
import clustering.Kmeans;


public class UIControl {
	
	static int REALTOPICNUM_28 = 28;
	static int REALTOPICNUM_45 = 45;
	
	public static int combination(int m, int n){
		int result = 1;
		int up = 1;
		int down = 1;
		int limit = n;
		
		for (int i = 0; i < limit; i++){
			up = up * m;
			m = m - 1;
			down = down * n;
			n = n-1;
		}
		result = up / down;
		return result;
	}
	
	
	public static void main(String args[]){
		final MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);//To show the main frame
				
		ReadIn reader = new ReadIn();
		ArrayList<Doc> docList = reader.readDoc("outputDoc.txt");
		final ArrayList<Doc> newList = reader.featureSelection(docList, 5);//截取前5个特征
		final ArrayList<String> titleList = reader.readTitle("outputTitles.txt");
		final ArrayList<String> topicList = reader.getRealTopic(titleList);
		
		ArrayList<Doc> docOntoList = reader.readDoc("outputOntoDoc.txt");
		final ArrayList<Doc> newOntoList = reader.featureSelection(docOntoList, 5);//截取前5个特征
		
		final Kmeans cluster = new Kmeans();
		
		//System.out.println(newList.get(0).getDocNum());  docNum = number + 1;
		
		//to get clustering centers automatically (randomly)
		mainFrame.result_panel.getCenters1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			try{//to ensure that the k must be a number
				int k = Integer.parseInt(mainFrame.result_panel.kNum.getText());
				ArrayList<Doc> meanPoints = cluster.initialization(k, newList);
				
				mainFrame.result_panel.centerRow.clear();
				int num = 0;
				for (Doc meanPoint : meanPoints ){
					Vector<String> temp = new Vector<String>();	
					temp.add(meanPoint.getDocNum()+"");//docNum
					temp.add(titleList.get(meanPoint.getDocNum()-1));//title
					temp.add((num++)+"");//class
					temp.add(newList.get(meanPoint.getDocNum()-1).wordsToString());//keywords
					mainFrame.result_panel.centerRow.add(temp);
				}
				SwingUtilities.updateComponentTreeUI(mainFrame.result_panel.clusterCenter);	
				}catch(Exception exception){
				//do nothing
			};
		}
		});
		
		//to get clustering centers automatically (by a new algorithm)
		mainFrame.result_panel.getCenters2.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
			try{//to ensure that the k must be a number
				int k = Integer.parseInt(mainFrame.result_panel.kNum.getText());
				ClusteringCenters centers = new ClusteringCenters();
				double meanDist = centers.getMeanDist(newList); //newOntoList, newList
				
				ArrayList<AbstractDoc> abstractDocList = centers.getAbstractDoc(newList, meanDist);
				ArrayList<Integer> centerList = centers.getCenters(abstractDocList, k);
				ArrayList<Doc> meanPoints = cluster.newIni(centerList, newList);
				
				mainFrame.result_panel.centerRow.clear();
				int num = 0;
				for (Doc meanPoint : meanPoints ){
					Vector<String> temp = new Vector<String>();	
					temp.add(meanPoint.getDocNum()+"");//docNum
					temp.add(titleList.get(meanPoint.getDocNum()-1));//title
					temp.add((num++)+"");//class
					temp.add(newList.get(meanPoint.getDocNum()-1).wordsToString());//keywords
					mainFrame.result_panel.centerRow.add(temp);
				}
				SwingUtilities.updateComponentTreeUI(mainFrame.result_panel.clusterCenter);	
				}catch(Exception exception){
				//do nothing
			};
		}
		});
		
		//to insert some clustering centers by hand
		mainFrame.result_panel.insertCenter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				try{//to ensure that the k must be a number
					int docNum = Integer.parseInt(mainFrame.result_panel.inputDocNum.getText());
					int num;
					
					if (mainFrame.result_panel.centerRow.size()==0){
						num = 0;
					}else {
						num = 1+Integer.parseInt(mainFrame.result_panel.centerRow.lastElement().get(2));
					}

					Vector<String> temp = new Vector<String>();	
					temp.add(docNum+"");//docNum
					temp.add(titleList.get(docNum-1));//title
					temp.add(num+"");//class
					temp.add(newList.get(docNum-1).wordsToString());//keywords
					mainFrame.result_panel.centerRow.add(temp);
					
					SwingUtilities.updateComponentTreeUI(mainFrame.result_panel.clusterCenter);	
				}catch(Exception exception){
					//do nothing
				};
			}
			});
		
		//to delete a clustering center
		mainFrame.result_panel.deleteCenter.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int rowNum = mainFrame.result_panel.clusterCenter.getSelectedRow();
				mainFrame.result_panel.centerRow.remove(rowNum);
				SwingUtilities.updateComponentTreeUI(mainFrame.result_panel.clusterCenter);	
			}
			});
		
		
		//to get the result of traditional K-means approach
		mainFrame.result_panel.getKmeansResult.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int k = mainFrame.result_panel.centerRow.size();//namely the number in the "kNum" JText
				ArrayList<Doc> meanPoints = new ArrayList<Doc>();
				
				if (!cluster.isInitialized()){
					ArrayList<Integer> centerList = new ArrayList<Integer>();
				
					for (int i=0; i < k; i++){
						int	index = Integer.parseInt(mainFrame.result_panel.centerRow.get(i).get(0));
						centerList.add(index-1);
					}
					meanPoints = cluster.newIni(centerList, newList);
				}else{
					for (int i=0; i < k; i++){
						int	index = Integer.parseInt(mainFrame.result_panel.centerRow.get(i).get(0));
						meanPoints.add(newList.get(index-1));
						}
				}
				
				int[] clusterResults = cluster.clustering(newList, k, meanPoints);
				
				mainFrame.result_panel.kmeansRow.clear();
				int num = 0;
				for (int result : clusterResults ){
					Vector<String> temp = new Vector<String>();	

					temp.add(newList.get(num).getDocNum()+"");//docNum
					temp.add(titleList.get(num));//Title
					temp.add(result+"");//result class
					temp.add(topicList.get(num));//pre-defined class
					temp.add(newList.get(num).wordsToString());//keywords
					num++;
					
					int resultSize = mainFrame.result_panel.kmeansRow.size();
					if (resultSize > 0){
						int index;
					for (index = 0; index < resultSize; index++){
						String comp = mainFrame.result_panel.kmeansRow.get(index).get(2);//result class
						if (result <= Integer.parseInt(comp)){
							break;
						}
					}	
						mainFrame.result_panel.kmeansRow.add(index, temp);
					} else {mainFrame.result_panel.kmeansRow.add(temp);}
				}
				SwingUtilities.updateComponentTreeUI(mainFrame.result_panel.kmeansResult);	
				
				//calculate evaluation values
				int resultSize = mainFrame.result_panel.kmeansRow.size();
				int frequency;
				
				//classPrefer
				HashMap<String, HashMap<String, Integer>> classPrefer = new HashMap<String, HashMap<String, Integer>>();
				for (int i=0; i<resultSize; i++){
					String className = mainFrame.result_panel.kmeansRow.get(i).get(2);
					String topicName = mainFrame.result_panel.kmeansRow.get(i).get(3);
					
					HashMap<String, Integer> topicList;
					if (classPrefer.containsKey(className)){		
						topicList = classPrefer.get(className);
		
						if (topicList.containsKey(topicName)){
							frequency = topicList.get(topicName)+1;
						}else{
							frequency = 1;
						}
						topicList.put(topicName, frequency);
					}else{
						topicList = new HashMap<String, Integer>();
						topicList.put(topicName, 1);
					}
					classPrefer.put(className, topicList);			
				}
				//System.out.println(classPrefer.get("2").toString());
				
				//topicPrefer
				HashMap<String, ArrayList<StringAndFreq>> topicPrefer = new HashMap<String, ArrayList<StringAndFreq>>();
				for (int i=0; i<resultSize; i++){
					String topicName = mainFrame.result_panel.kmeansRow.get(i).get(3);
					String className = mainFrame.result_panel.kmeansRow.get(i).get(2);
					
					ArrayList<StringAndFreq> classList;	
					if (topicPrefer.containsKey(topicName)){		
						classList = topicPrefer.get(topicName);
						
						int classListSize = classList.size();
						int index;
						boolean flag = false;
						for (index = 0; index < classListSize; index ++){
							if (classList.get(index).getTermString().equals(className)){
								flag = true; //classList contains "className"
								break;
							}
						}
						if (flag){
							classList.get(index).addFrequency();
						}else{
							StringAndFreq tempTerm = new StringAndFreq();
							tempTerm.setTermString(className);
							classList.add(tempTerm);
						}
					}else{
						classList = new ArrayList<StringAndFreq>();
						StringAndFreq tempTerm = new StringAndFreq();
						tempTerm.setTermString(className);
						classList.add(tempTerm);
					}
					topicPrefer.put(topicName, classList);			
				}
				
				//to calculate the purity value
				int puritySum = 0;
				for (int i = 0; i < k; i++){
					int max = 0;
					Iterator<Entry<String, Integer>> iterator = classPrefer.get(i+"").entrySet().iterator();
					while (iterator.hasNext()){
						int tempMax = iterator.next().getValue();
						if (tempMax > max){
							max = tempMax;
						}
					}
					//System.out.println("i= "+i+" and max="+max);
					puritySum += max;
				}
				double purity = (double) puritySum / (double) resultSize; 
				mainFrame.result_panel.purity_Kmeans.setText(purity+"");
				
				// to calculate the TP, FP, FN, and TN values
				int TPandFP = 0;
				for (int i =0; i < k; i++){//k is the number of clusters
					int tempSum = 0;
					Iterator<Entry<String, Integer>> iterator = classPrefer.get(i+"").entrySet().iterator();
					while (iterator.hasNext()){
						tempSum += iterator.next().getValue();
						}
					TPandFP += UIControl.combination(tempSum, 2);
				}// we then get TP + FP
				
				int TP = 0;
				for (int i =0; i < k; i++){//k is the number of clusters
					int tempSum = 0;
					Iterator<Entry<String, Integer>> iterator = classPrefer.get(i+"").entrySet().iterator();
					while (iterator.hasNext()){
						int tempNum = iterator.next().getValue();
						if (tempNum >= 2)
							tempSum += UIControl.combination(tempNum, 2);
						}
					TP += tempSum;
				}//we then get TP here
			
				int FP = TPandFP - TP;// here the FP
			
				int FN = 0;
				for (int i = 0; i < REALTOPICNUM_28; i++){//REALTOPICNUM_28, REALTOPICNUM_45, here, REALTOPICNUM_? is the real number of topics
					ArrayList<StringAndFreq> classList = topicPrefer.get((i+1)+"");
					int listSize = classList.size();
					for (int n = 0; n < listSize; n++){
						for (int m = n + 1; m < listSize; m++){
							FN += classList.get(n).getFrequency() * classList.get(m).getFrequency();
						}
					}
				}// we then get FN here
			
				int TN = UIControl.combination(resultSize, 2) - TP - FP - FN;// we then get TN 
				
				System.out.println("TP= "+TP);
				System.out.println("FP= "+FP);
				System.out.println("TN= "+TN);
				System.out.println("FN= "+FN);
						
				//to calculate the precision, recall and f-score
				double precision = (double) TP / (double) (TP+FP); 
				mainFrame.result_panel.p_Kmeans.setText(precision+"");
				
				double recall = (double) TP / (double) (TP+FN); 
				mainFrame.result_panel.r_Kmeans.setText(recall+"");
				
				double beta = 1.0;
				double fscore = ((beta * beta + 1) * precision * recall) / (beta * beta * precision + recall); 
				mainFrame.result_panel.f_Kmeans.setText(fscore+"");
				
				System.out.println("Precision= "+precision);
				System.out.println("Recall= "+recall);
				System.out.println("F-score= "+fscore);
				System.out.println("Purity= "+purity);
			}
			
		});
		
		//to get the result of Ontology approach
		mainFrame.result_panel.getOntologyResult.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int k = mainFrame.result_panel.centerRow.size();//namely the number in the "kNum" JText
				ArrayList<Doc> meanPoints = new ArrayList<Doc>();

				if (!cluster.isInitialized()){
					ArrayList<Integer> centerList = new ArrayList<Integer>();
				
					for (int i=0; i < k; i++){
						int	index = Integer.parseInt(mainFrame.result_panel.centerRow.get(i).get(0));
						centerList.add(index-1);
					}
					meanPoints = cluster.newIni(centerList, newOntoList);
				}else{
					for (int i=0; i < k; i++){
						int	index = Integer.parseInt(mainFrame.result_panel.centerRow.get(i).get(0));
						meanPoints.add(newOntoList.get(index-1));
						}
				}

				int[] clusterResults = cluster.clustering(newOntoList, k, meanPoints);
				
				mainFrame.result_panel.ontologyRow.clear();
				int num = 0;
				for (int result : clusterResults ){
					Vector<String> temp = new Vector<String>();	

					temp.add(newOntoList.get(num).getDocNum()+"");//docNum
					temp.add(titleList.get(num));//Title
					temp.add(result+"");//result class
					temp.add(topicList.get(num));//pre-defined class
					temp.add(newOntoList.get(num).wordsToString());//keywords
					num++;
					
					int resultSize = mainFrame.result_panel.ontologyRow.size();
					if (resultSize > 0){
						int index;
					for (index = 0; index < resultSize; index++){
						String comp = mainFrame.result_panel.ontologyRow.get(index).get(2);//result class
						if (result <= Integer.parseInt(comp)){
							break;
						}
					}	
						mainFrame.result_panel.ontologyRow.add(index, temp);
					} else {mainFrame.result_panel.ontologyRow.add(temp);}
				}
				SwingUtilities.updateComponentTreeUI(mainFrame.result_panel.ontologyResult);	
				
				//calculate evaluation values
				int resultSize = mainFrame.result_panel.ontologyRow.size();
				int frequency;
				
				//classPrefer
				HashMap<String, HashMap<String, Integer>> classPrefer = new HashMap<String, HashMap<String, Integer>>();
				for (int i=0; i<resultSize; i++){
					String className = mainFrame.result_panel.ontologyRow.get(i).get(2);
					String topicName = mainFrame.result_panel.ontologyRow.get(i).get(3);
					
					HashMap<String, Integer> topicList;
					if (classPrefer.containsKey(className)){		
						topicList = classPrefer.get(className);
		
						if (topicList.containsKey(topicName)){
							frequency = topicList.get(topicName)+1;
						}else{
							frequency = 1;
						}
						topicList.put(topicName, frequency);
					}else{
						topicList = new HashMap<String, Integer>();
						topicList.put(topicName, 1);
					}
					classPrefer.put(className, topicList);			
				}
				//System.out.println(classPrefer.get("2").toString());
				
				//topicPrefer
				HashMap<String, ArrayList<StringAndFreq>> topicPrefer = new HashMap<String, ArrayList<StringAndFreq>>();
				for (int i=0; i<resultSize; i++){
					String topicName = mainFrame.result_panel.ontologyRow.get(i).get(3);
					String className = mainFrame.result_panel.ontologyRow.get(i).get(2);
					
					ArrayList<StringAndFreq> classList;	
					if (topicPrefer.containsKey(topicName)){		
						classList = topicPrefer.get(topicName);
						
						int classListSize = classList.size();
						int index;
						boolean flag = false;
						for (index = 0; index < classListSize; index ++){
							if (classList.get(index).getTermString().equals(className)){
								flag = true; //classList contains "className"
								break;
							}
						}
						if (flag){
							classList.get(index).addFrequency();
						}else{
							StringAndFreq tempTerm = new StringAndFreq();
							tempTerm.setTermString(className);
							classList.add(tempTerm);
						}
					}else{
						classList = new ArrayList<StringAndFreq>();
						StringAndFreq tempTerm = new StringAndFreq();
						tempTerm.setTermString(className);
						classList.add(tempTerm);
					}
					topicPrefer.put(topicName, classList);			
				}
				
				//to calculate the purity value
				int puritySum = 0;
				for (int i = 0; i < k; i++){
					int max = 0;
					Iterator<Entry<String, Integer>> iterator = classPrefer.get(i+"").entrySet().iterator();
					while (iterator.hasNext()){
						int tempMax = iterator.next().getValue();
						if (tempMax > max){
							max = tempMax;
						}
					}
					//System.out.println("i= "+i+" and max="+max);
					puritySum += max;
				}
				double purity = (double) puritySum / (double) resultSize; 
				mainFrame.result_panel.purity_Ontology.setText(purity+"");

				
				// to calculate the TP, FP, FN, and TN values
				int TPandFP = 0;
				for (int i =0; i < k; i++){//k is the number of clusters
					int tempSum = 0;
					Iterator<Entry<String, Integer>> iterator = classPrefer.get(i+"").entrySet().iterator();
					while (iterator.hasNext()){
						tempSum += iterator.next().getValue();
						}
					TPandFP += UIControl.combination(tempSum, 2);
				}// we then get TP + FP
				
				int TP = 0;
				for (int i =0; i < k; i++){//k is the number of clusters
					int tempSum = 0;
					Iterator<Entry<String, Integer>> iterator = classPrefer.get(i+"").entrySet().iterator();
					while (iterator.hasNext()){
						int tempNum = iterator.next().getValue();
						if (tempNum >= 2)
							tempSum += UIControl.combination(tempNum, 2);
						}
					TP += tempSum;
				}//we then get TP here
				
				int FP = TPandFP - TP;// here the FP
				
				int FN = 0;
				for (int i = 0; i < REALTOPICNUM_28; i++){//REALTOPICNUM_28, REALTOPICNUM_45, here, REALTOPICNUM_? is the real number of topics (or 28)
					ArrayList<StringAndFreq> classList = topicPrefer.get((i+1)+"");
					int listSize = classList.size();
					for (int n = 0; n < listSize; n++){
						for (int m = n + 1; m < listSize; m++){
							FN += classList.get(n).getFrequency() * classList.get(m).getFrequency();
						}
					}
				}// we then get FN here
				
				int TN = UIControl.combination(resultSize, 2) - TP - FP - FN;// we then get TN 
				
				System.out.println("TP= "+TP);
				System.out.println("FP= "+FP);
				System.out.println("TN= "+TN);
				System.out.println("FN= "+FN);
						
				//to calculate the precision, recall and f-score
				double precision = (double) TP / (double) (TP+FP); 
				mainFrame.result_panel.p_Ontology.setText(precision+"");
				
				double recall = (double) TP / (double) (TP+FN); 
				mainFrame.result_panel.r_Ontology.setText(recall+"");
				
				double beta = 1.0;
				double fscore = ((beta * beta + 1) * precision * recall) / (beta * beta * precision + recall); 
				mainFrame.result_panel.f_Ontology.setText(fscore+"");
				
				System.out.println("Precision= "+precision);
				System.out.println("Recall= "+recall);
				System.out.println("F-score= "+fscore);
				System.out.println("Purity= "+purity);
			}
			
		});
	}
}
