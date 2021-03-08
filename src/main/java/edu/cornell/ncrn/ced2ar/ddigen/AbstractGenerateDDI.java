package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.csv.SpssCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.StataCsvGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.VariableCsv;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.CodebookVariable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.VariableDDIGenerator;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;

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

public abstract class AbstractGenerateDDI {

	public abstract void generateDDI(String dataFile, boolean runSumStats, long observationLimit) throws Exception;

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
}