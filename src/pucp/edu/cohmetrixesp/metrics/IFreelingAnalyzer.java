package pucp.edu.cohmetrixesp.metrics;

import edu.upc.freeling.Maco;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;

public interface IFreelingAnalyzer {
	Maco getMorfological();
	Tokenizer getTokenizer();
	Splitter getSplitter();
}
