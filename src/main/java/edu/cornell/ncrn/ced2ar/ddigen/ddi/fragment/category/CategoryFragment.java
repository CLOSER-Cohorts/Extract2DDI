package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CategoryFragment extends FragmentWithUrn {

	public static final String NODE_NAME_CATEGORY = "Category";

	private Label label;
	private AbstractVariableRepresentation variableRepresentation;

	public CategoryFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public Label getLabel() {
		return label;
	}

	public AbstractVariableRepresentation getVariableRepresentation() {
		return variableRepresentation;
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
		fragment.setAttribute(ATTRIBUTE_NAME_NAMESPACE_R, ATTRIBUTE_VALUE_NAMESPACE_R);

		Element variable = doc.createElementNS(namespace, NODE_NAME_CATEGORY);
		fragment.appendChild(variable);
		element.appendChild(fragment);

		super.appendToElement(variable, doc, namespace);

		// Label
		getLabel().appendToElement(variable, doc, namespace);
	}
}