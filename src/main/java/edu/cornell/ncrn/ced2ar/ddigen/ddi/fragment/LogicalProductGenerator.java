package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategoryFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategoryReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategorySchemeFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategorySchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code.CodeFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code.CodeListFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance.DataFileIdentification;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance.GrossFileStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance.PhysicalInstanceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance.PhysicalInstanceReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship.DataRelationshipReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.resource.ResourcePackageFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.resource.TopLevelReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.CodeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship.DataRelationshipFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship.LogicalRecordFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship.VariableUsedReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.DateTimeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.NumericVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.StatisticType;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.SummaryStatistic;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.TextVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableCategoryFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableSchemeFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableSchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableStatisticsFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Category;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Code;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.LogicalProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Representation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.logical.VariableScheme;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.math3.stat.Frequency;

public class LogicalProductGenerator {

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private Frequency frequency;
	private LogicalProduct logicalProduct;
	private int recordCount;
	private String title;
	private List<Ced2arVariableStat> variableStatList = new ArrayList<>();
	private int version;

	public LogicalProductGenerator(
		LogicalProduct logicalProduct,
		List<Ced2arVariableStat> variableStatList,
		Map<String, String> excludeVariableToStatMap,
		String agency,
		String ddiLanguage,
		String title,
		int recordCount
	) {
		setAgency(agency);
		setDdiLanguage(ddiLanguage);
		setLogicalProduct(logicalProduct);
		setExcludeVariableToStatMap(excludeVariableToStatMap);
		setTitle(title);
		setRecordCount(recordCount);
		setVariableStatList(variableStatList);
		setVersion(1);
	}

	public String getAgency() {
		return agency;
	}

	public List<Fragment> getCategoryFragmentList(
		Map<String, UUID> categorySchemeIdToUuidMap,
		Map<String, UUID> categoryIdToUuidMap
	) {
		List<Fragment> fragmentList = new ArrayList<>();
		List<Fragment> categoryFragmentList = new ArrayList<>();

		for (CategoryScheme categoryScheme : getLogicalProduct().getCategorySchemeList()) {
			UUID categorySchemeId = categorySchemeIdToUuidMap.get(categoryScheme.getId());
			CategorySchemeFragment fragment = new CategorySchemeFragment(categorySchemeId.toString(), getAgency(), getVersion());
			fragmentList.add(fragment);
			for (Category category : categoryScheme.getCategoryList()) {
				String id = categoryIdToUuidMap.get(category.getId()).toString();
				CategoryReferenceFragment reference = new CategoryReferenceFragment(id, getAgency(), getVersion());
				fragment.addCategoryReference(reference);

				CategoryFragment categoryFragment = new CategoryFragment(id, getAgency(), getVersion());

				Label label = new Label(category.getLabel(), getDdiLanguage());
				categoryFragment.setLabel(label);

				categoryFragmentList.add(categoryFragment);
			}
		}

		fragmentList.addAll(categoryFragmentList);
		return fragmentList;
	}

	public List<Fragment> getCodeListFragmentList(
		Map<String, UUID> codeListIdToUuidMap,
		Map<String, UUID> categoryIdToUuidMap
	) {
		List<Fragment> fragmentList = new ArrayList<>();
		for (CodeList codeList : getLogicalProduct().getCodeListList()) {
			UUID categorySchemeId = codeListIdToUuidMap.get(codeList.getId());
			CodeListFragment codeListFragment = new CodeListFragment(categorySchemeId.toString(), getAgency(), getVersion());

			Label label = new Label(codeList.getLabel(), getDdiLanguage());
			codeListFragment.setLabel(label);

			fragmentList.add(codeListFragment);
			for (Code code : codeList.getCodeList()) {
				String id = UUID.randomUUID().toString();

				String categoryId = categoryIdToUuidMap.get(code.getCategoryId()).toString();
				CodeFragment codeFragment = new CodeFragment(id, getAgency(), getVersion(), code.getValue());
				CategoryReferenceFragment reference = new CategoryReferenceFragment(categoryId, getAgency(), getVersion());
				codeFragment.setCategoryReference(reference);
				codeListFragment.addCode(codeFragment);
			}
		}

		return fragmentList;
	}

