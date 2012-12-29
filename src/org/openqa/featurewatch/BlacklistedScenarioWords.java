package org.openqa.featurewatch;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;

public class BlacklistedScenarioWords implements FeatureWatcher{

	private HashSet<String> blacklistedWords = new HashSet<String>();

	public void setBlacklistWords(File blacklist) throws IOException {
		blacklistedWords.addAll(FileUtils.readLines(blacklist));
	}

	public Diagnosis execute(String contents) {
		Diagnosis result = new Diagnosis(blacklistedWords.size());
		for (String word: blacklistedWords) {
			if(Pattern.compile(word, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE).matcher(contents).find()){
				result.recordFailure("FAILURE: Feature contains '" + word + "'");
			}
		}
		return result;
	}


}
