package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SummaryStatistic implements Appendable {

	public static final String NODE_NAME_SUMMARY_STATISTIC = "SummaryStatistic";
	public static final String NODE_NAME_TYPE = "TypeOfSummaryStatistic";
	public static final String NODE_NAME_STATISTIC = "Statistic";
	public static final String NODE_VALUE_INVALID_CASES = "InvalidCases";
	public static final String NODE_VALUE_MAXIMUM = "Maximum";
	public static final String NODE_VALUE_MEAN = "Mean";
	public static final String NODE_VALUE_MINIMUM = "Minimum";
	public static final String NODE_VALUE_STANDARD_DEVIATION = "StandardDeviation";
	public static final String NODE_VALUE_VALID_CASES = "ValidCases";

	private StatisticType type;
	private String statistic;

	public SummaryStatistic(String statistic, StatisticType type) {
		setStatistic(statistic);
		setType(type);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element summaryStatistic = doc.createElement(NODE_NAME_SUMMARY_STATISTIC);

		Element type = doc.createElement(NODE_NAME_TYPE);

		switch (getType()) {
			case INVALID_CASES:
				type.setTextContent(NODE_VALUE_INVALID_CASES);
				break;
			case MAXIMUM:
				type.setTextContent(NODE_VALUE_MAXIMUM);
				break;
			case MEAN:
				type.setTextContent(NODE_VALUE_MEAN);
				break;
			case MINIMUM:
				type.setTextContent(NODE_VALUE_MINIMUM);
				break;
			case STANDARD_DEVIATION:
				type.setTextContent(NODE_VALUE_STANDARD_DEVIATION);
				break;
			case VALID_CASES:
				type.setTextContent(NODE_VALUE_VALID_CASES);
				break;
		}

		summaryStatistic.appendChild(type);

		Element statistic = doc.createElement(NODE_NAME_STATISTIC);
		statistic.setTextContent(getStatistic());
		summaryStatistic.appendChild(statistic);

		element.appendChild(summaryStatistic);
	}

	public String getStatistic() {
		return statistic;
	}

	public StatisticType getType() {
		return type;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public void setType(StatisticType type) {
		this.type = type;
	}
}