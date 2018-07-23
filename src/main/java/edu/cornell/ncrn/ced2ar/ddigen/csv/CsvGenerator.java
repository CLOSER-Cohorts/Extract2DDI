package edu.cornell.ncrn.ced2ar.ddigen.csv;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;

/**
 * This is a super class that abstracts the methods commonly used to convert
 * SPSS and STATA to CSV
 * 
 * generate two CSV's that can be used to create a DDI. CSV 1 format
 * name,label,valid,invalid,min,max,mean,stdev CSV 2 format variable name,
 * value,label, value , label
 * 
 * @author Cornell University, Copyright 2012-2015
 * @author Venky Kambhampaty
 * 
 * @author Cornell Institute for Social and Economic Research
 * @author Cornell Labor Dynamics Institute
 * @author NCRN Project Team
 */

public class CsvGenerator {
	private static final Logger logger = Logger.getLogger(CsvGenerator.class);
	public final long PROCESS_ALL_RECORDS = -1;

	/**
	 * This method loops through list of variables and updates summary
	 * statistics from the observation
	 * 
	 * @param variables
	 * @param record
	 * @return
	 */
	protected long updateVariableStatistics(List<Ced2arVariableStat> variables,
			String[] observation) {
		long readErrors = 0;
		for (Ced2arVariableStat variable : variables) {
			String value = "";
			try {
				value = observation[variable.getVariableNumber() - 1];
			} catch (ArrayIndexOutOfBoundsException ex) {
				// if the last variables do not have any response, spss record
				// does not contain commas
			}

			if (isValidValue(variable, value)) {
				variable.setValidCount(variable.getValidCount() + 1);
				if (variable.isNumeric() && !variable.isDate()) {
					try {
						if (value.matches("-?\\d+(\\.\\d+)?"))
							variable.getStats().addValue(
									Double.parseDouble(value));
						else
							readErrors++;
					} catch (Exception ex) {
						logger.error("Invalid numeric value for the variable "
								+ variable.getName() + " in the observation "
								+ observation);
					}
				}
			} else {
				variable.setInvalidCount(variable.getInvalidCount() + 1);
			}
		}
		return readErrors;
	}

	/**
	 * This method determines if a given value is valid for the variable.
	 * 
	 * Valid value is the one which is part of valid values list if there is
	 * one. If there us no valid values list, the value is considered valid. If
	 * the value is a part of missing values list, the value is invalid
	 * 
	 * @param variable
	 * @param value
	 * @return
	 */
	private boolean isValidValue(Ced2arVariableStat variable, String value) {
		HashMap<String, String> missingValues = variable.getMissingValues();

		HashMap<String, String> validLabels = variable.getValidValues();

		if (!validLabels.isEmpty()) {
			return validLabels.containsKey(value);
		}
		// SPSS Reader sometimes reads '.' as this long value 
		if (value.equalsIgnoreCase(".") || value.equalsIgnoreCase("-179769313486231570000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000")) {
			return false;
		}
		if (missingValues.containsKey(value)) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * This method generates CSV in the format of CSV 1 format
	 * name,label,valid,invalid,min,max,mean,stdev
	 * 
	 * @param ced2arVariableStats
	 * @param includeSummaryStatistics
	 * @return String representing csv
	 */
	protected String getSummaryStatisticsVaribleCSV(
			List<Ced2arVariableStat> ced2arVariableStats,
			boolean includeSummaryStatistics) {
		StringBuilder sb = new StringBuilder(
				"name,label,valid,invalid,min,max,mean,stdev\n");
		int i = 0;
		for (Ced2arVariableStat v : ced2arVariableStats) {
			logger.debug("Processing variable " + (++i) + " of "
					+ ced2arVariableStats.size() + " " + v.getName());
			sb.append(v.getCSVValue(includeSummaryStatistics) + "\n");
		}
		return sb.toString();
	}

	/**
	 * This method generates CSV in the format of variable name, value,label,
	 * [value , label ... repeat]
	 * 
	 * @param ced2arVariableStats
	 * @return String representing csv
	 */
	public String getVariableValueLabelCSV(
			List<Ced2arVariableStat> ced2arVariableStats) {
		StringBuilder varValuesSB = new StringBuilder();
		for (Ced2arVariableStat v : ced2arVariableStats) {
			varValuesSB.append(v.getName());
			HashMap<String, String> validValues = v.getValidValues();
			Iterator iterator = validValues.entrySet().iterator();
			while (iterator.hasNext()) {
				Map.Entry mapEntry = (Map.Entry) iterator.next();
				varValuesSB.append("," + mapEntry.getKey() + "="
						+ mapEntry.getValue());
			}
			varValuesSB.append("\n");
		}
		return varValuesSB.toString();
	}
}
