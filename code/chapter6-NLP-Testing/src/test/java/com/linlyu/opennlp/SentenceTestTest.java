package com.linlyu.opennlp;

import java.io.IOException;

import org.junit.Test;

import opennlp.tools.util.InvalidFormatException;

public class SentenceTestTest {

	@Test
	public void testSentenceDetect() throws InvalidFormatException, IOException {
		SentenceTest.SentenceDetect("target/test-classes/en-sent.bin");
	}

	@Test
	public void testTrain() throws IOException {
		SentenceTest.train("target/test-classes/en-animal.train", "en-animal.model");
	}
	
	@Test
	public void testClassifyToto() throws InvalidFormatException, IOException {
	    String toto = "Toto belongs to Dorothy Gale, the heroine of "
	            + "the first and many subsequent books. In the first "
	            + "book, he never spoke, although other animals, native "
	            + "to Oz, did. In subsequent books, other animals "
	            + "gained the ability to speak upon reaching Oz or "
	            + "similar lands, but Toto remained speechless.";
	    
	    System.out.println("Testing toto dog");
		SentenceTest.classify("en-animal.model", toto);
	}
	
	@Test
	public void testClassifyCalico() throws InvalidFormatException, IOException {
		String calico = "This cat is also known as a calimanco cat or "
		        + "clouded tiger cat, and by the abbreviation 'tortie'. "
		        + "In the cat fancy, a tortoiseshell cat is patched "
		        + "over with red (or its dilute form, cream) and black "
		        + "(or its dilute blue) mottled throughout the coat.";
	    
		 System.out.println("Testing calico cat");
		SentenceTest.classify("en-animal.model", calico);
	}
}
