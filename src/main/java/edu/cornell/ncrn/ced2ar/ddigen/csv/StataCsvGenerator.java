package edu.cornell.ncrn.ced2ar.ddigen.csv;

/**
 * Purpose of this class is to read STATA data file and 
 * generate two CSV's that can be used to create a DDI.

 * CSV 1 format
 * 	name,label,valid,invalid,min,max,mean,stdev
 * CSV 2 format
 * 	variable name, value,label, value , label
 *@author Cornell University, Copyright 2012-2015
 *@author Venky Kambhampaty
 *
 *@author Cornell Institute for Social and Economic Research
 *@author Cornell Labor Dynamics Institute
 *@author NCRN Project Team 
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.util.Map;
import org.apache.commons.math3.stat.Frequency;
import org.apache.log4j.Logger;

import edu.cornell.ncrn.ced2ar.stata.StataReaderFactory;
import edu.cornell.ncrn.ced2ar.stata.StataReader;
import edu.cornell.ncrn.ced2ar.stata.impl.DtaVariable;

public class StataCsvGenerator extends CsvGenerator {
	private static final Logger logger = Logger
			.getLogger(StataCsvGenerator.class);

	public VariableCsv generateVariablesCsv(String dataFileLocation,
			boolean includeSummaryStatistics, long recordLimit)
			throws Exception {
		StataReaderFactory stataReaderFactory = new StataReaderFactory();
		StataReader stataReader = stataReaderFactory.getStataReader(dataFileLocation);
		VariableCsv variableCSV = getVariablesCsv(stataReader,
				includeSummaryStatistics, recordLimit);
		return variableCSV;
	}

	public VariableCsv getVariablesCsv(StataReader stataReader,
			boolean includeSummaryStatistics, long recordLimit)
			throws IOException {
		List<Ced2arVariableStat> ced2arVariableStats = new ArrayList<Ced2arVariableStat>();
		List<DtaVariable> dtaVariables = stataReader.getDtaVariables();
		Map<String, Frequency> variableToFrequencyMap = new HashMap<>();

		int variableNumber = 0;
		for (DtaVariable dtaVariable : dtaVariables) {
			variableNumber++;
			Ced2arVariableStat ced2arVariableStat = new Ced2arVariableStat();
			ced2arVariableStat.setName(dtaVariable.getName());
			ced2arVariableStat.setLabel(dtaVariable.getVariableLabel());
			ced2arVariableStat.setNumeric(dtaVariable.isInt() || dtaVariable.isDouble() || dtaVariable.isFloat() || dtaVariable.isLong());
			ced2arVariableStat.setDate(dtaVariable.isDate());
			ced2arVariableStat.setValidValues(dtaVariable
					.getVariableValueLabels());
			ced2arVariableStat.setVariableNumber(variableNumber);

			// Data item attributes
			if (dtaVariable.isFloat()) {
				ced2arVariableStat.setDecimals(8);
				ced2arVariableStat.setFormat("Float");
				ced2arVariableStat.setWidth(4);

			}
			if (dtaVariable.isDouble()) {
				ced2arVariableStat.setDecimals(16);
				ced2arVariableStat.setFormat("Double");
				ced2arVariableStat.setWidth(8);
			}
			if (dtaVariable.isString()) {
				int width = dtaVariable.getVariableType();
				ced2arVariableStat.setFormat("str" + width);
				ced2arVariableStat.setWidth(width);
			}
			if (dtaVariable.isInt()) {
				ced2arVariableStat.setFormat("Integer");
				ced2arVariableStat.setWidth(2);
			}
			if (dtaVariable.isLong()) {
				ced2arVariableStat.setFormat("Long");
				ced2arVariableStat.setWidth(4);
			}
			if (dtaVariable.isDate()) {
				ced2arVariableStat.setFormat("Date");
			}
			if (dtaVariable.isByte()) {
				ced2arVariableStat.setFormat("Byte");
				ced2arVariableStat.setWidth(1);
			}

			ced2arVariableStats.add(ced2arVariableStat);

			variableToFrequencyMap.put(dtaVariable.getName(), new Frequency());
		}

		long readErrors = 0;

		if (includeSummaryStatistics) {
			readErrors = setSummaryStatistics(stataReader, ced2arVariableStats, variableToFrequencyMap, recordLimit);
		}

		String variableStatistics = getSummaryStatisticsVaribleCSV(
				ced2arVariableStats, includeSummaryStatistics);
		String variableValueLabels = getVariableValueLabelCSV(ced2arVariableStats);

		VariableCsv variablesCSV = new VariableCsv();
		variablesCSV.setVariableStatistics(variableStatistics);

		String statistics = getStatisticsCSV(ced2arVariableStats, includeSummaryStatistics);
		variablesCSV.setStatistics(statistics);

		List<DdiFrequency> ddiFrequencyList = new ArrayList<>();
		for (Map.Entry<String, Frequency> entry : variableToFrequencyMap.entrySet()) {
			ddiFrequencyList.add(new DdiFrequency(entry.getKey(), entry.getKey(), entry.getValue()));
		}

		String frequencies = getFrequenciesCSV(ddiFrequencyList, ced2arVariableStats);
		variablesCSV.setFrequencies(frequencies);

		variablesCSV.setVariableValueLables(variableValueLabels);
		variablesCSV.setReadErrors(readErrors);
		variablesCSV.setVariableStatList(ced2arVariableStats);

		return variablesCSV;

	}

	private long setSummaryStatistics(StataReader stataReader,
			List<Ced2arVariableStat> ced2arVariableStats, Map<String, Frequency> variableToFrequencyMap, long recordLimit)
			throws IOException {
		long startTime = System.currentTimeMillis();
		logger.info("Start reading stata observations");
		long observationsToProcess = recordLimit;
		long readErrors = 0;
		if (observationsToProcess == PROCESS_ALL_RECORDS)
			observationsToProcess = stataReader.getDtaHeader().getNumberOfObservations();
		try {
			stataReader.openDtaFile();
			for (long l = 1; l <= observationsToProcess; l++) {
				if (l % 100 == 0)
					logger.debug("Processing observation " + 500);

				List<String> observation = stataReader.getObservation(l);
				String[] varValues = observation.toArray(new String[observation
						.size()]);
				readErrors = updateVariableStatistics(ced2arVariableStats,
						variableToFrequencyMap,
						varValues);
				// Observation is in CSV format that confirms to RFC 4180
				// https://www.ietf.org/rfc/rfc4180.txt
				// String[] varValues =
				// observation.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)"); //
				// expensive operations
				// String[] varValues = observation.split(",");
			}
		} finally {
			stataReader.closeDtaFile();
		}
		logger.info("Time took to read obseravtions from desk: "
				+ ((System.currentTimeMillis() - startTime) / 1000)
				+ " seconds");
		return readErrors;
	}

	/*
	 * public static void main(String a[]) throws Exception{ long s =
	 * System.currentTimeMillis(); StataCsvGenerator stataCsvGenerator = new
	 * StataCsvGenerator(); stataCsvGenerator.generateVariablesCsv(
	 * "C:\\java\\info\\Data\\STATA\\1995Preg.dta",true,-1);
	 * System.out.println("Time to gen csv: " +(System.currentTimeMillis()-s));
	 * //stataCsvGenerator.generateVariablesCsv(
	 * "C:\\java\\info\\Data\\STATA\\IMF Fin Reform.dta",true,-1);
	 * //stataCsvGenerator
	 * .generateVariablesCsv("C:\\java\\info\\Data\\STATA\\dc2010UR1_ALL_VARS.DTA"
	 * ,true,100); }
	 */
}
