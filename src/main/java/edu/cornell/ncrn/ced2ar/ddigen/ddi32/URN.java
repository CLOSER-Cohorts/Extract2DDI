package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class URN implements Appendable {

	public static final String ATTRIBUTE_NAME_TYPE_OF_IDENTIFIER = "typeOfIdentifier";
	public static final String ATTRIBUTE_VALUE_TYPE_OF_IDENTIFIER = "Canonical";

	private String identifierType;

	public URN() {
		setIdentifierType(ATTRIBUTE_VALUE_TYPE_OF_IDENTIFIER);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		// URN
		Element urn = doc.createElement("r:URN");
		urn.setAttribute(ATTRIBUTE_NAME_TYPE_OF_IDENTIFIER, getIdentifierType());
		urn.setTextContent("urn:ddi:uk.closer:YjBrJZJriqdWsl1g:1.0.0");
		element.appendChild(urn);
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}
}
