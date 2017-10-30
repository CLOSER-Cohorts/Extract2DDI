package edu.ncrn.cornell.ced2ar;

import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.springframework.util.StringUtils;

/**
 *
 * @author NCRN Project Team Main class to generated DDI from data file.
 *         Validated the command line input using Util class. If the input is
 *         valid, generated ddi using GenerateDDI class
 * 
 * @author Cornell University, Copyright 2012-2015
 * @author Ben Perry, Venky Kambhampaty
 *
 * @author Cornell Institute for Social and Economic Research
 * @author Cornell Labor Dynamics Institute
 * @author NCRN Project Team
 */

public class Main {
	public static void main(String args[]) throws Exception {
		Util util = new Util();
		String observationLimit;
		String dataFile;
		String processSummaryStatics;

		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse(util.getOptions(), args);
			dataFile = cmd.getOptionValue("f");
			processSummaryStatics = cmd.getOptionValue("s");
			observationLimit = cmd.getOptionValue("l");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		if (StringUtils.isEmpty(dataFile)) {
			util.help();
		}
		
		Boolean summaryStats = Util.runSumStatsCheck(processSummaryStatics);
		Long obsLimit = Util.observationLimitCheck(observationLimit);

		Util.fileCheck(dataFile);

		GenerateDDI generateDDI = new GenerateDDI();
		generateDDI.generateDDI(dataFile, summaryStats, obsLimit);
		System.out.println("Finished. Exiting.");
	}
}
