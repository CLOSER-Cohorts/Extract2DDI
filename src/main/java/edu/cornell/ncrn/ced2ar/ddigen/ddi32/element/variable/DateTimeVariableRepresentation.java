package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DateTimeVariableRepresentation extends VariableRepresentation {

	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:DateTimeRepresentation";
	public static final String NODE_NAME_DATE_TYPE_CODE = "r:DateTypeCode";
	public static final String NODE_VALUE_DATE_TYPE_CODE = "DateTime";

	public DateTimeVariableRepresentation(String recommendedDataType) {
		super(recommendedDataType);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element representation = doc.createElement(NODE_NAME_NUMERIC_REPRESENTATION);

		super.appendToElement(representation, doc);

		// Date Type Code
		Element dateTypeCode = doc.createElement(NODE_NAME_DATE_TYPE_CODE);
		dateTypeCode.setTextContent(NODE_VALUE_DATE_TYPE_CODE);
		representation.appendChild(dateTypeCode);

		element.appendChild(representation);
	}
}
