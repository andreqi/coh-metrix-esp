package pucp.edu.cohmetrixesp.utils;

import edu.upc.freeling.ChartParser;
import edu.upc.freeling.HmmTagger;
import edu.upc.freeling.Maco;
import edu.upc.freeling.Nec;
import edu.upc.freeling.Senses;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Ukb;

public interface IFreelingAnalyzer {
	Maco getMorfological();
	Tokenizer getTokenizer();
	Splitter getSplitter();
	HmmTagger getPosTagger();
	ChartParser getChunkParser();
	Nec getNec();
	Senses getSenses();
	Ukb getDisambiguator();
}
