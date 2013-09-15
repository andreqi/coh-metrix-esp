import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import pucp.edu.cohmetrixesp.structs.CohParagraph;
import pucp.edu.cohmetrixesp.utils.ParagraphSplitter;
import edu.upc.freeling.Maco;
import edu.upc.freeling.MacoOptions;
import edu.upc.freeling.Splitter;
import edu.upc.freeling.Tokenizer;
import edu.upc.freeling.Util;

public class Main {
	private static final String FREELINGDIR = "/usr/local";
	private static final String DATA = FREELINGDIR + "/share/freeling/";
	private static final String LANG = "es";

	static public void main(String[] args) {

		long startTime = 0, endTime = 0;
		startTime = System.nanoTime();
		System.loadLibrary("freeling_javaAPI");

		Util.initLocale("default");
		endTime = System.nanoTime();
		System.out.println(endTime - startTime);
		// Create options set for maco analyzer.
		// Default values are Ok, except for data files.
		MacoOptions op = new MacoOptions(LANG);
		op.setActiveModules(false, true, true, true, true, true, true, true,
				true, true);
		
		op.setDataFiles("", DATA + LANG + "/locucions.dat", DATA + LANG
				+ "/quantities.dat", DATA + LANG + "/afixos.dat", DATA + LANG
				+ "/probabilitats.dat", DATA + LANG + "/dicc.src", DATA + LANG
				+ "/np.dat", DATA + "common/punct.dat");
		// Create analyzers.

		Tokenizer tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
		Splitter sp = new Splitter(DATA + LANG + "/splitter.dat");
		Maco mf = new Maco(op);

		/*HmmTagger tg = new HmmTagger(DATA + LANG + "/tagger.dat", true, 2);
		ChartParser parser = new ChartParser(DATA + LANG
				+ "/chunker/grammar-chunk.dat");
		DepTxala dep = new DepTxala(DATA + LANG + "/dep/dependences.dat",
			parser.getStartSymbol());
		Nec neclass = new Nec(DATA + LANG + "/nerc/nec/nec-ab-poor1.dat");

		Senses sen = new Senses(DATA + LANG + "/senses.dat"); // sense
																// dictionary
		Ukb dis = new Ukb(DATA + LANG + "/ukb.dat"); // sense disambiguator
		*/

		// Make sure the encoding matches your input text (utf-8, iso-8859-15,
		// ...)
		String text = "";
		try {
			text = new String(Files.readAllBytes(Paths.get("./testfiles/smallFile.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<CohParagraph> paragraphs = ParagraphSplitter.split(text);
		for (CohParagraph p : paragraphs) {
			p.split(tk, sp);
			System.out.println(p.getText());
			p.morfological(mf);
			/*
			p.posTagging(tg);
			p.neClassification(neclass);
			p.senses(sen);
			p.disambiguation(dis);
			p.chunkParsing(parser);
			p.dependency(dep);
			*/
/*
 			Al parecer el orden de esos analisis es importante
			mf.analyze(ls);
			tg.analyze(ls);
			neclass.analyze(ls);
			sen.analyze(ls);
			dis.analyze(ls);
			parser.analyze(ls);
			dep.analyze(ls);
*/
		}

	}
}