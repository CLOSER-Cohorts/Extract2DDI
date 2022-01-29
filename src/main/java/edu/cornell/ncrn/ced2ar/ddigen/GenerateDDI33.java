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
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.VariableScheme;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GenerateDDI33 {

	private static final Logger logger = Logger.getLogger(GenerateDDI33.class);

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private String outputFile;
	private String statistics;

	public GenerateDDI33(
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

	protected void createFile(String csv, String fileName) throws IOException {
		BufferedWriter bw = null;
		try {
			File varsFile = new File(fileName);
			varsFile.createNewFile();
			FileWriter fw = new FileWriter(varsFile.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(csv);
		} finally {
			bw.close();
		}
	}

	public void generateDDI(
		String dataFile,
		boolean runSumStats,
		long observationLimit

	) throws Exception {
		long s = System.currentTimeMillis();
		VariableCsv variableCsv = null;
		int recordCount = 0;
		Document logicalProductDocument = null;

		if (dataFile.toLowerCase().endsWith(".dta")) {
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);
		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);

			File serverFile = new File(dataFile);
			SPSSFile spssFile = new SPSSFile(serverFile);

			logicalProductDocument = spssGen.getDDI3LogicalProduct(spssFile);

			recordCount = spssFile.getRecordCount();
		}

		LogicalProduct logicalProduct = LogicalProductFactory.createLogicalProduct(logicalProductDocument);

		List<Variable> variableList = new ArrayList<>();
		for (Ced2arVariableStat stat : variableCsv.getVariableStatList()) {
			System.out.println("VARIABLE NAME: " + stat.getName());
			Variable variable = new Variable(UUID.randomUUID().toString());
			variable.setName(stat.getName());
			variableList.add(variable);
		}

		VariableScheme defaultVariableScheme = new VariableScheme(UUID.randomUUID().toString());
		defaultVariableScheme.setVariableList(variableList);

		LogicalProductGenerator logicalProductGenerator = new LogicalProductGenerator(
			logicalProduct.getCategorySchemeList(),
			logicalProduct.getCodeListList(),
			Arrays.asList(defaultVariableScheme),
			variableCsv.getVariableStatList(),
			getStatistics(),
			getExcludeVariableToStatMap(),
			getAgency(),
			getDdiLanguage(),
			dataFile,
			recordCount
		);
		logicalProductGenerator.setVariableToFrequencyMap(variableCsv.getVariableToFrequencyMap());
		List<Fragment> fragmentList = logicalProductGenerator.toFragmentList();

		FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
		Document fragmentInstanceDocument = transformer.toDocument();

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		String xml = variableDDIGenerator.domToString(fragmentInstanceDocument, "UTF-8");

		String fileName;
		if (getOutputFile() != null && !getOutputFile().trim().isEmpty()) {
			fileName = getOutputFile();
		} else {
			fileName = dataFile;
		}

		createFile(xml, fileName + ".xml");
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