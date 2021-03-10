package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractVariableRepresentation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextVariableRepresentation extends AbstractVariableRepresentation {

	public static final String NODE_NAME_TEXT_REPRESENTATION = "r:TextRepresentation";

	@Override
	public void appendToElement(Element element, Document doc) {
		Element representation = doc.createElement(NODE_NAME_VARIABLE_REPRESENTATION);
		super.appendToElement(representation, doc);
		element.appendChild(representation);

		Element textRepresentation = doc.createElement(NODE_NAME_TEXT_REPRESENTATION);
		textRepresentation.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		textRepresentation.setAttribute(ATTRIBUTE_NAME_CLASSIFICATION_LEVEL, ATTRIBUTE_VALUE_NOMINAL);
		representation.appendChild(textRepresentation);
	}
}