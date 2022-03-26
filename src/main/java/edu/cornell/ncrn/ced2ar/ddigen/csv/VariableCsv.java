package edu.cornell.ncrn.ced2ar.ddigen.csv;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.Frequency;

public class VariableCsv implements Serializable {
	private static final long serialVersionUID = 3064037465077888018L;
	private String variableStatistics;
	private String frequencies;
	private String statistics;
	private String variableValueLables;
	private long readErrors;
	private Map<String, Frequency> variableToFrequencyMap = new HashMap<>();
	private List<Ced2arVariableStat> variableStatList = new ArrayList<>();

	public String getStatistics() {
		return statistics;
	}

	public String getFrequencies() {
		return frequencies;
	}

	public Map<String, Frequency> getVariableToFrequencyMap() {
		return variableToFrequencyMap;
	}

	public void setVariableToFrequencyMap(Map<String, Frequency> variableToFrequencyMap) {
		this.variableToFrequencyMap = variableToFrequencyMap;
	}

	public List<Ced2arVariableStat> getVariableStatList() {
		return variableStatList;
	}

	public void setVariableStatList(List<Ced2arVariableStat> variableStatList) {
		this.variableStatList = variableStatList;
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

	public void setFrequencies(String frequencies) {
		this.frequencies = frequencies;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
}
