package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CategoryReferenceFragment extends FragmentReference {

	public static final String NODE_NAME_CATEGORY_REFERENCE = "r:CategoryReference";
	public static final String NODE_NAME_OBJECT_TYPE = "r:TypeOfObject";

	private String objectType = "Category";

	public CategoryReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element variable = doc.createElementNS(namespace, NODE_NAME_CATEGORY_REFERENCE);
		element.appendChild(variable);

		super.appendToElement(variable, doc, namespace);

		Element objectType = doc.createElementNS(namespace, NODE_NAME_OBJECT_TYPE);
		objectType.setTextContent(getObjectType());
		variable.appendChild(objectType);
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}