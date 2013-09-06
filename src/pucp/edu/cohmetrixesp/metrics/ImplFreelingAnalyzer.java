package pucp.edu.cohmetrixesp.metrics;

import edu.upc.freeling.ChartParser;
import edu.upc.freeling.HmmTagger;
import edu.upc.freeling.Maco;
import edu.upc.freeling.MacoOptions;
import edu.upc.freeling.Nec;
import edu.upc.freeling.Senses;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Ukb;
import edu.upc.freeling.Util;

public class ImplFreelingAnalyzer implements IFreelingAnalyzer {
	private static final String FREELINGDIR = "/usr/local";
	private static final String DATA = FREELINGDIR + "/share/freeling/";
	private static final String LANG = "es";
	Maco mf = null;
	Tokenizer tk = null;
	Splitter sp = null;
	HmmTagger tg = null;
	ChartParser parser = null;
	Nec nec = null;
	Senses sen;
	Ukb dis;
	static ImplFreelingAnalyzer instance = null;
	public static IFreelingAnalyzer getInstance() {
		if (instance == null) return instance = new ImplFreelingAnalyzer();
		return instance;
	}
	ImplFreelingAnalyzer() {
		System.loadLibrary("freeling_javaAPI");
		Util.initLocale("default");
		MacoOptions op = new MacoOptions(LANG);
		op.setActiveModules(false, true, true, true, true, true, true, true,
				true, true);
		
		op.setDataFiles("", DATA + LANG + "/locucions.dat", DATA + LANG
				+ "/quantities.dat", DATA + LANG + "/afixos.dat", DATA + LANG
				+ "/probabilitats.dat", DATA + LANG + "/dicc.src", DATA + LANG
				+ "/np.dat", DATA + "common/punct.dat");
		// Create analyzers.
		tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
		sp = new Splitter(DATA + LANG + "/splitter.dat");
		mf = new Maco(op);		
		tg = new HmmTagger(DATA + LANG + "/tagger.dat", true, 2);
		parser = new ChartParser(DATA + LANG
				+ "/chunker/grammar-chunk.dat");
		nec = new Nec(DATA + LANG + "/nerc/nec/nec-ab-poor1.dat");
		sen = new Senses(DATA + LANG + "/senses.dat"); 
		dis = new Ukb( DATA + LANG + "/ukb.dat" );
	}
	@Override
	public Maco getMorfological() {
		return mf;
	}

	@Override
	public Tokenizer getTokenizer() {
		return tk;
	}

	@Override
	public Splitter getSplitter() {
		return sp;
	}
	
	@Override
	public HmmTagger getPosTagger() {
		return tg;
	}
	@Override
	public ChartParser getChunkParser() {
		return parser;
	}
	@Override
	public Nec getNec() {
		return nec;
	}
	@Override
	public Senses getSenses() {
		return sen;
	}
	@Override
	public Ukb getDisambiguator() {
		return dis;
	}
	
}
