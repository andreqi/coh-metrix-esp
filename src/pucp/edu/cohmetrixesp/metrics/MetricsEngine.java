package pucp.edu.cohmetrixesp.metrics;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pucp.edu.cohmetrixesp.structs.CohText;
import pucp.edu.cohmetrixesp.utils.IFreelingAnalyzer;
import pucp.edu.cohmetrixesp.utils.ImplFreelingAnalyzer;

public class MetricsEngine {
	static private MetricsEngine instance = new MetricsEngine();
	private IFreelingAnalyzer freeling = ImplFreelingAnalyzer.getInstance();
	private RefCohesionAnalyzer ref = RefCohesionAnalyzer.getInstance();
	private ConnectivesAnalyzer con = ConnectivesAnalyzer.getInstance();
	private DescriptiveAnalyzer desc = DescriptiveAnalyzer.getInstance();
	private LexicalDiversityAnalyzer lex = LexicalDiversityAnalyzer.getInstance();
	private SyntacticComplexityAnalyzer sca = SyntacticComplexityAnalyzer.getInstance();
	private SyntacticPDAnalyzer spda = SyntacticPDAnalyzer.getInstance();
	private WordInformationAnalyzer wi = WordInformationAnalyzer.getInstance();
	private ReadabilityAnalyzer read = ReadabilityAnalyzer.getInstance();
	private TextEasabilityAnalizer tex = TextEasabilityAnalizer.getInstance();
	private MetricsEngine(){
	}
	
	static public MetricsEngine getInstance() {
		return instance;
	}
	
	public Map<String, Double> analyze(String text) {
		Map<String, Double> ans = new HashMap<>();
		CohText ctxt = new CohText(text);
		ctxt.analyze(freeling);
		long t = System.currentTimeMillis();
		ref.analyze(ans, ctxt);
		long t1 = System.currentTimeMillis();
		System.out.println(t1 - t);
		con.analyze(ans, ctxt);
		desc.analyze(ans, ctxt);
		lex.analyze(ans, ctxt);
		sca.analyze(ans, ctxt);
		spda.analyze(ans, ctxt);
		wi.analyze(ans, ctxt);
		read.analyze(ans, ctxt);
		tex.analyze(ans, ctxt);
		return ans;
	}
	
	public void process(String in, String out) {
//		File folder = new File("/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/txt");
		File folder = new File(in);
//		String ansDir = "/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/desc/";
		String ansDir = out;
		File [] files = folder.listFiles();
		int ans = 0;
		for (File f: files) {
			if (f.isFile()) {
				if(f.getName().startsWith("Texto") && f.getName().endsWith("txt")) {
					ans ++;
					File ansFile = new File(ansDir + "Metrics_" + f.getName());
					try {

						if (ansFile.exists()) {}
						else ansFile.createNewFile();

						String text = new String(Files.readAllBytes(Paths
								.get(f.getPath())));
						Map<String, Double> acum = analyze(text);
						FileWriter fw = new FileWriter(ansFile);
						BufferedWriter bfw = new BufferedWriter(fw);
						for (Entry<String, Double> entry : acum.entrySet() ) {
							bfw.write(entry.getKey() +  " " + entry.getValue()  + "\n");
						}
						bfw.close();
						fw.close();
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
		System.out.println(ans);
	}
}
