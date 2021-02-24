package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NumericVariableRepresentation extends AbstractVariableRepresentation {

	private String type;

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element representationElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REPRESENTATION);

		super.appendToElement(representationElement, doc, namespace);
		element.appendChild(representationElement);

		Element numericTypeCodeElement = doc.createElementNS(namespace, NODE_NAME_NUMERIC_TYPE_CODE);
		numericTypeCodeElement.setTextContent(getType());

		Element numericRepresentationElement = doc.createElementNS(namespace, NODE_NAME_NUMERIC_REPRESENTATION);
		numericRepresentationElement.appendChild(numericTypeCodeElement);
		numericRepresentationElement.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		numericRepresentationElement.setAttribute(ATTRIBUTE_NAME_DECIMAL_POSITIONS, "4");

		representationElement.appendChild(numericRepresentationElement);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}