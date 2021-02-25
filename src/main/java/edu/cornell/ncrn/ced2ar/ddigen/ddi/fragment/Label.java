package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Label implements Appendable {

	public static final String ATTRIBUTE_NAME_XML_LANG = "xml:lang";

	public static final String NODE_NAME_LABEL = "r:Label";
	public static final String NODE_NAME_CONTENT = "r:Content";

	private String content;
	private String xmlLang;

	public Label(String content, String xmlLang) {
		setContent(content);
		setXmlLang(xmlLang);
	}

	public String getContent() {
		return content;
	}

	public String getXmlLang() {
		return xmlLang;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setXmlLang(String xmlLang) {
		this.xmlLang = xmlLang;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element label = doc.createElementNS(namespace, NODE_NAME_LABEL);
		Element content = doc.createElementNS(namespace, NODE_NAME_CONTENT);
		content.setAttribute(ATTRIBUTE_NAME_XML_LANG, getXmlLang());
		content.setTextContent(getContent());
		label.appendChild(content);
		element.appendChild(label);
	}
}