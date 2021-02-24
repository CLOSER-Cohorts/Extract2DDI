package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableReferenceFragment extends Fragment {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:VariableReference";

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element variableElement = doc.createElementNS(namespace, NODE_NAME_VARIABLE_REFERENCE);
		element.appendChild(variableElement);

		super.appendToElement(variableElement, doc, namespace);
	}
}