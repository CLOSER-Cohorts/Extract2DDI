package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategoryReferenceFragment;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CodeFragment extends FragmentWithUrn {
	public static final String NODE_NAME_CODE = "Code";
	public static final String NODE_NAME_VALUE = "r:Value";

	private String value;
	private CategoryReferenceFragment categoryReference;

	public CodeFragment(String id, String agency, int version, String value) {
		super(id, agency, version);
		setValue(value);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element code = doc.createElementNS(namespace, NODE_NAME_CODE);
		super.appendToElement(code, doc, namespace);

		if (getCategoryReference() != null) {
			getCategoryReference().appendToElement(code, doc, namespace);
		}

		Element value = doc.createElementNS(namespace, NODE_NAME_VALUE);
		value.setTextContent(getValue());
		code.appendChild(value);

		element.appendChild(code);
	}

	public CategoryReferenceFragment getCategoryReference() {
		return categoryReference;
	}

	public String getValue() {
		return value;
	}

	public void setCategoryReference(CategoryReferenceFragment categoryReference) {
		this.categoryReference = categoryReference;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
