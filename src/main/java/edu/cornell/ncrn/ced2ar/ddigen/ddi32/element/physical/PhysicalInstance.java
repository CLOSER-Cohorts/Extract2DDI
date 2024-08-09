package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Citation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.RecordLayoutReference;
import edu.cornell.ncrn.ced2ar.ddigen.DataFileIdentification;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableStatistics;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable.StatisticType;
import edu.cornell.ncrn.ced2ar.ddigen.representation.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.apache.commons.math3.stat.Frequency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PhysicalInstance extends ElementWithUrn {

	public static final String NODE_NAME_PHYSICAL_INSTANCE = "pi:PhysicalInstance";
	public Citation citation;
	private RecordLayoutReference recordLayoutReference;
	private final DataFileIdentification dataFileIdentification;
	private final GrossFileStructure grossFileStructure;
	private final StatisticalSummary statisticalSummary;

	public PhysicalInstance(
			String agency,
			String dataFileUri,
			String isPublic,
			String ddiLang,
			long caseQuantity,
			FileFormatInfo.Format dataFormat,
			String productIdentification,
			String citationTitle,
			String citationAlternateTitle,
			List<VariableScheme> variableSchemesList,
			String statistics,
			Map<String, String> excludeVariableToStatMap,
			Map<String, Frequency> variableToFrequencyMap,
			List<CodeList> codeListList
	) {
		super(agency);
		this.dataFileIdentification = new DataFileIdentification(dataFileUri, "pi", isPublic);
		this.grossFileStructure = new GrossFileStructure(agency, ddiLang, caseQuantity, dataFormat, productIdentification);
		setCitation(new Citation(citationTitle + " Physical Instance", citationAlternateTitle, ddiLang));

		StatisticalSummary statisticalSummary = new StatisticalSummary();

		for (VariableScheme variableScheme : variableSchemesList) {
			for (Variable variable : variableScheme.getVariableList()) {
				if (variable.getId() == null) {
					continue;
				}

				VariableStatistics variableStatistics = new VariableStatistics(getAgency(), variable.getUuid());

				List<String> statisticList = new ArrayList<>();
				if (statistics != null && !statistics.trim().isEmpty()) {
					String[] statisticArray = statistics.split(",");
					statisticList.addAll(Arrays.asList(statisticArray));
				}

				String excludeVariableStat = excludeVariableToStatMap.get(variable.getName());

				boolean excludeValid = !statisticList.isEmpty() && !statisticList.contains("valid");
				boolean excludeInvalid = !statisticList.isEmpty() && !statisticList.contains("invalid");
				boolean excludeMin = !statisticList.isEmpty() && !statisticList.contains("min");
				boolean excludeMax = !statisticList.isEmpty() && !statisticList.contains("max");
				boolean excludeMean = !statisticList.isEmpty() && !statisticList.contains("mean");
				boolean excludeStdDev = !statisticList.isEmpty() && !statisticList.contains("stdev");
				boolean excludeFrequency = !statisticList.isEmpty() && !statisticList.contains("freq");

				if (excludeVariableStat != null) {
					String[] excludeVariableStatArray = excludeVariableStat.split(":");

					if (excludeVariableStatArray.length > 0 && !excludeVariableStatArray[0].isEmpty()) {
						List<String> excludeVariableStatList = Arrays.asList(excludeVariableStatArray[0].split(","));
						if (!excludeValid) {
							excludeValid = excludeVariableStatList.contains("valid");
						}
						if (!excludeInvalid) {
							excludeInvalid = excludeVariableStatList.contains("invalid");
						}
						if (!excludeMin) {
							excludeMin = excludeVariableStatList.contains("min");
						}
						if (!excludeMax) {
							excludeMax = excludeVariableStatList.contains("max");
						}
						if (!excludeMean) {
							excludeMean = excludeVariableStatList.contains("mean");
						}
						if (!excludeStdDev) {
							excludeStdDev = excludeVariableStatList.contains("stdev");
						}
						if (!excludeFrequency) {
							excludeFrequency = excludeVariableStatList.contains("freq");
						}
					}
				}

				long validCount = variable.getValidCount();
				if (!excludeValid) {
					variableStatistics.addSummaryStatistic(Long.toString(validCount), StatisticType.VALID_CASES, "pi");
				}

				long invalidCount = variable.getInvalidCount();
				if (!excludeInvalid) {
					variableStatistics.addSummaryStatistic(Long.toString(invalidCount), StatisticType.INVALID_CASES, "pi");
				}

				String min = variable.getMin();
				if (!excludeMin && min != null && !min.isEmpty()) {
					variableStatistics.addSummaryStatistic(min, StatisticType.MINIMUM, "pi");
				}

				String max = variable.getMax();
				if (!excludeMax && max != null && !max.isEmpty()) {
					variableStatistics.addSummaryStatistic(max, StatisticType.MAXIMUM, "pi");
				}

				String mean = variable.getMean();
				if (!excludeMean && mean != null && !mean.isEmpty()) {
					variableStatistics.addSummaryStatistic(mean, StatisticType.MEAN, "pi");
				}

				String stdDeviation = variable.getStdDeviation();
				if (!excludeStdDev && stdDeviation != null && !stdDeviation.isEmpty()) {
					variableStatistics.addSummaryStatistic(stdDeviation, StatisticType.STANDARD_DEVIATION, "pi");
				}

				if (!excludeFrequency && variableToFrequencyMap != null) {
					if (variable.getRepresentation() instanceof CodeRepresentation) {
						Frequency variableFrequency = variableToFrequencyMap.get(variable.getName());
						CodeRepresentation representation = (CodeRepresentation) variable.getRepresentation();
						for (CodeList codeList : codeListList) {
							if (representation.getCodeSchemeId().equalsIgnoreCase(codeList.getId()) && variableFrequency != null) {
								long invalidValueFrequency = variableFrequency.getCount(".");
								if (invalidValueFrequency > 0) {
									variableStatistics.addVariableCategory(".", invalidValueFrequency, "pi");
								}
								for (Code code : codeList.getCodeList()) {
									long frequency = variableFrequency.getCount(code.getCategoryId());
									if (frequency > 0) {
										variableStatistics.addVariableCategory(code.getValue(), frequency, "pi");
									}
								}
							}
						}
					}
				}
				statisticalSummary.addVariableStatistics(variableStatistics);
			}
		}

		this.statisticalSummary = statisticalSummary;
	}

	@Override
	public void appendToElement(Element parent, Document doc) {
		Element physicalInstance = doc.createElement(NODE_NAME_PHYSICAL_INSTANCE);
		super.appendToElement(physicalInstance, doc);

		// Citation
		if (getCitation() != null) {
			getCitation().appendToElement(physicalInstance, doc);
		}
		getRecordLayoutReference().appendToElement(physicalInstance, doc);

		dataFileIdentification.appendToElement(physicalInstance, doc);

		grossFileStructure.appendToElement(physicalInstance, doc);

		statisticalSummary.appendToElement(physicalInstance, doc);

		parent.appendChild(physicalInstance);
	}

	public Citation getCitation() {
		return citation;
	}

	public RecordLayoutReference getRecordLayoutReference() {
		return recordLayoutReference;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}

	public void setRecordLayoutReference(String recordLayoutId) {
		this.recordLayoutReference = new RecordLayoutReference(recordLayoutId, getAgency());
	}
}
