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
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.DescriptiveAnalyzer;
import pucp.edu.cohmetrixesp.structs.CohStats;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.utils.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ImplFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ParagraphSplitter;

public class DescriptiveAnalyzerTest {
	DescriptiveAnalyzer desc = DescriptiveAnalyzer.getInstance();
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
		assertEquals("numero de oraciones distintos", 16,
				desc.numberOfSentences(ctMedium));
		assertEquals("numero de oraciones distintos", 3,
				desc.numberOfSentences(ctSmall));
	}

	public void testStatistics(CohStats returned, CohStats expected) {
		assertNotNull(returned);
		System.out.println(returned);
	}

	@Test
	public void lenghtParagraphTest() {
		testStatistics(desc.lengthOfParagraphs(ctMedium),
				new CohStats(1.0, 2.9));
		testStatistics(desc.lengthOfParagraphs(ctSmall), new CohStats(1.0, 2.9));
	}

	@Test
	public void numberOfWordsInSentencesTest() {
		testStatistics(desc.numberOfWordsInSentences(ctMedium), new CohStats(
				1.0, 2.9));
		testStatistics(desc.numberOfWordsInSentences(ctSmall), new CohStats(
				1.0, 2.9));
	}

	@Test
	public void numberOfLettersInWordsTest() {
		testStatistics(desc.numberOfLettersInWords(ctMedium), new CohStats(1.0,
				2.9));
		testStatistics(desc.numberOfLettersInWords(ctSmall), new CohStats(1.0,
				2.9));
	}

	@Test
	public void numberOfSyllablesInWordsTest() {
		testStatistics(desc.numberOfSyllablesInWords(ctMedium), new CohStats(
				1.0, 2.9));
		testStatistics(desc.numberOfSyllablesInWords(ctSmall), new CohStats(
				1.0, 2.9));
	}
	
	@Test
	public void correGeneracionDeDescriptives() {/*
	TODO : base para procesamiento de archivos
		File folder = new File("/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/txt");
		String ansDir = "/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/desc/";
		File [] files = folder.listFiles();
		int ans = 0;
		for (File f: files) {
			if (f.isFile()) {
				if(f.getName().startsWith("Texto") && f.getName().endsWith("txt")) {
					ans ++;
					File ansFile = new File(ansDir + "Desc" + f.getName());
					try {

						if (ansFile.exists()) {}
						else ansFile.createNewFile();

						String text = new String(Files.readAllBytes(Paths
								.get(f.getPath())));
						CohText ctext = new CohText(text);
						ctext.analyze(freeling);
						Map<String, Double> acum = new HashMap<>();
						desc.analyze(acum, ctext);
						FileWriter fw = new FileWriter(ansFile);
						BufferedWriter bfw = new BufferedWriter(fw);
						for (Entry<String, Double> entry : acum.entrySet() ) {
							bfw.append(entry.getKey() +  " " + entry.getValue()  + "\n");
						}
						bfw.close();
						fw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
		System.out.println(ans);
		*/
	}
}
