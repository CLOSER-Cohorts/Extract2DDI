package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.VariableScheme;
import org.apache.commons.math3.stat.Frequency;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AbstractSchemaGenerator {

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private Map<String, Frequency> variableToFrequencyMap;
	private List<CategoryScheme> categorySchemeList = new ArrayList<>();
	private List<CodeList> codeListList = new ArrayList<>();
	private List<VariableScheme> variableSchemeList = new ArrayList<>();
	private int recordCount;
	private String title;
	private String statistics;
	private List<Ced2arVariableStat> variableStatisticList;
	private int version;

	public AbstractSchemaGenerator(
		List<CategoryScheme> categorySchemeList,
		List<CodeList> codeListList,
		List<VariableScheme> variableSchemeList,
		List<Ced2arVariableStat> variableStatistics,
		String statistics,
		Map<String, String> excludeVariableToStatMap,
		String agency,
		String ddiLanguage,
		String title,
		int recordCount
	) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		setCodeListList(codeListList);
		setVariableSchemeList(variableSchemeList);
		setCategorySchemeList(categorySchemeList);
		setExcludeVariableToStatMap(excludeVariableToStatMap);
		setStatistics(statistics);
		setTitle(title);
		setRecordCount(recordCount);
		setVariableStatistics(variableStatistics);
		setVersion(1);
	}

	public String getAgency() {
		return agency;
	}

	public List<CategoryScheme> getCategorySchemeList() {
		return categorySchemeList;
	}

	public List<CodeList> getCodeListList() {
		return codeListList;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public Map<String, String> getExcludeVariableToStatMap() {
		return excludeVariableToStatMap;
	}

	public String getStatistics() {
		return statistics;
	}

	public String getTitle() {
		return title;
	}

	public int getRecordCount() {
		return recordCount;
	}

	protected Map<String, UUID> getVariableIdToUuidMap() {
		Map<String, UUID> variableIdToUuidMap = new HashMap<>();
		for (VariableScheme variableScheme : getVariableSchemeList()) {
			for (Variable variable : variableScheme.getVariableList()) {
				if (variable.getId() != null) {
					variableIdToUuidMap.put(variable.getId(), UUID.randomUUID());
				}
			}
		}

		return variableIdToUuidMap;
	}

	public List<Ced2arVariableStat> getVariableStatisticList() {
		return variableStatisticList;
	}

	public Map<String, Frequency> getVariableToFrequencyMap() {
		return variableToFrequencyMap;
	}

	public List<VariableScheme> getVariableSchemeList() {
		return variableSchemeList;
	}

	public int getVersion() {
		return version;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setCategorySchemeList(List<CategoryScheme> categorySchemeList) {
		this.categorySchemeList = categorySchemeList;
	}

	public void setCodeListList(List<CodeList> codeListList) {
		this.codeListList = codeListList;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setExcludeVariableToStatMap(Map<String, String> excludeVariableToStatMap) {
		this.excludeVariableToStatMap = excludeVariableToStatMap;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVariableSchemeList(List<VariableScheme> variableSchemeList) {
		this.variableSchemeList = variableSchemeList;
	}

	public void setVariableStatistics(List<Ced2arVariableStat> variableStatisticList) {
		this.variableStatisticList = variableStatisticList;
	}

	public void setVariableToFrequencyMap(Map<String, Frequency> map) {
		this.variableToFrequencyMap = map;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}