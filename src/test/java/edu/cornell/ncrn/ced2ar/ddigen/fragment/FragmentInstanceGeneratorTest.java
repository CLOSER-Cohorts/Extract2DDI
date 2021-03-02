package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFileException;
import edu.cornell.ncrn.ced2ar.ddigen.ConfigUtil;
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
import java.util.Properties;
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
		return document.getFirstChild().getChildNodes().item(2).getFirstChild();
	}

	private Node getCategoryReference(Document document) {
		return getCategoryScheme(document).getChildNodes().item(4);
	}

	private Node getCode(Document document) {
		return getCodeList(document).getChildNodes().item(5);
	}

	private Node getCodeList(Document document) {
		return document.getFirstChild().getChildNodes().item(18).getFirstChild();
	}

	private Node getResourcePackage(Document document) {
		return document.getFirstChild().getChildNodes().item(1).getFirstChild();
	}

	private Node getRepresentation(Document document, int index) {
		return getVariable(document, index).getLastChild().getLastChild();
	}

	private Node getTopLevelReference(Document document) {
		return document.getFirstChild().getFirstChild();
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
	public void testToDocument_Code() {
		Node code = getCode(fragmentInstanceDocument);
		Assert.assertEquals("Code", code.getNodeName());
		Assert.assertEquals("r:URN", code.getFirstChild().getNodeName());
		Assert.assertNotEquals("", code.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", code.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", code.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", code.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", code.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", code.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", code.getChildNodes().item(3).getTextContent());
		Assert.assertEquals("r:CategoryReference", code.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:Value", code.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("1", code.getChildNodes().item(5).getTextContent());
	}

	@Test
	public void testToDocument_CodeList() {
		Node category = getCodeList(fragmentInstanceDocument);
		Assert.assertEquals("CodeList", category.getNodeName());
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
		Node representation = getRepresentation(fragmentInstanceDocument,33);

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
	public void testToDocument_PackageReference() {
		Node resourcePackage = getResourcePackage(fragmentInstanceDocument);

		Assert.assertEquals("ResourcePackage", resourcePackage.getNodeName());
		Assert.assertEquals("r:URN", resourcePackage.getFirstChild().getNodeName());
		Assert.assertNotEquals("", resourcePackage.getFirstChild().getTextContent());
		Assert.assertEquals("r:Agency", resourcePackage.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", resourcePackage.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", resourcePackage.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", resourcePackage.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", resourcePackage.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", resourcePackage.getChildNodes().item(3).getTextContent());
		Assert.assertEquals("r:Citation", resourcePackage.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:Title", resourcePackage.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("r:String", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getAttributes().getNamedItem("xml:lang").getNodeName());
		Assert.assertEquals("en-GB", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getAttributes().getNamedItem("xml:lang").getTextContent());
		Assert.assertEquals("test-file-data-types.sav", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getTextContent());

		// Physical Instance Reference
		Node physicalInstanceReference = resourcePackage.getChildNodes().item(5);
		Assert.assertEquals("r:PhysicalInstanceReference", physicalInstanceReference.getNodeName());
		Assert.assertEquals("r:Agency", physicalInstanceReference.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", physicalInstanceReference.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", physicalInstanceReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", physicalInstanceReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", physicalInstanceReference.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", physicalInstanceReference.getChildNodes().item(2).getTextContent());

		// Physical Instance Reference
		Node categorySchemeReference = resourcePackage.getChildNodes().item(6);
		Assert.assertEquals("r:CategorySchemeReference", categorySchemeReference.getNodeName());
		Assert.assertEquals("r:Agency", categorySchemeReference.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", categorySchemeReference.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", categorySchemeReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", categorySchemeReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", categorySchemeReference.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", categorySchemeReference.getChildNodes().item(2).getTextContent());

		// Physical Instance Reference
		Node variableSchemeReference = resourcePackage.getChildNodes().item(9);
		Assert.assertEquals("r:VariableSchemeReference", variableSchemeReference.getNodeName());
		Assert.assertEquals("r:Agency", variableSchemeReference.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", variableSchemeReference.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", variableSchemeReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", variableSchemeReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", variableSchemeReference.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", variableSchemeReference.getChildNodes().item(2).getTextContent());
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
	public void testToDocument_TopLevelReference() {
		Node topLevelReference = getTopLevelReference(fragmentInstanceDocument);

		Assert.assertEquals("ddi:TopLevelReference", topLevelReference.getNodeName());
		Assert.assertEquals("r:Agency", topLevelReference.getFirstChild().getNodeName());
		Assert.assertEquals("r:ID", topLevelReference.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", topLevelReference.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", topLevelReference.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", topLevelReference.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:TypeOfObject", topLevelReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("PackageReference", topLevelReference.getChildNodes().item(3).getTextContent());
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

		Properties properties = FileUtil.getPropertiesFromResource(FragmentInstanceGeneratorTest.class);
		ConfigUtil configUtil = new ConfigUtil(properties);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		LogicalProductGenerator logicalProductGenerator = new LogicalProductGenerator(
			logicalProduct,
			configUtil.getAgency(),
			configUtil.getDdiLanguage(),
			"test-file-data-types.sav"
		);

		List<Fragment> fragmentList = logicalProductGenerator.toFragmentList();
		FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
		fragmentInstanceDocument = transformer.toDocument();
	}
}
