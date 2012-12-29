package org.openqa.featurewatch;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

public class Examinator {

	// TODO Wrap the IOException that we're potentially throwing with a nice
	// domain centered one that the user will understand
	public Map<String, Diagnosis> examineFeatures(File featureFile, FeatureWatcher... feature) throws IOException {
		String contents = FileUtils.readFileToString(featureFile);

		Map<String, Diagnosis> results = new HashMap<String, Diagnosis>();
		for (FeatureWatcher featureWatcher : feature) {
			results.put(featureWatcher.getClass().getName(), featureWatcher.execute(contents));
		}
		return results;
	}
}
