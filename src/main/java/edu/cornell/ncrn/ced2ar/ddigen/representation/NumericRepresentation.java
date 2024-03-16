package edu.cornell.ncrn.ced2ar.ddigen.representation;

public class NumericRepresentation extends Representation {
	private String type;

	public NumericRepresentation() {
	}

	public NumericRepresentation(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}