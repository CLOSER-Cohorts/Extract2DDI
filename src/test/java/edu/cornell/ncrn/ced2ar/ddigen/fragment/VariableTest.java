package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class VariableTest extends AbstractFragmentInstanceGeneratorTest {

	private Node getRepresentation(Document document, int index) {
		return getVariable(document, index).getLastChild().getLastChild();
	}

	private Node getVariable(Document document) {
		return getVariable(document, 22);
	}

	private Node getVariable(Document document, int index) {
		return document.getFirstChild().getChildNodes().item(index).getFirstChild();
	}

	private Node getVariableReference(Document document) {
		return getVariableScheme(document).getChildNodes().item(17);
	}

	private Node getVariableScheme(Document document) {
		return document.getFirstChild().getChildNodes().item(21).getFirstChild();
	}

	@Test
	public void testToDocument() {
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
		System.out.println(xml);

		Node variable = getVariable(fragmentInstanceDocument);

		Assert.assertEquals(52, fragmentInstanceDocument.getFirstChild().getChildNodes().getLength());
		testFragment(variable);
		Assert.assertEquals("VariableName", variable.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:String", variable.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", variable.getChildNodes().item(4).getFirstChild().getAttributes().item(0).getNodeName());
		Assert.assertEquals("en-GB", variable.getChildNodes().item(4).getFirstChild().getAttributes().item(0).getTextContent());
		Assert.assertEquals("TestString", variable.getChildNodes().item(4).getFirstChild().getTextContent());
		Assert.assertEquals("r:Label", variable.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("r:Content", variable.getChildNodes().item(5).getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", variable.getChildNodes().item(5).getFirstChild().getAttributes().item(0).getNodeName());
		Assert.assertEquals("en-GB", variable.getChildNodes().item(5).getFirstChild().getAttributes().item(0).getTextContent());
		Assert.assertEquals("VariableRepresentation", variable.getChildNodes().item(6).getNodeName());
		Assert.assertEquals("VariableRole", variable.getChildNodes().item(6).getFirstChild().getNodeName());
		Assert.assertEquals("input", variable.getChildNodes().item(6).getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_CodeRepresentation() {
		Node representation = getRepresentation(fragmentInstanceDocument,33);

		Assert.assertEquals("r:CodeRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("r:CodeListReference", representation.getFirstChild().getNodeName());
		testFragmentReference(representation.getFirstChild());
		Assert.assertEquals("r:TypeOfObject", representation.getFirstChild().getChildNodes().item(3).getNodeName());
		Assert.assertEquals("CodeList", representation.getFirstChild().getChildNodes().item(3).getTextContent());
	}

	@Test
	public void testToDocument_DateTimeRepresentation() {
		Node representation = getRepresentation(fragmentInstanceDocument, 28);

		Assert.assertEquals("r:DateTimeRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("r:DateTypeCode", representation.getFirstChild().getNodeName());
		Assert.assertEquals("Date", representation.getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_NumericRepresentation() {
		Node representation = getRepresentation(fragmentInstanceDocument, 23);

		Assert.assertEquals("r:NumericRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("decimalPositions", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("4", representation.getAttributes().item(1).getTextContent());
		Assert.assertEquals("r:NumericTypeCode", representation.getFirstChild().getNodeName());
		Assert.assertEquals("BigInteger", representation.getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_TextRepresentation() {
		Node representation = getRepresentation(fragmentInstanceDocument, 22);

		Assert.assertEquals("r:TextRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("classificationLevel", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("Nominal", representation.getAttributes().item(1).getTextContent());
	}

	@Test
	public void testToDocument_VariableScheme() {
		Node variableScheme = getVariableScheme(fragmentInstanceDocument);

		Assert.assertEquals("VariableScheme", variableScheme.getNodeName());
		testFragment(variableScheme);
	}

	@Test
	public void testToDocument_VariableReference() {
		Node variableReference = getVariableReference(fragmentInstanceDocument);

		Assert.assertEquals("r:VariableReference", variableReference.getNodeName());
		testFragmentReference(variableReference);
		Assert.assertEquals("r:TypeOfObject", variableReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("Variable", variableReference.getChildNodes().item(3).getTextContent());
	}
}
