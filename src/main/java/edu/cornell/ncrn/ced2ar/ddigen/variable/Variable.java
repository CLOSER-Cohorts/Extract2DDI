package edu.cornell.ncrn.ced2ar.ddigen.variable;

import edu.cornell.ncrn.ced2ar.ddigen.representation.Representation;

public class Variable {

	private String id;
	private String label;
	private String name;
	private Representation representation;

	public Variable() {
	}

	public Variable(String id) {
		setId(id);
	}

	public String getId() {
		return id;
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

	public void setId(String id) {
		this.id = id;
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