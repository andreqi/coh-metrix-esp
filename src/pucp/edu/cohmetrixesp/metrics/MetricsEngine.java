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
		ref.analyze(ans, ctxt);
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
	
	public Map<String, Map<String, Double>> analyzeClasified(String text) {
		Map <String, Map<String, Double>> ans = new HashMap<>();
		CohText ctxt = new CohText(text);
		ctxt.analyze(freeling);
		
		Map<String, Double> refmap = new HashMap<>();
		ref.analyze(refmap, ctxt);
		ans.put("Referential Cohesion", refmap);
		
		Map<String, Double> conmap = new HashMap<>();
		con.analyze(conmap, ctxt);
		ans.put("Connectives", conmap);
		
		Map<String, Double> descmap = new HashMap<>();
		desc.analyze(descmap, ctxt);
		ans.put("Descriptives", descmap);
		
		Map<String, Double> lexmap = new HashMap<>();
		lex.analyze(lexmap, ctxt);
		ans.put("Lexical Diversity", lexmap);
		
		Map<String, Double> scamap = new HashMap<>();
		sca.analyze(scamap, ctxt);
		ans.put("Syntactic Complexity", scamap);
		
		Map<String, Double> spdamap = new HashMap<>();
		spda.analyze(spdamap, ctxt);
		ans.put("Syntactic Pattern Density", spdamap);
		
		Map<String, Double> wimap = new HashMap<>();
		wi.analyze(wimap, ctxt);
		ans.put("Word Information", wimap);
		
		Map<String, Double> readmap = new HashMap<>();
		read.analyze(readmap, ctxt);
		ans.put("Readability", readmap);
		
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
					System.out.println(f.getName());
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
