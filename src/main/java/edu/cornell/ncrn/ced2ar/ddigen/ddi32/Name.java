package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Name implements Appendable {

	private String name;
	private String content;

	public Name(String name, String content) {
		setContent(content);
		setName(name);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element nameElement = doc.createElement(getName());

		Element string = doc.createElement(StringElement.NODE_NAME_STRING);

		string.setTextContent(getContent());
		nameElement.appendChild(string);

		element.appendChild(nameElement);
	}

	public String getContent() {
		return content;
	}

	public String getName() {
		return name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setName(String name) {
		this.name = name;
	}
}
