package pucp.edu.cohmetrixesp.metrics;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import edu.upc.freeling.ParseTree;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.TreeNode;
import edu.upc.freeling.Word;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.structs.FreelingWordIterable;

public class LexicalDiversityAnalyzer implements ICohAnalyzer{
	//TODO: test
	static private LexicalDiversityAnalyzer instance = new LexicalDiversityAnalyzer();
	private String TYPE_TOKEN_CONTENT = "LDTTRc";
	private String TYPE_TOKEN_ALL = "LDTTRa";
	private char [] banContent = "ZWFIC".toCharArray();

	public static LexicalDiversityAnalyzer getInstance() {
		return instance;
	}

	private LexicalDiversityAnalyzer() {
	}
	
	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		toFill.put(TYPE_TOKEN_ALL, typeTokenRatioAll(text));

		toFill.put(TYPE_TOKEN_CONTENT, typeTokenRatioContent(text));
	}
	
	boolean isContentWord(Word w){
		//Z-W-F-I-C
		String tag = w.getTag();
		for (char c : banContent) {
			if (tag.length() == 0) continue;
			if (tag.charAt(0) == c) return false;
		}
		return true;
	}
	
	public double typeTokenRatioContent (CohText text) {
		double total = 0;
		HashSet<String> content = new HashSet<>();
		for (CohParagraph p: text) {
			for (Sentence s: p) {
				for (Word w: new FreelingWordIterable(s)){
					if (isContentWord(w)) {
						total++;
						content.add(w.getForm());
					}
				}
			}
		}
		if (total <= 0) return 0;
		return content.size()/total;
	}
	
	public double typeTokenRatioAll (CohText text) {
		double total = 0;
		HashSet<String> content = new HashSet<>();
		for (CohParagraph p: text) {
			for (Sentence s: p) {
				for (Word w: new FreelingWordIterable(s)){
						total++;
						content.add(w.getForm());
				}
			}
		}
		if (total <= 0) return 0;
		return content.size()/total;
	}
	
}
