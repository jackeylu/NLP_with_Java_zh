package com.linlyu.stanford;

import java.io.File;
import java.util.Properties;
import java.util.Set;
import java.util.function.*;

import edu.stanford.nlp.classify.Classifier;
import edu.stanford.nlp.classify.ColumnDataClassifier;
import edu.stanford.nlp.ie.crf.CRFClassifier;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.Datum;
import edu.stanford.nlp.neural.rnn.RNNCoreAnnotations;
import edu.stanford.nlp.objectbank.ObjectBank;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations.SentimentAnnotatedTree;
import edu.stanford.nlp.stats.Counter;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.util.CoreMap;

public class UseStanfordClassifier {

	public static void usingStandfordClassifier(String prop_file, String train_file, String test_file) {
		ColumnDataClassifier cdc = new ColumnDataClassifier(prop_file);

		Classifier<String, String> classifier = cdc.makeClassifier(cdc.readTrainingExamples(train_file));
		String encoding = "utf-8";
		for (String line : ObjectBank.getLineIterator(test_file, encoding)) {
			// instead of the method in the line below, if you have the
			// individual elements
			// already you can use cdc.makeDatumFromStrings(String[])
			Datum<String, String> datum = cdc.makeDatumFromLine(line);
			System.out.println("Datum: {" + line + "]\tPredicted Category: " + classifier.classOf(datum));
			System.out.println(" Scores: " + classifier.scoresOf(datum));
			Counter<String> counter = classifier.scoresOf(datum);
			Set<String> set = counter.keySet();
			for (String element : set) {
				System.out.printf("Scores - %-6s: %5.2f ", element,
						counter.getCount(element));
			}
			System.out.println();
		}

		System.out.println();
		String sample[] = { "", "6.90", "9.8", "15.69" };
		Datum<String, String> datum = cdc.makeDatumFromStrings(sample);
		System.out.println("Category: " + classifier.classOf(datum));
	}
	
	
	public static void usingStanfordSentimentAnalysis() {        
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        String review = "An overly sentimental film with a somewhat "
                + "problematic message, but its sweetness and charm "
                + "are occasionally enough to approximate true depth "
                + "and grace. ";
        
        Annotation annotation = new Annotation(review);
        pipeline.annotate(annotation);       
        
        System.out.println("---sentimentText");
        String[] sentimentText = {"Very Negative", "Negative", "Neutral",
            "Positive", "Very Positive"};
        for (CoreMap sentence : annotation.get( CoreAnnotations.SentencesAnnotation.class)) {
            Tree tree = sentence.get(SentimentAnnotatedTree.class);
            System.out.println("---Number of children: " + tree.numChildren());
            System.out.println("[" + tree.getChild(0) + "][" + tree.getChild(1) + "]");
            tree.printLocalTree();
            int score = RNNCoreAnnotations.getPredictedClass(tree);
            System.out.println(sentimentText[score]);
        }        
        
        String sam = "Sam was an odd sort of fellow. Not prone to angry and "
                + "not prone to merriment. Overall, an odd fellow.";
        String mary = "Mary thought that custard pie was the best pie in the "
                + "world. However, she loathed chocolate pie.";
        
    }    
	
	public static void CRF() {
		// Classifer
        CRFClassifier<CoreMap> crf
                = CRFClassifier.getClassifierNoExceptions(
                        "C:/Current Books in Progress/NLP and Java/Models"
                        + "/english.all.3class.distsim.crf.ser.gz");
        String S1 = "Good afternoon Rajat Raina, how are you today?";
        String S2 = "I go to school at Stanford University, which is located in California.";
        System.out.println(crf.classifyToString(S1));
        System.out.println(crf.classifyWithInlineXML(S2));
        System.out.println(crf.classifyToString(S2, "xml", true));

        Object classification[] = crf.classify(S2).toArray();
        for (int i = 0; i < classification.length; i++) {
            System.out.println(classification[i]);
        }
	}
}
