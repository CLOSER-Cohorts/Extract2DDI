package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StringElement implements Appendable {

	public static final String ATTRIBUTE_NAME_XML_LANG = "xml:lang";

	public static final String NODE_NAME_STRING = "r:String";

	private java.lang.String content;
	private java.lang.String xmlLang;

	public StringElement(String content, String xmlLang) {
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
		Element string = doc.createElementNS(namespace, NODE_NAME_STRING);
		string.setAttribute(ATTRIBUTE_NAME_XML_LANG, getXmlLang());
		string.setTextContent(getContent());
		element.appendChild(string);
	}
}