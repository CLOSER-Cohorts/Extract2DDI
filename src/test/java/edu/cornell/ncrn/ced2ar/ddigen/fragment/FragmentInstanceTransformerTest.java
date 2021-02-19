package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFileException;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentInstanceTransformer;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class FragmentInstanceTransformerTest {

	private File getFileFromResource(String fileName) throws URISyntaxException {
		ClassLoader classLoader = getClass().getClassLoader();
		URL resource = classLoader.getResource(fileName);
		if (resource == null) {
			throw new IllegalArgumentException("file not found! " + fileName);
		} else {
			return new File(resource.toURI());
		}
	}

	private Node getRepresentationNode(Document document, int index) {
		return document
			.getFirstChild()
			.getChildNodes()
			.item(index)
			.getFirstChild()
			.getLastChild()
			.getLastChild();
	}

	private Node getVariableNode(Document document) {
		return document.getFirstChild().getFirstChild().getFirstChild();
	}

	@Test
	public void testToDocument() throws URISyntaxException, IOException, SPSSFileException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = getFileFromResource("test-file-data-types.sav");
		Document document = spssGen.getLogicalProduct(file);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		FragmentInstanceTransformer transformer = new FragmentInstanceTransformer(logicalProduct);
		Document fragmentInstanceDocument = transformer.toDocument();

/*		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentListDocument);
		System.out.println(xml);*/

		Node variable = getVariableNode(fragmentInstanceDocument);

		Assert.assertEquals(14, fragmentInstanceDocument.getFirstChild().getChildNodes().getLength());
		Assert.assertEquals("r:URN", variable.getFirstChild().getNodeName());
		Assert.assertEquals("r:Agency", variable.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("r:ID", variable.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("r:Version", variable.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("VariableName", variable.getChildNodes().item(4).getNodeName());
		Assert.assertEquals("r:Label", variable.getChildNodes().item(5).getNodeName());
		Assert.assertEquals("VariableRepresentation", variable.getChildNodes().item(6).getNodeName());
		Assert.assertEquals("VariableRole", variable.getChildNodes().item(6).getFirstChild().getNodeName());
		Assert.assertEquals("input", variable.getChildNodes().item(6).getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_DateTimeRepresentation() throws URISyntaxException, IOException, SPSSFileException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = getFileFromResource("test-file-data-types.sav");
		Document document = spssGen.getLogicalProduct(file);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		FragmentInstanceTransformer transformer = new FragmentInstanceTransformer(logicalProduct);
		Document fragmentInstanceDocument = transformer.toDocument();

		Node representation = getRepresentationNode(fragmentInstanceDocument, 6);
		Assert.assertEquals("r:DateTimeRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("r:DateTypeCode", representation.getFirstChild().getNodeName());
		Assert.assertEquals("Date", representation.getFirstChild().getTextContent());
	}

	@Test
	public void testToDocument_TextRepresentation() throws URISyntaxException, IOException, SPSSFileException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = getFileFromResource("test-file-data-types.sav");
		Document document = spssGen.getLogicalProduct(file);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		FragmentInstanceTransformer transformer = new FragmentInstanceTransformer(logicalProduct);
		Document FragmentInstanceDocument = transformer.toDocument();

		Node representation = getRepresentationNode(FragmentInstanceDocument, 0);
		Assert.assertEquals("r:TextRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("classificationLevel", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("Nominal", representation.getAttributes().item(1).getTextContent());
	}

	@Test
	public void testToDocument_NumericRepresentation() throws URISyntaxException, IOException, SPSSFileException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = getFileFromResource("test-file-data-types.sav");
		Document document = spssGen.getLogicalProduct(file);

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(document);
		FragmentInstanceTransformer transformer = new FragmentInstanceTransformer(logicalProduct);
		Document fragmentInstanceDocument = transformer.toDocument();

		Node representation = getRepresentationNode(fragmentInstanceDocument, 1);
		Assert.assertEquals("r:NumericRepresentation", representation.getNodeName());
		Assert.assertEquals("blankIsMissingValue", representation.getAttributes().item(0).getNodeName());
		Assert.assertEquals("false", representation.getAttributes().item(0).getTextContent());
		Assert.assertEquals("decimalPositions", representation.getAttributes().item(1).getNodeName());
		Assert.assertEquals("4", representation.getAttributes().item(1).getTextContent());
		Assert.assertEquals("r:NumericTypeCode", representation.getFirstChild().getNodeName());
		Assert.assertEquals("BigInteger", representation.getFirstChild().getTextContent());
	}
}
