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
		ConfigUtil configUtil;
		Map<String, String> excludeVariableToStatMap = new HashMap<>();

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

		Util.fileCheck(dataFile);

		AbstractGenerateDDI generateDDI;
		if (formatOutput.equalsIgnoreCase("2.5Fragment")) {
			generateDDI = new GenerateDDI();
		} else {
			generateDDI = new GenerateDDI3(agency, ddiLanguage, excludeVariableToStatMap);
		}
		generateDDI.generateDDI(dataFile, summaryStats, obsLimit);


		System.out.println("Finished. Exiting.");
	}
}
