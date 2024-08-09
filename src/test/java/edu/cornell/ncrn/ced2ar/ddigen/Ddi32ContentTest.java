package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.StringReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

@RunWith(Parameterized.class)
public class Ddi32ContentTest {
	private static final Logger logger = Logger.getLogger(Ddi32ContentTest.class);
	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = false;
	private static final long RECORD_LIMIT = 100;
	private final String dataFileName;
	public Ddi32ContentTest(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	@Parameterized.Parameters
	public static Collection parameters() {
		return Arrays.asList("test-file-data-types.sav", "test-file-data-types.dta");
	}

	@Test
	public void testXmlMatchesProfile() throws Exception {

		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, dataFileName);

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"max,min,valid,invalid,freq,stdev", "", "", "", "");

		// Act
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);
		// Create XPath
		XPathFactory xPathFactory = XPathFactory.newInstance();
		XPath xpath = xPathFactory.newXPath();

		// Set Namespace Context
		xpath.setNamespaceContext(new Ddi32NamespaceContext());

		// XPath expression
		File xpathFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddi32-profile.xml");

		List<String> xpathExpressions = loadXPathExpressions(xpathFile);

		boolean isTestSucessfull = true;
		int expressionNotFoundCount = 0;
		for (String xpathExpression : xpathExpressions) {
			xpathExpression = xpathExpression.replace("DDIInstance", "ns:DDIInstance");
			XPathExpression expression = xpath.compile(xpathExpression);

			StringReader stringReader = new StringReader(ddi.getXml());
			InputSource inputSource = new InputSource(stringReader);
			boolean result = (boolean) expression.evaluate(inputSource, XPathConstants.BOOLEAN);

			if (!result) {
				logger.info(xpathExpression + " not found in " + dataFileName);
				isTestSucessfull = false;
				expressionNotFoundCount++;
			}
		}

		Assert.assertTrue(expressionNotFoundCount + " expressions not found in " + dataFileName, isTestSucessfull);
	}

	private List<String> loadXPathExpressions(File xpathFile) throws Exception {
		List<String> xpathExpressions = new ArrayList<>();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(Files.newInputStream(xpathFile.toPath()));

		XPath xpath = XPathFactory.newInstance().newXPath();

		String expression = "//*[local-name()='Used']";
		NodeList nodeList = (NodeList) xpath.compile(expression).evaluate(doc, XPathConstants.NODESET);

		for (int i = 0; i < nodeList.getLength(); i++) {
			Element expressionElement = (Element) nodeList.item(i);
			String attribute = expressionElement.getAttribute("xpath");

			String isRequiredString = expressionElement.getAttribute("isRequired");
			boolean isRequired = Boolean.parseBoolean(isRequiredString);

			if (isRequired) {
				xpathExpressions.add(attribute);
			}
		}

		return xpathExpressions;
	}
}