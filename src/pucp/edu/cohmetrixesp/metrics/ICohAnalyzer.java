package pucp.edu.cohmetrixesp.metrics;

import java.util.HashMap;

public interface ICohAnalyzer {
	void analyze(HashMap<String, Double> toFill, CohText text);
}
