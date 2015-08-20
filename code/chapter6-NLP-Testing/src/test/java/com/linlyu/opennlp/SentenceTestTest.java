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
		SentenceTest.train("sten-animal.train", "en_animal.model");
	}
}
