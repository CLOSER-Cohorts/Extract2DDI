package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

public class Variable {
	private String label;
	private String name;
	private Representation representation;

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public Representation getRepresentation() {
		return representation;
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
}