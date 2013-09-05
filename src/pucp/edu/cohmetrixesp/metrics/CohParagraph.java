package pucp.edu.cohmetrixesp.metrics;

import java.util.List;

import edu.upc.freeling.Maco;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;

public class CohParagraph {
	List<Sentence> sentences;
	String text;
	boolean isTitle;

	public String getText() {
		return text;
	}
	
	public List<Sentence> getSentences() {
		if (sentences == null) throw new IllegalStateException("Aun no se hizo el analisis morfologico desde freeling");
		return sentences;
	}
	
	public CohParagraph(String text) {
		this.text = text;
	}
		
	void analyze(Maco maco, Tokenizer tok, Splitter sp ) {
		
	}
	
}
