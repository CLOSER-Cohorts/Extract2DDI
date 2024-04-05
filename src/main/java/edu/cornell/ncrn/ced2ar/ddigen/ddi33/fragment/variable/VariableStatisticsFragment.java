package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.SummaryStatistic;
import edu.cornell.ncrn.ced2ar.ddigen.VariableCategory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.UserAttributePairFragment;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableStatisticsFragment extends FragmentWithUrn {

	public static final String NODE_NAME_VARIABLE_STATISTICS = "VariableStatistics";
	public static final String NODE_NAME_TOTAL_RESPONSES = "TotalResponses";
	public static final String NODE_NAME_UNFILTERED_CATEGORY_STATISTICS = "UnfilteredCategoryStatistics";

	private long totalResponses;
	private VariableReferenceFragment variableReference;
	private List<SummaryStatistic> summaryStatisticList = new ArrayList<>();
	private List<VariableCategory> variableCategoryList = new ArrayList<>();
	private UserAttributePairFragment userAttributePair;

	public VariableStatisticsFragment(String agency, int version) {
		super(UUID.randomUUID().toString(), agency, version);
	}

	public void addSummaryStatistic(SummaryStatistic statistic) {
		this.summaryStatisticList.add(statistic);
	}

	public void addVariableCategory(VariableCategory variableCategory) {
		this.variableCategoryList.add(variableCategory);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element fragment = createFragment(doc);

		Element variableStatistics = doc.createElement(NODE_NAME_VARIABLE_STATISTICS);
		setVersionDateAttribute(variableStatistics);
		setUniversallyUniqueAttribute(variableStatistics);
		setNamespace(variableStatistics, NAMESPACE_PHYSICAL_INSTANCE);

		super.appendToElement(variableStatistics, doc);

		if (getUserAttributePair() != null) {
			getUserAttributePair().appendToElement(variableStatistics, doc);
		}

		if (getVariableReference() != null) {
			getVariableReference().appendToElement(variableStatistics, doc);
		}

		Element totalResponses = doc.createElement(NODE_NAME_TOTAL_RESPONSES);
		totalResponses.setTextContent(Long.toString(getTotalResponses()));
		variableStatistics.appendChild(totalResponses);

		for (SummaryStatistic statistic : getSummaryStatisticList()) {
			statistic.appendToElement(variableStatistics, doc);
		}

		if (getVariableCategoryList().size() > 0) {
			Element unfilteredCategoryStatistics = doc.createElement(NODE_NAME_UNFILTERED_CATEGORY_STATISTICS);
			for (VariableCategory variableCategory : getVariableCategoryList()) {
				variableCategory.appendToElement(unfilteredCategoryStatistics, doc);
			}
			variableStatistics.appendChild(unfilteredCategoryStatistics);
		}

		fragment.appendChild(variableStatistics);
		element.appendChild(fragment);
	}

	public long getTotalResponses() {
		return totalResponses;
	}

	public List<SummaryStatistic> getSummaryStatisticList() {
		return summaryStatisticList;
	}

	public List<VariableCategory> getVariableCategoryList() {
		return variableCategoryList;
	}

	public VariableReferenceFragment getVariableReference() {
		return variableReference;
	}

	public UserAttributePairFragment getUserAttributePair() {
		return userAttributePair;
	}

	public void setTotalResponses(long totalResponses) {
		this.totalResponses = totalResponses;
	}

	public void setSummaryStatisticList(List<SummaryStatistic> summaryStatisticList) {
		this.summaryStatisticList = summaryStatisticList;
	}

	public void setVariableCategoryList(List<VariableCategory> variableCategoryList) {
		this.variableCategoryList = variableCategoryList;
	}

	public void setVariableReference(VariableReferenceFragment variableReference) {
		this.variableReference = variableReference;
	}

	public void setUserAttributePair(UserAttributePairFragment userAttributePair) {
		this.userAttributePair = userAttributePair;
	}
}