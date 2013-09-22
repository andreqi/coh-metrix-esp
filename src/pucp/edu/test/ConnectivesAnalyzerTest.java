package pucp.edu.test;

import static org.junit.Assert.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import pucp.edu.cohmetrixesp.metrics.ConnectivesAnalyzer;
import pucp.edu.cohmetrixesp.metrics.DescriptiveAnalyzer;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.utils.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ImplFreelingAnalyzer;

public class ConnectivesAnalyzerTest {
	ConnectivesAnalyzer desc = ConnectivesAnalyzer.getInstance();
	IFreelingAnalyzer freeling = ImplFreelingAnalyzer.getInstance();
	String small, medium;
	Properties prop;
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
	}
	
	@Test
	public void allConnectivesIncidenceTest(){
		assertEquals( 0.001 , desc.allConnectivesIncidence( ctSmall ) , 1E-9 ) ;
		assertEquals( 0.018 , desc.allConnectivesIncidence( ctMedium ) , 1E-9 ) ;
	}
	
	@Test
	public void causalConnectivesIncidenceTest(){
		double c1 = desc.causalConnectivesIncidence( ctSmall ) ;
		double c2 = desc.causalConnectivesIncidence( ctMedium ) ;
		System.out.println( "causal: " + c1 + " " + c2 ) ;
	}
	
	@Test
	public void logicalConnectivesIncidenceTest(){
		double l1 = desc.logicalConnectivesIncidence( ctSmall ) ;
		double l2 = desc.logicalConnectivesIncidence( ctMedium ) ;
		System.out.println( "logical: " + l1 + " " + l2 ) ;
	}
	
	@Test
	public void adversativeConnectivesIncidenceTest(){
		double a1 = desc.adversativeConnectivesIncidence( ctSmall ) ;
		double a2 = desc.adversativeConnectivesIncidence( ctMedium ) ;
		System.out.println( "adversative: " + a1 + " " + a2 ) ;
	}
	
	@Test
	public void temporalConnectivesIncidenceTest(){
		double t1 = desc.temporalConnectivesIncidence( ctSmall ) ;
		double t2 = desc.temporalConnectivesIncidence( ctMedium ) ;
		System.out.println( "temporal: " + t1 + " " + t2 ) ;
	}
	
	@Test
	public void additiveConnectivesIncidenceTest(){
		double p1 = desc.additiveConnectivesIncidence( ctSmall ) ;
		double p2 = desc.additiveConnectivesIncidence( ctMedium ) ;
		System.out.println( "additive: " + p1 + " " + p2 ) ;
	}
}