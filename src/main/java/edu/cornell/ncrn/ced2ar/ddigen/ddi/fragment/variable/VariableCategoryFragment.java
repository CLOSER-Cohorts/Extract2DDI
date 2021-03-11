package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableCategoryFragment implements Appendable {

	public static final String NODE_NAME_VARIABLE_CATEGORY = "VariableCategory";
	public static final String NODE_NAME_CATEGORY_VALUE = "CategoryValue";
	public static final String NODE_NAME_VALUE = "r:Value";
	public static final String NODE_NAME_CATEGORY_STATISTIC = "CategoryStatistic";
	public static final String NODE_NAME_CATEGORY_STATISTIC_TYPE = "TypeOfCategoryStatistic";
	public static final String NODE_NAME_STATISTIC = "Statistic";

	private String categoryValue;
	private String frequency;

	public VariableCategoryFragment(String categoryValue, String frequency) {
		setCategoryValue(categoryValue);
		setFrequency(frequency);
	}

	public String getCategoryValue() {
		return categoryValue;
	}

	public String getFrequency() {
		return frequency;
	}

	public void setCategoryValue(String categoryValue) {
		this.categoryValue = categoryValue;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variableCategory = doc.createElement(NODE_NAME_VARIABLE_CATEGORY);

		Element categoryValue = doc.createElement(NODE_NAME_CATEGORY_VALUE);
		Element value = doc.createElement(NODE_NAME_VALUE);
		value.setTextContent(getCategoryValue());
		categoryValue.appendChild(value);

		variableCategory.appendChild(categoryValue);

		Element categoryStatisticType = doc.createElement(NODE_NAME_CATEGORY_STATISTIC_TYPE);
		categoryStatisticType.setTextContent("Frequency");

		Element statistic = doc.createElement(NODE_NAME_STATISTIC);
		statistic.setTextContent(getFrequency());

		Element categoryStatistic = doc.createElement(NODE_NAME_CATEGORY_STATISTIC);
		categoryStatistic.appendChild(categoryStatisticType);
		categoryStatistic.appendChild(statistic);
		variableCategory.appendChild(categoryStatistic);

		element.appendChild(variableCategory);
	}
}