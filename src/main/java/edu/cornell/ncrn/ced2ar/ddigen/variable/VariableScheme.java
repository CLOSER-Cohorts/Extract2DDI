package edu.cornell.ncrn.ced2ar.ddigen.variable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class VariableScheme {

	private String id;
	private List<Variable> variableList = new ArrayList<>();
	private final String uuid;

	public VariableScheme() {
		this.uuid = UUID.randomUUID().toString();
	}

	public VariableScheme(String id) {
		this();
		setId(id);
	}

	public String getId() {
		return id;
	}

	public List<Variable> getVariableList() {
		return variableList;
	}

	public String getUuid() {
		return uuid;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setVariableList(List<Variable> variableList) {
		this.variableList = variableList;
	}
}
