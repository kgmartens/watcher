package org.openqa.featurewatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class BlacklistedScenarioWordsTest {

	private static final String NEWLINE = System.getProperty("line.separator");

	private BlacklistedScenarioWords blacklistedScenarioWords = new BlacklistedScenarioWords();

	@Before
	public void setup() throws IOException {
		File blacklistedWords = FileUtils.toFile(this.getClass().getResource("/blacklisted_words.txt"));
		blacklistedScenarioWords.setBlacklistWords(blacklistedWords);
	}

	@Test
	public void testFailsBlacklistedWord() {
		String feature = "Feature x: Y" + NEWLINE + "Scenario: X" + NEWLINE + "Given I love XML";
		Diagnosis result = blacklistedScenarioWords.execute(feature);
		assertFalse(result.isOkToGo());
		assertEquals("FAILURE: Feature contains 'xml'", result.getFailures().toString());
	}

	@Test
	public void passesWhenNoBlacklistedWordsPresent() throws Exception {
		String feature = "Feature x: Y" + NEWLINE + "Scenario: X" + NEWLINE + "Given I love ubiquitous language";
		Diagnosis result = blacklistedScenarioWords.execute(feature);
		assertTrue(result.isOkToGo());
		assertEquals("", result.getFailures().toString());
	}

}