	private DataRelationshipFragment getDataRelationshipFragment(
		String dataRelationshipId,
		Map<String, UUID> variableIdToUuidMap
	) {
		DataRelationshipFragment dataRelationshipFragment = new DataRelationshipFragment(
			dataRelationshipId,
			getAgency(),
			getVersion()
		);

		dataRelationshipFragment.setName(new StringElement(getTitle(), getDdiLanguage()));

		LogicalRecordFragment logicalRecordFragment = new LogicalRecordFragment(
			UUID.randomUUID().toString(),
			getAgency(),
			getVersion()
		);

		logicalRecordFragment.setName(new StringElement(getTitle(), getDdiLanguage()));

		for (Map.Entry<String, UUID> variableIdToUuidEntry : variableIdToUuidMap.entrySet()) {
			VariableUsedReferenceFragment fragment = new VariableUsedReferenceFragment(
				variableIdToUuidEntry.getValue().toString(),
				getAgency(),
				getVersion()
			);
			logicalRecordFragment.addVariableUsedReference(fragment);
		}
		dataRelationshipFragment.setLogicalRecord(logicalRecordFragment);

		return dataRelationshipFragment;
	}

	public String getDdiLanguage() {
		return ddiLanguage;
	}

	public Map<String, String> getExcludeVariableToStatMap() {
		return excludeVariableToStatMap;
	}

	public Frequency getFrequency() {
		return frequency;
	}

	public LogicalProduct getLogicalProduct() {
		return logicalProduct;
	}

	private PhysicalInstanceFragment getPhysicalInstanceFragment(String dataRelationshipId) {
		PhysicalInstanceFragment physicalInstanceReference = new PhysicalInstanceFragment(
			UUID.randomUUID().toString(),
			getAgency(),
			getVersion()
		);

		StringElement string = new StringElement(getTitle(), getDdiLanguage());
		Title titleElement = new Title(string);
		Citation citation = new Citation(titleElement);
		physicalInstanceReference.setCitation(citation);

		DataRelationshipReferenceFragment dataRelationshipReference = new DataRelationshipReferenceFragment(
			dataRelationshipId,
			getAgency(),
			getVersion()
		);
		physicalInstanceReference.setDataRelationshipReference(dataRelationshipReference);

		DataFileIdentification dataFileIdentification = new DataFileIdentification(getTitle());
		physicalInstanceReference.setDataFileIdentification(dataFileIdentification);

		GrossFileStructure grossFileStructure = new GrossFileStructure(
			UUID.randomUUID().toString(),
			getAgency(),
			getVersion(),
			3
		);
		physicalInstanceReference.setGrossFileStructure(grossFileStructure);

		return physicalInstanceReference;
	}

	public Fragment getResourcePackageFragment(
		String title,
		Map<String, UUID> categorySchemeIdToUuidMap,
		Map<String, UUID> physicalInstanceIdToUuidMap,
		Map<String, UUID> variableSchemeIdToUuidMap
	) {
		ResourcePackageFragment resourcePackage = new ResourcePackageFragment(
			UUID.randomUUID().toString(),
			getAgency(),
			getVersion()
		);

		StringElement string = new StringElement(title, getDdiLanguage());
		Title titleElement = new Title(string);
		Citation citation = new Citation(titleElement);
		resourcePackage.setCitation(citation);

		for (Map.Entry<String, UUID> physicalInstanceEntry : physicalInstanceIdToUuidMap.entrySet()) {
			PhysicalInstanceReferenceFragment fragment = new PhysicalInstanceReferenceFragment(
				physicalInstanceEntry.getValue().toString(),
				getAgency(),
				getVersion()
			);
			resourcePackage.addPhysicalInstanceReference(fragment);
		}

		for (Map.Entry<String, UUID> categorySchemeEntry : categorySchemeIdToUuidMap.entrySet()) {
			CategorySchemeReferenceFragment fragment = new CategorySchemeReferenceFragment(
				categorySchemeEntry.getValue().toString(),
				getAgency(),
				getVersion()
			);
			resourcePackage.addCategorySchemeReference(fragment);
		}

		for (Map.Entry<String, UUID> variableSchemeEntry : variableSchemeIdToUuidMap.entrySet()) {
			VariableSchemeReferenceFragment fragment = new VariableSchemeReferenceFragment(
				variableSchemeEntry.getValue().toString(),
				getAgency(),
				getVersion()
			);
			resourcePackage.addVariableSchemeReference(fragment);
		}

		return resourcePackage;
	}

	public String getTitle() {
		return title;
	}

