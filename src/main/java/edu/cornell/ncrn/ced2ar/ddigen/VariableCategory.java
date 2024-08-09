package edu.cornell.ncrn.ced2ar.ddigen;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableCategory implements Appendable {

	public static final String NODE_NAME_VARIABLE_CATEGORY = "VariableCategory";
	public static final String NODE_NAME_CATEGORY_VALUE = "CategoryValue";
	public static final String NODE_NAME_VALUE = "r:Value";
	public static final String NODE_NAME_CATEGORY_STATISTIC = "CategoryStatistic";
	public static final String NODE_NAME_CATEGORY_STATISTIC_TYPE = "TypeOfCategoryStatistic";
	public static final String NODE_NAME_STATISTIC = "Statistic";

	private final String categoryValue;
	private final String frequency;
	private String prefix;

	public VariableCategory(String categoryValue, String frequency) {
		this.categoryValue = categoryValue;
		this.frequency = frequency;
	}

	public VariableCategory(String categoryValue, String frequency, String prefix) {
		this(categoryValue, frequency);
		this.prefix = prefix;
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		String prefixLocal = prefix != null && !prefix.trim().isEmpty() ? prefix + ":" : "";

		Element variableCategory = doc.createElement(prefixLocal + NODE_NAME_VARIABLE_CATEGORY);

		Element categoryValueElement = doc.createElement(prefixLocal + NODE_NAME_CATEGORY_VALUE);
		Element value = doc.createElement(NODE_NAME_VALUE);
		value.setTextContent(categoryValue);
		categoryValueElement.appendChild(value);

		variableCategory.appendChild(categoryValueElement);

		Element categoryStatisticType = doc.createElement(prefixLocal + NODE_NAME_CATEGORY_STATISTIC_TYPE);
		categoryStatisticType.setTextContent("Frequency");

		Element statistic = doc.createElement(prefixLocal + NODE_NAME_STATISTIC);
		statistic.setAttribute("isWeighted", "false");
		statistic.setTextContent(frequency);

		Element categoryStatistic = doc.createElement(prefixLocal + NODE_NAME_CATEGORY_STATISTIC);
		categoryStatistic.appendChild(categoryStatisticType);
		categoryStatistic.appendChild(statistic);
		variableCategory.appendChild(categoryStatistic);

		parent.appendChild(variableCategory);
	}
}