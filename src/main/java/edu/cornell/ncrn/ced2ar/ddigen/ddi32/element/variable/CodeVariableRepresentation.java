package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeVariableRepresentation extends VariableRepresentation {

	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:CodeRepresentation";

	public CodeVariableRepresentation(String recommendedDataType) {
		super(recommendedDataType);
		setRecommendedDataType(recommendedDataType);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableRepresentation = doc.createElement(NODE_NAME_VARIABLE_REPRESENTATION);

		Element numericRepresentation = doc.createElement(NODE_NAME_NUMERIC_REPRESENTATION);

		variableRepresentation.appendChild(numericRepresentation);

		super.appendToElement(numericRepresentation, doc);

		element.appendChild(variableRepresentation);
	}
}
