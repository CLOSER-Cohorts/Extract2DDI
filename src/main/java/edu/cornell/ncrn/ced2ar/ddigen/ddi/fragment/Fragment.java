package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Fragment implements Appendable {

	public static final String ATTRIBUTE_NAME_NAMESPACE_R = "xmlns:r";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_R = "ddi:reusable:3_3";

	public static final String NODE_NAME_FRAGMENT = "ddi:Fragment";
	public static final String NODE_NAME_AGENCY = "r:Agency";
	public static final String NODE_NAME_ID = "r:ID";
	public static final String NODE_NAME_VERSION = "r:Version";

	private String agency;
	private String id;
	private int version;

	public Fragment(String id, String agency, int version) {
		setAgency(agency);
		setId(id);
		setVersion(version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		// Agency
		Element agency = doc.createElement(NODE_NAME_AGENCY);
		agency.setTextContent(getAgency());
		element.appendChild(agency);

		// ID
		Element id = doc.createElement(NODE_NAME_ID);
		id.setTextContent(getId());
		element.appendChild(id);

		// Version
		Element version = doc.createElement(NODE_NAME_VERSION);
		version.setTextContent(Integer.toString(getVersion()));
		element.appendChild(version);
	}

	protected Element createFragment(Document doc) {
		return doc.createElementNS(ATTRIBUTE_VALUE_NAMESPACE_R, NODE_NAME_FRAGMENT);
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
}