package pucp.edu.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.LexicalDiversityAnalyzer;
import pucp.edu.cohmetrixesp.metrics.RefCohesionAnalyzer;
import pucp.edu.cohmetrixesp.metrics.SyntacticComplexityAnalyzer;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.utils.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ImplFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ParagraphSplitter;

public class LexicalDiversityTest {
	LexicalDiversityAnalyzer lex = LexicalDiversityAnalyzer.getInstance();
	SyntacticComplexityAnalyzer syn = SyntacticComplexityAnalyzer.getInstance();
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
	public void test() { 
		lex.typeTokenRatioContent(ctMedium);
		syn.meanModifiersPerNP(ctMedium);
	}

}
