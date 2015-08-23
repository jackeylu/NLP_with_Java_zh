package com.linlyu.stanford;

import static org.junit.Assert.*;

import org.junit.Test;

public class UseStanfordClassifierTest {

	@Test
	public void testUsingStandfordClassifier() {
		UseStanfordClassifier.usingStandfordClassifier("target/test-classes/box.prop",
				"target/test-classes/box.train",
				"target/test-classes/box.test");
	}

	@Test
	public void testUsingStanfordSentimentAnalysis() {
		fail("Not yet implemented");
	}

}
