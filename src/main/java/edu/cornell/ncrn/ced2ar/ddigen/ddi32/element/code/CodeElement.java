package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategoryReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeElement extends ElementWithUrn {

	public static final String NODE_NAME_CODE = "ddi:Code";

	private CategoryReference categoryReference;

	public CodeElement(String agency) {
		super(agency);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element code = doc.createElement(NODE_NAME_CODE);

		super.appendToElement(code, doc);

		// Category Reference
		getCategoryReference().appendToElement(code, doc);

		element.appendChild(code);
	}

	public CategoryReference getCategoryReference() {
		return categoryReference;
	}

	public void setCategoryReference(String categoryId) {
		this.categoryReference = new CategoryReference(categoryId, getAgency());
	}
}
