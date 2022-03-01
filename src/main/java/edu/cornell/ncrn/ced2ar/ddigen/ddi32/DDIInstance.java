package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DDIInstance extends ElementWithUrn {

	public static final String NODE_NAME_DDI_INSTANCE = "DDIInstance";

	public static final String ATTRIBUTE_NAME_NAMESPACE = "xmlns";
	public static final String ATTRIBUTE_VALUE_NAMESPACE = "ddi:instance:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_R = "xmlns:r";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_R = "ddi:instance:3_2";

	private ResourcePackageElement resourcePackage;

	public DDIInstance(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element ddiInstance = doc.createElement(NODE_NAME_DDI_INSTANCE);

		super.appendToElement(element, doc);

		// Resource Package
		getResourcePackage().appendToElement(ddiInstance, doc);

		element.appendChild(ddiInstance);
	}

	public ResourcePackageElement getResourcePackage() {
		return resourcePackage;
	}

	public void setResourcePackage(ResourcePackageElement resourcePackage) {
		this.resourcePackage = resourcePackage;
	}
}
