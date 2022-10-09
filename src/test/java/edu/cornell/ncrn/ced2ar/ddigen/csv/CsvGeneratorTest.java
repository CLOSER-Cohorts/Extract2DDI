package edu.cornell.ncrn.ced2ar.ddigen.csv;

import org.apache.commons.math3.stat.Frequency;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CsvGeneratorTest extends CsvGenerator {

	@Test
	public void testUpdateVariableStatistics() {
		String record = "TestString5,5,5.5555,5.55,5.55,$5.55,05-MAR-05,MAY,1 Q 04,00:05,05-MAR-2005 05:05:05.14,2,-3,97";
		String[] varValues = record.split(",");

		List<Ced2arVariableStat> ced2arVariableStatList = new ArrayList<>();
		Ced2arVariableStat stat = new Ced2arVariableStat();
		stat.setName("TestString5");
		stat.setVariableNumber(6);
		stat.setNumeric(true);
		ced2arVariableStatList.add(stat);

		Map<String, Frequency> variableToFrequencyMap = new HashMap<>();
		variableToFrequencyMap.put("TestString5", new Frequency());

		long readErrors = updateVariableStatistics(ced2arVariableStatList, variableToFrequencyMap, varValues);
		Assert.assertEquals(0, readErrors);
		Frequency frequency = variableToFrequencyMap.get("TestString5");
		Assert.assertEquals(1, frequency.getUniqueCount());
		Assert.assertEquals(1, frequency.getCount("5"));
		Assert.assertEquals(1, frequency.getCumFreq("5"));
	}

	@Test
	public void testUpdateVariableStatisticsWithDot() {
		String record = "Teststring4,4,.,.,.,.,.,.,.,.,.,.,-1,99";
		String[] varValues = record.split(",");

		List<Ced2arVariableStat> ced2arVariableStatList = new ArrayList<>();
		Ced2arVariableStat stat = new Ced2arVariableStat();
		stat.setName("Teststring4");
		stat.setVariableNumber(6);
		stat.setNumeric(true);
		ced2arVariableStatList.add(stat);

		Map<String, Frequency> variableToFrequencyMap = new HashMap<>();
		variableToFrequencyMap.put("Teststring4", new Frequency());

		long readErrors = updateVariableStatistics(ced2arVariableStatList, variableToFrequencyMap, varValues);
		Assert.assertEquals(0, readErrors);
	}
}
