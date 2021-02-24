package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextVariableRepresentation extends AbstractVariableRepresentation {

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element representation = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REPRESENTATION);
		super.appendToElement(representation, doc, namespace);
		element.appendChild(representation);

		Element textRepresentation = doc.createElementNS(namespace, NODE_NAME_TEXT_REPRESENTATION);
		textRepresentation.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		textRepresentation.setAttribute(ATTRIBUTE_NAME_CLASSIFICATION_LEVEL, ATTRIBUTE_VALUE_NOMINAL);
		representation.appendChild(textRepresentation);
	}
}