package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableStatisticsFragment extends FragmentWithUrn {

	public static final String NODE_NAME_VARIABLE_STATISTICS = "r:VariableStatistics";
	public static final String NODE_NAME_TOTAL_RESPONSES = "TotalResponses";
	public static final String NODE_NAME_UNFILTERED_CATEGORY_STATISTICS = "UnfilteredCategoryStatistics";

	private int totalResponses;
	private VariableReferenceFragment variableReference;
	private List<SummaryStatistic> summaryStatisticList = new ArrayList<>();
	private List<VariableCategoryFragment> variableCategoryFragmentList = new ArrayList<>();

	public VariableStatisticsFragment(String id, String agency, int version, int totalResponses) {
		super(id, agency, version);
		setTotalResponses(totalResponses);
	}

	public void addSummaryStatistic(SummaryStatistic statistic) {
		this.summaryStatisticList.add(statistic);
	}

	public void addVariableCategory(VariableCategoryFragment variableCategory) {
		this.variableCategoryFragmentList.add(variableCategory);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		fragment.setAttribute(ATTRIBUTE_NAME_NAMESPACE_R, ATTRIBUTE_VALUE_NAMESPACE_R);

		Element variableStatistics = doc.createElementNS(namespace, NODE_NAME_VARIABLE_STATISTICS);
		super.appendToElement(variableStatistics, doc, namespace);
		fragment.appendChild(variableStatistics);

		Element totalResponses = doc.createElementNS(namespace, NODE_NAME_TOTAL_RESPONSES);
		totalResponses.setTextContent(Integer.toString(getTotalResponses()));
		variableStatistics.appendChild(totalResponses);

		if (getVariableReference() != null) {
			getVariableReference().appendToElement(variableStatistics, doc, namespace);
		}

		for (SummaryStatistic statistic : getSummaryStatisticList()) {
			statistic.appendToElement(variableStatistics, doc, namespace);
		}

		if (getVariableCategoryFragmentList().size() > 0) {
			Element unfilteredCategoryStatistics = doc.createElementNS(namespace, NODE_NAME_UNFILTERED_CATEGORY_STATISTICS);
			for (VariableCategoryFragment variableCategory : getVariableCategoryFragmentList()) {
				variableCategory.appendToElement(unfilteredCategoryStatistics, doc, namespace);
			}
			variableStatistics.appendChild(unfilteredCategoryStatistics);
		}

		element.appendChild(fragment);
	}

	public int getTotalResponses() {
		return totalResponses;
	}

	public List<SummaryStatistic> getSummaryStatisticList() {
		return summaryStatisticList;
	}

	public List<VariableCategoryFragment> getVariableCategoryFragmentList() {
		return variableCategoryFragmentList;
	}

	public VariableReferenceFragment getVariableReference() {
		return variableReference;
	}

	public void setTotalResponses(int totalResponses) {
		this.totalResponses = totalResponses;
	}

	public void setSummaryStatisticList(List<SummaryStatistic> summaryStatisticList) {
		this.summaryStatisticList = summaryStatisticList;
	}

	public void setVariableCategoryFragmentList(List<VariableCategoryFragment> variableCategoryFragmentList) {
		this.variableCategoryFragmentList = variableCategoryFragmentList;
	}

	public void setVariableReference(VariableReferenceFragment variableReference) {
		this.variableReference = variableReference;
	}
}