package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class UserAttributePairFragment implements Appendable {

	public static final String NODE_NAME_USER_ATTRIBUTE_PAIR = "r:UserAttributePair";
	public static final String NODE_NAME_USER_ATTRIBUTE_KEY = "r:AttributeKey";
	public static final String NODE_NAME_USER_ATTRIBUTE_VALUE = "r:AttributeValue";

	private Map<String, String> attributeMap = new HashMap<>();

	public void addAttribute(String key, String value) {
		attributeMap.put(key, value);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		for (Map.Entry<String, String> entry : getAttributeMap().entrySet()) {
			Element userAttributePair = doc.createElement(NODE_NAME_USER_ATTRIBUTE_PAIR);

			Element userAttributeKey = doc.createElement(NODE_NAME_USER_ATTRIBUTE_KEY);
			userAttributeKey.setTextContent(entry.getKey());
			userAttributePair.appendChild(userAttributeKey);

			Element userAttributeValue = doc.createElement(NODE_NAME_USER_ATTRIBUTE_VALUE);
			userAttributeValue.setTextContent(entry.getValue());
			userAttributePair.appendChild(userAttributeValue);

			element.appendChild(userAttributePair);
		}
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	public void setAttributeMap(Map<String, String> attributeMap) {
		this.attributeMap = attributeMap;
	}
}