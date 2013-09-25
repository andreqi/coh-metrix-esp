package pucp.edu.cohmetrixesp.metrics;

import java.util.Map;

import pucp.edu.cohmetrixesp.structs.CohText;

public interface ICohAnalyzer {
	void analyze(Map<String, Double> toFill, CohText text);
}
