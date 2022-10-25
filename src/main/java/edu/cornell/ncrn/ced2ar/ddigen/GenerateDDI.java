package edu.cornell.ncrn.ced2ar.ddigen;

import java.util.List;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;

import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;

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

	public DDI generateDDI(String dataFile, boolean runSumStats, long observationLimit) throws Exception {
		VariableCsv variableCsv = null;
		if (dataFile.toLowerCase().endsWith(".dta")) {
			StataCsvGenerator stataCsvGenerator = new StataCsvGenerator();
			variableCsv = stataCsvGenerator.generateVariablesCsv(dataFile, runSumStats, observationLimit);
		} else if (dataFile.toLowerCase().endsWith(".sav")) {
			SpssCsvGenerator spssGen = new SpssCsvGenerator();
			variableCsv = spssGen.generateVariablesCsv(dataFile, runSumStats, observationLimit);
		}

		VariableDDIGenerator variableDDIGenerator = new VariableDDIGenerator();
		List<CodebookVariable> codebookVariables = variableDDIGenerator.getCodebookVariables(variableCsv);
		Document document = variableDDIGenerator.getCodebookDocument(codebookVariables,dataFile,runSumStats);
		String xml = variableDDIGenerator.domToString(document);

		return new DDI(xml, variableCsv);
	}
}