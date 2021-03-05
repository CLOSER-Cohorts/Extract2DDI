package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class VariableStatisticsTest extends AbstractFragmentInstanceGeneratorTest {

	private Node getVariableStatistics(Document document) {
		return document.getFirstChild().getChildNodes().item(38).getFirstChild();
	}

	@Test
	public void testToDocument_VariableStatistics() {
		Node variableStatistics = getVariableStatistics(fragmentInstanceDocument);

		Assert.assertEquals("r:VariableStatistics", variableStatistics.getNodeName());
		testFragment(variableStatistics);
		Assert.assertEquals("TotalResponses", variableStatistics.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("0", variableStatistics.getChildNodes().item(4).getTextContent());

		// Variable Reference
		Node variableReference = variableStatistics.getChildNodes().item(5);
		Assert.assertEquals("r:VariableReference", variableReference.getNodeName());
		testFragmentReference(variableReference);
	}
}
