package pucp.edu.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;

import edu.upc.freeling.Util;
import pucp.edu.cohmetrixesp.metrics.DescriptiveAnalyzer;

public class DescriptiveTest {
	
	String small, medium;
	Properties prop;
	DescriptiveAnalyzer desc; 

	@Before
	public void load_test_files(){
		System.loadLibrary("freeling_javaAPI");
		Util.initLocale("default");
		prop = new Properties();
		try {
			small = new String(Files.readAllBytes(Paths.get("./testfiles/smallFile.txt")));
			medium = new String(Files.readAllBytes(Paths.get("./testfiles/mediumFile.txt")));
			prop.load(new FileInputStream("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//System.out.println(prop.getProperty("tok_file"));
		desc = new DescriptiveAnalyzer(prop);
		//System.out.println(desc.number_of_words(small));
	}

	@Test
	public void number_of_paragraphs() {
		assertEquals("error - numero distinto de parrafos", 2, desc.number_of_paragraphs(small));
		assertEquals("error - numero distinto de parrafos", 7, desc.number_of_paragraphs(medium));
	}
	
	@Test
	public void number_of_words() {
		System.out.println(desc);
		assertEquals("error - numero distinto de palabras", 22, desc.number_of_words(small));
		assertTrue("error - numero menor de palabras", 160 < desc.number_of_words(medium));
	}

}
