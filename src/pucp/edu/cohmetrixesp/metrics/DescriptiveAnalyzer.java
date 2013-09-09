package pucp.edu.cohmetrixesp.metrics;

import java.util.List;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

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
	
	public CohStats lenghtParagraphs(CohText text) {
		CohStats ans = new CohStats();
		DescriptiveStatistics desc = new DescriptiveStatistics();
		for (CohParagraph par : text) 
			desc.addValue(par.length());
		ans.setMean(desc.getMean());
		ans.setStdDeviation(desc.getStandardDeviation());
		return ans;
	}
	
	private boolean isWord(Word w) {
		return !w.getTag().startsWith("F") && !w.getTag().startsWith("Z");
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
					desc.addValue(numberOfSyllables(strWord));
				}
			}
		}
		ans.setMean(desc.getMean());
		ans.setStdDeviation(desc.getStandardDeviation());
		return ans;
	}	
	
}
