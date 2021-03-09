package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.CodebookVariable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProductFactory;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
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
		if (dataFile.toLowerCase().endsWith(".sav")) {
			long s = System.currentTimeMillis();
			VariableCsv variableCsv = null;
			if (dataFile.toLowerCase().endsWith(".dta")) {
				StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
				variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);
			} else if (dataFile.toLowerCase().endsWith(".sav")) {
				SpssCsvGenerator spssGen = new SpssCsvGenerator();
				variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);
			}

			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);

			Document logicalProductDocument = spssGen.getLogicalProduct(spssFile);
			LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(logicalProductDocument);
			List<Ced2arVariableStat> variableStatList = spssGen.getVariableStats(spssFile);


			long readErrors = 0;
			if (runSumStats) {
				readErrors = spssGen.setSummaryStatistics(spssFile, variableStatList, observationLimit);
			}

			int recordCount = spssFile.getRecordCount();

			LogicalProductGenerator logicalProductGenerator = new LogicalProductGenerator(
				logicalProduct,
				variableStatList,
				getExcludeVariableToStatMap(),
				getAgency(),
				getDdiLanguage(),
				dataFile,
				recordCount
			);
			List<Fragment> fragmentList = logicalProductGenerator.toFragmentList();

			FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
			Document fragmentInstanceDocument = transformer.toDocument();

			VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
			String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
			createFile(xml, dataFile+".xml");
			logger.info("Successfully created DDI file");

			logger.info("CSV created in: "+ ((System.currentTimeMillis() - s) / 1000.0) + " seconds ");
			createFile(variableCsv.getVariableStatistics(), dataFile+".vars.csv");
			createFile(variableCsv.getVariableValueLables(), dataFile+"_var_values.csv");
			logger.info("Successfully created csv files");

			logger.info(observationLimit);
		}
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