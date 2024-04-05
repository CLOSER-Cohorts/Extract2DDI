package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.apache.commons.lang.StringUtils;
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
public class GenerateDdi33Test {

	private static final Logger logger = Logger.getLogger(GenerateDdi33Test.class);
	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = false;
	private static final long RECORD_LIMIT = 100;
	private final String dataFileName;

	public GenerateDdi33Test(String dataFileName) {
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

		GenerateDDI33 generateDDI33 = new GenerateDDI33("uk.closer", "en-GB", new HashMap<>(),"max,min,valid,invalid,freq,stdev");

		// Act
		DDI ddi = generateDDI33.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		// Assert
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputStream stream = new ByteArrayInputStream(ddi.getXml().getBytes(StandardCharsets.UTF_8));
		Document document = builder.parse(stream);

		// Expect different IDs for VariableStatistics and Variables
		NodeList variableStatisticsNodeList = document.getElementsByTagName("VariableStatistics");
		String variableStatisticsId = variableStatisticsNodeList.item(0).getChildNodes().item(5).getTextContent();

		int matches = StringUtils.countMatches(ddi.getXml(), variableStatisticsId);
		// 2 - one in URN and one in ID
		Assert.assertEquals(2, matches);
	}
}