import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import pucp.edu.classifier.ComplexityClassifier;
import pucp.edu.cohmetrixesp.metrics.MetricsEngine;
import com.google.gson.Gson;

public class Main {

	static public void main(String[] args) {

		final MetricsEngine engine = MetricsEngine.getInstance();
		final ComplexityClassifier classifier = ComplexityClassifier.getInstance();
		
		String inputFile = args[0]; 
		
		String inputText;
		try {
			inputText = Main.readFile(inputFile, Charset.defaultCharset());
		}catch (IOException e) {
			System.out.println("Error reading input file");
			return;
		}
		
	   	Map<String, Map<String, Double>> ans = engine.analyzeClasified(inputText);
	   	String class_ = classifier.classify(ans);
	   	Gson gson = new Gson();
	   	Map<String, Object> ret = new HashMap<String, Object> ();
	   	ret.put("metrics", ans);
	   	ret.put("class", class_);
	   	System.out.println(gson.toJson(ret));
	}
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}