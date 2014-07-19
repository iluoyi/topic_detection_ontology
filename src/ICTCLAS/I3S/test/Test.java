package ICTCLAS.I3S.test;

import java.util.ArrayList;

import ICTCLAS.I3S.AC.*;

public class Test {
public static void main(String[] args)
{
	ArrayList<TaggedWord> words = new ArrayList<TaggedWord>();
	ArrayList<TaggedWord> resultWords = new ArrayList<TaggedWord>();
	
        	try
		{
			ICTCLAS50 testICTCLAS50 = new ICTCLAS50();
			//分词所需库的路径
			String argu = ".";
			//初始化
		if (testICTCLAS50.ICTCLAS_Init(argu.getBytes("GB2312")) == false)
			{
				System.out.println("Init Fail!");
				return;
			}
			else { System.out.println("Init Succeed!"); }

		ReadIn reader = new ReadIn();
String sInput=reader.readData("testData.txt");
byte nativeBytes[] = testICTCLAS50.ICTCLAS_ParagraphProcess(sInput.getBytes("GB2312"), 0, 1);
			//System.out.println(nativeBytes.length);
			String nativeStr = new String(nativeBytes, 0, nativeBytes.length, "GB2312");
			System.out.println(nativeStr);
			ExtractWord extractWords = new ExtractWord();
			
			words = extractWords.extractTaggedWords(nativeStr,"n");
				
			testICTCLAS50.ICTCLAS_Exit();
		}
		catch (Exception ex)
		{
		}
		
		for (TaggedWord oneWord: words){
			
			Boolean flag = true;
			for (TaggedWord word: resultWords){
				if (oneWord.getSrcWord().equals(word.getSrcWord()))
					{word.addFrequency();
					flag = false; }
			}
			if ((flag) && (oneWord.getSrcWord().length()>1)){
				TaggedWord word = new TaggedWord(oneWord.getSrcWord(),oneWord.getTag());
				resultWords.add(word);
			}
		}
		
		for (TaggedWord word: resultWords){
			System.out.print(word.getSrcWord()+" ");
		}
		
		
}
}
