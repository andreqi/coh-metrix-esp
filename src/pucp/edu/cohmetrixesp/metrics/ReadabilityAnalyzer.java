package pucp.edu.cohmetrixesp.metrics;

import java.util.Map;

import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.structs.FreelingWordIterable;

public class ReadabilityAnalyzer implements ICohAnalyzer {

	private String FLESH_FERNANDEZ = "RDFFGL";
	private DescriptiveAnalyzer desc = DescriptiveAnalyzer.getInstance();
	static private ReadabilityAnalyzer instance = new ReadabilityAnalyzer();
	
	public static ReadabilityAnalyzer getInstance() {
		return instance;
	}

	private ReadabilityAnalyzer () {}

	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		toFill.put(FLESH_FERNANDEZ, fleshFernandezFormula(text));
	}
	
	public double fleshFernandezFormula(CohText text) {
		double avgSyllables = desc.numberOfSyllablesInWords(text).getMean();
		//double avgWords = desc.numberOfWordsInSentences(text).getMean();
		double avgSentences = 0, words = 0;
		for (CohParagraph p: text) {
			for (Sentence s: p) {
				avgSentences++;
				for (Word w : new FreelingWordIterable(s)) {
					words++;
				}
			}
		}
		if (words != 0) avgSentences /= words;
		double ans = 206.84 - 60 * avgSyllables - 102 * avgSentences;		
		return ans;
	}

}
