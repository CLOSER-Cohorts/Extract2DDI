package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class StataVariableTest extends AbstractStataFragmentInstanceGeneratorTest {

	private Node getVariable(Document document) {
		return getVariable(document, 3);
	}

	private Node getVariable(Document document, int index) {
		return document.getFirstChild().getChildNodes().item(index).getFirstChild();
	}

	@Test
	public void testToDocument() {

		Node variable = getVariable(fragmentInstanceDocument);

		Assert.assertEquals(9, fragmentInstanceDocument.getFirstChild().getChildNodes().getLength());
		testFragment(variable);
		Assert.assertEquals("VariableName", variable.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:String", variable.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", variable.getChildNodes().item(4).getFirstChild().getAttributes().item(0).getNodeName());
		Assert.assertEquals("en-GB", variable.getChildNodes().item(4).getFirstChild().getAttributes().item(0).getTextContent());
		Assert.assertEquals("TestInteger", variable.getChildNodes().item(4).getFirstChild().getTextContent());
		Assert.assertEquals("r:Label", variable.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("r:Content", variable.getChildNodes().item(5).getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", variable.getChildNodes().item(5).getFirstChild().getAttributes().item(0).getNodeName());
		Assert.assertEquals("en-GB", variable.getChildNodes().item(5).getFirstChild().getAttributes().item(0).getTextContent());
	}
}
