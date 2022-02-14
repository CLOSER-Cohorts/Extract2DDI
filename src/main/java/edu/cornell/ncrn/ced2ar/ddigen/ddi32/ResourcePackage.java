package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ResourcePackage implements Appendable {

	public static final String NODE_NAME_RESOURCE_PACKAGE = "g:ResourcePackage";

	private URN urn;
	private Purpose purpose;

	@Override
	public void appendToElement(Element element, Document doc) {
		Element resourcePackage = doc.createElement(NODE_NAME_RESOURCE_PACKAGE);

		// URN
		getUrn().appendToElement(resourcePackage, doc);

		// Purpose
		getPurpose().appendToElement(resourcePackage, doc);

		element.appendChild(resourcePackage);
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public URN getUrn() {
		return urn;
	}

	public void setUrn(URN urn) {
		this.urn = urn;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}
}
