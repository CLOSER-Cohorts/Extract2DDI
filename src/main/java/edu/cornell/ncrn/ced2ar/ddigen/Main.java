package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.LogicalProductGenerator;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
		String exclude;

		CommandLineParser parser = new BasicParser();
		try {
			CommandLine cmd = parser.parse(util.getOptions(), args);
			dataFile = cmd.getOptionValue("f");
			processSummaryStatics = cmd.getOptionValue("s");
			observationLimit = cmd.getOptionValue("l");
			format = cmd.getOptionValue("format");
			config = cmd.getOptionValue("config");
			exclude = cmd.getOptionValue("exclude");
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

			Properties properties = FileUtil.getPropertiesFromResource(LogicalProductGenerator.class);
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

		System.out.println(dataFile);
		Util.fileCheck(dataFile);

		GenerateDDI generateDDI = new GenerateDDI(agency, ddiLanguage, excludeVariableToStatMap, stats, outputFile);
		generateDDI.generateDDI(dataFile, summaryStats, obsLimit);

		System.out.println("Finished. Exiting.");
	}
}
