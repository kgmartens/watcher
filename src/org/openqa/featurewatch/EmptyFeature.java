package org.openqa.featurewatch;

import java.util.regex.Pattern;


public class EmptyFeature implements FeatureWatcher {

	public Diagnosis execute(String contents) {
		Diagnosis result = new Diagnosis(1);
		if (!Pattern.compile("^[Ss]cenario:.+", Pattern.MULTILINE).matcher(contents).find()){
			result.recordFailure("FAILURE: No Scenarios Present");
		}
		return result;
			
	}

}
