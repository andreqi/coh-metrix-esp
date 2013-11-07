package pucp.edu.cohmetrixesp.metrics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeSet;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.moment.Mean;

import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohStats;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.structs.FreelingWordIterable;

public class RefCohesionAnalyzer implements ICohAnalyzer {

	private static RefCohesionAnalyzer instance = new RefCohesionAnalyzer();
	String NOUN_PAT = "N.*";
	String NOUN_OVERLAP_LOCAL = "CRFNO1";
	String NOUN_OVERLAP_GLOBAL = "CRFNOa";
	String ARGUMENT_OVERLAP_LOCAL = "CRFAO1";
	String ARGUMENT_OVERLAP_GLOBAL = "CRFAOa";
	String STEM_OVERLAP_LOCAL = "CRFSO1";
	String STEM_OVERLAP_GLOBAL = "CRFSOa";
	String CONTENT_WORD_OVERLAP_LOCAL = "CRFCWO1";
	String CONTENT_WORD_OVERLAP_GLOBAL = "CRFCWOa";
	String CONTENT_WORD_OVERLAP_LOCAL_d = "CRFCWO1d";
	String CONTENT_WORD_OVERLAP_GLOBAL_d = "CRFCWOad";
	String ANAPHOR_OVERLAP_LOCAL = "CRFANP1";
	String ANAPHOR_OVERLAP_GLOBAL = "CRFANP1a";

	public static RefCohesionAnalyzer getInstance() {
		return instance;
	}

	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		CohStats ans = null;
		toFill.put(NOUN_OVERLAP_LOCAL, nounOverlapLocal(text));
		toFill.put(NOUN_OVERLAP_GLOBAL, nounOverlapGlobal(text));

		toFill.put(ARGUMENT_OVERLAP_LOCAL, argumentOverlapLocal(text));
		toFill.put(ARGUMENT_OVERLAP_GLOBAL, argumentOverlapGlobal(text));

		toFill.put(STEM_OVERLAP_GLOBAL, steamOverlapGlobal(text));
		toFill.put(STEM_OVERLAP_LOCAL, stemOverlapLocal(text));

		ans = contentWordOverlapGlobal(text);
		toFill.put(CONTENT_WORD_OVERLAP_GLOBAL, ans.getMean());
		toFill.put(CONTENT_WORD_OVERLAP_GLOBAL_d, ans.getStdDeviation());

		ans = contentWordOverlapLocal(text);
		toFill.put(CONTENT_WORD_OVERLAP_LOCAL, ans.getMean());
		toFill.put(CONTENT_WORD_OVERLAP_LOCAL_d, ans.getStdDeviation());

