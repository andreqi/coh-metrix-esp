package pucp.edu.cohmetrixesp.structs;


import java.util.Iterator;

import edu.upc.freeling.ChartParser;
import edu.upc.freeling.DepTxala;
import edu.upc.freeling.HmmTagger;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWord;
import edu.upc.freeling.Maco;
import edu.upc.freeling.Nec;
import edu.upc.freeling.Senses;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Ukb;

public class CohParagraph implements Iterable<Sentence>{
	ListSentence sentences;
	String text;
	boolean isTitle;

	public String getText() {
		return text;
	}
	
	public ListSentence getSentences() {
		if (sentences == null) throw new IllegalStateException("Aun no se hizo la tokenizacion desde freeling");
		return sentences;
	}
	
	public CohParagraph(String text) {
		this.text = text;
	}
	
	public void split (Tokenizer tok, Splitter sp) {
		ListWord lw = tok.tokenize(this.text);
		sentences = sp.split(lw, true);
	}

	public void morfological(Maco mf) {
		mf.analyze(getSentences());
	}

	public void dependency(DepTxala dep) {
		dep.analyze(getSentences());
	}

	public void chunkParsing(ChartParser parser) {
		parser.analyze(getSentences());
	}

	public void disambiguation(Ukb dis) {
		dis.analyze(getSentences());
	}

	public void senses(Senses sen) {
		sen.analyze(getSentences());
	}

	public void neClassification(Nec nec) {
		nec.analyze(getSentences());
	}

	public void posTagging(HmmTagger tg) {
		tg.analyze(getSentences());
	}

	@Override
	public Iterator<Sentence> iterator() {

		return new Iterator<Sentence>() {
			ListSentenceIterator lsIt = new ListSentenceIterator(sentences);
			@Override
			public boolean hasNext() {
				return lsIt.hasNext();
			}

			@Override
			public Sentence next() {
				return lsIt.next();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}

		};
	}

	public long length() {
		return text.length();
	}

	public String substring(long spanStart, long spanFinish) {
		try {
			//System.out.println(spanStart  + " " + spanFinish);
			//System.out.println(text);
			return text.substring((int)spanStart, (int)spanFinish);
		} catch (Exception e) {
			System.err.println("error en splitter o tokenizer");
			//System.out.println(spanStart  + " " + spanFinish);
		//	System.out.println(text);
		}
		return "";
	}
	
}
