package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractReferenceFragment extends Fragment {

	public static final String NODE_NAME_OBJECT_TYPE = "r:TypeOfObject";

	public AbstractReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variable = doc.createElement(getNodeNameReference());
		super.appendToElement(variable, doc);

		Element objectType = doc.createElement(NODE_NAME_OBJECT_TYPE);
		objectType.setTextContent(getObjectType());
		variable.appendChild(objectType);

		element.appendChild(variable);
	}

	protected abstract String getNodeNameReference();

	public abstract String getObjectType();
}