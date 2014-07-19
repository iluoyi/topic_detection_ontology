package MODEL;

/*
 * A general class for general use
 */

public class StringAndFreq {
	String termString = new String();
	int frequency = 1;
	
	public void setTermString(String name){
		termString=name;
	}
	public String getTermString(){
		return termString;
	}

	public void setFrequency(int freq){
		frequency=freq;
	}
	public int getFrequency(){
		return frequency;
	}
	public void addFrequency(){
		frequency++;
	}
}
