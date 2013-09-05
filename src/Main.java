import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import pucp.edu.cohmetrixesp.metrics.ParagraphSplitter;
import edu.upc.freeling.*;

public class Main {
	private static final String FREELINGDIR = "/usr/local";
	private static final String DATA = FREELINGDIR + "/share/freeling/";
	private static final String LANG = "es";

	static public void main(String[] args) {

		System.loadLibrary("freeling_javaAPI");

		Util.initLocale("default");

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
		LangIdent lgid = new LangIdent(DATA
				+ "/common/lang_ident/ident-few.dat");

		Tokenizer tk = new Tokenizer(DATA + LANG + "/tokenizer.dat");
		Splitter sp = new Splitter(DATA + LANG + "/splitter.dat");
		Maco mf = new Maco(op);

		HmmTagger tg = new HmmTagger(DATA + LANG + "/tagger.dat", true, 2);
		ChartParser parser = new ChartParser(DATA + LANG
				+ "/chunker/grammar-chunk.dat");
		DepTxala dep = new DepTxala(DATA + LANG + "/dep/dependences.dat",
				parser.getStartSymbol());
		Nec neclass = new Nec(DATA + LANG + "/nerc/nec/nec-ab-poor1.dat");

		Senses sen = new Senses(DATA + LANG + "/senses.dat"); // sense
																// dictionary
		Ukb dis = new Ukb(DATA + LANG + "/ukb.dat"); // sense disambiguator

		// Make sure the encoding matches your input text (utf-8, iso-8859-15,
		// ...)
		BufferedReader input = null;
		try {
			input = new BufferedReader(new InputStreamReader(
					System.in, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null;
		try {
			line = input.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Identify language of the text.
		// Note that this will identify the language, but will NOT adapt
		// the analyzers to the detected language. All the processing
		// in the loop below is done by modules for LANG (set to "es" at
		// the beggining of this class) created above.
		String lg = lgid.identifyLanguage(line);
		System.out.println("-------- LANG_IDENT results -----------");
		System.out
				.println("Language detected (from first line in text): " + lg);

		while (line != null) {
			// Extract the tokens from the line of text.
			ListWord l = tk.tokenize(line);

			// Split the tokens into distinct sentences.
			ListSentence ls = sp.split(l, false);

			// Perform morphological analysis
			mf.analyze(ls);

			// Perform part-of-speech tagging.
//			tg.analyze(ls);

			// Perform named entity (NE) classificiation.
/*			neclass.analyze(ls);

			sen.analyze(ls);
			dis.analyze(ls);*/
			printResults(ls, "tagged");
/*
			// Chunk parser
			parser.analyze(ls);
			printResults(ls, "parsed");

			// Dependency parser
			dep.analyze(ls);
			printResults(ls, "dep");
*/
			try {
				line = input.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private static void printSenses(Word w) {
		String ss = w.getSensesString();

		// The senses for a FreeLing word are a list of
		// pair<string,double> (sense and page rank). From java, we
		// have to get them as a string with format
		// sense:rank/sense:rank/sense:rank
		// which will have to be splitted to obtain the info.
		//
		// Here, we just output it:
		System.out.print(" " + ss);
	}

	private static void printResults(ListSentence ls, String format) {

		if (format == "parsed") {
			System.out.println("-------- CHUNKER results -----------");

			ListSentenceIterator sIt = new ListSentenceIterator(ls);
			while (sIt.hasNext()) {
				Sentence s = sIt.next();
				TreeNode tree = s.getParseTree();
				printParseTree(0, tree);
			}
		} else if (format == "dep") {
			System.out
					.println("-------- DEPENDENCY PARSER results -----------");

			ListSentenceIterator sIt = new ListSentenceIterator(ls);
			while (sIt.hasNext()) {
				Sentence s = sIt.next();
				TreeNode tree = s.getParseTree();
				printParseTree(0, tree);
			}
		} else {
			System.out.println("-------- TAGGER results -----------");

			// get the analyzed words out of ls.
			ListSentenceIterator sIt = new ListSentenceIterator(ls);
			while (sIt.hasNext()) {
				Sentence s = sIt.next();
				ListWordIterator wIt = new ListWordIterator(s);
				while (wIt.hasNext()) {
					Word w = wIt.next();

					System.out.print(w.getForm() + " " + w.getLemma() + " "
							+ w.getTag());
					printSenses(w);
					System.out.println();
				}

				System.out.println();
			}
		}
	}

	private static void printParseTree(int depth, TreeNode tr) {
		Word w;
		TreeNode child;
		long nch;

		// Indentation
		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}

		nch = tr.numChildren();

		if (nch == 0) {
			// The node represents a leaf
			if (tr.getInfo().isHead()) {
				System.out.print("+");
			}
			w = tr.getInfo().getWord();
			System.out.print("(" + w.getForm() + " " + w.getLemma() + " "
					+ w.getTag());
			printSenses(w);
			System.out.println(")");
		} else {
			// The node probably represents a tree
			if (tr.getInfo().isHead()) {
				System.out.print("+");
			}

			System.out.println(tr.getInfo().getLabel() + "_[");

			for (int i = 0; i < nch; i++) {
				child = tr.nthChildRef(i);

				if (child != null) {
					printParseTree(depth + 1, child);
				} else {
					System.err.println("ERROR: Unexpected NULL child.");
				}
			}
			for (int i = 0; i < depth; i++) {
				System.out.print("  ");
			}

			System.out.println("]");
		}
	}

	private static void printDepTree(int depth, TreeDepnode tr) {
		TreeDepnode child = null;
		TreeDepnode fchild = null;
		Depnode childnode;
		long nch;
		int last, min;
		Boolean trob;

		for (int i = 0; i < depth; i++) {
			System.out.print("  ");
		}

		System.out.print(tr.getInfo().getLinkRef().getInfo().getLabel() + "/"
				+ tr.getInfo().getLabel() + "/");

		Word w = tr.getInfo().getWord();

		System.out.print("(" + w.getForm() + " " + w.getLemma() + " "
				+ w.getTag());
		printSenses(w);
		System.out.print(")");

		nch = tr.numChildren();

		if (nch > 0) {
			System.out.println(" [");

			for (int i = 0; i < nch; i++) {
				child = tr.nthChildRef(i);

				if (child != null) {
					if (!child.getInfo().isChunk()) {
						printDepTree(depth + 1, child);
					}
				} else {
					System.err.println("ERROR: Unexpected NULL child.");
				}
			}

			// Print chunks (in order)
			last = 0;
			trob = true;

			// While an unprinted chunk is found, look for the one with lower
			// chunk_ord value.
			while (trob) {
				trob = false;
				min = 9999;

				for (int i = 0; i < nch; i++) {
					child = tr.nthChildRef(i);
					childnode = child.getInfo();

					if (childnode.isChunk()) {
						if ((childnode.getChunkOrd() > last)
								&& (childnode.getChunkOrd() < min)) {
							min = childnode.getChunkOrd();
							fchild = child;
							trob = true;
						}
					}
				}
				if (trob && (child != null)) {
					printDepTree(depth + 1, fchild);
				}

				last = min;
			}

			for (int i = 0; i < depth; i++) {
				System.out.print("  ");
			}

			System.out.print("]");
		}

		System.out.println("");
	}

}
