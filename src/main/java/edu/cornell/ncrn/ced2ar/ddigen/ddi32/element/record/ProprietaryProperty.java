package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ProprietaryProperty implements Appendable {

	public static final String NODE_NAME_PROPRIETARY_PROPERTY = "r:ProprietaryProperty";
	public static final String NODE_NAME_ATTRIBUTE_KEY = "r:AttributeKey";
	public static final String NODE_NAME_ATTRIBUTE_VALUE = "r:AttributeValue";

	private String key;
	private String value;

	public ProprietaryProperty(String key, String value) {
		setKey(key);
		setValue(value);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element proprietaryProperty = doc.createElement(NODE_NAME_PROPRIETARY_PROPERTY);

		Element attributeKey = doc.createElement(NODE_NAME_ATTRIBUTE_KEY);
		attributeKey.setTextContent(getKey());

		Element attributeValue = doc.createElement(NODE_NAME_ATTRIBUTE_VALUE);
		attributeValue.setTextContent(getValue());

		proprietaryProperty.appendChild(attributeKey);
		proprietaryProperty.appendChild(attributeValue);

		element.appendChild(proprietaryProperty);
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
