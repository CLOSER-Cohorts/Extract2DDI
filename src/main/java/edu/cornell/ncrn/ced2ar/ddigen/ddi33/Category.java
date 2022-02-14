package edu.cornell.ncrn.ced2ar.ddigen.ddi33;

public class Category {
	private String id;
	private String label;

	public Category() {
	}

	public Category(String id) {
		setId(id);
	}

	public String getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setId(String id) {
		this.id = id;
	}
}