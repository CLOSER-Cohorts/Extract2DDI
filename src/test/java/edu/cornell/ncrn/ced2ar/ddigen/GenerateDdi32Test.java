package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@RunWith(Parameterized.class)
public class GenerateDdi32Test {

	private static final Logger logger = Logger.getLogger(GenerateDdi32Test.class);
	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = false;
	private static final long RECORD_LIMIT = 100;

	private final String dataFileName;

	public GenerateDdi32Test(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	@Parameterized.Parameters
	public static Collection parameters() {
		return Arrays.asList("test-file-data-types.sav", "test-file-data-types.dta");
	}

	@Test
	public void testGenerateDdi() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, dataFileName);

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"max,min,valid,invalid,freq,stdev");

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(ddi.getXml().getBytes(StandardCharsets.UTF_8));
		Document document = builder.parse(stream);

		// Expecting one PhysicalInstance to be present
		NodeList variableRepresentationNodeList = document.getElementsByTagName("ddi:VariableRepresentation");
		Assert.assertEquals(14, variableRepresentationNodeList.getLength());

		// Expecting no attributes in all data items
		NodeList proprietaryInfoNodeList = document.getElementsByTagName("r:ProprietaryInfo");
		Assert.assertEquals(0, proprietaryInfoNodeList.getLength());

		// Expecting at least 3 citations
		NodeList citationNodeList = document.getElementsByTagName("r:Citation");
		Assert.assertEquals(3, citationNodeList.getLength());
	}
}