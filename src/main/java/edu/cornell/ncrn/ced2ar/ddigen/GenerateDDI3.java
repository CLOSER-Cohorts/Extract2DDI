package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProductFactory;
import java.io.File;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.stat.Frequency;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

public class GenerateDDI3 extends AbstractGenerateDDI {

	private static final Logger logger = Logger.getLogger(GenerateDDI3.class);

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;

	public GenerateDDI3(String agency, String ddiLanguage, Map<String, String> excludeVariableToStatMap) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		setExcludeVariableToStatMap(excludeVariableToStatMap);
	}

	public void generateDDI(
		String dataFile,
		boolean runSumStats,
		long observationLimit

	) throws Exception {
		long s = System.currentTimeMillis();
		VariableCsv variableCsv = null;
		int recordCount = 0;
		Frequency frequency = null;
		Document logicalProductDocument = null;

		if (dataFile.toLowerCase().endsWith(".dta")) {
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			throw new Exception("STATA files are not supported yet for DDI 3.3Fragment");
		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);

			logicalProductDocument = spssGen.getLogicalProduct(spssFile);

			recordCount = spssFile.getRecordCount();
		}

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(logicalProductDocument);

		LogicalProductGenerator logicalProductGenerator = new LogicalProductGenerator(
			logicalProduct,
			variableCsv.getVariableStatistics(),
			getExcludeVariableToStatMap(),
			getAgency(),
			getDdiLanguage(),
			dataFile,
			recordCount
		);
		logicalProductGenerator.setFrequency(frequency);
		List<Fragment> fragmentList = logicalProductGenerator.toFragmentList();

		FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
		Document fragmentInstanceDocument = transformer.toDocument();

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument, "UTF-8");
		createFile(xml, dataFile+".xml");
		logger.info("Successfully created DDI file");

		logger.info("CSV created in: "+ ((System.currentTimeMillis() - s) / 1000.0) + " seconds ");
		createFile(variableCsv.getVariableStatistics(), dataFile+".vars.csv");
		createFile(variableCsv.getVariableValueLables(), dataFile+"_var_values.csv");
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

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setExcludeVariableToStatMap(Map<String, String> excludeVariableToStatMap) {
		this.excludeVariableToStatMap = excludeVariableToStatMap;
	}
}