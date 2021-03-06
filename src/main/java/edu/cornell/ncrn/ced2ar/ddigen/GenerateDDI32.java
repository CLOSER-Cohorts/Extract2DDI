package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.DDI32DocumentGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.DDIInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ElementGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GenerateDDI32 {

	private static final Logger logger = Logger.getLogger(GenerateDDI32.class);

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private String outputFile;
	private String statistics;

	public GenerateDDI32(
		String agency,
		String ddiLanguage,
		Map<String, String> excludeVariableToStatMap,
		String statistics,
		String outputFile
	) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		setExcludeVariableToStatMap(excludeVariableToStatMap);
		setOutputFile(outputFile);
		setStatistics(statistics);
	}

	public void generateDDI(
		String dataFile,
		boolean runSumStats,
		long observationLimit

	) throws Exception {
		long s = System.currentTimeMillis();
		VariableCsv variableCsv = null;
		int recordCount = 0;
		List<CategoryScheme> categorySchemeList = new ArrayList<>();
		List<CodeList> codeListList = new ArrayList<>();
		List<VariableScheme> variableSchemeList = new ArrayList<>();

		if (dataFile.toLowerCase().endsWith(".dta")) {
			System.out.println("STATA files are not yet supported in 3.2 format");

		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);

			Document logicalProductDocument = spssGen.getDDI3LogicalProduct(spssFile);
			//Document physicalDataProduct = spssGen.getDDI3PhysicalDataProduct(spssFile);

//			OutputFormat format = new OutputFormat();
//			format.setLineWidth(65);
//			format.setIndenting(true);
//			format.setIndent(2);
//			Writer out = new StringWriter();
//			XMLSerializer serializer = new XMLSerializer(out, format);
//			serializer.serialize(logicalProductDocument);


			//System.out.println("xmlString");
			//printDocument(physicalDataProduct, System.out);

			categorySchemeList.addAll(LogicalProductFactory.createCategorySchemeList(logicalProductDocument));
			codeListList.addAll(LogicalProductFactory.createCodeListList(logicalProductDocument));
			variableSchemeList.addAll(LogicalProductFactory.createVariableSchemeList(logicalProductDocument));

			recordCount = spssFile.getRecordCount();
		}

		ElementGenerator elementGenerator = new ElementGenerator(
			categorySchemeList,
			codeListList,
			variableSchemeList,
			variableCsv.getVariableStatList(),
			getStatistics(),
			getExcludeVariableToStatMap(),
			getAgency(),
			getDdiLanguage(),
			dataFile,
			recordCount
		);
		elementGenerator.setVariableToFrequencyMap(variableCsv.getVariableToFrequencyMap());
		DDIInstance ddiInstance = elementGenerator.getInstance();

		DDI32DocumentGenerator generator = new DDI32DocumentGenerator(ddiInstance);
		Document fragmentInstanceDocument = generator.toDocument();

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument, "UTF-8");

		String fileName;
		if (getOutputFile() != null && !getOutputFile().trim().isEmpty()) {
			fileName = getOutputFile();
		} else {
			fileName = dataFile;
		}

		FileUtil.createFile(xml, fileName + ".xml");
		logger.info("Successfully created DDI file");

		logger.info("CSV created in: "+ ((System.currentTimeMillis() - s) / 1000.0) + " seconds ");
		FileUtil.createFile(variableCsv.getVariableStatistics(), dataFile+".vars.csv");
		FileUtil.createFile(variableCsv.getVariableValueLables(), dataFile+"_var_values.csv");
		logger.info("Successfully created csv files");

		logger.info(observationLimit);
	}

	public String getAgency() {
		return agency;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public Map<String, String> getExcludeVariableToStatMap() {
		return excludeVariableToStatMap;
	}

	public static Logger getLogger() {
		return logger;
	}

	public String getOutputFile() {
		return outputFile;
	}

	public String getStatistics() {
		return statistics;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setExcludeVariableToStatMap(Map<String, String> excludeVariableToStatMap) {
		this.excludeVariableToStatMap = excludeVariableToStatMap;
	}

	public void setOutputFile(String outputFile) {
		this.outputFile = outputFile;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}
}