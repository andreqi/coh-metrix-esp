package pucp.edu.cohmetrixesp.metrics;

import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohStats;
import pucp.edu.cohmetrixesp.structs.CohSyllable;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.structs.FreelingWordIterable;
import pucp.edu.cohmetrixesp.utils.SyllableMatcher;
import edu.upc.freeling.ListSentence;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;

public class DescriptiveAnalyzer implements ICohAnalyzer{
	// puede ser util para luego pasarle multiples analysis al texto
	// aunque deberia devolver un map<keyMetrica, valor>
	static String NUMBER_OF_PARAGRAPHS = "DESPC";
	static String NUMBER_OF_SENTENCES = "DESSC";
	static String NUMBER_OF_WORDS = "DESWC";
	static String PARAGRAPH_LENGTH_MEAN = "DESPL";
	static String PARAGRAPH_LENGTH_DEV = "DESPLd";
	static String NUMBER_OF_WORDS_IN_SENTECES_MEAN = "DESSL";
	static String NUMBER_OF_WORDS_IN_SENTECES_DEV = "DESSLd";
	static String NUMBER_OF_SYLLABLES_IN_WORDS_MEAN = "DESWLsy";
	static String NUMBER_OF_SYLLABLES_IN_WORDS_DEV = "DESWLsyd";
	
	static String NUMBER_OF_LETTERS_IN_WORDS_MEAN = "DESWLlt";
	static String NUMBER_OF_LETTERS_IN_WORDS_DEV = "DESWLltd";
	static DescriptiveAnalyzer instance;
	public static DescriptiveAnalyzer getInstance() {
		if (instance == null) return instance = new DescriptiveAnalyzer();
		return instance;
	}
	
	private DescriptiveAnalyzer() {}

	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		CohStats ans = null;
		double dAns = 0.0;
		dAns = numberOfParagraphs(text);
		toFill.put(NUMBER_OF_PARAGRAPHS, dAns);
		
		dAns = numberOfSentences(text);
		toFill.put(NUMBER_OF_SENTENCES, dAns);
		
		dAns = numberOfWords(text);
		toFill.put(NUMBER_OF_WORDS, dAns);
		
		ans = lengthOfParagraphs(text);
		toFill.put(PARAGRAPH_LENGTH_MEAN, ans.getMean());
		toFill.put(PARAGRAPH_LENGTH_MEAN, ans.getStdDeviation());
		
		ans = numberOfWordsInSentences(text);
		toFill.put(NUMBER_OF_WORDS_IN_SENTECES_MEAN, ans.getMean());
		toFill.put(NUMBER_OF_WORDS_IN_SENTECES_DEV, ans.getStdDeviation());
		
		ans = numberOfSyllablesInWords(text);
		toFill.put(NUMBER_OF_SYLLABLES_IN_WORDS_MEAN, ans.getMean());
		toFill.put(NUMBER_OF_SYLLABLES_IN_WORDS_DEV, ans.getStdDeviation());
			
		ans = numberOfLettersInWords(text);
		toFill.put(NUMBER_OF_LETTERS_IN_WORDS_MEAN, ans.getMean());
		toFill.put(NUMBER_OF_LETTERS_IN_WORDS_DEV, ans.getStdDeviation());
		
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
		long ans = 0;
		for (Sentence s: p) {
			FreelingWordIterable sIt = new FreelingWordIterable(s);
			for (Word w: sIt) {
				if (isWord(w)) {
					ans++;
				}
			}
		}
		return ans;
	}
	
	
	private boolean isWord(Word w) {
		return !w.getTag().startsWith("F") && 
			   !w.getTag().startsWith("Z") &&
			   !w.getTag().startsWith("W");
	}
	
	public CohStats numberOfWordsInSentences(CohText text) {
		CohStats ans = new CohStats();
		DescriptiveStatistics desc = new DescriptiveStatistics();
		for (CohParagraph paragraph: text) {
			for (Sentence sentence : paragraph) {
				FreelingWordIterable words = new FreelingWordIterable(sentence);
				double n = 0;
				for (Word word : words) {
					if (isWord(word)) {
						n += 1;
					}
				}
				desc.addValue(n);
			}	
		}
		ans.setMean(desc.getMean());
		ans.setStdDeviation(desc.getStandardDeviation());
		return ans;
	}
	
	public CohStats lengthOfParagraphs(CohText text) {
		CohStats ans = new CohStats();
		DescriptiveStatistics desc = new DescriptiveStatistics();
		for (CohParagraph p: text) {
			desc.addValue(p.length());
		}
		ans.setMean(desc.getMean());
		ans.setStdDeviation(desc.getStandardDeviation());
		return ans;
	}
	
	
	public CohStats numberOfLettersInWords(CohText text) {
		CohStats ans = new CohStats();
		DescriptiveStatistics desc = new DescriptiveStatistics();
		for (CohParagraph paragraph : text) {
			for (Sentence sentence : paragraph) {
				FreelingWordIterable sentenceIt = new FreelingWordIterable(sentence);
				for (Word word : sentenceIt) if (isWord(word)){
					desc.addValue(word.getSpanFinish()-word.getSpanStart());
				}
			}
		}
		ans.setMean(desc.getMean());
		ans.setStdDeviation(desc.getStandardDeviation());
		return ans;
	}	
	
	long numberOfSyllables(String word) {
		String CVRep = CohSyllable.getCVRepresentation(word);
		return SyllableMatcher.getNumberOfSyllable(CVRep);
	}
	
	public CohStats numberOfSyllablesInWords(CohText text) {
		CohStats ans = new CohStats();
		DescriptiveStatistics desc = new DescriptiveStatistics();
		for (CohParagraph paragraph : text) {
			for (Sentence sentence : paragraph) {
				FreelingWordIterable sentenceIt = new FreelingWordIterable(sentence);
				for (Word word : sentenceIt) if (isWord(word)){
					String strWord = paragraph.substring(word.getSpanStart(), word.getSpanFinish());
					if (strWord == "") {
					//	System.out.println(word.getForm());
					//	System.out.println(word.getTag());
					}
					desc.addValue(numberOfSyllables(strWord));
				}
			}
		}
		ans.setMean(desc.getMean());
		ans.setStdDeviation(desc.getStandardDeviation());
		return ans;
	}
}
