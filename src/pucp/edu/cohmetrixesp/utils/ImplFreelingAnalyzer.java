package pucp.edu.cohmetrixesp.utils;

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
	Senses sen = null;
	Ukb dis = null;
	static ImplFreelingAnalyzer instance = null;

	public static IFreelingAnalyzer getInstance() {
		if (instance == null)
			return instance = new ImplFreelingAnalyzer();
		return instance;
	}

	private ImplFreelingAnalyzer() {
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
		mf = new Maco(op);

	}

	@Override
	public Maco getMorfological() {
		return mf;
	}

	@Override
	public Tokenizer getTokenizer() {
		tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
		return tk;
	}

	@Override
	public Splitter getSplitter() {
		sp = new Splitter(DATA + LANG + "/splitter.dat");
		return sp;
	}

	@Override
	public HmmTagger getPosTagger() {
		if (tg == null)
			tg = new HmmTagger(DATA + LANG + "/tagger.dat", true, 2);
		return tg;
	}

	@Override
	public ChartParser getChunkParser() {
		if (parser == null)
			parser = new ChartParser(DATA + LANG + "/chunker/grammar-chunk.dat");
		return parser;
	}

	@Override
	public Nec getNec() {
		if (nec == null)
			nec = new Nec(DATA + LANG + "/nerc/nec/nec-ab-poor1.dat");
		return nec;
	}

	@Override
	public Senses getSenses() {
		if (sen == null)
			sen = new Senses(DATA + LANG + "/senses.dat");
		return sen;
	}

	@Override
	public Ukb getDisambiguator() {
		if (dis == null)
			dis = new Ukb(DATA + LANG + "/ukb.dat");
		return dis;
	}

}
