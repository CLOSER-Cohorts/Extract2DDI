package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.UUID;

public abstract class ElementWithUrn implements Appendable {

	public static final String ATTRIBUTE_NAME_TYPE_OF_IDENTIFIER = "typeOfIdentifier";
	public static final String ATTRIBUTE_VALUE_TYPE_OF_IDENTIFIER = "Canonical";
	public static final String NODE_NAME_URN = "r:URN";

	private String agency;
	private String id;
	private String identifierType;
	private String urn;
	private int version;

	public ElementWithUrn(String agency) {
		this(UUID.randomUUID().toString(), agency, 1);
	}

	public ElementWithUrn(String id, String agency) {
		this(id, agency, 1);
	}

	public ElementWithUrn(String id, String agency, int version) {
		setAgency(agency);
		setId(id);
		setIdentifierType(ATTRIBUTE_VALUE_TYPE_OF_IDENTIFIER);
		setUrn(FragmentUtil.generateUrn(id, agency, version));
		setVersion(version);
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element urn = doc.createElement(NODE_NAME_URN);
		urn.setAttribute(ATTRIBUTE_NAME_TYPE_OF_IDENTIFIER, getIdentifierType());
		urn.setTextContent(getUrn());
		parent.appendChild(urn);
	}

	public String getAgency() {
		return agency;
	}

	public String getId() {
		return id;
	}

	public String getIdentifierType() {
		return identifierType;
	}

	public String getUrn() {
		return urn;
	}

	public int getVersion() {
		return version;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdentifierType(String identifierType) {
		this.identifierType = identifierType;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}