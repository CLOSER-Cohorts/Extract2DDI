package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DDI32DocumentGenerator {

	public static final String ATTRIBUTE_NAME_NAMESPACE_DDI = "xmlns:ddi";
	public static final String ATTRIBUTE_VALUE_NAMESPACE_DDI = "ddi:logicalproduct:3_2";
	public static final String NODE_NAME_FRAGMENT_INSTANCE = "ddi:DDIInstance";

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

		Element ddiInstanceElement = doc.createElement(NODE_NAME_FRAGMENT_INSTANCE);
		ddiInstanceElement.setAttribute(DDIInstance.ATTRIBUTE_NAME_NAMESPACE, DDIInstance.ATTRIBUTE_VALUE_NAMESPACE);
		ddiInstanceElement.setAttribute(DDIInstance.ATTRIBUTE_NAME_NAMESPACE_R, DDIInstance.ATTRIBUTE_VALUE_NAMESPACE_R);
		ddiInstanceElement.setAttribute(ATTRIBUTE_NAME_NAMESPACE_DDI, ATTRIBUTE_VALUE_NAMESPACE_DDI);

		doc.appendChild(ddiInstanceElement);
		doc.getDocumentElement().normalize();

		ddiInstance.appendToElement(ddiInstanceElement, doc);

		return doc;
	}
}