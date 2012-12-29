package org.openqa.featurewatch;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmptyFeatureTest {

	private static final String NEWLINE = System.getProperty("line.separator");
	private EmptyFeature emptyFeature = new EmptyFeature();

	@Test
	public void testRulePassesWhenScenarioIsPresent() {
		String feature = "Feature: I have a dream" + NEWLINE + "Scenario: stuff";
		Diagnosis result = emptyFeature.execute(feature);
		assertTrue(result.isOkToGo());
	}

	@Test
	public void testRulePassesWhenLowerCaseScenarioIsPresent() {
		String feature = "Feature: I have a dream" + NEWLINE + "scenario: stuff";
		Diagnosis result = emptyFeature.execute(feature);
		assertTrue(result.isOkToGo());
		assertEquals(0, result.getFailures().length());
	}

	@Test
	public void testRuleFailsWhenNoScenarioIsPresent() {
		String feature = "Feature: I feel empty";
		Diagnosis result = emptyFeature.execute(feature);
		assertFalse(result.isOkToGo());
		assertTrue(result.getFailures().toString().contains("FAILURE: No Scenarios Present"));
	}

}
