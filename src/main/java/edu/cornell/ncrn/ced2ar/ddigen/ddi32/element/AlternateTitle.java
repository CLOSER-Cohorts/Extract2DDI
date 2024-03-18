package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class AlternateTitle implements Appendable {

	public static final String NODE_NAME_ALTERNATE_TITLE = "r:AlternateTitle";

	private StringElement string;

	public AlternateTitle(String alternateTitle, String ddiLanguage) {
		setString(new StringElement(alternateTitle, ddiLanguage));
	}

	public StringElement getString() {
		return string;
	}

	public void setString(StringElement string) {
		this.string = string;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element title = doc.createElement(NODE_NAME_ALTERNATE_TITLE);
		if (getString() != null) {
			getString().appendToElement(title, doc);
		}
		element.appendChild(title);
	}
}