package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class Result_Panel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	JTextField kNum = new JTextField("");
	JTextField inputDocNum = new JTextField("");
	JTextField p_Kmeans = new JTextField("");
	JTextField r_Kmeans = new JTextField("");
	JTextField f_Kmeans = new JTextField("");
	JTextField purity_Kmeans = new JTextField("");
	JTextField p_Ontology = new JTextField("");
	JTextField r_Ontology = new JTextField("");
	JTextField f_Ontology = new JTextField("");
	JTextField purity_Ontology = new JTextField("");
	JButton getCenters1 = new JButton("Randomly");
	JButton getCenters2 = new JButton("Algorithm");
	JButton insertCenter = new JButton("Insert");
	JButton deleteCenter = new JButton("Delete");
	JButton getKmeansResult = new JButton("Get the result of traditional K-means method");
	JButton getOntologyResult = new JButton("Get the result after introducing the domain Ontology");
	JTable clusterCenter, kmeansResult, ontologyResult;
	
	Vector<String> centerColumn = new Vector<String>();
	Vector<Vector<String>> centerRow = new Vector<Vector<String>>();
	Vector<String> kmeansColumn = new Vector<String>();
	Vector<Vector<String>> kmeansRow = new Vector<Vector<String>>();
	Vector<String> ontologyColumn = new Vector<String>();
	Vector<Vector<String>> ontologyRow = new Vector<Vector<String>>();
	
	public Result_Panel(){
		this.setLayout(null);
		Font labelFont = new Font("Arial", Font.PLAIN, 15 );
		
		//put in the information to get centers
		JLabel label1 = new JLabel("The number of clustering centers: k = ");
		label1.setFont(labelFont);
		label1.setBounds(10, 25, 250, 15);
		this.add(label1);
		kNum.setBounds(260, 20, 60, 25);
		kNum.setEditable(true);
		this.add(kNum);	
		getCenters1.setBounds(335, 20, 90, 25);
		this.add(getCenters1);
		getCenters2.setBounds(435, 20, 90, 25);
		this.add(getCenters2);
		
		JLabel label2 = new JLabel("or add a center by hand: docNum = ");
		label2.setFont(labelFont);
		label2.setBounds(550, 25, 250, 15);
		this.add(label2);
		inputDocNum.setBounds(790, 20, 70, 25);
		inputDocNum.setEditable(true);
		this.add(inputDocNum);
		insertCenter.setBounds(870, 20, 80, 25);
		this.add(insertCenter);
		
		JLabel label3 = new JLabel("or choose and delete one");
		label3.setFont(labelFont);
		label3.setBounds(975, 25, 200, 15);
		this.add(label3);
		deleteCenter.setBounds(1145, 20, 80, 25);
		this.add(deleteCenter);
		
		//Now put in the table of clustering centers
		centerColumn.add("docNum");
		centerColumn.add("title");
		centerColumn.add("class");
		centerColumn.add("keywords");

//		for (int i=0; i<10; i++){
//			Vector<String> row = new Vector<String>();
//			row.add("");
//			row.add("");
//			row.add("");
//			row.add("");
//			centerRow.add(row);
//		}

		clusterCenter = new JTable(new AllTableModel(centerRow, centerColumn));
		clusterCenter.setRowHeight(20);
		clusterCenter.setRowSelectionAllowed(true);
		clusterCenter.setSelectionBackground(Color.lightGray);
		clusterCenter.setSelectionForeground(Color.white);
		clusterCenter.setGridColor(Color.black);
		clusterCenter.setShowGrid(true);
		clusterCenter.setShowHorizontalLines(true);
		clusterCenter.setShowVerticalLines(true);
		clusterCenter.setBackground(Color.white);
	    JScrollPane tablePane1 = new JScrollPane(clusterCenter);
	    tablePane1.setBackground(Color.white);
	    tablePane1.setBounds(10, 55, 1220, 150);
	    this.add(tablePane1);
	    
		//Now put in the information of kmeans results
	    getKmeansResult.setBounds(10, 220, 300, 25);
		this.add(getKmeansResult);
		
		kmeansColumn.add("docNum");
	    kmeansColumn.add("title");
	    kmeansColumn.add("class");
	    kmeansColumn.add("topic");
	    kmeansColumn.add("keywords");
		
		for (int i=0; i<20; i++){
			Vector<String> row = new Vector<String>();
			row.add("");
			row.add("");
			row.add("");
			row.add("");
			kmeansRow.add(row);
		}

		kmeansResult = new JTable(new AllTableModel(kmeansRow, kmeansColumn));
		kmeansResult.setRowHeight(20);
		kmeansResult.setRowSelectionAllowed(true);
		kmeansResult.setSelectionBackground(Color.lightGray);
		kmeansResult.setSelectionForeground(Color.white);
		kmeansResult.setGridColor(Color.black);
		kmeansResult.setShowGrid(true);
		kmeansResult.setShowHorizontalLines(true);
		kmeansResult.setShowVerticalLines(true);
		kmeansResult.setBackground(Color.white);
	    JScrollPane tablePane2 = new JScrollPane(kmeansResult);
	    tablePane2.setBackground(Color.white);
	    tablePane2.setBounds(10, 250, 600, 390);
	    
	    this.add(tablePane2);
	    
		JLabel precision_k = new JLabel("Precision: ");
		int startLine = 30;
		int topLine = 660;
		precision_k.setFont(labelFont);
		precision_k.setBounds(startLine, topLine, 80, 15);
		this.add(precision_k);
		p_Kmeans.setBounds(startLine+70, topLine-5, 60, 25);
		this.add(p_Kmeans);
		
		JLabel recall_k = new JLabel("Recall: ");
		recall_k.setFont(labelFont);
		recall_k.setBounds(startLine+150, topLine, 80, 15);
		this.add(recall_k);
		r_Kmeans.setBounds(startLine+200, topLine-5, 60, 25);
		this.add(r_Kmeans);
		
		JLabel fscore_k = new JLabel("F-score: ");
		fscore_k.setFont(labelFont);
		fscore_k.setBounds(startLine+275, topLine, 80, 15);
		this.add(fscore_k);
		f_Kmeans.setBounds(startLine+335, topLine-5, 60, 25);
		this.add(f_Kmeans);
		
		JLabel purity_k = new JLabel("Purity: ");
		purity_k.setFont(labelFont);
		purity_k.setBounds(startLine+415, topLine, 80, 15);
		this.add(purity_k);
		purity_Kmeans.setBounds(startLine+465, topLine-5, 60, 25);
		this.add(purity_Kmeans);
	    
	    
	  //Now put in the information of ontology results
	    getOntologyResult.setBounds(630, 220, 330, 25);
		this.add(getOntologyResult);

	    ontologyColumn.add("docNum");
	    ontologyColumn.add("title");
	    ontologyColumn.add("class");
	    ontologyColumn.add("topic");
	    ontologyColumn.add("keywords");

		for (int i=0; i<20; i++){
			Vector<String> row = new Vector<String>();
			row.add("");
			row.add("");
			row.add("");
			ontologyRow.add(row);
		}

		ontologyResult = new JTable(new AllTableModel(ontologyRow, ontologyColumn));
		ontologyResult.setRowHeight(20);
		ontologyResult.setRowSelectionAllowed(true);
		ontologyResult.setSelectionBackground(Color.lightGray);
		ontologyResult.setSelectionForeground(Color.white);
		ontologyResult.setGridColor(Color.black);
		ontologyResult.setShowGrid(true);
		ontologyResult.setShowHorizontalLines(true);
		ontologyResult.setShowVerticalLines(true);
		ontologyResult.setBackground(Color.white);
	    JScrollPane tablePane3 = new JScrollPane(ontologyResult);
	    tablePane3.setBackground(Color.white);
	    tablePane3.setBounds(630, 250, 600, 390);
	    this.add(tablePane3);
	   
	    JLabel precision_o = new JLabel("Precision: ");
		startLine = startLine+620;
		precision_o.setFont(labelFont);
		precision_o.setBounds(startLine, topLine, 80, 15);
		this.add(precision_o);
		p_Ontology.setBounds(startLine+70, topLine-5, 60, 25);
		this.add(p_Ontology);
		
		JLabel recall_o = new JLabel("Recall: ");
		recall_o.setFont(labelFont);
		recall_o.setBounds(startLine+150, topLine, 80, 15);
		this.add(recall_o);
		r_Ontology.setBounds(startLine+200, topLine-5, 60, 25);
		this.add(r_Ontology);
		
		JLabel fscore_o = new JLabel("F-score: ");
		fscore_o.setFont(labelFont);
		fscore_o.setBounds(startLine+275, topLine, 80, 15);
		this.add(fscore_o);
		f_Ontology.setBounds(startLine+335, topLine-5, 60, 25);
		this.add(f_Ontology);
		
		JLabel purity_o = new JLabel("Purity: ");
		purity_o.setFont(labelFont);
		purity_o.setBounds(startLine+415, topLine, 80, 15);
		this.add(purity_o);
		purity_Ontology.setBounds(startLine+465, topLine-5, 60, 25);
		this.add(purity_Ontology);

	}
}
