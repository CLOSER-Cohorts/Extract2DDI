package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Label;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableFragment extends Fragment {

	public static final String NODE_NAME_VARIABLE = "ddi:Variable";
	public static final String NODE_NAME_VARIABLE_NAME = "VariableName";

	private Label label;
	private StringElement name;
	private AbstractVariableRepresentation variableRepresentation;

	public VariableFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public StringElement getName() {
		return name;
	}

	public Label getLabel() {
		return label;
	}

	public AbstractVariableRepresentation getVariableRepresentation() {
		return variableRepresentation;
	}

	public void setName(StringElement name) {
		this.name = name;
	}

	public void setLabel(Label label) {
		this.label = label;
	}

	public void setRepresentation(AbstractVariableRepresentation variableRepresentation) {
		this.variableRepresentation = variableRepresentation;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		Element variable = doc.createElementNS(namespace, NODE_NAME_VARIABLE);
		fragment.appendChild(variable);
		element.appendChild(fragment);

		super.appendToElement(variable, doc, namespace);

		// VariableName
		Element variableName = doc.createElementNS(namespace, NODE_NAME_VARIABLE_NAME);
		getName().appendToElement(variableName, doc, namespace);
		variable.appendChild(variableName);

		// Label
		getLabel().appendToElement(variable, doc, namespace);

		// VariableRepresentation
		AbstractVariableRepresentation variableRepresentation = getVariableRepresentation();
		if (variableRepresentation != null) {
			variableRepresentation.appendToElement(variable, doc, namespace);
		}
	}
}