package pucp.edu.cohmetrixesp.metrics;

import java.util.Properties;

import edu.upc.freeling.ListAnalysis;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.ListWordIterator;
import edu.upc.freeling.Maco;
import edu.upc.freeling.MacoOptions;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Word;

public class DescriptiveAnalyzer {
	Properties files;
	Tokenizer tokenizer;
	Splitter splitter;
	Maco macAnalysis;
	String base_path, LANG, DATA;

	Maco get_maco() {
		if (macAnalysis != null) return macAnalysis;
		MacoOptions op = new MacoOptions(LANG);

		op.setActiveModules(false, true, true, true, true, true, true, true,
				true, true);

		op.setDataFiles("", DATA + LANG + "/locucions.dat", DATA + LANG
				+ "/quantities.dat", DATA + LANG + "/afixos.dat", DATA + LANG
				+ "/probabilitats.dat", DATA + LANG + "/dicc.src", DATA + LANG
				+ "/np.dat", DATA + "common/punct.dat");
		Maco mac = new Maco(op);
		return macAnalysis = mac;
	}
	
	Tokenizer get_tokenizer() {
		if (tokenizer == null) {
			tokenizer = new Tokenizer(base_path + "/tokenizer.dat");
		}
		return tokenizer;
	}

	Splitter get_splitter() {
		if (splitter == null) {
			splitter = new Splitter(base_path + "/splitter.dat");
		}
		return splitter;
	}

	public DescriptiveAnalyzer(Properties files) {
		this.files = files;
		tokenizer = null;
		splitter = null;
		LANG = files.getProperty("lang");
		DATA = files.getProperty("data");
		base_path = files.getProperty("data") + files.getProperty("lang");
	}

	// number of hard returns
	public int number_of_paragraphs(String s) {
		int prev = -2;
		int ans = 0;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == '\n') {
				ans += ((prev + 1) == i) ? 0 : 1;
				prev = i;
			}
		}
		if (prev != s.length())
			ans++;
		return ans;
	}

	public int number_of_words(String text) {
		Tokenizer tok = get_tokenizer();
		Splitter sp = get_splitter();
		Maco maco = get_maco();
		
		ListWord words = tok.tokenize(text);
		ListSentence sentences = sp.split(words, false);
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

}
