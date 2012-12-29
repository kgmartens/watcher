package org.openqa.featurewatch;

public class Diagnosis {
	private boolean okToGo = true;
	private StringBuilder failures;

	public Diagnosis(int initialSize) {
		okToGo = true;
		failures = new StringBuilder(initialSize);
	}

	public boolean isOkToGo() {
		return okToGo;
	}

	public void setOkToGo(boolean okToGo) {
		this.okToGo = okToGo;
	}

	public StringBuilder getFailures() {
		return failures;
	}

	public void recordFailure(String failure) {
		failures.append(failure);
		okToGo = false;
	}
	
}
