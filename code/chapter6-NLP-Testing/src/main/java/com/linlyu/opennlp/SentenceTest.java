package com.linlyu.opennlp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class SentenceTest {
	public static void SentenceDetect(String sent_model) throws InvalidFormatException, IOException {
		String paragraph = "Hi. How are you? This is Mike.";

		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream(sent_model);
		SentenceModel model = new SentenceModel(is);
		is.close();
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

		String sentences[] = sdetector.sentDetect(paragraph);

		System.out.println(sentences[0]);
		System.out.println(sentences[1]);		
	}
	
	public static void  train(String file_train, String file_model) throws IOException {
		DoccatModel model = null;
		ObjectStream<String> lineStream =
				new PlainTextByLineStream(new MarkableFileInputStreamFactory(
						new File(file_train)), "UTF-8");
		ObjectStream<DocumentSample> sampleStream =
				new DocumentSampleStream(lineStream);

		TrainingParameters param = TrainingParameters.defaultParams();
		DoccatFactory factory = new DoccatFactory();
		model = DocumentCategorizerME.train("en", sampleStream,param,factory);

		model.serialize(new FileOutputStream(file_model));
	}
}
