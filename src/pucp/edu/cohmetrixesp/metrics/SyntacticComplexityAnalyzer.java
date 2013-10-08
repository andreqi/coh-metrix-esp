package pucp.edu.cohmetrixesp.metrics;

import java.util.Map;

import edu.upc.freeling.Sentence;
import edu.upc.freeling.TreeNode;
import edu.upc.freeling.Word;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohText;

public class SyntacticComplexityAnalyzer implements ICohAnalyzer {

	private static SyntacticComplexityAnalyzer instance = new SyntacticComplexityAnalyzer();
	private String NOUN_PHRASE_TAG = "sn";
	private String[] MODIFIERS_NP = {"A", "D", "Z"};
	private String MODIFIERS_PER_NOUN_PHRASE = "SYNNP";
	private SyntacticComplexityAnalyzer() {
	}

	public static SyntacticComplexityAnalyzer getInstance() {
		return instance;
	}

	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		toFill.put(MODIFIERS_PER_NOUN_PHRASE, meanModifiersPerNP(text) );
	}

	private void countNodes(TreeNode node, int count[], String nodeTag, String [] words, boolean passed) {
		long nch = node.numChildren();
		if ( nch == 0) {
			// hoja
			Word w = node.getInfo().getWord();
			if (!passed) return;
			int add = 0;
			String tag = w.getTag();
			for (String s : words) {
				if (tag.startsWith(s)) {
					add = 1; 
					break;
				}
			}
			count[1] += add;
			return ;
		}
		TreeNode child = null;
		if (node.getInfo().getLabel().startsWith(nodeTag)) {
			count[0] += 1;
			passed = true;
		}
		for (int i = 0; i < nch; i++) {
			child = node.nthChildRef(i);
			countNodes(child, count, nodeTag, words, passed);
		}
	}

	public double meanModifiersPerNP(CohText text) {
		int [] count = new int[2];
		for (CohParagraph p : text) {
			for (Sentence s : p) {
				countNodes(s.getParseTree(),count , NOUN_PHRASE_TAG,
						MODIFIERS_NP, false);
			}
		}
		if (count[0] == 0) return 0;
		return (double)count[1] / (double)count[0];
	}

}
