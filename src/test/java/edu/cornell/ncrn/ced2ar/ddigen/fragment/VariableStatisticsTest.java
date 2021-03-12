package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class VariableStatisticsTest extends AbstractFragmentInstanceGeneratorTest {

	private Node getVariableStatistics(Document document, int index) {
		return document.getFirstChild().getChildNodes().item(index).getFirstChild();
	}

	@Test
	public void testToDocument_ExcludeVariableStatistics() {
		Node variableStatistics = getVariableStatistics(fragmentInstanceDocument, 38);

		Assert.assertEquals("VariableStatistics", variableStatistics.getNodeName());
		testFragment(variableStatistics);

		// User Attribute Pair
		Node userAttributePair = variableStatistics.getChildNodes().item(4);
		Assert.assertEquals("r:UserAttributePair", userAttributePair.getNodeName());
		Assert.assertEquals("r:AttributeKey", userAttributePair.getFirstChild().getNodeName());
		Assert.assertEquals("extension:redaction-information", userAttributePair.getFirstChild().getTextContent());
		Assert.assertEquals("r:AttributeValue", userAttributePair.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("invalid,valid,max,min,mean", userAttributePair.getChildNodes().item(1).getTextContent());

		// Variable Reference
		Node variableReference = variableStatistics.getChildNodes().item(5);
		Assert.assertEquals("r:VariableReference", variableReference.getNodeName());
		testFragmentReference(variableReference);

		Assert.assertEquals("TotalResponses", variableStatistics.getChildNodes().item(6).getNodeName());
		Assert.assertEquals("3", variableStatistics.getChildNodes().item(6).getTextContent());

		// Summary Statistic
		Node standardDeviation = variableStatistics.getChildNodes().item(7);
		Assert.assertEquals("SummaryStatistic", standardDeviation.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", standardDeviation.getFirstChild().getNodeName());
		Assert.assertEquals("StandardDeviation", standardDeviation.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", standardDeviation.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("1.4142135623730951", standardDeviation.getChildNodes().item(1).getTextContent());
	}

	@Test
	public void testToDocument_VariableStatistics() {
		Node variableStatistics = getVariableStatistics(fragmentInstanceDocument, 39);

		Assert.assertEquals("VariableStatistics", variableStatistics.getNodeName());
		testFragment(variableStatistics);

		// Variable Reference
		Node variableReference = variableStatistics.getChildNodes().item(4);
		Assert.assertEquals("r:VariableReference", variableReference.getNodeName());
		testFragmentReference(variableReference);

		Assert.assertEquals("TotalResponses", variableStatistics.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("3", variableStatistics.getChildNodes().item(5).getTextContent());

		// Summary Statistic
		Node validCases = variableStatistics.getChildNodes().item(6);
		Assert.assertEquals("SummaryStatistic", validCases.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", validCases.getFirstChild().getNodeName());
		Assert.assertEquals("ValidCases", validCases.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", validCases.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("3", validCases.getChildNodes().item(1).getTextContent());

		Node invalidCases = variableStatistics.getChildNodes().item(7);
		Assert.assertEquals("SummaryStatistic", invalidCases.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", invalidCases.getFirstChild().getNodeName());
		Assert.assertEquals("InvalidCases", invalidCases.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", invalidCases.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("3", invalidCases.getChildNodes().item(1).getTextContent());

		Node maximum = variableStatistics.getChildNodes().item(8);
		Assert.assertEquals("SummaryStatistic", maximum.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", maximum.getFirstChild().getNodeName());
		Assert.assertEquals("Minimum", maximum.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", maximum.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("1.0", maximum.getChildNodes().item(1).getTextContent());

		Node minimum = variableStatistics.getChildNodes().item(9);
		Assert.assertEquals("SummaryStatistic", minimum.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", minimum.getFirstChild().getNodeName());
		Assert.assertEquals("Maximum", minimum.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", minimum.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("3.0", minimum.getChildNodes().item(1).getTextContent());

		Node mean = variableStatistics.getChildNodes().item(10);
		Assert.assertEquals("SummaryStatistic", mean.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", mean.getFirstChild().getNodeName());
		Assert.assertEquals("Mean", mean.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", mean.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("2.0", mean.getChildNodes().item(1).getTextContent());

		Node standardDeviation = variableStatistics.getChildNodes().item(11);
		Assert.assertEquals("SummaryStatistic", standardDeviation.getNodeName());
		Assert.assertEquals("TypeOfSummaryStatistic", standardDeviation.getFirstChild().getNodeName());
		Assert.assertEquals("StandardDeviation", standardDeviation.getFirstChild().getTextContent());
		Assert.assertEquals("Statistic", standardDeviation.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("1.4142135623730951", standardDeviation.getChildNodes().item(1).getTextContent());
	}
}
