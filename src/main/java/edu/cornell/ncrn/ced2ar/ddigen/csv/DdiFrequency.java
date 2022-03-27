package edu.cornell.ncrn.ced2ar.ddigen.csv;

import org.apache.commons.math3.stat.Frequency;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class DdiFrequency {
	private static final Logger logger = Logger.getLogger(DdiFrequency.class);

	private String variableName;
	private String variableLabel;
	private Frequency frequency;

	public DdiFrequency(String variableName, String variableLabel, Frequency frequency) {
		setVariableName(variableName);
		setVariableLabel(variableLabel);
		setFrequency(frequency);
	}

	public void appendToStringBuilder(StringBuilder sb, Ced2arVariableStat stat) {
		Frequency freq = getFrequency();

		HashMap<String, String> validValues = stat.getValidValues();
		for (Map.Entry<String, String> mapEntry : validValues.entrySet()) {
			String frequencyCSV = String.format(
				"%s,%s,%s,%s,%s,%s,%s",
				getVariableName(),
				getVariableLabel(),
				mapEntry.getKey(),
				mapEntry.getValue(),
				freq.getCount(mapEntry.getKey()),
				freq.getPct(mapEntry.getKey()),
				freq.getCumFreq(mapEntry.getKey())
			);
			sb.append(frequencyCSV + "\n");
		}
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public String getVariableLabel() {
		return variableLabel;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public void setVariableLabel(String variableLabel) {
		this.variableLabel = variableLabel;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
}
