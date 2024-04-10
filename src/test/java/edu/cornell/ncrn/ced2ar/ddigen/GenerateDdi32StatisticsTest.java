package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class GenerateDdi32StatisticsTest {

	private static final Logger logger = Logger.getLogger(GenerateDdi32StatisticsTest.class);

	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = true;
	private static final long RECORD_LIMIT = 100;

	@Test
	public void testSpssStatistics() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.sav");

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"max,min,mean,valid,invalid,freq,stdev");

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(ddi.getXml().getBytes(StandardCharsets.UTF_8));
		Document document = builder.parse(stream);

		// Ensure there is 14 statistics in both STATA and SPSS files
		NodeList variableStatisticsNodeList = document.getElementsByTagName("pi:VariableStatistics");

		Assert.assertEquals("Invalid ValidCases statistic value", "0", variableStatisticsNodeList.item(0).getChildNodes().item(5).getChildNodes().item(3).getTextContent());
		Assert.assertEquals("Invalid InvalidCases statistic value","1", variableStatisticsNodeList.item(0).getChildNodes().item(7).getChildNodes().item(3).getTextContent());

		Assert.assertEquals("Invalid Maximum statistic count", 9, StringUtils.countMatches(ddi.getXml(), "Maximum"));
		Assert.assertEquals("Invalid Minimum statistic count", 9, StringUtils.countMatches(ddi.getXml(), "Minimum"));
		Assert.assertEquals("Invalid ValidCases statistic count", 14, StringUtils.countMatches(ddi.getXml(), "ValidCases"));
		Assert.assertEquals("Invalid InvalidCases statistic", 14, StringUtils.countMatches(ddi.getXml(), "InvalidCases"));
		Assert.assertEquals("Invalid StandardDeviation statistic", 9, StringUtils.countMatches(ddi.getXml(), "StandardDeviation"));
		Assert.assertEquals("Invalid Mean statistic count", 9, StringUtils.countMatches(ddi.getXml(), "Mean"));
	}

	@Test
	public void testStataStatistics() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.dta");

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"max,min,mean,valid,invalid,freq,stdev");

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(ddi.getXml().getBytes(StandardCharsets.UTF_8));
		Document document = builder.parse(stream);

		// Ensure there is 14 statistics in both STATA and SPSS files
		NodeList variableStatisticsNodeList = document.getElementsByTagName("pi:VariableStatistics");

		Assert.assertEquals("Invalid ValidCases statistic value", "3", variableStatisticsNodeList.item(0).getChildNodes().item(5).getChildNodes().item(3).getTextContent());
		Assert.assertEquals("Invalid InvalidCases statistic value", "97",  variableStatisticsNodeList.item(0).getChildNodes().item(7).getChildNodes().item(3).getTextContent());

		Assert.assertEquals("Invalid StandardDeviation statistic count", 9, StringUtils.countMatches(ddi.getXml(), "StandardDeviation"));
		Assert.assertEquals("Invalid Maximum statistic count", 9, StringUtils.countMatches(ddi.getXml(), "Maximum"));
		Assert.assertEquals("Invalid Minimum statistic count", 9, StringUtils.countMatches(ddi.getXml(), "Minimum"));
		Assert.assertEquals("Invalid ValidCases statistic count", 14, StringUtils.countMatches(ddi.getXml(), "ValidCases"));
		Assert.assertEquals("Invalid InvalidCases statistic count", 14, StringUtils.countMatches(ddi.getXml(), "InvalidCases"));
		Assert.assertEquals("Invalid Mean statistic count", 9, StringUtils.countMatches(ddi.getXml(), "Mean"));
	}

	@Test
	public void testSpssValidCasesStatistic() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.sav");

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"valid");

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		Assert.assertEquals("Invalid Maximum statistic count", 0, StringUtils.countMatches(ddi.getXml(), "Maximum"));
		Assert.assertEquals("Invalid Minimum statistic count", 0, StringUtils.countMatches(ddi.getXml(), "Minimum"));
		Assert.assertEquals("Invalid ValidCases statistic count", 14, StringUtils.countMatches(ddi.getXml(), "ValidCases"));
		Assert.assertEquals("Invalid InvalidCases statistic count", 0, StringUtils.countMatches(ddi.getXml(), "InvalidCases"));
		Assert.assertEquals("Invalid StandardDeviation statistic count", 0, StringUtils.countMatches(ddi.getXml(), "StandardDeviation"));
		Assert.assertEquals("Invalid Mean statistic count", 0, StringUtils.countMatches(ddi.getXml(), "Mean"));
	}

	@Test
	public void testStataValidCasesStatistic() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.dta");

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"valid");

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		Assert.assertEquals("Invalid StandardDeviation statistic count", 0, StringUtils.countMatches(ddi.getXml(), "StandardDeviation"));
		Assert.assertEquals("Invalid Maximum statistic count", 0, StringUtils.countMatches(ddi.getXml(), "Maximum"));
		Assert.assertEquals("Invalid Minimum statistic count", 0, StringUtils.countMatches(ddi.getXml(), "Minimum"));
		Assert.assertEquals("Invalid ValidCases statistic count", 14, StringUtils.countMatches(ddi.getXml(), "ValidCases"));
		Assert.assertEquals("Invalid InvalidCases statistic count", 0, StringUtils.countMatches(ddi.getXml(), "InvalidCases"));
		Assert.assertEquals("Invalid Mean statistic count", 0, StringUtils.countMatches(ddi.getXml(), "Mean"));
	}

	@Test
	public void testSpssExcludeStatistic() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.sav");

		GenerateDDI32 generateDDI32 = new GenerateDDI32(
				"uk.closer",
				"en-GB",
				new HashMap<String, String>() {{ put("TestInteger", "max,min,mean,valid,invalid,freq,stdev:remove statistics"); }},
				"max,min,mean,valid,invalid,freq,stdev"
		);

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		Assert.assertEquals("Invalid Maximum statistic count", 11, StringUtils.countMatches(ddi.getXml(), "Maximum"));
		Assert.assertEquals("Invalid Minimum statistic count", 11, StringUtils.countMatches(ddi.getXml(), "Minimum"));
		Assert.assertEquals("Invalid ValidCases statistic count", 13, StringUtils.countMatches(ddi.getXml(), "ValidCases"));
		Assert.assertEquals("Invalid InvalidCases statistic count", 13, StringUtils.countMatches(ddi.getXml(), "InvalidCases"));
		Assert.assertEquals("Invalid StandardDeviation statistic count", 11, StringUtils.countMatches(ddi.getXml(), "StandardDeviation"));
		Assert.assertEquals("Invalid Mean statistic count", 11, StringUtils.countMatches(ddi.getXml(), "Mean"));
	}

	@Test
	public void testStataExcludeStatistic() throws Exception {
		// Arrange
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.dta");

		GenerateDDI32 generateDDI32 = new GenerateDDI32(
				"uk.closer",
				"en-GB",
				new HashMap<String, String>() {{ put("TestInteger", "max,min,mean,valid,invalid,freq,stdev:remove statistics"); }},
				"max,min,mean,valid,invalid,freq,stdev"
		);

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		Assert.assertEquals("Invalid Maximum statistic count", 8, StringUtils.countMatches(ddi.getXml(), "Maximum"));
		Assert.assertEquals("Invalid Minimum statistic count", 8, StringUtils.countMatches(ddi.getXml(), "Minimum"));
		Assert.assertEquals("Invalid ValidCases statistic count", 13, StringUtils.countMatches(ddi.getXml(), "ValidCases"));
		Assert.assertEquals("Invalid InvalidCases statistic count", 13, StringUtils.countMatches(ddi.getXml(), "InvalidCases"));
		Assert.assertEquals("Invalid StandardDeviation statistic count", 8, StringUtils.countMatches(ddi.getXml(), "StandardDeviation"));
		Assert.assertEquals("Invalid Mean statistic count is incorrect", 8, StringUtils.countMatches(ddi.getXml(), "Mean"));
	}
}