package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Name;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class VariableSchemeElement extends ElementWithUrn {

	public static final String NODE_NAME_VARIABLE_SCHEME = "ddi:VariableScheme";
	public static final String NODE_NAME_VARIABLE_SCHEME_NAME = "ddi:VariableSchemeName";

	private Name variableSchemeName;

	private List<VariableElement> variableElementList = new ArrayList<>();

	public VariableSchemeElement(String id, String agency) {
		super(id, agency);
	}

	public void addVariableElement(VariableElement variableElement) {
		synchronized (variableElementList) {
			variableElementList.add(variableElement);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableScheme = doc.createElement(NODE_NAME_VARIABLE_SCHEME);

		super.appendToElement(variableScheme, doc);

		getVariableSchemeName().appendToElement(variableScheme, doc);

		for (VariableElement variableElement : getVariableElementList()) {
			variableElement.appendToElement(variableScheme, doc);
		}

		element.appendChild(variableScheme);
	}

	public List<VariableElement> getVariableElementList() {
		return variableElementList;
	}

	public Name getVariableSchemeName() {
		return variableSchemeName;
	}

	public void setVariableSchemeName(String variableSchemeName) {
		this.variableSchemeName = new Name(NODE_NAME_VARIABLE_SCHEME_NAME, variableSchemeName);
	}
}
