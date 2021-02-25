package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DateTimeVariableRepresentation extends AbstractVariableRepresentation {

	public static final String NODE_NAME_DATE_TYPE_CODE = "r:DateTypeCode";
	public static final String NODE_NAME_DATE_TIME_REPRESENTATION = "r:DateTimeRepresentation";

	private String type;

	public DateTimeVariableRepresentation(String type) {
		setType(type);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element representation = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REPRESENTATION);

		super.appendToElement(representation, doc, namespace);
		element.appendChild(representation);

		Element dateTypeCode = doc.createElementNS(namespace, NODE_NAME_DATE_TYPE_CODE);
		dateTypeCode.setTextContent(getType());

		Element dateTimeRepresentation = doc.createElementNS(namespace, NODE_NAME_DATE_TIME_REPRESENTATION);
		dateTimeRepresentation.appendChild(dateTypeCode);
		dateTimeRepresentation.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		representation.appendChild(dateTimeRepresentation);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}