package edu.cornell.ncrn.ced2ar.ddigen.csv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.stat.Frequency;

public class VariableCsv implements Serializable {
	private static final long serialVersionUID = 3064037465077888018L;
	private String variableStatistics;
	private String variableValueLables;
	private long readErrors;
	private Frequency frequency;
	private List<String> representationTypeCodeList = new ArrayList<>();

	public Frequency getFrequency() {
		return frequency;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public List<String> getRepresentationTypeCodeList() {
		return representationTypeCodeList;
	}

	public void setRepresentationTypeCodeList(List<String> representationTypeCodeList) {
		this.representationTypeCodeList = representationTypeCodeList;
	}

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
