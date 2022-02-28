package edu.cornell.ncrn.ced2ar.ddigen.variable;

import java.util.ArrayList;
import java.util.List;

public class VariableScheme {

	private String id;
	private List<Variable> variableList = new ArrayList<>();

	public VariableScheme(String id) {
		setId(id);
	}

	public String getId() {
		return id;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}
}
