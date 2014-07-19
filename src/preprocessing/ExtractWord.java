package preprocessing;

import java.util.ArrayList;

import MODEL.TaggedWord;

public class ExtractWord {

	public ArrayList<TaggedWord> extractTaggedWords(String taggedSentence, String c) {
	ArrayList<TaggedWord> segmentSentence = new ArrayList<TaggedWord>();
	// 以空格形式分词
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
		// 以空格形式分词
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
		words = extractWords.extractTaggedWords("?/ww 歌名/n ：/wm 跨/v 时代/n 歌手/n");
		for (TaggedWord word : words){
			System.out.println(word.getSrcWord());
		}
	}
}
