package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableSchemeReference extends Reference {

	public static final String NODE_NAME_VARIABLE_SCHEME_REFERENCE = "r:VariableSchemeReference";

	public VariableSchemeReference(String id, String agency) {
		super(id, agency, NODE_NAME_VARIABLE_SCHEME_REFERENCE, ReferenceObjectType.VariableScheme);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		super.appendToElement(element, doc);
	}

}
