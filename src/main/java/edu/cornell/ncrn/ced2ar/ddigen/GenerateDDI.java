package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.spss.SPSSFile;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentInstanceGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProductFactory;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.CodebookVariable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;

/**
 *
 * @author NCRN Project Team Gnerates DDI from data file. (SAS ot STATA)
 * @author Cornell University, Copyright 2012-2015
 * @author Venky Kambhampaty, Ben Perry
 *
 * @author Cornell Institute for Social and Economic Research
 * @author Cornell Labor Dynamics Institute
 * @author NCRN Project Team
 */

public class GenerateDDI {
	private static final Logger logger = Logger.getLogger(GenerateDDI.class);

	public void generateDDI(
		String dataFile,
		boolean runSumStats,
		long observationLimit,
		String format,
		String agency,
		String ddiLanguage
	) throws Exception {
		
		long s = System.currentTimeMillis();
		VariableCsv variableCsv = null;
		if (dataFile.toLowerCase().endsWith(".dta")) {
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);
		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);
		}

		if (dataFile.toLowerCase().endsWith(".sav") && format.equalsIgnoreCase("2.5") || dataFile.toLowerCase().endsWith(".dta")) {
			VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
			List<CodebookVariable> codebookVariables = variableDDIGenerator.getCodebookVariables(variableCsv);
			Document document = variableDDIGenerator.getCodebookDocument(codebookVariables,dataFile,runSumStats);
			String xml = variableDDIGenerator.domToString(document);
			createFile(xml, dataFile+".xml");
			logger.info("Successfully created DDI file");
		} else if (dataFile.toLowerCase().endsWith(".sav") && format.equalsIgnoreCase("3.3")) {
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

			LogicalProductGenerator logicalProductGenerator = new LogicalProductGenerator(
				logicalProduct,
				variableStatList,
				agency,
				ddiLanguage,
				dataFile
			);
			List<Fragment> fragmentList = logicalProductGenerator.toFragmentList();

			FragmentInstanceGenerator transformer = new FragmentInstanceGenerator(fragmentList);
			Document fragmentInstanceDocument = transformer.toDocument();

			VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
			String xml = variableDDIGenerator.domToString(fragmentInstanceDocument);
			createFile(xml, dataFile+".xml");
			logger.info("Successfully created DDI file");
		}

		logger.info("CSV created in: "+ ((System.currentTimeMillis() - s) / 1000.0) + " seconds ");
		createFile(variableCsv.getVariableStatistics(), dataFile+".vars.csv");
		createFile(variableCsv.getVariableValueLables(), dataFile+"_var_values.csv");
		logger.info("Successfully created csv files");

		logger.info(observationLimit);
	}

	private void createFile(String csv, String fileName) throws IOException {
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
}