		toFill.put(ANAPHOR_OVERLAP_GLOBAL, anaphorOverlapGlobal(text));
		toFill.put(ANAPHOR_OVERLAP_LOCAL, anaphorOverlapLocal(text));
	}

	private RefCohesionAnalyzer() {
	}

	private List<Word> getTaggedWords(Sentence s, String pat) {
		FreelingWordIterable it = new FreelingWordIterable(s);
		String patsu = pat.charAt(0) + "";
		ArrayList<Word> ans = new ArrayList<>();
		for (Word w : it) {
			if (w.getTag().startsWith(patsu)) {
				ans.add(w);
			}
		}
		return ans;
	}

	private SentenceTester nounOverlapTester = new SentenceTester() {
		@Override
		public double sameFeature(Sentence prev, Sentence cur) {
			List<Word> prevNouns = getTaggedWords(prev, "N.*");
			List<Word> curNouns = getTaggedWords(cur, "N.*");
			TreeSet<String> nouns = new TreeSet<>();
			for (Word w : prevNouns)
				nouns.add(w.getForm());
			for (Word w : curNouns)
				if (nouns.contains(w.getForm()))
					return 1;
			return 0;
		}
	};

	public double nounOverlapLocal(CohText text) {
		return iterateOverAdjacentSentences(text, nounOverlapTester).getMean();
	}

	public double nounOverlapGlobal(CohText text) {
		return iterateOverAllPairsOfSentences(text, nounOverlapTester)
				.getMean();
	}

	private SentenceTester argumentOverlapTester = new SentenceTester() {
		@Override
		public double sameFeature(Sentence prev, Sentence cur) {
			List<Word> prevNouns = getTaggedWords(prev, "N.*");
			List<Word> curNouns = getTaggedWords(cur, "N.*");
			List<Word> prevPro = getTaggedWords(prev, "P.*");
			List<Word> curPro = getTaggedWords(cur, "P.*");
			TreeSet<String> usd = new TreeSet<>();
			for (Word w : prevNouns)
				usd.add(w.getLemma());
			for (Word w : prevPro)
				usd.add(w.getForm());
			for (Word w : curNouns)
				if (usd.contains(w.getLemma()))
					return 1;
			for (Word w : curPro)
				if (usd.contains(w.getForm()))
					return 1;
			return 0;
		}
	};

	public double argumentOverlapLocal(CohText text) {
		return iterateOverAdjacentSentences(text, argumentOverlapTester)
				.getMean();
	}

	public double argumentOverlapGlobal(CohText text) {
		return iterateOverAllPairsOfSentences(text, argumentOverlapTester)
				.getMean();
	}

	private SentenceTester stemOverlapTester = new SentenceTester() {
		@Override
		public double sameFeature(Sentence prev, Sentence cur) {
			List<Word> curNouns = getTaggedWords(cur, "N.*");
			TreeSet<String> lemmas = new TreeSet<>();
			for (Word w : new FreelingWordIterable(prev))
				lemmas.add(w.getLemma().toLowerCase());
			for (Word w : curNouns)
				if (lemmas.contains(w.getLemma().toLowerCase()))
					return 1;
			return 0;
		}
	};

	public double stemOverlapLocal(CohText text) {
		return iterateOverAdjacentSentences(text, stemOverlapTester).getMean();
	}

	public double steamOverlapGlobal(CohText text) {
		return iterateOverAllPairsOfSentences(text, stemOverlapTester)
				.getMean();
	}

	private SentenceTester anaphorOverlapTester = new SentenceTester() {
		public double sameFeature(Sentence prev, Sentence cur) {
			List<Word> curNouns = getTaggedWords(cur, "P.*");
			List<Word> prevNouns = getTaggedWords(prev, "P.*");
			TreeSet<String> pro = new TreeSet<>();
			for (Word w : curNouns)
				pro.add(w.getForm().toLowerCase());
			for (Word w : prevNouns)
				if (pro.contains(w.getForm().toLowerCase()))
					;
			return 0;
		};
	};

	public double anaphorOverlapLocal(CohText text) {
		return iterateOverAdjacentSentences(text, anaphorOverlapTester)
				.getMean();
	}

	public double anaphorOverlapGlobal(CohText text) {
		return iterateOverAllPairsOfSentences(text, anaphorOverlapTester)
				.getMean();
	}

	private SentenceTester contentWordOverlapTester = new SentenceTester() {
		@Override
		public double sameFeature(Sentence prev, Sentence cur) {
			double total = prev.size() + cur.size();
			if (total == 0)
				return 0;
			double match = 0;
			TreeSet<String> str = new TreeSet<>();
			for (Word w : new FreelingWordIterable(prev))
				str.add(w.getForm());
			for (Word w : new FreelingWordIterable(cur))
				if (str.contains(w.getForm()))
					match++;
			return match / total;
		}
	};

	public CohStats contentWordOverlapLocal(CohText text) {
		return iterateOverAdjacentSentences(text, contentWordOverlapTester);
	}

	public CohStats contentWordOverlapGlobal(CohText text) {
		return iterateOverAllPairsOfSentences(text, contentWordOverlapTester);
	}

	private CohStats iterateOverAdjacentSentences(CohText text,
			SentenceTester tester) {
		Sentence prev = null;
		SummaryStatistics stats = new SummaryStatistics();
		boolean hay = false;
		for (CohParagraph p : text) {
			for (Sentence s : p) {
				if (prev != null) {
					stats.addValue(tester.sameFeature(prev, s));
					hay = true;
				}
				prev = s;
			}
		}
		if (!hay) return new CohStats(0, 0);
		CohStats ans = new CohStats();
		ans.setMean(stats.getMean());
		ans.setStdDeviation(stats.getStandardDeviation());
		return ans;
	}

	private CohStats iterateOverAllPairsOfSentences(CohText text,
			SentenceTester tester) {
		DescriptiveStatistics stats = new DescriptiveStatistics();
		boolean hay = false;
		for (CohParagraph p1 : text) {
			for (CohParagraph p2: text) {
				for (Sentence s1 : p1) {
					for (Sentence s2: p2){
						stats.addValue(tester.sameFeature(s1, s2));
						hay = true;
					}
				}
			}
		}
		if (!hay) return new CohStats(0, 0);
		CohStats ans = new CohStats(stats.getMean(),
		stats.getStandardDeviation());
		return ans;
	}

	private interface SentenceTester {
		public double sameFeature(Sentence prev, Sentence cur);
	}

}
