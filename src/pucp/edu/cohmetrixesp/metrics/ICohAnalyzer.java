package pucp.edu.cohmetrixesp.metrics;

import java.util.HashMap;

import pucp.edu.cohmetrixesp.structs.CohText;

public interface ICohAnalyzer {
	void analyze(HashMap<String, Double> toFill, CohText text);
}
