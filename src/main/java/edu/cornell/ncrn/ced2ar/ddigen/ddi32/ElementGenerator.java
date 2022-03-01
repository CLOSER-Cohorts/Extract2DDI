package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.AbstractSchemaGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategorySchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.BasedOnObject;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.BasedOnReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.GrossRecordStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.LogicalRecordReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalDataProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalRecordSegment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructureScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.DataItem;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.PhysicalStructureLinkReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.ProprietaryInfo;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.ProprietaryProperty;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayout;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayoutScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.CodeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.DateTimeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.NumericVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.TextVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableUsedReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariablesInRecordElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.VariableScheme;

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
		return dataRelationshipElement;
	}

	public DDIInstance getInstance() {
		Map<String, UUID> variableIdToUuidMap = getVariableIdToUuidMap();
		Map<String, UUID> categorySchemeIdToUuidMap = getCategorySchemeIdToUuidMap();

		DDIInstance ddiInstance = new DDIInstance(getAgency());

		// Resource package
		ResourcePackageElement resourcePackage = new ResourcePackageElement(getAgency());

		resourcePackage.setPurpose(new PurposeElement());

		LogicalProductElement logicalProduct = new LogicalProductElement(getAgency());

		// Data Relationship
		DataRelationshipElement dataRelationshipElement = getDataRelationshipElement(variableIdToUuidMap);

		logicalProduct.setDataRelationship(dataRelationshipElement);

		for (Map.Entry<String, UUID> entry : categorySchemeIdToUuidMap.entrySet()) {
			UUID uuid = entry.getValue();
			CategorySchemeReference categorySchemeReference = new CategorySchemeReference(uuid.toString(), getAgency());
			logicalProduct.addCategorySchemeReference(categorySchemeReference);
		}

		resourcePackage.setLogicalProduct(logicalProduct);

		// Physical Data Product
		PhysicalDataProduct physicalDataProduct = getPhysicalDataProduct();
		UUID variableSchemeId = UUID.randomUUID();
		physicalDataProduct.setRecordLayoutScheme(
			getRecordLayoutScheme(variableSchemeId, variableIdToUuidMap)
		);
		resourcePackage.setPhysicalDataProduct(physicalDataProduct);

		// Variable Scheme
		List<VariableSchemeElement> variableSchemeElementList = getVariableSchemeElementList(variableIdToUuidMap);

		for (VariableSchemeElement variableSchemeElement : variableSchemeElementList) {
			resourcePackage.addVariableSchemeList(variableSchemeElement);
		}

		ddiInstance.setResourcePackage(resourcePackage);

		return ddiInstance;
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

		logicalRecord.setLogicalProductName(new Name(LogicalRecordElement.NODE_NAME_LOGICAL_PRODUCT_NAME, getTitle()));

		return logicalRecord;
	}

	protected PhysicalDataProduct getPhysicalDataProduct() {
		PhysicalDataProduct physicalDataProduct = new PhysicalDataProduct(getAgency());

		PhysicalStructureScheme physicalStructureScheme = new PhysicalStructureScheme(getAgency());

		PhysicalStructure physicalStructure = new PhysicalStructure(getAgency());

		BasedOnObject basedOnObject = new BasedOnObject(getAgency());

		BasedOnReference basedOnReference = new BasedOnReference(UUID.randomUUID().toString(), getAgency());
		basedOnObject.setBasedOnReference(basedOnReference);

		physicalStructure.setBasedOnObject(basedOnObject);

		// Gross Record Structure
		GrossRecordStructure grossRecordStructure = new GrossRecordStructure(getAgency());

		grossRecordStructure.setLogicalRecordReference(
			new LogicalRecordReference(UUID.randomUUID().toString(), getAgency())
		);
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
		recordLayout.setReference(new PhysicalStructureLinkReference("id", getAgency()));

		// Data List Item
		for (Map.Entry<String, UUID> entry : variableIdToUuidMap.entrySet()) {
			DataItem dataItem = new DataItem();

			dataItem.setReference(new VariableReference(entry.getValue().toString(), getAgency()));

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

	protected List<VariableSchemeElement> getVariableSchemeElementList(Map<String, UUID> variableIdToUuidMap) {
		List<VariableSchemeElement> variableSchemeElementList = new ArrayList<>();
		for (VariableScheme variableScheme : getVariableSchemeList()) {
			VariableSchemeElement variableSchemeElement = new VariableSchemeElement("id", getAgency(), getVersion());
			variableSchemeElement.setVariableSchemeName(getTitle());

			for (Variable variable : variableScheme.getVariableList()) {
				UUID id = variableIdToUuidMap.get(variable.getId());
				VariableElement variableElement = new VariableElement(id.toString(), getAgency(), getVersion());
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
					codeVariableRepresentation.setReferenceElement(new CodeListReference("id", getAgency()));
					variableElement.setVariableRepresentation(codeVariableRepresentation);
				}

				variableSchemeElement.addVariableElement(variableElement);
			}
			variableSchemeElementList.add(variableSchemeElement);
		}

		return variableSchemeElementList;
	}
}