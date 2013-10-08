package pucp.edu.cohmetrixesp.metrics;

import java.util.Map;

import pucp.edu.cohmetrixesp.structs.CohText;

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
		double avgWords = desc.numberOfWordsInSentences(text).getMean();
		double ans = 206.84  - 0.6 * avgSyllables -1.02 * avgWords;		
		return ans;
	}

}
