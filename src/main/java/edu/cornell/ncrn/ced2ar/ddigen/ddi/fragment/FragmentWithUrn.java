package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import java.time.LocalDateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class FragmentWithUrn extends Fragment {

	public static final String ATTRIBUTE_NAME_IS_UNIVERSALLY_UNIQUE = "isUniversallyUnique";
	public static final String ATTRIBUTE_NAME_NAMESPACE = "xmlns";
	public static final String ATTRIBUTE_NAME_VERSION_DATE = "versionDate";

	public static final String NAMESPACE_GROUP = "ddi:group:3_3";
	public static final String NAMESPACE_LOGICAL_PRODUCT = "ddi:logicalproduct:3_3";
	public static final String NAMESPACE_PHYSICAL_INSTANCE = "ddi:physicalinstance:3_3";

	public static final String ATTRIBUTE_VALUE_TRUE = "true";

	public static final String NODE_NAME_URN = "r:URN";

	private String urn;

	public FragmentWithUrn(String id, String agency, int version) {
		super(id, agency, version);
		setUrn(FragmentUtil.generateUrn(id, agency, version));
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element urn = doc.createElement(NODE_NAME_URN);
		urn.setTextContent(getUrn());
		element.appendChild(urn);

		super.appendToElement(element, doc);
	}

	public String getUrn() {
		return urn;
	}


	protected void setNamespace(Element element, String namespace) {
		element.setAttributeNS(NAMESPACE, ATTRIBUTE_NAME_NAMESPACE, namespace);
	}

	protected void setVersionDateAttribute(Element element) {
		element.setAttribute(ATTRIBUTE_NAME_VERSION_DATE, LocalDateTime.now().toString());
	}

	protected void setUniversallyUniqueAttribute(Element element) {
		element.setAttribute(ATTRIBUTE_NAME_IS_UNIVERSALLY_UNIQUE, ATTRIBUTE_VALUE_TRUE);
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}
}