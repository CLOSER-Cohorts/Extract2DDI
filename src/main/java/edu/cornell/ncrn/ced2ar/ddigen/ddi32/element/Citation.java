package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class Citation implements Appendable {

	public static final String NODE_NAME_CITATION = "r:Citation";
	private Title title;
	private AlternateTitle alternateTitle;

	public Citation(String title, String alternateTitle, String ddiLanguage) {
		setTitle(new Title(title, ddiLanguage));
		setAlternateTitle(new AlternateTitle(alternateTitle, ddiLanguage));
	}

	public AlternateTitle getAlternateTitle() {
		return alternateTitle;
	}

	public Title getTitle() {
		return title;
	}

	public void setAlternateTitle(AlternateTitle alternateTitle) {
		this.alternateTitle = alternateTitle;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element citation = doc.createElement(NODE_NAME_CITATION);
		if (getTitle() != null) {
			getTitle().appendToElement(citation, doc);
		}
		if (getAlternateTitle() != null) {
			getAlternateTitle().appendToElement(citation, doc);
		}
		element.appendChild(citation);
	}
}