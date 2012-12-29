package org.openqa.featurewatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.junit.Before;
import org.junit.Test;

public class ExaminatorTest {
	private Examinator watcher = new Examinator();
	private BlacklistedScenarioWords blacklistScenarioWords;
	private EmptyFeature emptyFeature;
	
	@Before
	public void setupFeatureWatchers() throws IOException{
		blacklistScenarioWords = new BlacklistedScenarioWords();
		File words = FileUtils.toFile(this.getClass().getResource("/blacklisted_words.txt"));
		blacklistScenarioWords.setBlacklistWords(words);
		emptyFeature = new EmptyFeature();
	}
	
	@Test
	public void failsBadFeatureFileExample() throws Exception {
		File badFeature = FileUtils.toFile(this.getClass().getResource("/feature_file_with_no_scenarios.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(badFeature, emptyFeature);
		
		assertFalse(result.get(EmptyFeature.class.getName()).isOkToGo());
	}

	@Test
	public void passesGoodFeatureFileExample() throws Exception {
		File goodFeatures = FileUtils.toFile(this.getClass().getResource("/feature_file_with_scenarios.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(goodFeatures, emptyFeature);
		
		assertTrue(result.get(EmptyFeature.class.getName()).isOkToGo());
	}

	@Test
	public void failsWhenContainsBlacklistedWords() throws Exception {
		File featureFile = FileUtils.toFile(this.getClass().getResource("/feature_file_with_blacklisted_words.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(featureFile, blacklistScenarioWords);
		
		assertFalse(result.get(BlacklistedScenarioWords.class.getName()).isOkToGo());
	}

	@Test
	public void examinesOnlySpecificTests() throws Exception {
		File featureFile = FileUtils.toFile(this.getClass().getResource("/feature_file_with_blacklisted_words.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(featureFile, emptyFeature);
		
		assertTrue(result.get(EmptyFeature.class.getName()).isOkToGo());
	}
	
	@Test
	public void tracksEachTestType() throws Exception {
		File featureFile = FileUtils.toFile(this.getClass().getResource("/feature_file_with_blacklisted_words.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(featureFile, blacklistScenarioWords, emptyFeature);
		
		assertTrue(result.keySet().contains(BlacklistedScenarioWords.class.getName()));
		assertTrue(result.keySet().contains(EmptyFeature.class.getName()));
		assertFalse(result.get(BlacklistedScenarioWords.class.getName()).isOkToGo());
		assertTrue(result.get(EmptyFeature.class.getName()).isOkToGo());
	}
	
	@Test
	public void indicatesFailingTests() throws Exception {
		File featureFile = FileUtils.toFile(this.getClass().getResource("/feature_file_with_no_scenarios.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(featureFile, blacklistScenarioWords, emptyFeature);
		
		assertEquals("FAILURE: No Scenarios Present", result.get(EmptyFeature.class.getName()).getFailures().toString());
	}
	
	@Test
	public void revealsMultipleFailuresInResults() throws Exception {
		File featureFile = FileUtils.toFile(this.getClass().getResource("/feature_file_with_blacklisted_words.feature"));
		
		Map<String, Diagnosis> result = watcher.examineFeatures(featureFile, blacklistScenarioWords, emptyFeature);
		
		assertTrue(result.get(BlacklistedScenarioWords.class.getName()).getFailures().toString().contains("FAILURE: Feature contains 'service'"));
		assertTrue(result.get(BlacklistedScenarioWords.class.getName()).getFailures().toString().contains("FAILURE: Feature contains 'xml'"));
		assertEquals("", result.get(EmptyFeature.class.getName()).getFailures().toString());
	}
}
