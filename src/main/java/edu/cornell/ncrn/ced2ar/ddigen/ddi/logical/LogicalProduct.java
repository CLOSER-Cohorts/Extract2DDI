package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.List;

public class LogicalProduct {

	private List<Category> categoryList;
	private List<Variable> variableList;

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}

	public void setCategoryList(
		List<Category> categoryList) {
		this.categoryList = categoryList;
	}
}
