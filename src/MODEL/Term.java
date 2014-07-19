package MODEL;

import java.util.ArrayList;

/**
 * The data model of a term
 * @author Royal
 *
 */
public class Term {

	String str = new String();
	ArrayList<Record> record = new ArrayList<Record>();
	
	public Term(String name){
		str=name;
	}
	
	public void setTermText(String name){
		str=name;
	}
	
	public String getTermText(){
		return str;
	}
	public void setRecordList(ArrayList<Record> list){
		record = list;
	}
	
	public ArrayList<Record> getRecordList(){
		return record;
	}
	
}
