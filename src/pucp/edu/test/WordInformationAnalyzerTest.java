package pucp.edu.test;

import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.DescriptiveAnalyzer;
import pucp.edu.cohmetrixesp.metrics.WordInformationAnalyzer;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohStats;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.utils.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ImplFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ParagraphSplitter;

public class WordInformationAnalyzerTest {
	WordInformationAnalyzer desc = WordInformationAnalyzer.getInstance();
	IFreelingAnalyzer freeling = ImplFreelingAnalyzer.getInstance();
	String small, medium;
	Properties prop;
	ParagraphSplitter p_splitter;
	CohText ctSmall, ctMedium;

	@Before
	public void load_test_files() {
		prop = new Properties();
		try {
			small = new String(Files.readAllBytes(Paths
					.get("./testfiles/smallFile.txt")));
			medium = new String(Files.readAllBytes(Paths
					.get("./testfiles/mediumFile.txt")));
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ctSmall = new CohText(small);
		ctMedium = new CohText(medium);
		ctSmall.analyze(freeling);
		ctMedium.analyze(freeling);
		// System.out.println(prop.getProperty("tok_file"));
		// System.out.println(desc.number_of_words(small));
	}

	@Test
	public void nounIncidence(){
		double c1 = desc.nounIncidence( ctSmall ) ;
		double c2 = desc.nounIncidence( ctMedium ) ;
		System.out.println( "noun: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void verbIncidence(){
		double c1 = desc.verbIncidence( ctSmall ) ;
		double c2 = desc.verbIncidence( ctMedium ) ;
		System.out.println( "verb: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void adjectiveIncidence(){
		double c1 = desc.adjectiveIncidence( ctSmall ) ;
		double c2 = desc.adjectiveIncidence( ctMedium ) ;
		System.out.println( "adjective: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void adverbIncidence(){
		double c1 = desc.adverbIncidence( ctSmall ) ;
		double c2 = desc.adverbIncidence( ctMedium ) ;
		System.out.println( "adverb: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void pronounIncidence(){
		double c1 = desc.pronounIncidence( ctSmall ) ;
		double c2 = desc.pronounIncidence( ctMedium ) ;
		System.out.println( "pronoun ALL: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void firstPersonSingularPronounIncidence(){
		double c1 = desc.firstPersonSingularPronounIncidence( ctSmall ) ;
		double c2 = desc.firstPersonSingularPronounIncidence( ctMedium ) ;
		System.out.println( "pronoun 1S: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void firstPersonPluralPronounIncidence(){
		double c1 = desc.firstPersonPluralPronounIncidence( ctSmall ) ;
		double c2 = desc.firstPersonPluralPronounIncidence( ctMedium ) ;
		System.out.println( "pronoun 1P: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void secondPersonPronounIncidence(){
		double c1 = desc.secondPersonPronounIncidence( ctSmall ) ;
		double c2 = desc.secondPersonPronounIncidence( ctMedium ) ;
		System.out.println( "pronoun 2: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void thirdPersonSingularPronounIncidence(){
		double c1 = desc.thirdPersonSingularPronounIncidence( ctSmall ) ;
		double c2 = desc.thirdPersonSingularPronounIncidence( ctMedium ) ;
		System.out.println( "pronoun 3S: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void thirdPersonPluralPronounIncidence(){
		double c1 = desc.thirdPersonPluralPronounIncidence( ctSmall ) ;
		double c2 = desc.thirdPersonPluralPronounIncidence( ctMedium ) ;
		System.out.println( "pronoun 3P: " + c1 + " " + c2 ) ;
	}
}