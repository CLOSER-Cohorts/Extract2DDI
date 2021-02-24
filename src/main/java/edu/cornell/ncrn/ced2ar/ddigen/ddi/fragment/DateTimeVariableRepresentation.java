package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DateTimeVariableRepresentation extends AbstractVariableRepresentation {
	private String type;

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element representationElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REPRESENTATION);

		super.appendToElement(representationElement, doc, namespace);
		element.appendChild(representationElement);

		Element dateTypeCodeElement = doc.createElementNS(namespace, NODE_NAME_DATE_TYPE_CODE);
		dateTypeCodeElement.setTextContent(getType());

		Element dateTimeRepresentationElement = doc.createElementNS(namespace, NODE_NAME_DATE_TIME_REPRESENTATION);
		dateTimeRepresentationElement.appendChild(dateTypeCodeElement);
		dateTimeRepresentationElement.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		representationElement.appendChild(dateTimeRepresentationElement);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}