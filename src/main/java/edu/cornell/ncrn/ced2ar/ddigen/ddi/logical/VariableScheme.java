package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.ArrayList;
import java.util.List;

public class VariableScheme {
	private List<Variable> variableList = new ArrayList<>();

	public void addVariable(Variable variable) {
		this.variableList.add(variable);
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}
}
