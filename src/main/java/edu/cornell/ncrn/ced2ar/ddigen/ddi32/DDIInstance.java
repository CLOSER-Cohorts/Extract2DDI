package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Appendable;

public class DDIInstance implements Appendable {

	public static final String NODE_NAME_DDI_INSTANCE = "DDIInstance";

	public static final String ATTRIBUTE_NAME_NAMESPACE = "xmlns";
	public static final String ATTRIBUTE_VALUE_NAMESPACE = "ddi:instance:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_R = "xmlns:r";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_R = "ddi:instance:3_2";

	public static final String ATTRIBUTE_NAME_VERSION_DATE = "versionDate";

	private ResourcePackage resourcePackage;
	private URN urn;

	@Override
	public void appendToElement(Element element, Document doc) {
		Element ddiInstance = doc.createElement(NODE_NAME_DDI_INSTANCE);

		// URN
		getUrn().appendToElement(ddiInstance, doc);

		// Resource Package
		getResourcePackage().appendToElement(ddiInstance, doc);

		element.appendChild(ddiInstance);
	}

	public ResourcePackage getResourcePackage() {
		return resourcePackage;
	}

	public URN getUrn() {
		return urn;
	}

	public void setResourcePackage(ResourcePackage resourcePackage) {
		this.resourcePackage = resourcePackage;
	}

	public void setUrn(URN urn) {
		this.urn = urn;
	}
}
