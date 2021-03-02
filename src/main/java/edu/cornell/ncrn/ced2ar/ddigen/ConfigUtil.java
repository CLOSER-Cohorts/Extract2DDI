package edu.cornell.ncrn.ced2ar.ddigen;

import java.util.Properties;

public class ConfigUtil {

	public static final String KEY_AGENCY = "agency";
	public static final String KEY_DDI_LANGUAGE = "ddilang";
	public static final String KEY_FILENAME = "filename";
	public static final String KEY_OUTPUT_FILE = "outputfile";
	public static final String KEY_RENAME = "rename";
	public static final String KEY_STATS = "stats";
	public static final String KEY_SUM_STATS = "sumstats";
	public static final String KEY_OBSERVATION_LIMIT = "obsLimit";

	private Properties properties;

	public ConfigUtil(Properties properties) {
		setProperties(properties);
	}

	public String getAgency() {
		String agency = getProperties().getProperty(KEY_AGENCY);
		return agency;
	}

	public String getDdiLanguage() {
		String ddiLang = getProperties().getProperty(KEY_DDI_LANGUAGE);
		return ddiLang;
	}

	public String getFilename() {
		String filename = getProperties().getProperty(KEY_FILENAME);
		return filename;
	}

	public long getObservationLimit() {
		String observationLimit = getProperties().getProperty(KEY_OBSERVATION_LIMIT);
		return Util.observationLimitCheck(observationLimit);
	}

	public String getOutputFile() {
		String outputFile = getProperties().getProperty(KEY_OUTPUT_FILE);
		return outputFile;
	}

	public Properties getProperties() {
		return properties;
	}

	public String getRename() {
		String rename = getProperties().getProperty(KEY_RENAME);
		return rename;
	}

	public String getStats() {
		String stats = getProperties().getProperty(KEY_STATS);
		return stats;
	}

	public boolean getSumStats() {
		String sumStats = getProperties().getProperty(KEY_SUM_STATS);
		return Util.runSumStatsCheck(sumStats);
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
}