package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableStatistics;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class StatisticalSummary implements Appendable {

	public static final String NODE_NAME_STATISTICAL_SUMMARY = "pi:StatisticalSummary";

	private final List<VariableStatistics> variableStatisticsList = new ArrayList<>();

	public void addVariableStatistics(VariableStatistics variableStatistics) {
		synchronized (variableStatisticsList) {
			variableStatisticsList.add(variableStatistics);
		}
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element statisticalSummary = doc.createElement(NODE_NAME_STATISTICAL_SUMMARY);

		for (VariableStatistics summaryStatistic : variableStatisticsList) {
			summaryStatistic.appendToElement(statisticalSummary, doc);
		}

		parent.appendChild(statisticalSummary);
	}
}
