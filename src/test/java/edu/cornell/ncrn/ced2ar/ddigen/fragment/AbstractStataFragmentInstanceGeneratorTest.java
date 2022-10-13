package edu.cornell.ncrn.ced2ar.ddigen.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.ConfigUtil;
import edu.cornell.ncrn.ced2ar.ddigen.FileUtil;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.FragmentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

public class AbstractStataFragmentInstanceGeneratorTest {

	protected static Document fragmentInstanceDocument;
	protected static List<Ced2arVariableStat> variableStatList = new ArrayList<>();

	static {
		Ced2arVariableStat variableStat1 = new Ced2arVariableStat();
		variableStat1.setName("TestInteger");
		variableStat1.setLabel("Integer");
		variableStat1.setValidCount(3L);
		variableStat1.setInvalidCount(3L);
		variableStat1.getStats().addValue(1L);
		variableStat1.getStats().addValue(3L);
		variableStatList.add(variableStat1);

		Ced2arVariableStat variableStat2 = new Ced2arVariableStat();
		variableStat2.setName("TestString");
		variableStat2.setLabel("String (32 characters)");
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
	public static void setUp() throws Exception {
		Properties properties = FileUtil.getPropertiesFromResource(AbstractStataFragmentInstanceGeneratorTest.class);
		ConfigUtil configUtil = new ConfigUtil(properties);

		Map<String, String> excludeVariableToStatMap = new HashMap<>();
		excludeVariableToStatMap.put("TestString", "invalid,valid,max,min,mean");

		List<Variable> variableList = new ArrayList<>();
		for (Ced2arVariableStat stat : variableStatList) {
			Variable variable = new Variable(UUID.randomUUID().toString());
			variable.setName(stat.getName());
			variableList.add(variable);
		}

		VariableScheme defaultVariableScheme = new VariableScheme(UUID.randomUUID().toString());
		defaultVariableScheme.setVariableList(variableList);

		FragmentGenerator logicalProductGenerator = new FragmentGenerator(
			Collections.emptyList(),
			Collections.emptyList(),
			Arrays.asList(defaultVariableScheme),
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
