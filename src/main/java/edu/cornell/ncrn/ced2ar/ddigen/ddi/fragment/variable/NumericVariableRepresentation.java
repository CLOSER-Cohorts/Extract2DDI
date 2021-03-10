package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractVariableRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NumericVariableRepresentation extends AbstractVariableRepresentation {

	public static final String NODE_NAME_NUMERIC_TYPE_CODE = "r:NumericTypeCode";
	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:NumericRepresentation";

	private String type;

	public NumericVariableRepresentation(String type) {
		setType(type);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element representation = doc.createElement(NODE_NAME_VARIABLE_REPRESENTATION);

		super.appendToElement(representation, doc);
		element.appendChild(representation);

		Element numericTypeCode = doc.createElement(NODE_NAME_NUMERIC_TYPE_CODE);
		numericTypeCode.setTextContent(getType());

		Element numericRepresentation = doc.createElement(NODE_NAME_NUMERIC_REPRESENTATION);
		numericRepresentation.appendChild(numericTypeCode);
		numericRepresentation.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		numericRepresentation.setAttribute(ATTRIBUTE_NAME_DECIMAL_POSITIONS, "4");

		representation.appendChild(numericRepresentation);
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}