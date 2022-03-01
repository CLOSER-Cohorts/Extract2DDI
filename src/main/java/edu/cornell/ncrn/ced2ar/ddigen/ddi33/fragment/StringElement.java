package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class StringElement implements Appendable {

	public static final String ATTRIBUTE_NAME_DDI_LANGUAGE = "xml:lang";

	public static final String NODE_NAME_STRING = "r:String";

	private String content;
	private String ddiLanguage;

	public StringElement(String content, String ddiLanguage) {
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
		Element string = doc.createElement(NODE_NAME_STRING);
		string.setAttribute(ATTRIBUTE_NAME_DDI_LANGUAGE, getDdiLanguage());
		string.setTextContent(getContent());
		element.appendChild(string);
	}
}