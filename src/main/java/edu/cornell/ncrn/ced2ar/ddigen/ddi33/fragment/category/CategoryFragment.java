package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.AbstractVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Label;
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
	public void appendToElement(Element element, Document doc) {
		Element fragment = createFragment(doc);

		Element category = doc.createElement(NODE_NAME_CATEGORY);
		setVersionDateAttribute(category);
		setUniversallyUniqueAttribute(category);
		setNamespace(category, NAMESPACE_LOGICAL_PRODUCT);

		super.appendToElement(category, doc);

		// Label
		if (getLabel() != null) {
			getLabel().appendToElement(category, doc);
		}

		fragment.appendChild(category);
		element.appendChild(fragment);
	}
}