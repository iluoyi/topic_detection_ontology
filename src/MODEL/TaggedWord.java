package MODEL;

public class TaggedWord {

		
		public TaggedWord(String srcWord, String tag) {
			super();
			this.srcWord = srcWord; 
			this.tag = tag;
			this.frequency = 1;
		}
		private String srcWord; 
		private String tag; 
		private int frequency;
		/**/
		public String getSrcWord() {
			return srcWord;
		}
		public void setSrcWord(String srcWord) {
			this.srcWord = srcWord;
		}
		public String getTag() {
			return tag;
		}
		public void setTag(String tag) {
			this.tag = tag;
		}
		public int getFrequency() {
			return frequency;
		}
		public void setFrequency(int frequency) {
			this.frequency = frequency;
		}
		public void addFrequency()
		{
			this.frequency++;
		}

}
