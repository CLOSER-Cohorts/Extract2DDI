package edu.cornell.ncrn.ced2ar.ddigen;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.apache.commons.math3.stat.Frequency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AbstractSchemaGenerator {

	private String agency;
	private Map<String, String> attributeMap;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private Map<String, Frequency> variableToFrequencyMap;
	private List<CategoryScheme> categorySchemeList;
	private Map<String, String> codeSchemeToCategorySchemeMap;
	private List<CodeList> codeListList;
	private List<VariableScheme> variableSchemeList;
	private FileFormatInfo.Format dataFormat;
	private String productIdentification;
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
		Map<String, String> attributeMap,
		Map<String, String> codeSchemeToCategorySchemeMap,
		String agency,
		String ddiLanguage,
		String title,
		FileFormatInfo.Format dataFormat,
		String productIdentification
	) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		this.codeListList = codeListList;
		this.variableSchemeList = variableSchemeList;
		this.categorySchemeList = categorySchemeList;
		this.attributeMap = attributeMap;
		this.codeSchemeToCategorySchemeMap = codeSchemeToCategorySchemeMap;
		this.excludeVariableToStatMap = excludeVariableToStatMap;
		this.statistics = statistics;
		this.title = title;
		this.variableStatisticList = variableStatistics;
		this.version = 1;
		this.dataFormat = dataFormat;
		this.productIdentification = productIdentification;
	}

	public String getAgency() {
		return agency;
	}

	public Map<String, String> getAttributeMap() {
		return attributeMap;
	}

	protected Map<String, UUID> getCodeListIdToUuidMap() {
		Map<String, UUID> codeListIdToUuidMap = new HashMap<>();
		for (CodeList codeList : codeListList) {
			codeListIdToUuidMap.put(codeList.getId(), UUID.randomUUID());
		}
		return codeListIdToUuidMap;
	}

	public List<CategoryScheme> getCategorySchemeList() {
		return categorySchemeList;
	}

	public Map<String, String> getCodeSchemeToCategorySchemeMap() {
		return codeSchemeToCategorySchemeMap;
	}

	public List<CodeList> getCodeListList() {
		return codeListList;
	}

	public FileFormatInfo.Format getDataFormat() {
		return dataFormat;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public Map<String, String> getExcludeVariableToStatMap() {
		return excludeVariableToStatMap;
	}

	public String getProductIdentification() {
		return productIdentification;
	}

	public String getStatistics() {
		return statistics;
	}

	public String getTitle() {
		return title;
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

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setVariableToFrequencyMap(Map<String, Frequency> variableToFrequencyMap) {
		this.variableToFrequencyMap = variableToFrequencyMap;
	}
}