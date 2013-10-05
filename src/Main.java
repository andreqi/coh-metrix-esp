import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import pucp.edu.cohmetrixesp.metrics.MetricsEngine;
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
		MetricsEngine engine = MetricsEngine.getInstance();
		engine.process(
				"/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/Lengua extranjera/txt",
				"/home/andre/Desktop/2do Round - Lengua extrajera/");
	}
}