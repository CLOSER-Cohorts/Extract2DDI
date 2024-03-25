package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.VariableCategory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.SummaryStatistic;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable.StatisticType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class VariableStatistics extends ElementWithUrn {

	public static final String NODE_NAME_VARIABLE_STATISTICS = "pi:VariableStatistics";
	public static final String NODE_NAME_UNFILTERED_CATEGORY_STATISTICS = "pi:UnfilteredCategoryStatistics";

	private final VariableReference variableReference;
	private final List<SummaryStatistic> summaryStatisticList = new ArrayList<>();
	private final List<VariableCategory> variableCategoryList = new ArrayList<>();

	public VariableStatistics(String agency, String variableId) {
		super(agency);
		variableReference = new VariableReference(variableId, agency);
	}

	public void addSummaryStatistic(String statistic, StatisticType statisticType, String prefix) {
		SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, statisticType, prefix);
		this.summaryStatisticList.add(summaryStatistic);
	}

	public void addVariableCategory(String code, long frequency) {
		VariableCategory variableCategory = new VariableCategory(code, Long.toString(frequency), null);
		variableCategoryList.add(variableCategory);
	}
	public void addVariableCategory(String code, long frequency, String prefix) {
		VariableCategory variableCategory = new VariableCategory(code, Long.toString(frequency), prefix);
		variableCategoryList.add(variableCategory);
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element variableStatistics = doc.createElement(NODE_NAME_VARIABLE_STATISTICS);
		super.appendToElement(variableStatistics, doc);

		variableReference.appendToElement(variableStatistics, doc);

		for (SummaryStatistic summaryStatistic : summaryStatisticList) {
			summaryStatistic.appendToElement(variableStatistics, doc);
		}

		if (variableCategoryList.size() > 0) {
			Element unfilteredCategoryStatistics = doc.createElement(NODE_NAME_UNFILTERED_CATEGORY_STATISTICS);
			for (VariableCategory variableCategory : variableCategoryList) {
				variableCategory.appendToElement(unfilteredCategoryStatistics, doc);
			}
			variableStatistics.appendChild(unfilteredCategoryStatistics);
		}

		parent.appendChild(variableStatistics);
	}
}