	public List<Fragment> getVariableFragmentList(
		Map<String, UUID> variableSchemeIdToUuidMap,
		Map<String, UUID> variableIdToUuidMap
	) {
		List<Fragment> fragmentList = new ArrayList<>();
		for (VariableScheme variableScheme : getLogicalProduct().getVariableSchemeList()) {
			UUID variableSchemeId = variableSchemeIdToUuidMap.get(variableScheme.getId());
			VariableSchemeFragment variableSchemeFragment = new VariableSchemeFragment(
				variableSchemeId.toString(),
				getAgency(),
				getVersion()
			);
			fragmentList.add(variableSchemeFragment);
			for (Variable variable : variableScheme.getVariableList()) {
				UUID id = variableIdToUuidMap.get(variable.getId());
				VariableReferenceFragment variableReferenceFragment = new VariableReferenceFragment(id.toString(), getAgency(), getVersion());
				variableSchemeFragment.addVariable(variableReferenceFragment);

				VariableFragment variableFragment = new VariableFragment(id.toString(), getAgency(), getVersion());

				Label label = new Label(variable.getLabel(), getDdiLanguage());
				variableFragment.setLabel(label);

				StringElement string = new StringElement(variable.getName(), getDdiLanguage());
				variableFragment.setName(string);

				Representation representation = variable.getRepresentation();
				if (representation instanceof TextRepresentation) {
					TextVariableRepresentation textVariableRepresentation = new TextVariableRepresentation();
					variableFragment.setRepresentation(textVariableRepresentation);
				} else if (representation instanceof NumericRepresentation) {
					NumericVariableRepresentation numericVariableRepresentation = new NumericVariableRepresentation(
						((NumericRepresentation) representation).getType()
					);
					variableFragment.setRepresentation(numericVariableRepresentation);
				} else if (representation instanceof DateTimeRepresentation) {
					DateTimeVariableRepresentation dateTimeVariableRepresentation = new DateTimeVariableRepresentation(
						((DateTimeRepresentation) representation).getType()
					);
					variableFragment.setRepresentation(dateTimeVariableRepresentation);
				} else if (representation instanceof CodeRepresentation) {
					CodeVariableRepresentation codeVariableRepresentation =
						new CodeVariableRepresentation(id.toString(), getAgency(), getVersion());
					variableFragment.setRepresentation(codeVariableRepresentation);
				}
				fragmentList.add(variableFragment);
			}
		}

		return fragmentList;
	}

	public int getRecordCount() {
		return recordCount;
	}

	public List<Ced2arVariableStat> getVariableStatList() {
		return variableStatList;
	}

