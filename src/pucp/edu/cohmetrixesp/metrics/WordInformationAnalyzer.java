package pucp.edu.cohmetrixesp.metrics;

import java.util.List;
import java.util.Map;

import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.structs.FreelingWordIterable;
import edu.upc.freeling.Sentence;
import edu.upc.freeling.Word;

public class WordInformationAnalyzer implements ICohAnalyzer{
	static String NOUN_INCIDENCE = "WRDNOUN" ;
	static String VERB_INCIDENCE = "WRDVERB" ;
	static String ADJECTIVE_INCIDENCE = "WRDADJ" ;
	static String ADVERB_INCIDENCE = "WRDADV" ;
	static String PRONOUN_INCIDENCE = "WRDPRO" ;
	static String FIRST_PERSON_SINGULAR_PRONOUN_INCIDENCE = "WRDPRP1s" ;
	static String FIRST_PERSON_PLURAL_PRONOUN_INCIDENCE = "WRDPRP1p" ;
	static String SECOND_PERSON_PRONOUN_INCIDENCE = "WRDPRP2" ;
	static String THIRD_PERSON_SINGULAR_PRONOUN_INCIDENCE = "WRDPRP3s" ;
	static String THRID_PERSON_PLURAL_PRONOUN_INCIDENCE = "WRDPRP3p" ;

	static double INCIDENCE = 1000. ;
	
	static WordInformationAnalyzer instance;
	
	public static WordInformationAnalyzer getInstance() {
		if (instance == null) return instance = new WordInformationAnalyzer();
		return instance;
	}
	
	private WordInformationAnalyzer() {}

	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		double dAns = 0.0;
		dAns = nounIncidence( text ) ;
		toFill.put( NOUN_INCIDENCE , dAns ) ;
		
		dAns = verbIncidence( text ) ;
		toFill.put( VERB_INCIDENCE , dAns ) ;
		
		dAns = adjectiveIncidence( text ) ;
		toFill.put( ADJECTIVE_INCIDENCE , dAns ) ;
		
		dAns = adverbIncidence( text ) ;
		toFill.put( ADVERB_INCIDENCE , dAns ) ;
		
		dAns = pronounIncidence( text ) ;
		toFill.put( PRONOUN_INCIDENCE , dAns ) ;
		
		dAns = firstPersonSingularPronounIncidence( text ) ;
		toFill.put( FIRST_PERSON_SINGULAR_PRONOUN_INCIDENCE , dAns ) ;
		
		dAns = firstPersonPluralPronounIncidence( text ) ;
		toFill.put( FIRST_PERSON_PLURAL_PRONOUN_INCIDENCE , dAns ) ;
		
		dAns = secondPersonPronounIncidence( text ) ;
		toFill.put( SECOND_PERSON_PRONOUN_INCIDENCE , dAns ) ;
		
		dAns = thirdPersonSingularPronounIncidence( text ) ;
		toFill.put( THIRD_PERSON_SINGULAR_PRONOUN_INCIDENCE , dAns ) ;
		
		dAns = thirdPersonPluralPronounIncidence( text ) ;
		toFill.put( THRID_PERSON_PLURAL_PRONOUN_INCIDENCE , dAns ) ;
	}
	
	public double nounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "N" ) ;
		return ans / INCIDENCE ;
	}
	
	public double verbIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "V" ) ;
		return ans / INCIDENCE ;
	}
	
	public double adjectiveIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "A" ) ;
		return ans / INCIDENCE ;
	}
	
	public double adverbIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "R" ) ;
		return ans / INCIDENCE ;
	}
	
	public double pronounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "PP" ) ;
		return ans / INCIDENCE ;
	}
	
	public double firstPersonSingularPronounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "PP1.S" ) ;
		return ans / INCIDENCE ;
	}
	
	public double firstPersonPluralPronounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "PP1.P" ) ;
		return ans / INCIDENCE ;
	}
	
	public double secondPersonPronounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "PP2" ) ;
		return ans / INCIDENCE ;
	}
	
	public double thirdPersonSingularPronounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "PP3.S" ) ;
		return ans / INCIDENCE ;
	}
	
	public double thirdPersonPluralPronounIncidence( CohText text ){
		List<CohParagraph> paragraphs = text.getParagraphs() ;
		double ans = 0.0 ;
		for( CohParagraph p : paragraphs ) ans += countWordsByTag( p , "PP3.P" ) ;
		return ans / INCIDENCE ;
	}
	
	public double countWordsByTag( CohParagraph p , String tag ){
		double ans = 0.0 ;
		for( Sentence s : p ){
			FreelingWordIterable sIt = new FreelingWordIterable( s ) ;
			for( Word w : sIt ) if( hasTag( w , tag ) ) ans++;
		}
		return ans ;
	}
	
	private boolean hasTag( Word w , String regex ){
		return w.getTag().matches( regex + ".*" ) ;
	}
}
