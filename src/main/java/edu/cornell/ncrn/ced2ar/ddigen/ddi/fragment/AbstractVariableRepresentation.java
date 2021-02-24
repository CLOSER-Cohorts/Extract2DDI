package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractVariableRepresentation implements Appendable {

	public static final String ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE = "blankIsMissingValue";
	public static final String ATTRIBUTE_NAME_CLASSIFICATION_LEVEL = "classificationLevel";
	public static final String ATTRIBUTE_NAME_DECIMAL_POSITIONS = "decimalPositions";
	public static final String ATTRIBUTE_VALUE_FALSE = "false";
	public static final String ATTRIBUTE_VALUE_NOMINAL = "Nominal";

	public static final String NODE_NAME_VARIABLE_REPRESENTATION = "VariableRepresentation";
	public static final String NODE_NAME_VARIABLE_ROLE = "VariableRole";
	public static final String NODE_NAME_NUMERIC_TYPE_CODE = "r:NumericTypeCode";
	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:NumericRepresentation";
	public static final String NODE_NAME_TEXT_REPRESENTATION = "r:TextRepresentation";
	public static final String NODE_NAME_DATE_TYPE_CODE = "r:DateTypeCode";
	public static final String NODE_NAME_DATE_TIME_REPRESENTATION = "r:DateTimeRepresentation";

	private String role;

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element variableRole = doc.createElementNS(namespace, NODE_NAME_VARIABLE_ROLE);
		variableRole.setTextContent(getRole());
		variableRole.setTextContent("input");
		element.appendChild(variableRole);
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}