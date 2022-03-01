package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableUsedReferenceElement extends ElementWithUrn {

	public static final String NODE_NAME_VARIABLE_USED_REFERENCE = "ddi:VariableUsedReference";

	public VariableUsedReferenceElement(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableUsedReference = doc.createElement(NODE_NAME_VARIABLE_USED_REFERENCE);

		super.appendToElement(variableUsedReference, doc);

		element.appendChild(variableUsedReference);
	}
}
