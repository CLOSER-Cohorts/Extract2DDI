package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

public class VariableRepresentation {
	private String role;
	private Representation representation;

	public Representation getRepresentation() {
		return representation;
	}

	public String getRole() {
		return role;
	}

	public void setRepresentation(Representation representation) {
		this.representation = representation;
	}

	public void setRole(String role) {
		this.role = role;
	}
}