package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class ElementWithUrn implements Appendable {

	public static final String ATTRIBUTE_NAME_TYPE_OF_IDENTIFIER = "typeOfIdentifier";
	public static final String ATTRIBUTE_VALUE_TYPE_OF_IDENTIFIER = "Canonical";
	public static final String NODE_NAME_URN = "r:URN";

	private String identifierType;
	private String urn;

	public ElementWithUrn(String id, String agency, int version) {
		setIdentifierType(ATTRIBUTE_VALUE_TYPE_OF_IDENTIFIER);
		setUrn(FragmentUtil.generateUrn(id, agency, version));
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element urn = doc.createElement(NODE_NAME_URN);
		urn.setAttribute(ATTRIBUTE_NAME_TYPE_OF_IDENTIFIER, getIdentifierType());
		urn.setTextContent(getUrn());
		element.appendChild(urn);
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public String getUrn() {
		return urn;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}
}