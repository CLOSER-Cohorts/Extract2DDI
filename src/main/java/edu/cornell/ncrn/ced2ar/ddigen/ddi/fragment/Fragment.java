package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Fragment implements Appendable {

	public static final String NODE_NAME_FRAGMENT = "ddi:Fragment";
	public static final String NODE_NAME_URN = "r:URN";
	public static final String NODE_NAME_AGENCY = "r:Agency";
	public static final String NODE_NAME_ID = "r:ID";
	public static final String NODE_NAME_VERSION = "r:Version";

	private String agency;
	private String id;
	private String urn;
	private String version;

	public String getAgency() {
		return agency;
	}

	public String getId() {
		return id;
	}

	public String getUrn() {
		return urn;
	}

	public String getVersion() {
		return version;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		// URN
		Element urnElement = doc.createElementNS(namespace, NODE_NAME_URN);
		element.appendChild(urnElement);

		// Agency
		Element agencyElement = doc.createElementNS(namespace, NODE_NAME_AGENCY);
		agencyElement.setTextContent(agency);
		element.appendChild(agencyElement);

		// ID
		Element idElement = doc.createElementNS(namespace, NODE_NAME_ID);
		idElement.setTextContent(getId());
		element.appendChild(idElement);

		// Version
		Element versionElement = doc.createElementNS(namespace, NODE_NAME_VERSION);
		element.appendChild(versionElement);
	}
}