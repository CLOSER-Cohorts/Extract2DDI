package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class SummaryStatistic implements Appendable {

	public static final String NODE_NAME_SUMMARY_STATISTIC = "SummaryStatistic";
	public static final String NODE_NAME_TYPE = "TypeOfSummaryStatistic";
	public static final String NODE_NAME_STATISTIC = "Statistic";

	private String type;
	private String statistic;

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element summaryStatistic = doc.createElementNS(namespace, NODE_NAME_SUMMARY_STATISTIC);

		Element type = doc.createElementNS(namespace, NODE_NAME_TYPE);
		type.setTextContent(getType());
		summaryStatistic.appendChild(type);

		Element statistic = doc.createElementNS(namespace, NODE_NAME_STATISTIC);
		type.setTextContent(getStatistic());
		summaryStatistic.appendChild(statistic);

		element.appendChild(summaryStatistic);
	}

	public String getStatistic() {
		return statistic;
	}

	public String getType() {
		return type;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public void setType(String type) {
		this.type = type;
	}
}