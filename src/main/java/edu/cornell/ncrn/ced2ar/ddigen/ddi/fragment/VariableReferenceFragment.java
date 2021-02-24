package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableReferenceFragment extends FragmentReference {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:VariableReference";
	public static final String NODE_NAME_OBJECT_TYPE = "r:TypeOfObject";

	private ReferenceType referenceType;

	public VariableReferenceFragment(String agency, String id, int version) {
		super(agency, id, version);
		setReferenceType(ReferenceType.VARIABLE);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element variableElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REFERENCE);
		element.appendChild(variableElement);

		super.appendToElement(variableElement, doc, namespace);

		if (getReferenceType() == ReferenceType.VARIABLE) {
			Element objectType = doc.createElementNS(namespace, NODE_NAME_OBJECT_TYPE);
			objectType.setTextContent("Variable");
			variableElement.appendChild(objectType);
		}
	}

	public ReferenceType getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(ReferenceType referenceType) {
		this.referenceType = referenceType;
	}
}