	public List<Fragment> getVariableStatisticsList(Map<String, UUID> variableIdToUuidMap) {
		List<Fragment> fragmentList = new ArrayList<>();
		for (VariableScheme variableScheme : getLogicalProduct().getVariableSchemeList()) {
			for (Variable variable : variableScheme.getVariableList()) {
				UUID id = variableIdToUuidMap.get(variable.getId());
				VariableReferenceFragment variableReferenceFragment =
					new VariableReferenceFragment(id.toString(), getAgency(), getVersion());

				VariableStatisticsFragment variableStatistics =
					new VariableStatisticsFragment(id.toString(), getAgency(), getVersion(), getRecordCount());

				variableStatistics.setVariableReference(variableReferenceFragment);

				for (Ced2arVariableStat variableStat : getVariableStatList()) {
					if (variableStat.getName() != null && variable.getName() != null && variableStat.getName().equalsIgnoreCase(variable.getName())) {
						String excludeVariableStat = getExcludeVariableToStatMap().get(variableStat.getName());

						if (excludeVariableStat == null || !excludeVariableStat.contains("valid")) {
							String statistic = Long.toString(variableStat.getValidCount());
							SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, StatisticType.VALID_CASES);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !excludeVariableStat.contains("invalid")) {
							String statistic = Long.toString(variableStat.getInvalidCount());
							SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, StatisticType.INVALID_CASES);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !Double.isNaN(variableStat.getStats().getMax()) && !excludeVariableStat.contains("max")) {
							String statistic = Double.toString(variableStat.getStats().getMax());
							SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, StatisticType.MAXIMUM);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !Double.isNaN(variableStat.getStats().getMin()) && !excludeVariableStat.contains("min")) {
							String statistic = Double.toString(variableStat.getStats().getMin());
							SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, StatisticType.MINIMUM);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !Double.isNaN(variableStat.getStats().getStandardDeviation()) && !excludeVariableStat.contains("mean")) {
							String statistic = Double.toString(variableStat.getStats().getMean());
							SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, StatisticType.MEAN);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !Double.isNaN(variableStat.getStats().getStandardDeviation()) && !excludeVariableStat.contains("stddev")) {
							String statistic = Double.toString(variableStat.getStats().getStandardDeviation());
							SummaryStatistic summaryStatistic = new SummaryStatistic(statistic, StatisticType.STANDARD_DEVIATION);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !excludeVariableStat.contains("freq")) {
							boolean isRepresentationTypeCodeList = variableStat.isRepresentationTypeCodeList();
							if (isRepresentationTypeCodeList) {
								for (CodeList codeList : getLogicalProduct().getCodeListList()) {
									for (Code code : codeList.getCodeList()) {
										long frequency = getFrequency().getCount(Integer.parseInt(code.getValue()));
										VariableCategoryFragment variableCategory = new VariableCategoryFragment(code.getValue(), Long.toString(frequency));
										variableStatistics.addVariableCategory(variableCategory);
									}
								}
							}
						}
					}
				}

				fragmentList.add(variableStatistics);
			}
		}

		return fragmentList;
	}

	public int getVersion() {
		return version;
	}

	public List<Fragment> toFragmentList() {
		List<Fragment> fragmentList = new ArrayList<>();

		Map<String, UUID> categoryIdToUuidMap = new HashMap<>();
		for (CategoryScheme categoryScheme : getLogicalProduct().getCategorySchemeList()) {
			for (Category category : categoryScheme.getCategoryList()) {
				if (category.getId() != null) {
					categoryIdToUuidMap.put(category.getId(), UUID.randomUUID());
				}
			}
		}

		Map<String, UUID> variableIdToUuidMap = new HashMap<>();
		for (VariableScheme variableScheme : getLogicalProduct().getVariableSchemeList()) {
			for (Variable variable : variableScheme.getVariableList()) {
				if (variable.getId() != null) {
					variableIdToUuidMap.put(variable.getId(), UUID.randomUUID());
				}
			}
		}

		Map<String, UUID> categorySchemeIdToUuidMap = new HashMap<>();
		for (CategoryScheme categoryScheme : getLogicalProduct().getCategorySchemeList()) {
			categorySchemeIdToUuidMap.put(categoryScheme.getId(), UUID.randomUUID());
		}

		Map<String, UUID> codeListIdToUuidMap = new HashMap<>();
		for (CodeList codeList : getLogicalProduct().getCodeListList()) {
			codeListIdToUuidMap.put(codeList.getId(), UUID.randomUUID());
		}

		Map<String, UUID> variableSchemeIdToUuidMap = new HashMap<>();
		for (VariableScheme variableScheme : getLogicalProduct().getVariableSchemeList()) {
			variableSchemeIdToUuidMap.put(variableScheme.getId(), UUID.randomUUID());
		}

		Fragment topLevelReferenceFragment = new TopLevelReferenceFragment(
			UUID.randomUUID().toString(),
			getAgency(),
			1
		);
		fragmentList.add(topLevelReferenceFragment);

		Fragment resourcePackageFragment = getResourcePackageFragment(
			getTitle(),
			categorySchemeIdToUuidMap,
			codeListIdToUuidMap,
			variableSchemeIdToUuidMap
		);

		fragmentList.add(resourcePackageFragment);

		List<Fragment> categoryFragmentList =
			getCategoryFragmentList(categorySchemeIdToUuidMap, categoryIdToUuidMap);
		fragmentList.addAll(categoryFragmentList);

		List<Fragment> codeListFragmentList =
			getCodeListFragmentList(codeListIdToUuidMap, categoryIdToUuidMap);
		fragmentList.addAll(codeListFragmentList);

		List<Fragment> variableFragmentList =
			getVariableFragmentList(variableSchemeIdToUuidMap, variableIdToUuidMap);
		fragmentList.addAll(variableFragmentList);

		String dataRelationshipId = UUID.randomUUID().toString();

		PhysicalInstanceFragment physicalInstanceReference = getPhysicalInstanceFragment(
			dataRelationshipId
		);
		fragmentList.add(physicalInstanceReference);

		DataRelationshipFragment dataRelationshipFragment = getDataRelationshipFragment(
			dataRelationshipId,
			variableIdToUuidMap
		);
		fragmentList.add(dataRelationshipFragment);

		List<Fragment> variableStatisticsList = getVariableStatisticsList(variableIdToUuidMap);
		fragmentList.addAll(variableStatisticsList);

		return fragmentList;
	}

	public void setAgency(String agency) {
		this.agency = agency;
	}

	public void setDdiLanguage(String ddiLanguage) {
		this.ddiLanguage = ddiLanguage;
	}

	public void setFrequency(Frequency frequency) {
		this.frequency = frequency;
	}

	public void setExcludeVariableToStatMap(Map<String, String> excludeVariableToStatMap) {
		this.excludeVariableToStatMap = excludeVariableToStatMap;
	}

	public void setLogicalProduct(LogicalProduct logicalProduct) {
		this.logicalProduct = logicalProduct;
	}

	public void setRecordCount(int recordCount) {
		this.recordCount = recordCount;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVariableStatList(List<Ced2arVariableStat> variableStatList) {
		this.variableStatList = variableStatList;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}