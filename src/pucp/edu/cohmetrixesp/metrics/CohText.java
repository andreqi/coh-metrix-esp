package pucp.edu.cohmetrixesp.metrics;

import java.util.Iterator;
import java.util.List;

import edu.upc.freeling.Maco;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;


public class CohText implements Iterable<CohParagraph> {
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
			p.posTagging(freeling.getPosTagger());
			p.neClassification(freeling.getNec());
			p.senses(freeling.getSenses());
			p.disambiguation(freeling.getDisambiguator());
			p.chunkParsing(freeling.getChunkParser());
		}
	}
	List<CohParagraph> getParagraphs() {
		return paragraphs;
	}
	@Override
	public Iterator<CohParagraph> iterator() {
		return paragraphs.iterator();
	}
}
