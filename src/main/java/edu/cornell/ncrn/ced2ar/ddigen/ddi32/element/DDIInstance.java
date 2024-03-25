package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class DDIInstance extends ElementWithUrn {

	public static final String NODE_NAME_DDI_INSTANCE = "DDIInstance";

	public static final String ATTRIBUTE_NAME_NAMESPACE = "xmlns";
	public static final String ATTRIBUTE_VALUE_NAMESPACE = "ddi:instance:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_R = "xmlns:r";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_R = "ddi:instance:3_2";

	private ResourcePackageElement resourcePackage;
	private Citation citation;

	public DDIInstance(String agency, String ddiLanguage, String citationTitle, String citationAlternateTitle) {
		super(agency);

		Citation citation = new Citation(citationTitle, citationAlternateTitle, ddiLanguage);
		setCitation(citation);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		// Resource Package
		super.appendToElement(element, doc);

		// Citation
		if (getCitation() != null) {
			getCitation().appendToElement(element, doc);
		}
		getResourcePackage().appendToElement(element, doc);
	}

	public Citation getCitation() {
		return citation;
	}

	public ResourcePackageElement getResourcePackage() {
		return resourcePackage;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}
	public void setResourcePackage(ResourcePackageElement resourcePackage) {
		this.resourcePackage = resourcePackage;
	}
}
