package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CodeTest extends AbstractFragmentInstanceGeneratorTest {

	private Node getCode(Document document) {
		return getCodeList(document).getChildNodes().item(5);
	}

	private Node getCodeList(Document document) {
		return document.getFirstChild().getChildNodes().item(18).getFirstChild();
	}

	@Test
	public void testToDocument_Code() {
		Node code = getCode(fragmentInstanceDocument);
		Assert.assertEquals("Code", code.getNodeName());
		testFragment(code);
		Assert.assertEquals("r:CategoryReference", code.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:Value", code.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("1", code.getChildNodes().item(5).getTextContent());
	}

	@Test
	public void testToDocument_CodeList() {
		Node codeList = getCodeList(fragmentInstanceDocument);
		Assert.assertEquals("CodeList", codeList.getNodeName());
		testFragment(codeList);
		Assert.assertEquals("r:Label", codeList.getChildNodes().item(4).getNodeName());
	}
}
