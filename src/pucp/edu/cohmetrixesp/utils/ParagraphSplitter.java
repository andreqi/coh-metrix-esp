package pucp.edu.cohmetrixesp.utils;

import java.util.ArrayList;
import java.util.List;

import pucp.edu.cohmetrixesp.structs.CohParagraph;

public class ParagraphSplitter {

	public ParagraphSplitter() {
	}

	// number of hard returns
	static public List<CohParagraph> split(String text) {
		int prev = -1;
		ArrayList<CohParagraph> ans = new ArrayList<>();
		text = text.trim();
		for (int i = 0; i < text.length(); i++) {
			if (text.charAt(i) == '\n') {
				if(prev + 1 < i) {
					String paragraph = text.substring(prev+1, i).trim();
					if (paragraph.length() == 0) 
						continue;
					CohParagraph p = new CohParagraph(paragraph);
					ans.add(p);
				}
				prev = i;
			}
		}
		if (prev + 1 != text.length()){
			String par = text.substring(prev+1).trim();
			if (par.length() > 0) { 
				CohParagraph p = new CohParagraph(par);
				ans.add(p);
			}
		}
		return ans;
		
	}
/*
	public int number_of_words(String text) {
		Tokenizer tok = get_tokenizer();
		Splitter sp = get_splitter();
		Maco maco = get_maco();
		
		ListWord words = tok.tokenize(text);
		maco.analyze(sentences);
		
		ListSentenceIterator sentence_iterator = 
				new ListSentenceIterator(sentences);
		
		int ans = 0;
		Word w = null; 
		Sentence s = null;
		ListWordIterator word_iterator = null;
		
		while (sentence_iterator.hasNext()) {
			s = sentence_iterator.next();
			word_iterator = new ListWordIterator(s);
			while (word_iterator.hasNext()) {
				w = word_iterator.next();
				//ListAnalysis anal = w.getAnalysis();
				//System.out.println(anal.size());
				//System.out.println(w.getForm() + " = " + w.getTag() + " = "
				//		+ w.getLemma() + " = ");
				if (w.getTag().compareTo("Fp") == 0 || w.getTag().compareTo("Z") == 0) 
					continue;
				ans++;
			}
		}
		return ans;
	}
*/
}
