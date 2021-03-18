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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.commons.math3.stat.Frequency;

public class LogicalProductGenerator {

	private String agency;
	private String ddiLanguage;
	private Map<String, String> excludeVariableToStatMap;
	private Map<String, Frequency> variableToFrequencyMap;
	private LogicalProduct logicalProduct;
	private int recordCount;
	private String title;
	private String statistics;
	private List<Ced2arVariableStat> variableStatistics;
	private int version;

	public LogicalProductGenerator(
		LogicalProduct logicalProduct,
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
		setLogicalProduct(logicalProduct);
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
		UUID dataRelationshipId,
		Map<String, UUID> variableIdToUuidMap
	) {
		DataRelationshipFragment dataRelationshipFragment = new DataRelationshipFragment(
			dataRelationshipId.toString(),
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

		for (Map.Entry variableIdToUuidEntry : variableIdToUuidMap.entrySet()) {
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

	public Map<String, Frequency> getVariableToFrequencyMap() {
		return variableToFrequencyMap;
	}

	public LogicalProduct getLogicalProduct() {
		return logicalProduct;
	}

	private PhysicalInstanceFragment getPhysicalInstanceFragment(
		UUID physicalInstanceId,
		UUID dataRelationshipId
	) {
		PhysicalInstanceFragment physicalInstanceReference = new PhysicalInstanceFragment(
			physicalInstanceId.toString(),
			getAgency(),
			getVersion()
		);

		StringElement string = new StringElement(getTitle(), getDdiLanguage());
		Title titleElement = new Title(string);
		Citation citation = new Citation(titleElement);
		physicalInstanceReference.setCitation(citation);

		DataRelationshipReferenceFragment dataRelationshipReference = new DataRelationshipReferenceFragment(
			dataRelationshipId.toString(),
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
		UUID physicalInstanceId,
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

		PhysicalInstanceReferenceFragment fragment = new PhysicalInstanceReferenceFragment(
			physicalInstanceId.toString(),
			getAgency(),
			getVersion()
		);
		resourcePackage.addPhysicalInstanceReference(fragment);

		for (Map.Entry categorySchemeEntry : categorySchemeIdToUuidMap.entrySet()) {
			CategorySchemeReferenceFragment categorySchemeReference = new CategorySchemeReferenceFragment(
				categorySchemeEntry.getValue().toString(),
				getAgency(),
				getVersion()
			);
			resourcePackage.addCategorySchemeReference(categorySchemeReference);
		}

		for (Map.Entry variableSchemeEntry : variableSchemeIdToUuidMap.entrySet()) {
			VariableSchemeReferenceFragment variableSchemeReference = new VariableSchemeReferenceFragment(
				variableSchemeEntry.getValue().toString(),
				getAgency(),
				getVersion()
			);
			resourcePackage.addVariableSchemeReference(variableSchemeReference);
		}

		return resourcePackage;
	}

	public String getStatistics() {
		return statistics;
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

	public List<Ced2arVariableStat> getVariableStatistics() {
		return variableStatistics;
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

				if (getExcludeVariableToStatMap().containsKey(variable.getName())) {
					UserAttributePairFragment userAttributePair = new UserAttributePairFragment();
					String excludeVariableMessage = getExcludeVariableToStatMap().get(variable.getName());
					userAttributePair.addAttribute("extension:redaction-information", excludeVariableMessage);
					variableStatistics.setUserAttributePair(userAttributePair);
				}

				variableStatistics.setVariableReference(variableReferenceFragment);


				List<String> statisticList = new ArrayList<>();
				if (getStatistics() != null && !getStatistics().trim().isEmpty()) {
					String[] statisticArray = getStatistics().split(",");
					statisticList.addAll(Arrays.asList(statisticArray));
				}

				for (Ced2arVariableStat variableStat : getVariableStatistics()) {
					if (variableStat.getName() != null && variable.getName() != null && variableStat.getName().equalsIgnoreCase(variable.getName())) {
						String excludeVariableStat = getExcludeVariableToStatMap().get(variable.getName());

						boolean excludeValid = !statisticList.isEmpty() && !statisticList.contains("valid");
						boolean excludeInvalid = !statisticList.isEmpty() && !statisticList.contains("invalid");
						boolean excludeMin = !statisticList.isEmpty() && !statisticList.contains("min");
						boolean excludeMax = !statisticList.isEmpty() && !statisticList.contains("max");
						boolean excludeMean = !statisticList.isEmpty() && !statisticList.contains("mean");
						boolean excludeStdDev = !statisticList.isEmpty() && !statisticList.contains("stdev");

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
							}
						}

						Long validCount = variableStat.getValidCount();
						if (!excludeValid && validCount != null) {
							SummaryStatistic summaryStatistic = new SummaryStatistic(Long.toString(validCount), StatisticType.VALID_CASES);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						Long invalidCount = variableStat.getInvalidCount();
						if (!excludeInvalid && invalidCount != null) {
							SummaryStatistic summaryStatistic = new SummaryStatistic(Long.toString(invalidCount), StatisticType.INVALID_CASES);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						String min = variableStat.getMinFormatted();
						if (!excludeMin && min != null) {
							SummaryStatistic summaryStatistic = new SummaryStatistic(min, StatisticType.MINIMUM);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						String max = variableStat.getMaxFormatted();
						if (!excludeMax && max != null) {
							SummaryStatistic summaryStatistic = new SummaryStatistic(max, StatisticType.MAXIMUM);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						String mean = variableStat.getMeanFormatted();
						if (!excludeMean && mean != null) {
							SummaryStatistic summaryStatistic = new SummaryStatistic(mean, StatisticType.MEAN);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						String stdDeviation = variableStat.getStdDeviationFormatted();
						if (!excludeStdDev && stdDeviation != null) {
							SummaryStatistic summaryStatistic = new SummaryStatistic(stdDeviation, StatisticType.STANDARD_DEVIATION);
							variableStatistics.addSummaryStatistic(summaryStatistic);
						}

						if (excludeVariableStat == null || !excludeVariableStat.contains("freq") && getVariableToFrequencyMap() != null) {
							if (variable.getRepresentation() instanceof CodeRepresentation) {
								Frequency variableFrequency = getVariableToFrequencyMap().get(variable.getName());
								CodeRepresentation representation = (CodeRepresentation) variable.getRepresentation();
								for (CodeList codeList : getLogicalProduct().getCodeListList()) {
									if (representation.getCodeSchemeId().equalsIgnoreCase(codeList.getId())) {
										long invalidValueFrequency = variableFrequency.getCount(".");
										if (invalidValueFrequency > 0) {
											VariableCategoryFragment variableCategory = new VariableCategoryFragment(
												".",
												Long.toString(invalidValueFrequency)
											);
											variableStatistics.addVariableCategory(variableCategory);
										}
										for (Code code : codeList.getCodeList()) {
											long frequency = variableFrequency.getCount(code.getValue());
											if (frequency > 0) {
												VariableCategoryFragment variableCategory = new VariableCategoryFragment(
													code.getValue(),
													Long.toString(frequency)
												);
												variableStatistics.addVariableCategory(variableCategory);
											}
										}
									}
								}
							}
						}
						break;
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

		UUID physicalInstanceId = UUID.randomUUID();

		Fragment topLevelReferenceFragment = new TopLevelReferenceFragment(
			UUID.randomUUID().toString(),
			getAgency(),
			1
		);
		fragmentList.add(topLevelReferenceFragment);

		Fragment resourcePackageFragment = getResourcePackageFragment(
			getTitle(),
			categorySchemeIdToUuidMap,
			physicalInstanceId,
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

		UUID dataRelationshipId = UUID.randomUUID();

		PhysicalInstanceFragment physicalInstanceReference = getPhysicalInstanceFragment(
			physicalInstanceId,
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

	public void setVariableToFrequencyMap(Map<String, Frequency> map) {
		this.variableToFrequencyMap = map;
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

	public void setStatistics(String statistics) {
		this.statistics = statistics;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVariableStatistics(List<Ced2arVariableStat> variableStatistics) {
		this.variableStatistics = variableStatistics;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}