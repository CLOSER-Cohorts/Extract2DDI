package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductGenerator;
import java.util.Properties;
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
		String format;
		String config;

		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse(util.getOptions(), args);
			dataFile = cmd.getOptionValue("f");
			processSummaryStatics = cmd.getOptionValue("s");
			observationLimit = cmd.getOptionValue("l");
			format = cmd.getOptionValue("format");
			config = cmd.getOptionValue("config");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Boolean summaryStats;
		Long obsLimit;
		String formatOutput;
		ConfigUtil configUtil;

		if (config != null && !config.isEmpty()) {
			Util.fileCheck(config);

			Properties properties = FileUtil.getPropertiesFromFile(config);
			configUtil = new ConfigUtil(properties);

			summaryStats = configUtil.getSumStats();
			obsLimit = configUtil.getObservationLimit();
			formatOutput = configUtil.getDdiLanguage();
			dataFile = configUtil.getFilename();

		} else {
			if (StringUtils.isEmpty(dataFile)) {
				util.help();
			}

			Properties properties = FileUtil.getPropertiesFromResource(LogicalProductGenerator.class);
			configUtil = new ConfigUtil(properties);
			summaryStats = Util.runSumStatsCheck(processSummaryStatics);
			obsLimit = Util.observationLimitCheck(observationLimit);
			formatOutput = Util.formatCheck(format);
		}

		String agency = configUtil.getAgency();
		String ddiLanguage = configUtil.getDdiLanguage();

		Util.fileCheck(dataFile);

		GenerateDDI generateDDI = new GenerateDDI();
		generateDDI.generateDDI(dataFile, summaryStats, obsLimit, formatOutput, agency, ddiLanguage);
		System.out.println("Finished. Exiting.");
	}
}
