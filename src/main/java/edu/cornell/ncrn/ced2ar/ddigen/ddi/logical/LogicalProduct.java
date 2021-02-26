package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.List;

public class LogicalProduct {

	private List<CategoryScheme> categorySchemeList;
	private List<VariableScheme> variableSchemeList;

	public List<CategoryScheme> getCategorySchemeList() {
		return categorySchemeList;
	}

	public List<VariableScheme> getVariableSchemeList() {
		return variableSchemeList;
	}

	public void setVariableSchemeList(List<VariableScheme> variableSchemeList) {
		this.variableSchemeList = variableSchemeList;
	}

	public void setCategorySchemeList(List<CategoryScheme> categorySchemeList) {
		this.categorySchemeList = categorySchemeList;
	}
}
