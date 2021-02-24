package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class TextVariableRepresentation extends AbstractVariableRepresentation {

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element representationElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REPRESENTATION);
		super.appendToElement(representationElement, doc, namespace);
		element.appendChild(representationElement);

		Element textRepresentationElement = doc.createElementNS(namespace, NODE_NAME_TEXT_REPRESENTATION);
		textRepresentationElement.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		textRepresentationElement.setAttribute(ATTRIBUTE_NAME_CLASSIFICATION_LEVEL, ATTRIBUTE_VALUE_NOMINAL);
		representationElement.appendChild(textRepresentationElement);
	}
}