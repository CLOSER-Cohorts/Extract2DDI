package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.AbstractSchemaGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.DDIInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical.LogicalProductElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Purpose;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ResourcePackageElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.GrossRecordStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalDataProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructureScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayoutScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ElementGenerator extends AbstractSchemaGenerator {

	public ElementGenerator(
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
		super(
			categorySchemeList,
			codeListList,
			variableSchemeList,
			variableStatistics,
			statistics,
			excludeVariableToStatMap,
			attributeMap,
			codeSchemeToCategorySchemeMap,
			agency,
			ddiLanguage,
			title,
			dataFormat,
			productIdentification
		);
	}

	public DDIInstance getInstance() {
		Map<String, UUID> codeListIdToUuidMap = getCodeListIdToUuidMap();

		DDIInstance ddiInstance = new DDIInstance(getAgency(), getDdiLanguage(), "", "");

		// Resource package
		ResourcePackageElement resourcePackage = new ResourcePackageElement(
				getAgency(),
				getDdiLanguage(),
				"",
				""
		);

		// Purpose
		resourcePackage.setPurpose(new Purpose());

		// Logical Product
		LogicalProductElement logicalProduct = new LogicalProductElement(
				getAgency(),
				getTitle(),
				getVariableSchemeList(),
				getCategorySchemeList(),
				getCodeListList()
		);
		resourcePackage.setLogicalProduct(logicalProduct);

		UUID physicalRecordSegmentId = UUID.randomUUID();

		// Physical Data Product
		PhysicalDataProduct physicalDataProduct = getPhysicalDataProduct(physicalRecordSegmentId);

		VariableScheme variableScheme = getVariableSchemeList().iterator().next();
		String variableSchemeId = variableScheme.getUuid();
		UUID recordLayoutId = UUID.randomUUID();
		RecordLayoutScheme recordLayoutScheme = new RecordLayoutScheme(
				getAgency(),
				recordLayoutId.toString(),
				variableSchemeId,
				getDdiLanguage(),
				getDataFormat(),
				getProductIdentification(),
				physicalRecordSegmentId.toString(),
				getVariableSchemeList(),
				getAttributeMap()
		);
		physicalDataProduct.setRecordLayoutScheme(recordLayoutScheme);
		resourcePackage.setPhysicalDataProduct(physicalDataProduct);

		// Physical Instance
		PhysicalInstance physicalInstance =  new PhysicalInstance(
				getAgency(),
				getTitle(),
				getDdiLanguage(),
				10,
				getDataFormat(),
				getProductIdentification(),
				"",
				"",
				getVariableSchemeList(),
				getStatistics(),
				getVariableStatisticList(),
				getExcludeVariableToStatMap(),
				getVariableToFrequencyMap(),
				getCodeListList()
		);
		physicalInstance.setRecordLayoutReference(recordLayoutId.toString());
		resourcePackage.setPhysicalInstance(physicalInstance);

		// Category Scheme
		for (CategoryScheme categoryScheme : getCategorySchemeList()) {
			resourcePackage.addCategoryScheme(getDdiLanguage(), categoryScheme.getUuid(), categoryScheme.getId(), categoryScheme.getCategoryList());
		}

		// Code List Schemes
		resourcePackage.setCodeListScheme(getCodeListList(), getCategorySchemeList(), getCodeSchemeToCategorySchemeMap());

		// Variable Scheme
		List<VariableSchemeElement> variableSchemeElementList = getVariableSchemeElementList(codeListIdToUuidMap);

		for (VariableSchemeElement variableSchemeElement : variableSchemeElementList) {
			resourcePackage.addVariableScheme(variableSchemeElement);
		}

		ddiInstance.setResourcePackage(resourcePackage);

		return ddiInstance;
	}

	protected PhysicalDataProduct getPhysicalDataProduct(UUID physicalRecordSegmentId) {
		PhysicalDataProduct physicalDataProduct = new PhysicalDataProduct(getAgency());

		PhysicalStructureScheme physicalStructureScheme = new PhysicalStructureScheme(getAgency());

		PhysicalStructure physicalStructure = new PhysicalStructure(getAgency());

		// Gross Record Structure
		GrossRecordStructure grossRecordStructure = new GrossRecordStructure(getAgency());

		grossRecordStructure.setPhysicalRecordSegment(physicalRecordSegmentId.toString());

		physicalStructure.setGrossRecordStructure(grossRecordStructure);

		physicalStructureScheme.setPhysicalStructure(physicalStructure);

		physicalDataProduct.setPhysicalStructureScheme(physicalStructureScheme);

		return physicalDataProduct;
	}

	protected List<VariableSchemeElement> getVariableSchemeElementList(Map<String, UUID> codeListIdToUuidMap) {
		List<VariableSchemeElement> variableSchemeElementList = new ArrayList<>();

		for (VariableScheme variableScheme : getVariableSchemeList()) {
			VariableSchemeElement variableSchemeElement = new VariableSchemeElement(
					variableScheme.getUuid(),
					getAgency(),
					getDdiLanguage(),
					getTitle(),
					variableScheme.getVariableList(),
					codeListIdToUuidMap,
					getVariableStatisticList()
			);

			variableSchemeElementList.add(variableSchemeElement);
		}

		return variableSchemeElementList;
	}
}