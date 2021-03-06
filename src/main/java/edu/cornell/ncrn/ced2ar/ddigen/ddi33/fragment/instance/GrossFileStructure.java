package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GrossFileStructure extends FragmentWithUrn {

	public static final String NODE_NAME_GROSS_FILE_STRUCTURE = "GrossFileStructure";
	public static final String NODE_NAME_CASE_QUANTITY = "CaseQuantity";

	private int caseQuantity;

	public GrossFileStructure(String id, String agency, int version, int caseQuantity) {
		super(id, agency, version);
		setCaseQuantity(caseQuantity);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element fragment = doc.createElement(NODE_NAME_GROSS_FILE_STRUCTURE);
		super.appendToElement(fragment, doc);

		Element caseQuantity = doc.createElement(NODE_NAME_CASE_QUANTITY);
		caseQuantity.setTextContent(Integer.toString(getCaseQuantity()));
		fragment.appendChild(caseQuantity);

		element.appendChild(fragment);
	}

	public int getCaseQuantity() {
		return caseQuantity;
	}

	public void setCaseQuantity(int caseQuantity) {
		this.caseQuantity = caseQuantity;
	}
}