package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.AbstractVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.code.CodeListReferenceFragment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeVariableRepresentation extends AbstractVariableRepresentation {

	private static String NODE_NAME_CODE_REPRESENTATION = "r:CodeRepresentation";

	private CodeListReferenceFragment codeListReferenceFragment;

	public CodeVariableRepresentation(String id, String agency, int version) {
		setCodeListReferenceFragment(new CodeListReferenceFragment(id, agency, version));
	}

	public CodeListReferenceFragment getCodeListReferenceFragment() {
		return codeListReferenceFragment;
	}

	public void setCodeListReferenceFragment(
		CodeListReferenceFragment codeListReferenceFragment) {
		this.codeListReferenceFragment = codeListReferenceFragment;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element representation = doc.createElement(NODE_NAME_VARIABLE_REPRESENTATION);
		super.appendToElement(representation, doc);
		element.appendChild(representation);

		Element codeRepresentation = doc.createElement(NODE_NAME_CODE_REPRESENTATION);
		codeRepresentation.setAttribute(ATTRIBUTE_NAME_BLANK_IS_MISSING_VALUE, ATTRIBUTE_VALUE_FALSE);
		codeRepresentation.setAttribute(ATTRIBUTE_NAME_CLASSIFICATION_LEVEL, ATTRIBUTE_VALUE_NOMINAL);
		representation.appendChild(codeRepresentation);

		getCodeListReferenceFragment().appendToElement(codeRepresentation, doc);
	}
}