package edu.cornell.ncrn.ced2ar.ddigen;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import org.apache.commons.cli.BasicParser;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.log4j.Logger;
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

	private static final Logger logger = Logger.getLogger(GenerateDDI33.class);

	private static String FORMAT_OUTPUT_2_5 = "2.5";
	private static String FORMAT_OUTPUT_3_2 = "3.2";
//	private static String FORMAT_OUTPUT_3_3_FRAGMENT = "3.3Fragment";

	public static void main(String args[]) throws Exception {
		Util util = new Util();
		String observationLimit;
		String dataFile;
		String processSummaryStatics;
		String format;
		String config;
		String exclude;
		Boolean isFrequencyFileEnabled;

		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse(util.getOptions(), args);
			dataFile = cmd.getOptionValue("f");
			processSummaryStatics = cmd.getOptionValue("s");
			observationLimit = cmd.getOptionValue("l");
			format = cmd.getOptionValue("format");
			config = cmd.getOptionValue("config");
			exclude = cmd.getOptionValue("exclude");
			isFrequencyFileEnabled = cmd.hasOption("frequencies");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Boolean summaryStats;
		Long obsLimit;
		String formatOutput;
		formatOutput = Util.formatCheck(format);
		summaryStats = Util.runSumStatsCheck(processSummaryStatics);
		obsLimit = Util.observationLimitCheck(observationLimit);
		ConfigUtil configUtil;
		Map<String, String> excludeVariableToStatMap = new HashMap<>();
		String stats;
		String outputFile;

		if (config != null && !config.isEmpty()) {
			Util.fileCheck(config);

			Properties properties = FileUtil.getPropertiesFromFile(config);
			configUtil = new ConfigUtil(properties);

			if ((dataFile == null || dataFile.trim().isEmpty()) && configUtil.getFilename() != null) {
				dataFile = configUtil.getFilename();
			}
			if (formatOutput.trim().isEmpty() && configUtil.getFormat() != null) {
				formatOutput = configUtil.getFormat();
			}
			if (obsLimit == -1L) {
				obsLimit = configUtil.getObservationLimit();
			}
			if (!summaryStats) {
				summaryStats = configUtil.getSumStats();
			}
		} else {
			if (StringUtils.isEmpty(dataFile)) {
				util.help();
			}

			Properties properties = FileUtil.getPropertiesFromResource(DdiLifecycleGenerator.class);
			configUtil = new ConfigUtil(properties);
		}

		if (exclude != null && !exclude.isEmpty()) {
			Util.fileCheck(exclude);

			Properties properties = FileUtil.getPropertiesFromFile(exclude);
			List propertyNameList = Collections.list(properties.propertyNames());
			for (Object propertyNameObj : propertyNameList) {
				String propertyName = propertyNameObj.toString();
				excludeVariableToStatMap.put(propertyName, properties.getProperty(propertyName));
			}
		}

		String agency = configUtil.getAgency();
		String ddiLanguage = configUtil.getDdiLanguage();
		stats = configUtil.getStats();
		outputFile = configUtil.getOutputFile();

		Util.fileCheck(dataFile);

		long s = System.currentTimeMillis();
		DDI ddi;
		if (formatOutput.equalsIgnoreCase(FORMAT_OUTPUT_2_5)) {
			GenerateDDI generateDDI = new GenerateDDI();
			ddi = generateDDI.generateDDI(dataFile, summaryStats, obsLimit);
		} else if (formatOutput.equalsIgnoreCase(FORMAT_OUTPUT_3_2)) {
			if (agency == null || agency.isEmpty()) {
				logger.error("Agency is required...");
				return;
			}
			if (ddiLanguage == null || ddiLanguage.isEmpty()) {
				logger.error("DDI language is required...");
				return;
			}
			GenerateDDI32 generateDDI = new GenerateDDI32(agency, ddiLanguage, excludeVariableToStatMap, stats);
			ddi = generateDDI.generateDDI(dataFile, summaryStats, obsLimit);

			if (summaryStats) {
				FileUtil.createFile(ddi.getVariableCsv().getStatistics(), dataFile+".stats.csv");
			}
//		} else if (formatOutput.equalsIgnoreCase(FORMAT_OUTPUT_3_3_FRAGMENT)) {
//			if (agency == null || agency.isEmpty()) {
//				logger.error("Agency is required...");
//				System.exit(1);
//				return;
//			}
//			if (ddiLanguage == null || ddiLanguage.isEmpty()) {
//				logger.error("DDI language is required...");
//				System.exit(1);
//				return;
//			}
//			GenerateDDI33 generateDDI = new GenerateDDI33(agency, ddiLanguage, excludeVariableToStatMap, stats);
//			ddi = generateDDI.generateDDI(dataFile, summaryStats, obsLimit);
//
//			if (isFrequencyFileEnabled) {
//				FileUtil.createFile(ddi.getVariableCsv().getFrequencies(), dataFile+".freq.csv");
//			}
//
//			if (summaryStats) {
//				FileUtil.createFile(ddi.getVariableCsv().getStatistics(), dataFile+".stats.csv");
//			}
		} else {
			throw new IllegalArgumentException("Unknown formatOutput: " + formatOutput);
		}
		FileUtil.createFile(ddi.getVariableCsv().getVariableStatistics(), dataFile+".vars.csv");
		FileUtil.createFile(ddi.getVariableCsv().getVariableValueLables(), dataFile+"_var_values.csv");
		logger.info("Successfully created csv files");
		logger.info("CSV created in: "+ ((System.currentTimeMillis() - s) / 1000.0) + " seconds ");

		String fileName;
		if (outputFile != null && !outputFile.trim().isEmpty()) {
			fileName = outputFile;
		} else {
			fileName = dataFile;
		}
		FileUtil.createFile(ddi.getXml(), fileName+".xml");
		logger.info("Successfully created DDI file");
		logger.info(observationLimit);

		System.out.println("Finished. Exiting.");
	}
}
