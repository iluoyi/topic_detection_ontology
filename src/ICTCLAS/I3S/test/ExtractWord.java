package ICTCLAS.I3S.test;

import java.util.ArrayList;

public class ExtractWord {

	public ArrayList<TaggedWord> extractTaggedWords(String taggedSentence, String c) {
	ArrayList<TaggedWord> segmentSentence = new ArrayList<TaggedWord>();
	// �Կո���ʽ�ִ�
	String[] arrTaggedSentence = null;
	arrTaggedSentence = taggedSentence.split("\\s");
	for (int i = 0; i < arrTaggedSentence.length; i++) {
		if (arrTaggedSentence[i].indexOf("/") != -1) {
			String srcWord = arrTaggedSentence[i].substring(0,
					arrTaggedSentence[i].indexOf("/"));
			String tag = arrTaggedSentence[i].substring(
					arrTaggedSentence[i].indexOf("/") + 1,
					arrTaggedSentence[i].indexOf("/") + 2);
			if (tag.contains(c)){
			TaggedWord taggedWord = new TaggedWord(srcWord, tag);
			segmentSentence.add(taggedWord);
			}
		}
	}
	return segmentSentence;
	}
	
	public ArrayList<TaggedWord> extractTaggedWords(String taggedSentence) {
		ArrayList<TaggedWord> segmentSentence = new ArrayList<TaggedWord>();
		// �Կո���ʽ�ִ�
		String[] arrTaggedSentence = null;
		arrTaggedSentence = taggedSentence.split("\\s");
		for (int i = 0; i < arrTaggedSentence.length; i++) {
			if (arrTaggedSentence[i].indexOf("/") != -1) {
				String srcWord = arrTaggedSentence[i].substring(0,
						arrTaggedSentence[i].indexOf("/"));
				String tag = arrTaggedSentence[i].substring(
						arrTaggedSentence[i].indexOf("/") + 1,
						arrTaggedSentence[i].indexOf("/") + 2);
				TaggedWord taggedWord = new TaggedWord(srcWord, tag);
				segmentSentence.add(taggedWord);
			}
		}
		return segmentSentence;
		}
	
	public static void main(String[] args){
		ExtractWord extractWords = new ExtractWord();
		ArrayList<TaggedWord> words = new ArrayList<TaggedWord>();
		words = extractWords.extractTaggedWords("?/ww ����/n ��/wm ��/v ʱ��/n ����/n","n");
		for (TaggedWord word : words){
			System.out.println(word.getSrcWord());
		}
	}
}
