package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Label;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Name;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableElement extends ElementWithUrn {

	public static final String NODE_NAME_VARIABLE = "ddi:Variable";
	public static final String NODE_NAME_VARIABLE_NAME = "ddi:VariableName";

	private Name name;
	private Label label;
	private VariableRepresentation variableRepresentation;

	public VariableElement(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableElement = doc.createElement(NODE_NAME_VARIABLE);

		super.appendToElement(variableElement, doc);

		getName().appendToElement(variableElement, doc);

		getLabel().appendToElement(variableElement, doc);

		getVariableRepresentation().appendToElement(variableElement, doc);

		element.appendChild(variableElement);
	}

	public Label getLabel() {
		return label;
	}

	public Name getName() {
		return name;
	}

	public VariableRepresentation getVariableRepresentation() {
		return variableRepresentation;
	}

	public void setLabel(String label) {
		this.label = new Label(label);
	}

	public void setName(String name) {
		this.name = new Name(NODE_NAME_VARIABLE_NAME, name);
	}

	public void setVariableRepresentation(VariableRepresentation variableRepresentation) {
		this.variableRepresentation = variableRepresentation;
	}
}
