package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Title implements Appendable {

	public static final String NODE_NAME_TITLE = "r:Title";

	private StringElement string;

	public Title(StringElement string) {
		setString(string);
	}

	public StringElement getString() {
		return string;
	}

	public void setString(StringElement string) {
		this.string = string;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element title = doc.createElement(NODE_NAME_TITLE);
		if (getString() != null) {
			getString().appendToElement(title, doc);
		}
		element.appendChild(title);
	}
}