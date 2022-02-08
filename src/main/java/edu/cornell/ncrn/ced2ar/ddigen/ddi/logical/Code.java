package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

public class Code {
	private String categoryId;
	private String value;

	public Code() {
	}

	public Code(String categoryId) {
		setCategoryId(categoryId);
	}

	public String getCategoryId() {
		return categoryId;
	}

	public String getValue() {
		return value;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	public void setValue(String value) {
		this.value = value;
	}
}