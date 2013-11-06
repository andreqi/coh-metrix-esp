import java.util.Map;
import java.util.Map.Entry;

import pucp.edu.cohmetrixesp.metrics.MetricsEngine;
import static spark.Spark.*;
import spark.*;

public class Main {

	static public void main(String[] args) {

		final MetricsEngine engine = MetricsEngine.getInstance();
		
	    post(new Route("/") {
	         @Override
	         public Object handle(Request request, Response response) {
	        	 Map<String, Double> ans = engine.analyze("negro del averno porque esto no funciona, eres un pendejo");
	        	 StringBuilder builder = new StringBuilder();
	        	 builder.append('[');
	        	 boolean first = true;
	        	 for (Entry<String, Double> e : ans.entrySet()) {
	        		 if (first) first = false;
	        		 else		builder.append(", ");
	        		 builder.append("{\"");
	        		 builder.append(e.getKey());
	        		 builder.append("\": ");
	        		 if (e.getValue().isNaN()) 
	        			 builder.append(0);
	        		 else
	        			 builder.append(e.getValue());
	        		 builder.append('}');
	        	 }
	        	 builder.append(']');
	        	 return builder.toString();
	         }
	      });
		
		/*
		 * engine.process(
				"/home/andre/Dropbox/Coh-Metrix-Esp/Entregables/Tesis 2/Corpus/Lengua extranjera/txt",
				"/home/andre/Desktop/2do Round - Lengua extrajera/");
				*/
	}
}