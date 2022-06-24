package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class CategoryTest extends AbstractFragmentInstanceGeneratorTest {

	private Node getCategory(Document document) {
		return document.getFirstChild().getChildNodes().item(9).getFirstChild();
	}

	private Node getCategoryReference(Document document) {
		return getCategoryScheme(document).getChildNodes().item(4);
	}

	private Node getCategoryScheme(Document document) {
		return document.getFirstChild().getChildNodes().item(2).getFirstChild();
	}

	@Test
	public void testToDocument_Category() {
		Assert.fail();
		Node category = getCategory(fragmentInstanceDocument);
		Assert.assertEquals("Category", category.getNodeName());
		testFragment(category);
		Assert.assertEquals("r:Label", category.getChildNodes().item(4).getNodeName());
	}

	@Test
	public void testToDocument_CategoryReference() {
		Node categoryReference = getCategoryReference(fragmentInstanceDocument);
		Assert.assertEquals("r:CategoryReference", categoryReference.getNodeName());
		testFragmentReference(categoryReference);
		Assert.assertEquals("r:TypeOfObject", categoryReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("Category", categoryReference.getChildNodes().item(3).getTextContent());
	}

	@Test
	public void testToDocument_CategoryScheme() {
		Node categoryScheme = getCategoryScheme(fragmentInstanceDocument);
		Assert.assertEquals("CategoryScheme", categoryScheme.getNodeName());
		testFragment(categoryScheme);
	}
}
