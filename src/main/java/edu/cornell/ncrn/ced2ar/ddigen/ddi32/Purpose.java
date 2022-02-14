package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Purpose implements Appendable {

	public static final String NODE_NAME_PURPOSE = "r:Purpose";
	public static final String NODE_NAME_CONTENT = "r:Content";

	@Override
	public void appendToElement(Element element, Document doc) {
		// Purpose
		Element purpose = doc.createElement(NODE_NAME_PURPOSE);

		// Content
		Element content = doc.createElement(NODE_NAME_CONTENT);
		purpose.appendChild(content);

		element.appendChild(purpose);
	}
}
