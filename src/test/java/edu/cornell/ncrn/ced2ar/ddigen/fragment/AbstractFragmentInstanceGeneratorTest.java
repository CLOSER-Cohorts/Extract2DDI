package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFileException;
import edu.cornell.ncrn.ced2ar.ddigen.ConfigUtil;
import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.FragmentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.LogicalProductFactory;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import javax.xml.parsers.ParserConfigurationException;

import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class AbstractFragmentInstanceGeneratorTest {

	protected static Document fragmentInstanceDocument;
	protected static List<Ced2arVariableStat> variableStatList = new ArrayList<>();

	static {
		Ced2arVariableStat variableStat1 = new Ced2arVariableStat();
		variableStat1.setName("TestInteger");
		variableStat1.setValidCount(3L);
		variableStat1.setInvalidCount(3L);
		variableStat1.getStats().addValue(1L);
		variableStat1.getStats().addValue(3L);
		variableStatList.add(variableStat1);

		Ced2arVariableStat variableStat2 = new Ced2arVariableStat();
		variableStat2.setName("TestString");
		variableStat2.setValidCount(3L);
		variableStat1.setInvalidCount(3L);
		variableStat2.getStats().addValue(1L);
		variableStat2.getStats().addValue(3L);
		variableStatList.add(variableStat2);
	}

	protected void testFragment(Node fragment) {
		Assert.assertEquals("r:URN", fragment.getFirstChild().getNodeName());
		Assert.assertNotEquals("", fragment.getFirstChild().getTextContent());
		Assert.assertEquals("r:Agency", fragment.getChildNodes().item(1).getNodeName());
		Assert.assertEquals("uk.closer", fragment.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:ID", fragment.getChildNodes().item(2).getNodeName());
		Assert.assertNotEquals("", fragment.getChildNodes().item(2).getTextContent());
		Assert.assertEquals("r:Version", fragment.getChildNodes().item(3).getNodeName());
		Assert.assertEquals("1", fragment.getChildNodes().item(3).getTextContent());
	}

	protected void testFragmentReference(Node fragment) {
		Assert.assertEquals("r:Agency", fragment.getFirstChild().getNodeName());
		Assert.assertEquals("uk.closer", fragment.getFirstChild().getTextContent());
		Assert.assertEquals("r:ID", fragment.getChildNodes().item(1).getNodeName());
		Assert.assertNotEquals("", fragment.getChildNodes().item(1).getTextContent());
		Assert.assertEquals("r:Version", fragment.getChildNodes().item(2).getNodeName());
		Assert.assertEquals("1", fragment.getChildNodes().item(2).getTextContent());
	}

	@BeforeClass
	public static void setUp() throws URISyntaxException, IOException, SPSSFileException, ParserConfigurationException {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "test-file-data-types.sav");
		Document document = spssGen.getDDI3LogicalProduct(file);

		Properties properties = FileUtil.getPropertiesFromResource(AbstractFragmentInstanceGeneratorTest.class);
		ConfigUtil configUtil = new ConfigUtil(properties);

		Map<String, String> excludeVariableToStatMap = new HashMap<>();
		excludeVariableToStatMap.put("TestString", "invalid,valid,max,min,mean");

		List<CategoryScheme> categorySchemeList = LogicalProductFactory.createCategorySchemeList(document);
		List<CodeList> codeListList = LogicalProductFactory.createCodeListList(document);
		List<VariableScheme> variableSchemeList = LogicalProductFactory.createVariableSchemeList(document);

		FragmentGenerator logicalProductGenerator = new FragmentGenerator(
			categorySchemeList,
			codeListList,
			variableSchemeList,
			variableStatList,
			configUtil.getStats(),
			excludeVariableToStatMap,
			configUtil.getAgency(),
			configUtil.getDdiLanguage(),
			"test-file-data-types.sav"
		);

		List<Fragment> fragmentList = logicalProductGenerator.getFragmentList();
		FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
		fragmentInstanceDocument = transformer.toDocument();
	}
}
