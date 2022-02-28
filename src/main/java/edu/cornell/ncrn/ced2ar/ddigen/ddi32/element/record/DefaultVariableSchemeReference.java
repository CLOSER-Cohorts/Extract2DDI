package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ReferenceObjectType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DefaultVariableSchemeReference extends Reference {

	public static final String NODE_NAME_DEFAULT_VARIABLE_SCHEME_REFERENCE = "r:DefaultVariableSchemeReference";

	public DefaultVariableSchemeReference(String id, String agency) {
		super(id, agency, NODE_NAME_DEFAULT_VARIABLE_SCHEME_REFERENCE, ReferenceObjectType.VariableScheme);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		super.appendToElement(element, doc);
	}

}
