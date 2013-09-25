package pucp.edu.cohmetrixesp.metrics;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.upc.freeling.ListSentence;
import edu.upc.freeling.Sentence;
import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.test.DescriptiveAnalyzerTest;

public class ConnectivesAnalyzer implements ICohAnalyzer {
	static String ALL_CONECTIVES_INCIDENCE = "CNCAll" ;
	static String CAUSAL_CONNECTIVES_INCIDENCE = "CNCCaus" ;
	static String LOGICAL_CONNECTIVES_INCIDENCE = "CNCLogic" ;
	static String ADVERSATIVE_CONNECTIVES_INCIDENCE = "CNCADC" ;
	static String TEMPORAL_CONNECTIVES_INCIDENCE = "CNCTemp" ;
	static String EXPANDED_TEMPORAL_CONNECTIVES_INCIDENCE = "CNCTempx" ; // NO
	static String ADDITIVE_CONNECTIVES_INCIDENCE = "CNCAdd" ;
	static String POSITIVE_CONNECTIVES_INCIDENCE = "CNCPos" ; // REV
	static String NEGATIVE_CONNECTIVES_INCIDENCE = "CNCNeg" ; // REV
	
	private String CAUSAL_TAG = "C" ;
	private String LOGICAL_TAG = "L" ;
	private String ADVERSATIVE_TAG = "A" ;
	private String TEMPORAL_TAG = "T" ;
	private String ADDITIVE_TAG = "P" ;
	
	static String connectivesPath = "./tagsets/tagset-es-connectives.txt" ;
	private String punctuation = "[.|¿|?|¡|!|,|(|)|—|-]" ;
	
	static double INCIDENCE = 1000. ;
	
	private HashMap<String, String> rules ;
	
	static ConnectivesAnalyzer instance ;
	
	public static ConnectivesAnalyzer getInstance(){
		if( instance == null ) instance = new ConnectivesAnalyzer() ;
		return instance ;
	}
	
	private ConnectivesAnalyzer(){
		loadConnectives() ;
	}
	
	@Override
	public void analyze(Map<String, Double> toFill, CohText text) {
		double dAns = 0.0 ;
		dAns = allConnectivesIncidence( text ) ;
		toFill.put( ALL_CONECTIVES_INCIDENCE , dAns ) ;
		
		dAns = causalConnectivesIncidence( text ) ;
		toFill.put( CAUSAL_CONNECTIVES_INCIDENCE , dAns ) ;
		
		dAns = logicalConnectivesIncidence( text ) ;
		toFill.put( LOGICAL_CONNECTIVES_INCIDENCE , dAns ) ;
		
		dAns = adversativeConnectivesIncidence( text ) ;
		toFill.put( ADVERSATIVE_CONNECTIVES_INCIDENCE , dAns ) ;
		
		dAns = temporalConnectivesIncidence( text ) ;
		toFill.put( TEMPORAL_CONNECTIVES_INCIDENCE , dAns ) ;
		
		dAns = additiveConnectivesIncidence( text ) ;
		toFill.put( ADDITIVE_CONNECTIVES_INCIDENCE , dAns ) ;
		
//		dAns = positiveConnectivesIncidence( text ) ;
//		toFill.put( POSITIVE_CONNECTIVES_INCIDENCE , dAns ) ;
//		
//		dAns = negativeConnectivesIncidence( text ) ;
//		toFill.put( NEGATIVE_CONNECTIVES_INCIDENCE , dAns ) ;
	}
	
	public double allConnectivesIncidence( CohText text ){
		return countConnectives( rules.keySet() ,  text ) ;
	}
	
	public double causalConnectivesIncidence( CohText text ){
		Set<String> st = rules.keySet() ;
		Set<String> causal = new HashSet() ;
		for( String key : st ) if( rules.get( key ).contains( CAUSAL_TAG ) ) causal.add( key ) ;
		return countConnectives( causal , text ) ;
	}
	
	public double logicalConnectivesIncidence( CohText text ){
		Set<String> st = rules.keySet() ;
		Set<String> logical = new HashSet() ;
		for( String key : st ) if( rules.get( key ).contains( LOGICAL_TAG ) ) logical.add( key ) ;
		return countConnectives( logical , text ) ;
	}
	
	public double adversativeConnectivesIncidence( CohText text ){
		Set<String> st = rules.keySet() ;
		Set<String> adversative = new HashSet() ;
		for( String key : st ) if( rules.get( key ).contains( ADVERSATIVE_TAG ) ) adversative.add( key ) ;
		return countConnectives( adversative , text ) ;
	}
	
	public double temporalConnectivesIncidence( CohText text ){
		Set<String> st = rules.keySet() ;
		Set<String> temporal = new HashSet() ;
		for( String key : st ) if( rules.get( key ).contains( TEMPORAL_TAG ) ) temporal.add( key ) ;
		return countConnectives( temporal , text ) ;
	}
	
	public double additiveConnectivesIncidence( CohText text ){
		Set<String> st = rules.keySet() ;
		Set<String> additive = new HashSet() ;
		for( String key : st ) if( rules.get( key ).contains( ADDITIVE_TAG ) ) additive.add( key ) ;
		return countConnectives( additive , text ) ;
	}
	
//	public double positiveConnectivesIncidence( CohText text ){
//		return 1.0 ;
//	}
//	
//	public double negativeConnectivesIncidence( CohText text ){
//		return 1.0 ;
//	}
	
	private void loadConnectives() {
		rules = new HashMap<String,String>() ;
		String conn , type ;
		try {
			BufferedReader bf = new BufferedReader( new FileReader( connectivesPath ) ) ;
			while( ( conn = bf.readLine() ) != null ){
				type = bf.readLine() ;
				rules.put( conn ,  type ) ;
			}
		} catch( Exception e ){
			e.printStackTrace();
		}
	}
	
	private double countConnectives( Set<String> st , CohText text ){
		double resp = 0.0 ;
		for( CohParagraph paragraph : text ){
			String par = " " + paragraph.getText().toLowerCase().replaceAll( punctuation , " " ) + " " ;
//			System.out.println( "/* ======== " + par + " ======== */" ) ;
			for( String key : st ){
//				System.out.println( "KEY: " + key ) ;
				int idx = 0 , pos ;
				while( idx < par.length() && ( pos = par.indexOf( " " + key + " " , idx ) ) != -1 ){
					idx = pos + key.length() ;
					resp++ ;
//					System.out.println( pos + "" ) ;
				}
			}
		}
		resp /= INCIDENCE ;
		return resp ;
	}

}
