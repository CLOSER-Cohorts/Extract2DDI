package edu.cornell.ncrn.ced2ar.ddigen.representation;

public class DateTimeRepresentation extends Representation {
	private String type;

	public DateTimeRepresentation() {
	}

	public DateTimeRepresentation(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}