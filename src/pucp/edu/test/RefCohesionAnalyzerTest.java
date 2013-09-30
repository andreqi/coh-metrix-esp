package pucp.edu.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.DescriptiveAnalyzer;
import pucp.edu.cohmetrixesp.metrics.RefCohesionAnalyzer;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.utils.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ImplFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ParagraphSplitter;

public class RefCohesionAnalyzerTest {
		// TODO Auto-generated constructor stub
	RefCohesionAnalyzer ref = RefCohesionAnalyzer.getInstance();
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
		assertEquals( 1. , ref.nounOverlapLocal( ctSmall ) , 1E-9 ) ;
		assertEquals( 1. , ref.nounOverlapGlobal( ctSmall ) , 1E-9 ) ;
		HashMap<String, Double> hash = new HashMap<>();
		ref.analyze(hash,ctMedium);
		for (Entry<String, Double> e: hash.entrySet())
			System.out.println(e.getKey() + " " + e.getValue());
	}

}
