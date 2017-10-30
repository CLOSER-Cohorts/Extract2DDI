package edu.ncrn.cornell.ced2ar.csv;

import java.io.Serializable;

public class VariableCsv implements Serializable {
	private static final long serialVersionUID = 3064037465077888018L;
	private String variableStatistics;
	private String variableValueLables;
	private long readErrors;

	public String getVariableStatistics() {
		return variableStatistics;
	}

	public void setVariableStatistics(String variableStatistics) {
		this.variableStatistics = variableStatistics;
	}

	public String getVariableValueLables() {
		return variableValueLables;
	}

	public void setVariableValueLables(String variableValueLables) {
		this.variableValueLables = variableValueLables;
	}

	public long getReadErrors() {
		return readErrors;
	}

	public void setReadErrors(long readErrors) {
		this.readErrors = readErrors;
	}
}
