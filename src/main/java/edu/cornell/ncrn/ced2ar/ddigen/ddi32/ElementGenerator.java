package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.AbstractSchemaGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.DDIInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical.DataRelationshipElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical.LogicalProductElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical.LogicalRecordElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Purpose;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ResourcePackageElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategorySchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListSchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.BasedOnObject;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.GrossRecordStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalDataProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalRecordSegment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructureScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.DataItem;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.ProprietaryInfo;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.ProprietaryProperty;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayout;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayoutScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.VariableSchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.CodeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.DateTimeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.NumericVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.TextVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableUsedReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariablesInRecordElement;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.representation.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.representation.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.variable.Variable;
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
		String agency,
		String ddiLanguage,
		String title,
		int recordCount
	) {
		super(categorySchemeList, codeListList, variableSchemeList, variableStatistics, statistics, excludeVariableToStatMap, agency, ddiLanguage, title, recordCount);
	}

	protected DataRelationshipElement getDataRelationshipElement(Map<String, UUID> variableIdToUuidMap) {
		DataRelationshipElement dataRelationshipElement = new DataRelationshipElement(getAgency());
		dataRelationshipElement.setLogicalRecord(getLogicalRecord(variableIdToUuidMap));
		return dataRelationshipElement;
	}

	public DDIInstance getInstance() {
		Map<String, UUID> categoryIdToUuidMap = getCategoryIdToUuidMap();
		Map<String, UUID> categorySchemeIdToUuidMap = getCategorySchemeIdToUuidMap();
		Map<String, UUID> codeListIdToUuidMap = getCodeListIdToUuidMap();
		Map<String, UUID> variableIdToUuidMap = getVariableIdToUuidMap();
		Map<String, UUID> variableSchemeIdToUuidMap = getVariableSchemeIdToUuidMap();

		DDIInstance ddiInstance = new DDIInstance(getAgency());

		// Resource package
		ResourcePackageElement resourcePackage = new ResourcePackageElement(getAgency());

		// Purpose
		resourcePackage.setPurpose(new Purpose());

		// Logical Product
		resourcePackage.setLogicalProduct(
			getLogicalProduct(
				categorySchemeIdToUuidMap,
				codeListIdToUuidMap,
				variableIdToUuidMap,
				variableSchemeIdToUuidMap
			)
		);

		// Physical Data Product
		PhysicalDataProduct physicalDataProduct = getPhysicalDataProduct();
		UUID variableSchemeId = UUID.randomUUID();
		physicalDataProduct.setRecordLayoutScheme(
			getRecordLayoutScheme(variableSchemeId, variableIdToUuidMap)
		);
		resourcePackage.setPhysicalDataProduct(physicalDataProduct);

		// TODO: Physical Instance

		// TODO: Category Scheme

		// Code List Schemes
		CodeListScheme codeListScheme = new CodeListScheme(getAgency());
		for (CodeList codeList : getCodeListList()) {
			UUID id = codeListIdToUuidMap.get(codeList.getId());
			CodeListElement codeListElement = new CodeListElement(id.toString(), getAgency());
			codeListElement.setName(codeList.getLabel());

			for (Code code : codeList.getCodeList()) {
				CodeElement codeElement = new CodeElement(getAgency());
				UUID categoryId = categoryIdToUuidMap.get(code.getCategoryId());
				codeElement.setCategoryReference(categoryId.toString());
				codeListElement.addCodeListName(codeElement);
			}

			codeListScheme.addCodeList(codeListElement);
		}
		resourcePackage.setCodeListScheme(codeListScheme);

		// Variable Scheme
		List<VariableSchemeElement> variableSchemeElementList = getVariableSchemeElementList(
			variableIdToUuidMap,
			variableSchemeIdToUuidMap
		);

		for (VariableSchemeElement variableSchemeElement : variableSchemeElementList) {
			resourcePackage.addVariableSchemeList(variableSchemeElement);
		}

		ddiInstance.setResourcePackage(resourcePackage);

		return ddiInstance;
	}

	protected LogicalProductElement getLogicalProduct(
		Map<String, UUID> categorySchemeIdToUuidMap,
		Map<String, UUID> codeListIdToUuidMap,
		Map<String, UUID> variableIdToUuidMap,
		Map<String, UUID> variableSchemeIdToUuidMap
	) {
		LogicalProductElement logicalProduct = new LogicalProductElement(getAgency());

		// Data Relationship
		DataRelationshipElement dataRelationshipElement = getDataRelationshipElement(variableIdToUuidMap);
		logicalProduct.setDataRelationship(dataRelationshipElement);

		// Category Schemes
		for (Map.Entry<String, UUID> entry : categorySchemeIdToUuidMap.entrySet()) {
			UUID uuid = entry.getValue();
			CategorySchemeReference categorySchemeReference = new CategorySchemeReference(uuid.toString(), getAgency());
			logicalProduct.addCategorySchemeReference(categorySchemeReference);
		}

		// Code List Schemes
		for (Map.Entry<String, UUID> entry : codeListIdToUuidMap.entrySet()) {
			UUID uuid = entry.getValue();
			CodeListSchemeReference codeListSchemeReference = new CodeListSchemeReference(uuid.toString(), getAgency());
			logicalProduct.addCodeListSchemeReference(codeListSchemeReference);
		}

		// Variable Schemes
		for (Map.Entry<String, UUID> entry : variableSchemeIdToUuidMap.entrySet()) {
			UUID uuid = entry.getValue();
			VariableSchemeReference variableSchemeReference = new VariableSchemeReference(uuid.toString(), getAgency());
			logicalProduct.addVariableSchemeReference(variableSchemeReference);
		}

		return logicalProduct;
	}

	protected LogicalRecordElement getLogicalRecord(Map<String, UUID> variableIdToUuidMap) {
		LogicalRecordElement logicalRecord = new LogicalRecordElement(getAgency());

		VariablesInRecordElement variablesInRecord = new VariablesInRecordElement();
		for (VariableScheme variableScheme : getVariableSchemeList()) {
			for (Variable variable : variableScheme.getVariableList()) {
				if (variable.getId() != null) {
					UUID id = variableIdToUuidMap.get(variable.getId());
					VariableUsedReference variableUsedReference = new VariableUsedReference(id.toString(), getAgency());
					variablesInRecord.addReference(variableUsedReference);
				}
			}
		}
		logicalRecord.setVariablesInRecord(variablesInRecord);

		logicalRecord.setLogicalProductName(getTitle());

		return logicalRecord;
	}

	protected PhysicalDataProduct getPhysicalDataProduct() {
		PhysicalDataProduct physicalDataProduct = new PhysicalDataProduct(getAgency());

		PhysicalStructureScheme physicalStructureScheme = new PhysicalStructureScheme(getAgency());

		PhysicalStructure physicalStructure = new PhysicalStructure(getAgency());

		BasedOnObject basedOnObject = new BasedOnObject(getAgency());

		basedOnObject.setBasedOnReference(UUID.randomUUID().toString());

		physicalStructure.setBasedOnObject(basedOnObject);

		// Gross Record Structure
		GrossRecordStructure grossRecordStructure = new GrossRecordStructure(getAgency());

		grossRecordStructure.setLogicalRecordReference(UUID.randomUUID().toString());
		grossRecordStructure.setPhysicalRecordSegment(new PhysicalRecordSegment(getAgency()));

		physicalStructure.setGrossRecordStructure(grossRecordStructure);

		physicalStructureScheme.setPhysicalStructure(physicalStructure);

		physicalDataProduct.setPhysicalStructureScheme(physicalStructureScheme);

		return physicalDataProduct;
	}

	protected RecordLayoutScheme getRecordLayoutScheme(UUID variableSchemeId, Map<String, UUID> variableIdToUuidMap) {
		RecordLayoutScheme recordLayoutScheme = new RecordLayoutScheme(getAgency());

		RecordLayout recordLayout = new RecordLayout(getAgency(), variableSchemeId.toString());

		// Physical Structure Link Reference
		recordLayout.setReference("id");

		// Data List Item
		for (Map.Entry<String, UUID> entry : variableIdToUuidMap.entrySet()) {
			DataItem dataItem = new DataItem();

			dataItem.setReference(entry.getValue().toString(), getAgency());

			ProprietaryInfo proprietaryInfo = new ProprietaryInfo();
			proprietaryInfo.addProprietaryProperty(new ProprietaryProperty("Width", "???"));
			proprietaryInfo.addProprietaryProperty(new ProprietaryProperty("Decimals", "???"));
			proprietaryInfo.addProprietaryProperty(new ProprietaryProperty("WriteFormatType", "???"));
			dataItem.setProprietaryInfo(proprietaryInfo);

			recordLayout.addDataItem(dataItem);
		}

		recordLayoutScheme.setRecordLayout(recordLayout);

		return recordLayoutScheme;
	}

	protected List<VariableSchemeElement> getVariableSchemeElementList(Map<String, UUID> variableIdToUuidMap, Map<String, UUID> variableSchemeIdToUuidMap) {
		List<VariableSchemeElement> variableSchemeElementList = new ArrayList<>();

		for (VariableScheme variableScheme : getVariableSchemeList()) {
			UUID variableSchemeId = variableSchemeIdToUuidMap.get(variableScheme.getId());

			VariableSchemeElement variableSchemeElement = new VariableSchemeElement(variableSchemeId.toString(), getAgency());
			variableSchemeElement.setVariableSchemeName(getTitle());

			for (Variable variable : variableScheme.getVariableList()) {
				UUID variableId = variableIdToUuidMap.get(variable.getId());
				VariableElement variableElement = new VariableElement(variableId.toString(), getAgency());
				variableElement.setName(variable.getName());
				variableElement.setLabel(variable.getLabel(), getDdiLanguage());

				if (variable.getRepresentation() instanceof NumericRepresentation) {
					NumericRepresentation representation = (NumericRepresentation) variable.getRepresentation();

					variableElement.setVariableRepresentation(
						new NumericVariableRepresentation(representation.getType())
					);
				} else if (variable.getRepresentation() instanceof TextRepresentation) {
					TextRepresentation representation = (TextRepresentation) variable.getRepresentation();

					variableElement.setVariableRepresentation(
						new TextVariableRepresentation("text")
					);
				} else if (variable.getRepresentation() instanceof DateTimeRepresentation) {
					DateTimeRepresentation representation = (DateTimeRepresentation) variable.getRepresentation();

					variableElement.setVariableRepresentation(
						new DateTimeVariableRepresentation(representation.getType())
					);
				} else if (variable.getRepresentation() instanceof CodeRepresentation) {
					CodeRepresentation representation = (CodeRepresentation) variable.getRepresentation();

					CodeVariableRepresentation codeVariableRepresentation = new CodeVariableRepresentation("type");
					codeVariableRepresentation.setReferenceElement("id", getAgency());
					variableElement.setVariableRepresentation(codeVariableRepresentation);
				}

				variableSchemeElement.addVariableElement(variableElement);
			}
			variableSchemeElementList.add(variableSchemeElement);
		}

		return variableSchemeElementList;
	}
}