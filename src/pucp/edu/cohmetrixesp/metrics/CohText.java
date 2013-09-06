package pucp.edu.cohmetrixesp.metrics;

import java.util.List;

import edu.upc.freeling.Maco;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;


public class CohText {
	List<CohParagraph> paragraphs;
	String text;
	public CohText(String text) {
		this.text = text;
		paragraphs = ParagraphSplitter.split(text);
	}
	public void analyze(IFreelingAnalyzer freeling) {
		Maco mf = freeling.getMorfological();
		Tokenizer tok = freeling.getTokenizer();
		Splitter sp = freeling.getSplitter();
		for (CohParagraph p: paragraphs) {
			p.split(tok, sp);
			p.morfological(mf);
		}
	}
	List<CohParagraph> getParagraphs() {
		return paragraphs;
	}
}
