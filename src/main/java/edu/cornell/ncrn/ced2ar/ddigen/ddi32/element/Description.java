package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Description implements Appendable {

	public static final String NODE_NAME_DESCRIPTION = "r:Description";
	public static final String NODE_NAME_CONTENT = "r:Content";

	private final String content;

	public Description(String content) {
		this.content = content;
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element label = doc.createElement(NODE_NAME_DESCRIPTION);

		Element contentElement = doc.createElement(NODE_NAME_CONTENT);
		contentElement.setTextContent(content);

		label.appendChild(contentElement);

		parent.appendChild(label);
	}
}
