package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Label implements Appendable {

	public static final String ATTRIBUTE_NAME_XML_LANG = "xml:lang";

	public static final String NODE_NAME_LABEL = "r:Label";
	public static final String NODE_NAME_CONTENT = "r:Content";

	private String content;
	private String ddiLanguage;

	public Label(String content, String ddiLanguage) {
		setContent(content);
		setDdiLanguage(ddiLanguage);
	}

	public String getContent() {
		return content;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element label = doc.createElement(NODE_NAME_LABEL);
		Element content = doc.createElement(NODE_NAME_CONTENT);
		content.setAttribute(ATTRIBUTE_NAME_XML_LANG, getDdiLanguage());
		content.setTextContent(getContent());
		label.appendChild(content);
		element.appendChild(label);
	}
}