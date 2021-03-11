package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FragmentInstanceGeneratorTest extends AbstractFragmentInstanceGeneratorTest {

	private Node getDataRelationship(Document document) {
		return document.getFirstChild().getChildNodes().item(37).getFirstChild();
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

	private Node getTopLevelReference(Document document) {
		return document.getFirstChild().getFirstChild();
	}

	@Test
	public void testToDocument_DataRelationship() {
		Node dataRelationship = getDataRelationship(fragmentInstanceDocument);
		Assert.assertEquals("DataRelationship", dataRelationship.getNodeName());
		testFragment(dataRelationship);
		Assert.assertEquals("DataRelationshipName", dataRelationship.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:String", dataRelationship.getChildNodes().item(4).getFirstChild().getNodeName());
		Assert.assertEquals("test-file-data-types.sav", dataRelationship.getChildNodes().item(4).getFirstChild().getTextContent());

		// Logical Record
		Node logicalRecord = dataRelationship.getChildNodes().item(5);
		Assert.assertEquals("LogicalRecord", logicalRecord.getNodeName());
		testFragment(logicalRecord);
		Assert.assertEquals("LogicalRecordName", logicalRecord.getChildNodes().item(4).getNodeName());
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
	public void testToDocument_TopLevelReference() {
		Node topLevelReference = getTopLevelReference(fragmentInstanceDocument);

		Assert.assertEquals("ddi:TopLevelReference", topLevelReference.getNodeName());
		testFragmentReference(topLevelReference);
		Assert.assertEquals("r:TypeOfObject", topLevelReference.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("ResourcePackage", topLevelReference.getChildNodes().item(3).getTextContent());
	}
}
