package MODEL;

/**
 * The keyword of a document/topic
 * @author Royal
 *
 */
public class Keyword {
	String wordText = new String();
	int frequency = 0;
	double tfidf = 0;
	
	public Keyword(String name, double weight){
		wordText=name;
		tfidf=weight;
	}
	
	public void setWordText(String name){
		wordText=name;
	}
	public String getWordText(){
		return wordText;
	}
	public void setWeight(double weight){
		tfidf=weight;
	}
	public double getWeight(){
		return tfidf;
	}
	
	public void addWeight(double weight){
		tfidf=tfidf+weight;
	}
	
	public void divideWeight(double num){
		tfidf=tfidf/num;
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