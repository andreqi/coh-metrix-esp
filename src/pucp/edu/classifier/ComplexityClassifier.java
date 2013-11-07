package pucp.edu.classifier;

import java.util.Map;

public class ComplexityClassifier {
	private static ComplexityClassifier instance = new ComplexityClassifier();
	
	private ComplexityClassifier () {
		// load arf
	}
		
	static public ComplexityClassifier getInstance() {
		return instance;
	}
	
	public String classify(Map<String, Map<String,Double>> metrics) {
		
		return "Complejo";
	}
}
