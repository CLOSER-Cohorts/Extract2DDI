package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CategoryElement extends ElementWithUrn {

	public static final String NODE_NAME_CATEGORY = "ddi:Category";

	private Label label;

	public CategoryElement(String id, String agency, String label, String ddiLanguage) {
		super(id, agency);
		setLabel(new Label(label, ddiLanguage));
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element category = doc.createElement(NODE_NAME_CATEGORY);

		super.appendToElement(category, doc);

		// Label
		getLabel().appendToElement(category, doc);

		element.appendChild(category);
	}

	public Label getLabel() {
		return label;
	}

	public void setLabel(Label label) {
		this.label = label;
	}
}
