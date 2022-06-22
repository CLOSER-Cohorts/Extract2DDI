package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.DDIInstance;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DDI32DocumentGenerator {

	public static final String ATTRIBUTE_NAME_NAMESPACE_DDI = "xmlns:ddi";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_DDI = "ddi:logicalproduct:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_XMLNS = "xmlns";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_XMLNS = "ddi:instance:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_DDI1 = "xmlns:ddi1";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_DDI1 = "ddi:physicaldataproduct_proprietary:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_G = "xmlns:g";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_G = "ddi:group:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_P = "xmlns:p";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_P = "ddi:physicaldataproduct:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_PI = "xmlns:pi";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_PI = "ddi:physicalinstance:3_2";

	public static final String ATTRIBUTE_NAME_NAMESPACE_R = "xmlns:r";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_R = "ddi:reusable:3_2";

	public static final String NODE_NAME_FRAGMENT_INSTANCE = "DDIInstance";

	public static final String ATTRIBUTE_NAME_XML_LANG = "xml:lang";
	public static final String ATTRIBUTE_VALUE_XML_LANG = "en-GB";

	public static final String ATTRIBUTE_NAME_IS_MAINTAINABLE = "isMaintainable";
	public static final String ATTRIBUTE_VALUE_IS_MAINTAINABLE = "true";

	public static final String ATTRIBUTE_NAME_SCOPE_OF_UNIQUENESS = "scopeOfUniqueness";
	public static final String ATTRIBUTE_VALUE_SCOPE_OF_UNIQUENESS = "Agency";

	private DDIInstance ddiInstance;

	public DDI32DocumentGenerator(DDIInstance ddiInstance) {
		setDdiInstance(ddiInstance);
	}

	public void setDdiInstance(DDIInstance ddiInstance) {
		this.ddiInstance = ddiInstance;
	}

	public Document toDocument() throws ParserConfigurationException {
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(true);

		DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
		Document doc = domBuilder.newDocument();

		Element ddiInstanceElement = doc.createElementNS(ATTRIBUTE_VALUE_NAMESPACE_XMLNS, NODE_NAME_FRAGMENT_INSTANCE);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_XML_LANG, ATTRIBUTE_VALUE_XML_LANG);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_IS_MAINTAINABLE, ATTRIBUTE_VALUE_IS_MAINTAINABLE);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_SCOPE_OF_UNIQUENESS, ATTRIBUTE_VALUE_SCOPE_OF_UNIQUENESS);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_DDI, ATTRIBUTE_VALUE_NAMESPACE_DDI);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_DDI1, ATTRIBUTE_VALUE_NAMESPACE_DDI1);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_G, ATTRIBUTE_VALUE_NAMESPACE_G);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_P, ATTRIBUTE_VALUE_NAMESPACE_P);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_PI, ATTRIBUTE_VALUE_NAMESPACE_PI);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_R, ATTRIBUTE_VALUE_NAMESPACE_R);

		doc.appendChild(ddiInstanceElement);
		doc.getDocumentElement().normalize();

		ddiInstance.appendToElement(ddiInstanceElement, doc);

		return doc;
	}
}