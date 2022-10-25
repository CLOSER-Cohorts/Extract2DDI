package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

@RunWith(Parameterized.class)
public class VariableDDIGeneratorTest {

	private static final boolean IS_SUMMARY_STATISTICS_ENABLED = false;
	private static final long RECORD_LIMIT = 100;

	private final String dataFileName;

	public VariableDDIGeneratorTest(String dataFileName) {
		this.dataFileName = dataFileName;
	}

	@Parameterized.Parameters
	public static Collection parameters() {
		return Arrays.asList("test-file-data-types.sav", "test-file-data-types.dta");
	}

	@Test
	public void testStataDdiCodebook25SchemaFile() throws Exception {
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, dataFileName);

		GenerateDDI generateDDI = new GenerateDDI();
		DDI ddi = generateDDI.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemaFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddiCodebook25Schema/codebook.xsd");
		Schema schema = schemaFactory.newSchema(schemaFile);

		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(ddi.getXml())));
	}

	@Test
	public void testStataDdiLifecycle32SchemaFile() throws Exception {
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, dataFileName);

		GenerateDDI32 generateDDI32 = new GenerateDDI32("uk.closer", "en-GB", new HashMap<>(),"max,min,valid,invalid,freq,stdev");
		DDI ddi = generateDDI32.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		File schemaFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddiLifecycle32Schema/instance.xsd");
		Schema schema = schemaFactory.newSchema(schemaFile);

		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(ddi.getXml())));
	}

	@Test
	public void testStataDdiLifecycle33SchemaFile() throws Exception {
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, dataFileName);

		GenerateDDI33 generateDDI33 = new GenerateDDI33("uk.closer", "en-GB", new HashMap<>(),"max,min,valid,invalid,freq,stdev");
		DDI ddi = generateDDI33.generateDDI(file.getPath(), IS_SUMMARY_STATISTICS_ENABLED, RECORD_LIMIT);

		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

		File schemaFile = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, "ddiLifecycle33Schema/instance.xsd");
		Schema schema = schemaFactory.newSchema(schemaFile);

		Validator validator = schema.newValidator();
		validator.validate(new StreamSource(new StringReader(ddi.getXml())));
	}

}