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

	private Node getDataRelationship(Document document) {
		return document.getFirstChild().getChildNodes().item(37);
	}

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

	private Node getPhysicalInstance(Document document) {
		return document.getFirstChild().getChildNodes().item(36).getFirstChild();
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

	private void testFragment(Node fragment) {
		Assert.assertEquals("r:URN", fragment.getFirstChild().getNodeName());
		Assert.assertNotEquals("", fragment.getFirstChild().getTextContent());
		Assert.assertEquals("r:Agency", fragment.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", fragment.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", fragment.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", fragment.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", fragment.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", fragment.getChildNodes().item(3).getTextContent());
	}

	private void testFragmentReference(Node fragment) {
		Assert.assertEquals("r:Agency", fragment.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", fragment.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", fragment.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", fragment.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", fragment.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", fragment.getChildNodes().item(2).getTextContent());
	}

	@Test
	public void testToDocument() {
		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
		System.out.println(xml);

		Node variable = getVariable(fragmentInstanceDocument);

		Assert.assertEquals(38, fragmentInstanceDocument.getFirstChild().getChildNodes().getLength());
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
	public void testToDocument_Category() {
		Node category = getCategory(fragmentInstanceDocument);
		Assert.assertEquals("Category", category.getNodeName());
		testFragment(category);
		Assert.assertEquals("r:Label", category.getChildNodes().item(4).getNodeName());
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

	@Test
	public void testToDocument_CategoryScheme() {
		Node categoryScheme = getCategoryScheme(fragmentInstanceDocument);
		Assert.assertEquals("CategoryScheme", categoryScheme.getNodeName());
		testFragment(categoryScheme);
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
	public void testToDocument_DataRelationship() {
		Node dataRelationship = getDataRelationship(fragmentInstanceDocument);
		Assert.assertEquals("r:DataRelationship", dataRelationship.getNodeName());
		testFragment(dataRelationship);
		Assert.assertEquals("r:DataRelationshipName", dataRelationship.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:String", dataRelationship.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("test-file-data-types.sav", dataRelationship.getChildNodes().item(4).getFirstChild().getTextContent());

		// Logical Record
		Node logicalRecord = dataRelationship.getChildNodes().item(5);
		Assert.assertEquals("r:LogicalRecord", logicalRecord.getNodeName());
		testFragment(logicalRecord);
		Assert.assertEquals("r:LogicalRecordName", logicalRecord.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:String", logicalRecord.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("test-file-data-types.sav", logicalRecord.getChildNodes().item(4).getFirstChild().getTextContent());

		// Variable Used Reference
		Node variablesInRecord = logicalRecord.getChildNodes().item(5);
		Assert.assertEquals("VariablesInRecord", variablesInRecord.getNodeName());

		Node variableUsed = variablesInRecord.getFirstChild();
		Assert.assertEquals("r:VariableUsedReference", variableUsed.getNodeName());
		testFragmentReference(variableUsed);
		Assert.assertEquals("r:TypeOfObject", variableUsed.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("Variable", variableUsed.getChildNodes().item(3).getTextContent());
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
		testFragment(resourcePackage);
		Assert.assertEquals("r:Citation", resourcePackage.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:Title", resourcePackage.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("r:String", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getAttributes().getNamedItem("xml:lang").getNodeName());
		Assert.assertEquals("en-GB", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getAttributes().getNamedItem("xml:lang").getTextContent());
		Assert.assertEquals("test-file-data-types.sav", resourcePackage.getChildNodes().item(4).getFirstChild().getFirstChild().getTextContent());

		// Physical Instance Reference
		Node physicalInstanceReference = resourcePackage.getChildNodes().item(5);
		Assert.assertEquals("r:PhysicalInstanceReference", physicalInstanceReference.getNodeName());
		testFragmentReference(physicalInstanceReference);

		// Physical Instance Reference
		Node categorySchemeReference = resourcePackage.getChildNodes().item(6);
		Assert.assertEquals("r:CategorySchemeReference", categorySchemeReference.getNodeName());
		testFragmentReference(categorySchemeReference);

		// Physical Instance Reference
		Node variableSchemeReference = resourcePackage.getChildNodes().item(9);
		Assert.assertEquals("r:VariableSchemeReference", variableSchemeReference.getNodeName());
		testFragmentReference(variableSchemeReference);
	}

	@Test
	public void testToDocument_PhysicalInstance() {
		Node physicalInstance = getPhysicalInstance(fragmentInstanceDocument);
		Assert.assertEquals("PhysicalInstance", physicalInstance.getNodeName());
		testFragment(physicalInstance);

		// Citation
		Assert.assertEquals("r:Citation", physicalInstance.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:Title", physicalInstance.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("r:String", physicalInstance.getChildNodes().item(4).getFirstChild().getFirstChild().getNodeName());
		Assert.assertEquals("xml:lang", physicalInstance.getChildNodes().item(4).getFirstChild().getFirstChild().getAttributes().getNamedItem("xml:lang").getNodeName());
		Assert.assertEquals("en-GB", physicalInstance.getChildNodes().item(4).getFirstChild().getFirstChild().getAttributes().getNamedItem("xml:lang").getTextContent());
		Assert.assertEquals("test-file-data-types.sav", physicalInstance.getChildNodes().item(4).getFirstChild().getFirstChild().getTextContent());

		// Data Relationship Reference
		Node dataRelationshipReference = physicalInstance.getChildNodes().item(5);
		Assert.assertEquals("r:DataRelationshipReference", dataRelationshipReference.getNodeName());
		testFragmentReference(dataRelationshipReference);
		Assert.assertEquals("r:TypeOfObject", dataRelationshipReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("DataRelationship", dataRelationshipReference.getChildNodes().item(3).getTextContent());

		// Data File Identification
		Node dataFileIdentification = physicalInstance.getChildNodes().item(6);
		Assert.assertEquals("DataFileIdentification", dataFileIdentification.getNodeName());
		Assert.assertEquals("DataFileURI", dataFileIdentification.getFirstChild().getNodeName());
		Assert.assertEquals("test-file-data-types.sav", dataFileIdentification.getFirstChild().getTextContent());

		// Gross File Structure
		Node grossFileStructure = physicalInstance.getChildNodes().item(7);
		Assert.assertEquals("GrossFileStructure", grossFileStructure.getNodeName());
		testFragment(grossFileStructure);
		Assert.assertEquals("CaseQuantity", grossFileStructure.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("3", grossFileStructure.getChildNodes().item(4).getTextContent());
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
		testFragmentReference(topLevelReference);
		Assert.assertEquals("r:TypeOfObject", topLevelReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("PackageReference", topLevelReference.getChildNodes().item(3).getTextContent());
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
