package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class Fragment extends FragmentReference {

	public static final String NODE_NAME_URN = "r:URN";

	private String urn;

	public Fragment(String agency, String id, int version) {
		super(agency, id, version);
		setUrn(FragmentUtil.generateUrn(agency, id, version));
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		// URN
		Element urnElement = doc.createElementNS(namespace, NODE_NAME_URN);
		urnElement.setTextContent(getUrn());
		element.appendChild(urnElement);

		super.appendToElement(element, doc, namespace);
	}
}