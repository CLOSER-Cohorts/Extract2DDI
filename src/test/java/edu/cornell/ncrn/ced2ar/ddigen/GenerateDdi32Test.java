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
import java.util.List;

@RunWith(Parameterized.class)
public class GenerateDdi32Test {
	private static final List<String> expectedSpssVariableLabels = Arrays.asList(
			"String (32 characters)", "Integer", "Numeric 4 decimal points", "Fake Currency",
			"Custom £ Currency", "Dollar", "Date dd/mmm/yy", "Date Jan,Feb,Mar", "Date Q1 2000",
			"Time mm:ss", "DateTime dd.mm.yyyy.mm.ss", "Coded Value basic", "Coded Value Basic plus missing",
			"Coded Value Basic plus range missing"
	);

	private static final List<String> expectedStataVariableLabels = Arrays.asList(
			"String (32 characters)", "Integer", "Numeric 4 decimal points", "Fake Currency",
			"Custom Currency", "Dollar", "Date dd/mmm/yy", "\"Date Jan,Feb,Mar\"", "Date Q1 2000",
			"Time mm:ss", "DateTime dd.mm.yyyy.mm.ss", "Coded Value basic", "Coded Value Basic plus missing",
			"Coded Value Basic plus range missing"
	);

	private static final List<String> expectedVariableNames = Arrays.asList(
			"TestString", "TestInteger", "TestDouble4dp", "TestDouble2dp", "TestCustomCurrency",
			"TestRealCurrency", "TestDate1", "TestDate2", "TestDate3", "TestTime", "TestDateTime",
			"TestCodedValue1", "TestCodeValue2", "TestCodeValue3"
	);

	private static final Logger logger = Logger.getLogger(GenerateDdi32Test.class);
	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = false;
	private static final long RECORD_LIMIT = 100;

	private final String dataFileName;
	private final List<String> expectedVariableLabels;

	public GenerateDdi32Test(String dataFileName, List<String> expectedVariableLabels) {
		this.dataFileName = dataFileName;
		this.expectedVariableLabels = expectedVariableLabels;
	}

	@Parameterized.Parameters
	public static Collection<Object[]> parameters() {
		return Arrays.asList(new Object[][] {
				{ "test-file-data-types.sav", expectedSpssVariableLabels },
				{ "test-file-data-types.dta", expectedStataVariableLabels }
		});
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

		// Expecting 14 ProprietaryInfo nodes
		NodeList proprietaryInfoNodeList = document.getElementsByTagName("r:ProprietaryInfo");
		Assert.assertEquals("Not enough ProprietaryInfo nodes found", 14, proprietaryInfoNodeList.getLength());

		// Expecting at least 3 citations
		NodeList citationNodeList = document.getElementsByTagName("r:Citation");
		Assert.assertEquals("Not enough Citations nodes found", 3, citationNodeList.getLength());

		// Ensure no representation with RecommendedDataType = type
		NodeList recommendedDataTypeNodeList = document.getElementsByTagName("r:RecommendedDataType");
		Assert.assertEquals(14, recommendedDataTypeNodeList.getLength());
		for (int i = 0; i < recommendedDataTypeNodeList.getLength(); i++) {
			Assert.assertNotEquals("type", recommendedDataTypeNodeList.item(i).getTextContent());
		}

		// Ensure there is 14 statistics in both STATA and SPSS files
		NodeList variableStatisticsNodeList = document.getElementsByTagName("pi:VariableStatistics");
		Assert.assertEquals(14, variableStatisticsNodeList.getLength());

		for (int i = 0; i < variableStatisticsNodeList.getLength(); i++) {
			// Ensure that all valid cases are 0
			Assert.assertEquals(String.format("Variable %d doesn't have valid cases = 0", i), "0", variableStatisticsNodeList.item(i).getChildNodes().item(5).getChildNodes().item(3).getTextContent());

			// Ensure that all invalid cases are 0
			Assert.assertEquals(String.format("Variable %d doesn't have invalid cases = 0", i),"0", variableStatisticsNodeList.item(i).getChildNodes().item(7).getChildNodes().item(3).getTextContent());
		}

		NodeList variableNodeList = document.getElementsByTagName("ddi:Variable");

		// Ensure that the variable names are as expected
		for (int i = 0; i < variableNodeList.getLength(); i++) {
			Assert.assertEquals(String.format("Variable name '%s' is not found in variable %d", expectedVariableNames.get(i), i), expectedVariableNames.get(i), variableNodeList.item(i).getChildNodes().item(3).getTextContent().trim());
		}
		// Ensure that the variable labels are as expected
		for (int i = 0; i < variableNodeList.getLength(); i++) {
			Assert.assertEquals(String.format("Label '%s' is not found in variable %d", expectedVariableLabels.get(i), i), expectedVariableLabels.get(i), variableNodeList.item(i).getChildNodes().item(5).getTextContent().trim());
		}

		// Expect all 5 statistics to be present: max, min, valid, invalid, freq, stdev
		//Assert.assertTrue("StandardDeviation statistic is not found", ddi.getXml().contains("StandardDeviation"));
	}
}