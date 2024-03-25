package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class NumericVariableRepresentation extends VariableRepresentation {

	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:NumericRepresentation";

	public NumericVariableRepresentation(String recommendedDataType) {
		super(recommendedDataType);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableRepresentation = doc.createElement(NODE_NAME_VARIABLE_REPRESENTATION);

		Element representation = doc.createElement(NODE_NAME_NUMERIC_REPRESENTATION);

		super.appendToElement(representation, doc);

		variableRepresentation.appendChild(representation);
		element.appendChild(variableRepresentation);
	}
}
