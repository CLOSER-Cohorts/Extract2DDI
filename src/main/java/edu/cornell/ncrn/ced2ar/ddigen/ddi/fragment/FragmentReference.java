package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class FragmentReference implements Appendable {

	public static final String NODE_NAME_FRAGMENT = "ddi:Fragment";
	public static final String NODE_NAME_AGENCY = "r:Agency";
	public static final String NODE_NAME_ID = "r:ID";
	public static final String NODE_NAME_VERSION = "r:Version";

	private String agency;
	private String id;
	private int version;

	public FragmentReference(String agency, String id, int version) {
		setAgency(agency);
		setId(id);
		setVersion(version);
	}

	public String getAgency() {
		return agency;
	}

	public String getId() {
		return id;
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

	public void setVersion(int version) {
		this.version = version;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {

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
		versionElement.setTextContent(Integer.toString(getVersion()));
		element.appendChild(versionElement);
	}
}