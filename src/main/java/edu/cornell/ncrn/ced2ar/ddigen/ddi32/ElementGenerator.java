package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.AbstractSchemaGenerator;
import edu.cornell.ncrn.ced2ar.ddigen.csv.Ced2arVariableStat;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.CodeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.DateTimeVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.NumericVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.TextVariableRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableUsedReferenceElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariablesInRecordElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.CodeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.DateTimeRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.NumericRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.TextRepresentation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.Variable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.VariableScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Citation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.StringElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Title;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.category.CategorySchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance.DataFileIdentification;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance.GrossFileStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance.PhysicalInstanceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance.PhysicalInstanceReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.relationship.DataRelationshipFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.relationship.DataRelationshipReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.relationship.LogicalRecordFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.relationship.VariableUsedReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.resource.ResourcePackageFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable.VariableSchemeReferenceFragment;

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

	public DDIInstance getInstance() {
		Map<String, UUID> variableIdToUuidMap = getVariableIdToUuidMap();

		DDIInstance ddiInstance = new DDIInstance("id", getAgency(), getVersion());

		// Resource package
		ResourcePackageElement resourcePackage = new ResourcePackageElement("id", getAgency(), getVersion());

		resourcePackage.setPurpose(new PurposeElement());

		LogicalProductElement logicalProduct = new LogicalProductElement("id", getAgency(), getVersion());

		LogicalRecordElement logicalRecord = getLogicalRecord(variableIdToUuidMap);

		logicalProduct.setLogicalRecord(logicalRecord);

		resourcePackage.setLogicalProduct(logicalProduct);

		// Variable Scheme
		List<VariableSchemeElement> variableSchemeElementList = getVariableSchemeElementList(variableIdToUuidMap);

		resourcePackage.setVariableSchemeList(variableSchemeElementList);

		ddiInstance.setResourcePackage(resourcePackage);

		return ddiInstance;
	}

	protected LogicalRecordElement getLogicalRecord(Map<String, UUID> variableIdToUuidMap) {
		LogicalRecordElement logicalRecord = new LogicalRecordElement("id", getAgency(), getVersion());

		VariablesInRecordElement variablesInRecord = new VariablesInRecordElement();

		List<VariableUsedReferenceElement> variableUsedReferenceList = new ArrayList<>();
		for (VariableScheme variableScheme : getVariableSchemeList()) {
			for (Variable variable : variableScheme.getVariableList()) {
				if (variable.getId() != null) {
					UUID id = variableIdToUuidMap.get(variable.getId());
					VariableUsedReferenceElement variableUsedReference = new VariableUsedReferenceElement(id.toString(), getAgency(), getVersion());
					variableUsedReferenceList.add(variableUsedReference);
				}
			}
		}

		variablesInRecord.setVariableUsedReferenceList(variableUsedReferenceList);
		logicalRecord.setVariablesInRecord(variablesInRecord);

		logicalRecord.setLogicalProductName(new Name(LogicalRecordElement.NODE_NAME_LOGICAL_PRODUCT_NAME, getTitle()));

		return logicalRecord;
	}

	protected List<VariableSchemeElement> getVariableSchemeElementList(Map<String, UUID> variableIdToUuidMap) {
		List<VariableSchemeElement> variableSchemeElementList = new ArrayList<>();
		for (VariableScheme variableScheme : getVariableSchemeList()) {
			VariableSchemeElement variableSchemeElement = new VariableSchemeElement("id", getAgency(), getVersion());
			variableSchemeElement.setVariableSchemeName(getTitle());
			List<VariableElement> variableElementList = new ArrayList<>();

			for (Variable variable : variableScheme.getVariableList()) {
				UUID id = variableIdToUuidMap.get(variable.getId());
				VariableElement variableElement = new VariableElement(id.toString(), getAgency(), getVersion());
				variableElement.setName(variable.getName());
				variableElement.setLabel(variable.getLabel());

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

					variableElement.setVariableRepresentation(
						new CodeVariableRepresentation("type")
					);
				}

				variableElementList.add(variableElement);
			}
			variableSchemeElement.setVariableElementList(variableElementList);
			variableSchemeElementList.add(variableSchemeElement);
		}

		return variableSchemeElementList;
	}
}