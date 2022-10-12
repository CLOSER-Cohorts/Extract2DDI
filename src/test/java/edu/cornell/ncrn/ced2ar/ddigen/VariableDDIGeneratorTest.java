package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.DDIInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.FragmentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VariableDDIGeneratorTest {

	private static final String FILE_NAME = "test-file-data-types.sav";

	@Test
	public void testDdiCodebook25SchemaFile() throws Exception {
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, FILE_NAME);
		SPSSFile spssFile = new SPSSFile(file);

		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		VariableCsv variableCsv = spssGen.generateVariablesCsv(spssFile, true, 100);

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		List<CodebookVariable> codebookVariables = variableDDIGenerator.getCodebookVariables(variableCsv);
		Document document = variableDDIGenerator.getCodebookDocument(codebookVariables, FILE_NAME, true);
		String xml = variableDDIGenerator.domToString(document);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemaFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddiCodebook25Schema/codebook.xsd");
		Schema schema = schemaFactory.newSchema(schemaFile);

		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(xml)));
	}

	@Test
	public void testDdiLifecycle32SchemaFile() throws Exception {
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, FILE_NAME);
		SPSSFile spssFile = new SPSSFile(file);

		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		VariableCsv variableCsv = spssGen.generateVariablesCsv(spssFile, true, 100);

		Document logicalProductDocument = spssFile.getDDI3LogicalProduct();
		List<CategoryScheme> categorySchemeList = new ArrayList<>(LogicalProductFactory.createCategorySchemeList(logicalProductDocument));
		List<CodeList> codeListList = new ArrayList<>(LogicalProductFactory.createCodeListList(logicalProductDocument));
		List<VariableScheme> variableSchemeList = new ArrayList<>(LogicalProductFactory.createVariableSchemeList(logicalProductDocument));

		ElementGenerator elementGenerator = new ElementGenerator(
				categorySchemeList,
				codeListList,
				variableSchemeList,
				variableCsv.getVariableStatList(),
				"max,min,valid,invalid,freq,stdev",
				new HashMap<>(),
				"uk.closer",
				"en-GB",
				FILE_NAME
		);
		elementGenerator.setVariableToFrequencyMap(variableCsv.getVariableToFrequencyMap());
		DDIInstance ddiInstance = elementGenerator.getInstance();

		DDI32DocumentGenerator generator = new DDI32DocumentGenerator(ddiInstance);
		Document fragmentInstanceDocument = generator.toDocument();

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument, "UTF-8");

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemaFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddiLifecycle32Schema/instance.xsd");
		Schema schema = schemaFactory.newSchema(schemaFile);

		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(xml)));
	}

	@Test
	public void testDdiLifecycle33SchemaFile() throws Exception {
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, FILE_NAME);
		SPSSFile spssFile = new SPSSFile(file);

		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		VariableCsv variableCsv = spssGen.generateVariablesCsv(spssFile, true, 100);

		Document logicalProductDocument = spssFile.getDDI3LogicalProduct();
		List<CategoryScheme> categorySchemeList = new ArrayList<>(LogicalProductFactory.createCategorySchemeList(logicalProductDocument));
		List<CodeList> codeListList = new ArrayList<>(LogicalProductFactory.createCodeListList(logicalProductDocument));
		List<VariableScheme> variableSchemeList = new ArrayList<>(LogicalProductFactory.createVariableSchemeList(logicalProductDocument));

		FragmentGenerator elementGenerator = new FragmentGenerator(
				categorySchemeList,
				codeListList,
				variableSchemeList,
				variableCsv.getVariableStatList(),
				"max,min,valid,invalid,freq,stdev",
				new HashMap<>(),
				"uk.closer",
				"en-GB",
				FILE_NAME
		);
		elementGenerator.setVariableToFrequencyMap(variableCsv.getVariableToFrequencyMap());
		List<Fragment> fragmentList = elementGenerator.getFragmentList();

		FragmentInstanceGenerator generator = new FragmentInstanceGenerator(fragmentList);
		Document fragmentInstanceDocument = generator.toDocument();

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument, "UTF-8");

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		File schemaFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddiLifecycle33Schema/instance.xsd");
		Schema schema = schemaFactory.newSchema(schemaFile);

		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(xml)));
	}
}