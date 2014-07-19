package MODEL;

/**
 * The record of a term
 * @author Royal
 *
 */
public class Record {
	int docNum = 0;
	int frequency = 0;
	double tfidf = 0;
	
	public Record(int num, int freq){
		docNum=num;
		frequency=freq;
	}
	
	public Record(int num, double weight){
		docNum=num;
		tfidf=weight;
	}
	
	public void setDocNum(int num){
		docNum=num;
	}
	public int getDocNum(){
		return docNum;
	}
	public void setWeight(double weight){
		tfidf=weight;
	}
	public double getWeight(){
		return tfidf;
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
