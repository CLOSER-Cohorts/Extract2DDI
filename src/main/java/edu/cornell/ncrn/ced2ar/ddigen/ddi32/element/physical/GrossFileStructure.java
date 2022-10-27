package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GrossFileStructure extends ElementWithUrn {

	public static final String NODE_NAME_GROSS_FILE_STRUCTURE = "pi:GrossFileStructure";
	public static final String NODE_NAME_CASE_QUANTITY = "pi:CaseQuantity";

	private Software software;
	private long caseQuantity;

	public GrossFileStructure(String agency, String ddiLanguage, long caseQuantity, String productIdentification) {
		super(agency);
		this.software = new Software(new StringElement("SPSS", ddiLanguage), productIdentification, "pi", SoftwareType.Creation);
		this.caseQuantity = caseQuantity;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element grossFileStructure = doc.createElement(NODE_NAME_GROSS_FILE_STRUCTURE);
		super.appendToElement(grossFileStructure, doc);

		software.appendToElement(grossFileStructure, doc);

		Element caseQuantityElement = doc.createElement(NODE_NAME_CASE_QUANTITY);
		caseQuantityElement.setTextContent(Long.toString(caseQuantity));
		grossFileStructure.appendChild(caseQuantityElement);

		element.appendChild(grossFileStructure);
	}
}
