import java.util.HashMap;
import java.util.Map;

import pucp.edu.classifier.ComplexityClassifier;
import pucp.edu.cohmetrixesp.metrics.MetricsEngine;
import static spark.Spark.*;
import spark.*;

import com.google.gson.Gson;

public class Main {

	static public void main(String[] args) {

		final MetricsEngine engine = MetricsEngine.getInstance();
		final ComplexityClassifier classifier = ComplexityClassifier.getInstance();
		
		
	    post(new Route("/") {
	         @Override
	         public Object handle(Request request, Response response) {
	        	// System.out.println(request.queryParams("text"));
	        	 String texto = request.queryParams("text");
	        	 Map<String, Map<String, Double>> ans = engine.analyzeClasified(texto);
	        	 String class_ = classifier.classify(ans);
	        	 Gson gson = new Gson();
	        	 Map<String, Object> ret = new HashMap<String, Object> ();
	        	 ret.put("metrics", ans);
	        	 ret.put("class", class_);
	        	 return gson.toJson(ret);
	         }
	    });
	    
		/*
		 * engine.process(
				"/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/Lengua extranjera/txt",
				"/home/andre/Desktop/2do Round - Lengua extrajera/");
				*/
	}
}