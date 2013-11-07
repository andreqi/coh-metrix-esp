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
	private Instances trainData;

	String routePath = "./files/classifier.arff";
	
	private ComplexityClassifier () {
		try {
			 DataSource source = new DataSource(routePath);
			 trainData = source.getDataSet();
			 trainData.setClassIndex(trainData.numAttributes() - 1);
			 classifier = new SMO();
			 classifier.buildClassifier(trainData);
		} catch (Exception e ) {
			e.printStackTrace();
		}
	}
		
	static public ComplexityClassifier getInstance() {
		return instance;
	}
	
	public String classifyText(Map<String, Map<String, Double>> metrics) throws Exception {
				
		Instances unlabeled = new Instances(trainData, 1);
		unlabeled.setClassIndex(trainData.classIndex());
		
		Instance cur = new DenseInstance(unlabeled.numAttributes());
		cur.setDataset(unlabeled);
		
		for (Entry<String, Map<String, Double>> type : metrics.entrySet()) {
			for (Entry<String, Double> e : type.getValue().entrySet()) {
				cur.setValue(unlabeled.attribute(e.getKey()), e.getValue());
			}
		}
		
		unlabeled.add(cur);
		cur.setMissing(unlabeled.numAttributes()-1);;
		Instances labeled = new Instances(unlabeled);
		
		double clsLabel = classifier.classifyInstance(unlabeled.instance(0));
		labeled.instance(0).setClassValue(clsLabel);
		
		return labeled.instance(0).stringValue(unlabeled.numAttributes()-1);
	}
	
	public String classify(Map<String, Map<String,Double>> metrics) {
		String clase = "error";
		try {
			 clase = classifyText(metrics);
		} catch (Exception e) {
			System.out.println("Error en el clasificador");
			e.printStackTrace();
		}
		//curText.setClassValue(index);
		
		return clase;
	}
}
