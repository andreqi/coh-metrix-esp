package pucp.edu.cohmetrixesp.metrics;

import java.util.List;

import edu.upc.freeling.ListSentence;
import edu.upc.freeling.ListSentenceIterator;
import edu.upc.freeling.ListWordIterator;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;

public class DescriptiveAnalyzer implements ICohAnalyzer{
	// puede ser util para luego pasarle multiples analysis al texto
	// aunque deberia devolver un map<keyMetrica, valor>
	static DescriptiveAnalyzer instance;
	public static DescriptiveAnalyzer getInstance() {
		if (instance == null) return instance = new DescriptiveAnalyzer();
		return instance;
	}
	DescriptiveAnalyzer() {
	}
	@Override
	public void analyze() {
		// TODO Auto-generated method stub
	}	
	
	public long numberOfParagraphs(CohText text) {
		List<CohParagraph> paragraphs = text.getParagraphs();
		return paragraphs.size();
	}
	
	public long numberOfSentences(CohText text) {
		List<CohParagraph> paragraphs = text.getParagraphs();
		long ans = 0;
		for (CohParagraph p : paragraphs) {
			ans += numberOfSentences(p);
		}
		return ans;
	}
	
	public long numberOfSentences(CohParagraph p) {
		ListSentence ls = p.getSentences();
		return ls.size();
	}
	
	public long numberOfWords(CohText text) {
		List<CohParagraph> paragraphs = text.getParagraphs();
		long ans = 0;
		for (CohParagraph p: paragraphs) {
			ans += numberOfWords(p);
			
		}
		return ans;
	}
	
	public long numberOfWords(CohParagraph p) {
		ListSentence ls = null;
		ListSentenceIterator lsIt = null;
		ListWordIterator lwIt = null; 
		Sentence sen = null;
		Word word = null;
		String tag = null;
		long ans = 0;
		lsIt = new ListSentenceIterator(ls);
		while (lsIt.hasNext()) {
			sen = lsIt.next();
			lwIt = new ListWordIterator(sen);
			while (lwIt.hasNext()) {
				word = lwIt.next();
				tag = word.getTag();
				// sacado de tagset.dat
				// F : puntuacion
				// Z : numeros
				if (tag.startsWith("F") || tag.startsWith("Z"))
					continue;
				ans++;
			}
		}
		return ans;
	}
	
	public double meanNumberOfWords(CohText text) {
		return 0;
	}
	
}
