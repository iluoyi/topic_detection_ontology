package ICTCLAS.I3S.test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class ReadIn {

	public String readData(String fileName){

	   BufferedReader br = null;
	   String line;
	   String result="";
	 
	    try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName),"UTF-8"));
			while ((line = br.readLine()) != null){
	    		result=result+line;
	    	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	    	
		return result;
	}
	
	public static void main(String[] args){
		ReadIn reader = new ReadIn();
		System.out.println(reader.readData("testData.txt"));
	}
}
