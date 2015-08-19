package com.linlyu.opennlp;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.util.InvalidFormatException;

public class SentenceTest {
	public static void SentenceDetect() throws InvalidFormatException, IOException {
		String paragraph = "Hi. How are you? This is Mike.";

		// always start with a model, a model is learned from training data
		InputStream is = new FileInputStream("src/resource/en-sent.bin");
		SentenceModel model = new SentenceModel(is);
		is.close();
		SentenceDetectorME sdetector = new SentenceDetectorME(model);

		String sentences[] = sdetector.sentDetect(paragraph);

		System.out.println(sentences[0]);
		System.out.println(sentences[1]);		
	}
}
