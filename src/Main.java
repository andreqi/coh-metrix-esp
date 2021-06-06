import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import pucp.edu.cohmetrixesp.metrics.MetricsEngine;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;


public class Main {

	static public void main(String[] args) {
		Options options = new Options();
		
		Option inputFile = Option.builder("i")
				.longOpt("inputFile")
				.hasArg()
				.desc("path to input file.")
				.argName("inputFile")
				.required()
				.build();
		
		Option outputFile = Option.builder("o")
				.longOpt("outputFile")
				.hasArg()
				.desc("path to output file (default: 'output.json').")
				.argName("outputFile")
				.build();
		
		options.addOption(inputFile);
		options.addOption(outputFile);
		
		CommandLineParser parser = new DefaultParser();
		try {
			CommandLine cmd = parser.parse( options, args);
			if(cmd.hasOption("inputFile")) {
				analyzeFile(cmd.getOptionValue( "inputFile" ), cmd.getOptionValue( "outputFile" , "output.json"));
			} else {
				printHelp(options);
			}
		} catch (MissingOptionException e) {
			printHelp(options);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
	}
	
	private static void printHelp(Options options){
		HelpFormatter formatter = new HelpFormatter();
		PrintWriter pw = new PrintWriter(System.out);
		pw.println("Coh-Mextrix-Esp");
		pw.println();
		formatter.printUsage(pw, 100, "java -jar coh-metrix-esp.jar -i <inputFile> [-o <outputFile>]");
		formatter.printOptions(pw, 100, options, 2, 5);
		pw.close();
	}
	

	static void analyzeFile(String inputFilePath, String outputFilePath) {
		final MetricsEngine engine = MetricsEngine.getInstance();
		
		// Read the input file
		String inputText;
		try {
			inputText = Main.readFile(inputFilePath, Charset.defaultCharset());
		}catch (IOException e) {
			System.out.println("Error reading input file");
			return;
		}
		
		// Process the input file
		System.out.println("Computing metrics for file: " + inputFilePath);
	   	Map<String, Map<String, Double>> ans = engine.analyzeClasified(inputText);
	   	Map<String, Object> ret = new HashMap<String, Object> ();
	   	ret.put("metrics", ans);
	   	
	   	// Generate the output
	   	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	   	try(Writer writer = new FileWriter(outputFilePath)) {
			gson.toJson(ret, writer);
		} catch (JsonIOException e) {
			System.out.println("Error writing output file.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error writing output file.");
			e.printStackTrace();
		}
	   	System.out.println("Metrics saved in file: " + outputFilePath);
	}
	
	static String readFile(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding);
	}
}
	