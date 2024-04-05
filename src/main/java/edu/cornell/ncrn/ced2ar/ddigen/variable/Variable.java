package edu.cornell.ncrn.ced2ar.ddigen.variable;

import edu.cornell.ncrn.ced2ar.ddigen.representation.Representation;

import java.util.UUID;

public class Variable {
	private int deciamls;
	private String id;
	private String label;
	private String name;
	private final String uuid;
	private Representation representation;
	private int width;
	private String format;
	private long validCount;
	private long invalidCount;
	private String min;
	private String max;
	private String mean;
	private String stdDeviation;

	public Variable(String id) {
		this.uuid = UUID.randomUUID().toString();
		setId(id);
	}

	public int getDeciamls() {
		return deciamls;
	}

	public String getId() {
		return id;
	}

	public String getFormat() {
		return format;
	}

	public String getLabel() {
		return label;
	}

	public String getName() {
		return name;
	}

	public Representation getRepresentation() {
		return representation;
	}

	public String getUuid() {
		return uuid;
	}

	public int getWidth() {
		return width;
	}

	public long getInvalidCount() {
		return invalidCount;
	}

	public void setDeciamls(int deciamls) {
		this.deciamls = deciamls;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public void setInvalidCount(long invalidCount) {
		this.invalidCount = invalidCount;
	}

	public void setValidCount(long validCount) {
		this.validCount = validCount;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public void setMean(String mean) {
		this.mean = mean;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public void setStdDeviation(String stdDeviation) {
		this.stdDeviation = stdDeviation;
	}

	public long getValidCount() {
		return validCount;
	}

	public String getMax() {
		return max;
	}

	public String getMean() {
		return mean;
	}

	public String getMin() {
		return min;
	}

	public String getStdDeviation() {
		return stdDeviation;
	}
}