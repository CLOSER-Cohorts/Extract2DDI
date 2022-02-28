package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeVariableRepresentation extends VariableRepresentation {

	public static final String NODE_NAME_NUMERIC_REPRESENTATION = "r:CodeRepresentation";
	public static final String NODE_NAME_CODE_LIST_REFERENCE = "r:CodeListReference";

	private Reference referenceElement;

	public CodeVariableRepresentation(String recommendedDataType) {
		super(recommendedDataType);
		setRecommendedDataType(recommendedDataType);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element representation = doc.createElement(NODE_NAME_NUMERIC_REPRESENTATION);

		super.appendToElement(representation, doc);

		// Code List Reference
		getReferenceElement().appendToElement(representation, doc);

		element.appendChild(representation);
	}

	public Reference getReferenceElement() {
		return referenceElement;
	}

	public void setReferenceElement(String codeId, String agency) {
		this.referenceElement = new CodeListReference(codeId, agency);
	}
}
