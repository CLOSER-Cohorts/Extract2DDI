package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.fragment.AbstractFragmentInstanceGeneratorTest;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.StringReader;
import java.net.URL;
import java.util.List;

public class VariableDDIGeneratorTest {

	private static final String SCHEMA_FILE = "https://ddialliance.org/Specification/DDI-Codebook/2.5/XMLSchema/codebook.xsd";
	private static final String FILE_NAME = "test-file-data-types.sav";

	@Test
	public void getCodebookDocumentTest() throws Exception {
		SpssCsvGenerator spssGen = new SpssCsvGenerator();
		File file = FileUtil.getFileFromResource(AbstractFragmentInstanceGeneratorTest.class, FILE_NAME);
		SPSSFile spssFile = new SPSSFile(file);

		VariableCsv variableCsv = spssGen.generateVariablesCsv(spssFile, true, 100);

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		List<CodebookVariable> codebookVariables = variableDDIGenerator.getCodebookVariables(variableCsv);
		Document document = variableDDIGenerator.getCodebookDocument(codebookVariables, FILE_NAME, true);
		String xml = variableDDIGenerator.domToString(document);


		SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		Schema schema = schemaFactory.newSchema(new URL(SCHEMA_FILE));

		Validator validator = schema.newValidator();

		validator.validate(new StreamSource(new StringReader(xml)));
	}
}