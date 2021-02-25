package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFileException;
import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FragmentInstanceGeneratorTest {

	private static Document fragmentInstanceDocument;

	private Node getCategory(Document document) {
		return document.getFirstChild().getChildNodes().item(9).getFirstChild();
	}

	private Node getCategoryScheme(Document document) {
		return document.getFirstChild().getChildNodes().item(1).getFirstChild();
	}

	private Node getCategoryReference(Document document) {
		return getCategoryScheme(document).getChildNodes().item(4);
	}

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
		/*VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
		System.out.println(xml);*/

		Node variable = getVariable(fragmentInstanceDocument);

		Assert.assertEquals(36, fragmentInstanceDocument.getFirstChild().getChildNodes().getLength());
		Assert.assertEquals("r:URN", variable.getFirstChild().getNodeName());
		Assert.assertNotEquals("", variable.getFirstChild().getTextContent());
		Assert.assertEquals("r:Agency", variable.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", variable.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", variable.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("r:Version", variable.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", variable.getChildNodes().item(3).getTextContent());
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
	public void testToDocument_Category() {
		Node category = getCategory(fragmentInstanceDocument);
		Assert.assertEquals("Category", category.getNodeName());
		Assert.assertEquals("r:URN", category.getFirstChild().getNodeName());
		Assert.assertNotEquals("", category.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", category.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", category.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", category.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", category.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", category.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", category.getChildNodes().item(3).getTextContent());
		Assert.assertEquals("r:Label", category.getChildNodes().item(4).getNodeName());
	}

	@Test
	public void testToDocument_CategoryScheme() {
		Node categoryScheme = getCategoryScheme(fragmentInstanceDocument);
		Assert.assertEquals("CategoryScheme", categoryScheme.getNodeName());
		Assert.assertEquals("r:URN", categoryScheme.getFirstChild().getNodeName());
		Assert.assertNotEquals("", categoryScheme.getFirstChild().getTextContent());
		Assert.assertEquals("r:Agency", categoryScheme.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", categoryScheme.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", categoryScheme.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", categoryScheme.getChildNodes().item(2).getTextContent());
	}

	@Test
	public void testToDocument_CategoryReference() {
		Node categoryReference = getCategoryReference(fragmentInstanceDocument);

		Assert.assertEquals("r:CategoryReference", categoryReference.getNodeName());
		Assert.assertEquals("r:Agency", categoryReference.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", categoryReference.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", categoryReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", categoryReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", categoryReference.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", categoryReference.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:TypeOfObject", categoryReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("Category", categoryReference.getChildNodes().item(3).getTextContent());
	}

	@Test
	public void testToDocument_CodeRepresentation() {
		Node representation = getRepresentation(fragmentInstanceDocument, 33);

		Assert.assertEquals("r:CodeRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("r:CodeListReference", representation.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", representation.getFirstChild().getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", representation.getFirstChild().getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", representation.getFirstChild().getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", representation.getFirstChild().getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", representation.getFirstChild().getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", representation.getFirstChild().getChildNodes().item(2).getTextContent());
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
	public void testToDocument_TextRepresentation() {
		Node representation = getRepresentation(fragmentInstanceDocument, 22);

		Assert.assertEquals("r:TextRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("classificationLevel", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("Nominal", representation.getAttributes().item(1).getTextContent());
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
	public void testToDocument_VariableScheme() {
		Node variableScheme = getVariableScheme(fragmentInstanceDocument);

		Assert.assertEquals("VariableScheme", variableScheme.getNodeName());
		Assert.assertEquals("r:URN", variableScheme.getFirstChild().getNodeName());
		Assert.assertNotEquals("", variableScheme.getFirstChild().getTextContent());
		Assert.assertEquals("r:Agency", variableScheme.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", variableScheme.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", variableScheme.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", variableScheme.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", variableScheme.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", variableScheme.getChildNodes().item(3).getTextContent());
	}

	@Test
	public void testToDocument_VariableReference() {
		Node variableReference = getVariableReference(fragmentInstanceDocument);

		Assert.assertEquals("r:VariableReference", variableReference.getNodeName());
		Assert.assertEquals("r:Agency", variableReference.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", variableReference.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", variableReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", variableReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", variableReference.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", variableReference.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:TypeOfObject", variableReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("Variable", variableReference.getChildNodes().item(3).getTextContent());
	}

	@BeforeClass
	public static void setUp()
		throws URISyntaxException, IOException, SPSSFileException, ParserConfigurationException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = FileUtil.getFileFromResource(FragmentInstanceGeneratorTest.class, "test-file-data-types.sav");
		Document document = spssGen.getLogicalProduct(file);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		LogicalProductGenerator logicalProductGenerator = new LogicalProductGenerator(logicalProduct);

		List<Fragment> fragmentList = logicalProductGenerator.toFragmentList();
		FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
		fragmentInstanceDocument = transformer.toDocument();
	}
}
