package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Label implements Appendable {

	public static final String NODE_NAME_LABEL = "ddi:Variable";
	public static final String NODE_NAME_CONTENT = "ddi:Content";

	private String content;

	public Label(String content) {
		setContent(content);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element label = doc.createElement(NODE_NAME_LABEL);

		Element content = doc.createElement(NODE_NAME_CONTENT);
		content.setTextContent(getContent());

		label.appendChild(content);

		element.appendChild(label);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
