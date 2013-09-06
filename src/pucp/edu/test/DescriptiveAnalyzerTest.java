package pucp.edu.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.CohText;
import pucp.edu.cohmetrixesp.metrics.DescriptiveAnalyzer;
import pucp.edu.cohmetrixesp.metrics.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.metrics.ImplFreelingAnalyzer;
import pucp.edu.cohmetrixesp.metrics.ParagraphSplitter;

public class DescriptiveAnalyzerTest {
	DescriptiveAnalyzer desc = DescriptiveAnalyzer.getInstance();
	IFreelingAnalyzer freeling = ImplFreelingAnalyzer.getInstance();
	String small, medium;
	Properties prop;
	ParagraphSplitter p_splitter;

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
		// System.out.println(prop.getProperty("tok_file"));
		// System.out.println(desc.number_of_words(small));
	}

	@Test
	public void numberOfParagraphsTest() {
		CohText ctSmall = new CohText(small);
		CohText ctMedium = new CohText(medium);
		ctSmall.analyze(freeling);
		ctMedium.analyze(freeling);
		assertEquals("numero de parrafo distintos", 2,
				desc.numberOfParagraphs(ctSmall));
		assertEquals("numero de parrafo distintos", 6,
				desc.numberOfParagraphs(ctMedium));
	}
	
	@Test
	public void numberOfSentencesTest() {
		
	}
}
