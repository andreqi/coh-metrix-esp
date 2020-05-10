import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import pucp.edu.classifier.ComplexityClassifier;
import pucp.edu.cohmetrixesp.metrics.MetricsEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;


public class Main {

	static public void main(String[] args) {

		final MetricsEngine engine = MetricsEngine.getInstance();
		final ComplexityClassifier classifier = ComplexityClassifier.getInstance();
		
		String inputFile = args[0]; 
		
		// Read the input file
		String inputText;
		try {
			inputText = Main.readFile(inputFile, Charset.defaultCharset());
		}catch (IOException e) {
			System.out.println("Error reading input file");
			return;
		}
		
		// Process the input file
	   	Map<String, Map<String, Double>> ans = engine.analyzeClasified(inputText);
	   	String class_ = classifier.classify(ans);
	   	Map<String, Object> ret = new HashMap<String, Object> ();
	   	ret.put("metrics", ans);
	   	ret.put("class", class_);
	   	
	   	// Generate the output
	   	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	   	try(Writer writer = new FileWriter("output.json")) {
			gson.toJson(ret, writer);
		} catch (JsonIOException e) {
			System.out.println("Error writing output file.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error writing output file.");
			e.printStackTrace();
		}
	}
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}