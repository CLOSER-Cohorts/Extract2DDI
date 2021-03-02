package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractReferenceFragment extends FragmentReference {

	public static final String NODE_NAME_OBJECT_TYPE = "r:TypeOfObject";

	private String objectType;
	protected String nodeNameReference;

	public AbstractReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element variable = doc.createElementNS(namespace, getNodeNameReference());
		element.appendChild(variable);

		super.appendToElement(variable, doc, namespace);

		Element objectType = doc.createElementNS(namespace, NODE_NAME_OBJECT_TYPE);
		objectType.setTextContent(getObjectType());
		variable.appendChild(objectType);
	}

	protected String getNodeNameReference() {
		return nodeNameReference;
	}

	public String getObjectType() {
		return objectType;
	}

	protected void setNodeNameReference(String nodeNameReference) {
		this.nodeNameReference = nodeNameReference;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
}