package edu.ncrn.cornell.ced2ar.ddi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CodebookVariable implements Serializable {
	private static final long serialVersionUID = 8231039563089976660L;

	private String name;
	private String label;
	private String minValue;
	private String maxValue;
	private String mean;
	private String stdDeviation;
	private String validCount;
	private String invalidCount;
	private String format;
	
	private List<String> variableCodes = new ArrayList<String>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getMinValue() {
		return minValue;
	}

	public void setMinValue(String minValue) {
		this.minValue = minValue;
	}

	public String getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(String maxValue) {
		this.maxValue = maxValue;
	}

	public String getMean() {
		return mean;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

	public String getStdDeviation() {
		return stdDeviation;
	}

	public void setStdDeviation(String stdDeviation) {
		this.stdDeviation = stdDeviation;
	}

	public String getValidCount() {
		return validCount;
	}

	public void setValidCount(String validCount) {
		this.validCount = validCount;
	}

	public String getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(String invalidCount) {
		this.invalidCount = invalidCount;
	}

	public List<String> getVariableCodes() {
		return variableCodes;
	}

	public void setVariableCodes(List<String> variableCodes) {
		this.variableCodes = variableCodes;
	}
	
	public String getFormat(){
		return this.format;
	}
	
	public void setFormat(String f){
		this.format = f;
	}
}
