package pucp.edu.classifier;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import weka.core.*;
import weka.core.converters.ConverterUtils.DataSource;
import weka.classifiers.Classifier;
import weka.classifiers.functions.SMO;

import java.util.ArrayList;
import java.util.Map.Entry;

public class ComplexityClassifier {
	private static ComplexityClassifier instance = new ComplexityClassifier();
	private Classifier classifier;


	String routePath = "./files/classifier.arff";
	
	private ComplexityClassifier () {
		try {
			 DataSource source = new DataSource(routePath);
			 Instances data = source.getDataSet();
			 data.setClassIndex(data.numAttributes() - 1);
			 classifier = new SMO();
			 classifier.buildClassifier(data);
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
		
	static public ComplexityClassifier getInstance() {
		return instance;
	}
	
	public Instances makeInstance(Map<String, Map<String, Double>> metrics) {
		ArrayList<Attribute> attributeList = new ArrayList<>();
		for (Entry<String, Map<String, Double>> type : metrics.entrySet()) {
			for (Entry<String, Double> e : type.getValue().entrySet()) {
				attributeList.add( new Attribute(e.getKey()));
			}
		}
		ArrayList<String> classVal = new ArrayList<>();
		classVal.add("simple");
		classVal.add("complejo");
		attributeList.add( new Attribute("class", classVal));
		
		Instances data = new Instances("CohMetrix", attributeList, 0);
		data.setClassIndex(data.numAttributes()-1);
		Instance ans = new DenseInstance(data.numAttributes());
		data.add(ans);
		int i = 0;
		for (Entry<String, Map<String, Double>> type : metrics.entrySet()) {
			for (Entry<String, Double> e : type.getValue().entrySet()) {
				ans.setValue(attributeList.get(i++), e.getValue());;
			}
		}
		
		return data;
	}
	
	public String classify(Map<String, Map<String,Double>> metrics) {
		Instances curText = makeInstance(metrics);
		double index = 0;
		try {
			index = classifier.classifyInstance(curText.firstInstance());
		} catch (Exception e) {
			System.out.println("Error en el clasificador");
			e.printStackTrace();
		}
		//curText.setClassValue(index);
		
		return curText.classAttribute().value((int)index);
	}
}
