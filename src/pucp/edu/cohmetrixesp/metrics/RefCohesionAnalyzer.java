package pucp.edu.cohmetrixesp.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.structs.FreelingWordIterable;

public class RefCohesionAnalyzer implements ICohAnalyzer {

	private static RefCohesionAnalyzer instance = new RefCohesionAnalyzer();
	String NOUN_PAT = "N.*";
	
	public static RefCohesionAnalyzer getInstance() {
		return instance;
	}
	
	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		
	}
	
	private RefCohesionAnalyzer() { }
	
	private int haveSameNoun(CohParagraph prevParagraph,
			Sentence prevSentence, CohParagraph p, Sentence s) {
		List<Word> nounPrev = getTaggedWords(prevSentence, NOUN_PAT);
		List<Word> nounCur = getTaggedWords(s, NOUN_PAT);
		TreeSet<String> nouns = new TreeSet<>();
		for (Word w: nounPrev) nouns.add(w.getForm());
		for (Word w: nounCur)  {
			if (nouns.contains(w.getForm())) {
				//System.out.println(w.getForm());
				return 1;		
			} 
		}
		return 0;
	}

	private List<Word> getTaggedWords(Sentence s, String pat) {
		FreelingWordIterable it = new FreelingWordIterable(s);
		ArrayList<Word> ans = new ArrayList<>();
		for (Word w : it) {
			if (w.getTag().matches(pat)){
				ans.add(w);
			}
		}
		return ans;
	}
	
	public double meanNounOverlapLocal(CohText text) {
		double ans = 0;
		Sentence prevSentence = null;
		CohParagraph prevParagraph = null;
		int total = 0;
		for (CohParagraph p : text) {
			for (Sentence s : p) {
				FreelingWordIterable it = new FreelingWordIterable(s);
				if (prevSentence != null) {
					total++;
					int same = haveSameNoun(prevParagraph, prevSentence, p, s);
					System.out.println(same);
					ans += same;
				}
				prevSentence = s;
				prevParagraph = p;
			}
		}
		if (total == 0) return 0;
		return ans / total;
	}
	
	public double meanNounOverlapGlobal(CohText text) {
		double ans = 0;
		int len = 0;
		HashMap<String, Integer> nouns = new HashMap<>(); 
		for (CohParagraph p: text) {
			for (Sentence s : p) {
				len++;
				//TODO: ver si varia si usamos solo los nouns unicos
				List<Word> SNouns = getTaggedWords(s, NOUN_PAT);
				for (Word w: SNouns) {
					int prev = 0;
					if (nouns.containsKey(w.getForm()))
						prev = nouns.get(w.getForm());
					nouns.put(w.getForm(), prev + 1);
				}
			}
		}
		if (len==0) return 0;
		len *= (len-1);
		for (Entry<String, Integer> entry : nouns.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
			ans += (double)(entry.getValue()*(entry.getValue()-1));
		}
		ans /= len;
		return ans;
	}

}
