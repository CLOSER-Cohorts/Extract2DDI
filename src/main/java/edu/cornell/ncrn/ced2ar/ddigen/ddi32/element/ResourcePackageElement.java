package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.data.FileFormatInfo;
import edu.cornell.ncrn.ced2ar.ddigen.category.Category;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategorySchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical.LogicalProductElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.GrossRecordStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalDataProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructure;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalStructureScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.RecordLayoutScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
import org.apache.commons.math3.stat.Frequency;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ResourcePackageElement extends ElementWithUrn {

	public static final String NODE_NAME_RESOURCE_PACKAGE = "g:ResourcePackage";

	private List<CategorySchemeElement> categorySchemeList = new ArrayList<>();
	private Citation citation;
	private CodeListScheme codeListScheme;
	private Purpose purpose;
	private LogicalProductElement logicalProduct;
	private PhysicalDataProduct physicalDataProduct;
	private PhysicalInstance physicalInstance;
	private List<VariableSchemeElement> variableSchemeList = new ArrayList<>();

	public ResourcePackageElement(
			String agency,
			String ddiLanguage,
			String title,
			String datasetUri,
			String isPublic,
			String citationTitle,
			String citationAlternateTitle,
			List<VariableScheme> variableSchemes,
			List<CategoryScheme> categorySchemes,
			List<CodeList> codeLists,
			FileFormatInfo.Format dataFormat,
			String productIdentification,
			String statistics,
			Map<String, String> codeSchemeToCategorySchemeMap,
			Map<String, String> excludeVariableToStatMap,
			Map<String, Frequency> variableToFrequencyMap
	) {
		super(agency);

		Citation citation = new Citation(citationTitle + " Resource Package", citationAlternateTitle, ddiLanguage);
		setCitation(citation);

		// Purpose
		setPurpose(new Purpose());

		// Logical Product
		LogicalProductElement logicalProduct = new LogicalProductElement(
				getAgency(),
				(citationTitle != null && !citationTitle.isEmpty()) ? citationTitle : title,
				variableSchemes,
				categorySchemes,
				codeLists
		);
		setLogicalProduct(logicalProduct);

		UUID physicalRecordSegmentId = UUID.randomUUID();

		// Physical Data Product
		PhysicalDataProduct physicalDataProduct = getPhysicalDataProduct(physicalRecordSegmentId);

		VariableScheme variableScheme = variableSchemes.iterator().next();
		String variableSchemeId = variableScheme.getUuid();
		UUID recordLayoutId = UUID.randomUUID();
		RecordLayoutScheme recordLayoutScheme = new RecordLayoutScheme(
				getAgency(),
				recordLayoutId.toString(),
				variableSchemeId,
				ddiLanguage,
				dataFormat,
				productIdentification,
				physicalRecordSegmentId.toString(),
				variableSchemes
		);
		physicalDataProduct.setRecordLayoutScheme(recordLayoutScheme);
		setPhysicalDataProduct(physicalDataProduct);

		// Physical Instance
		PhysicalInstance physicalInstance =  new PhysicalInstance(
				getAgency(),
				(datasetUri != null && !datasetUri.isEmpty()) ? datasetUri : title,
				isPublic,
				ddiLanguage,
				10,
				dataFormat,
				productIdentification,
				citationTitle,
				citationAlternateTitle,
				variableSchemes,
				statistics,
				excludeVariableToStatMap,
				variableToFrequencyMap,
				codeLists
		);
		physicalInstance.setRecordLayoutReference(recordLayoutId.toString());
		setPhysicalInstance(physicalInstance);

		// Category Scheme
		for (CategoryScheme categoryScheme : categorySchemes) {
			addCategoryScheme(ddiLanguage, categoryScheme.getUuid(), categoryScheme.getId(), categoryScheme.getCategoryList());
		}

		// Code List Schemes
		setCodeListScheme(codeLists, categorySchemes, codeSchemeToCategorySchemeMap);

		// Variable Scheme
		List<VariableSchemeElement> variableSchemeElementList = new ArrayList<>();
		Map<String, UUID> codeListIdToUuidMap = new HashMap<>();
		for (CodeList codeList : codeLists) {
			codeListIdToUuidMap.put(codeList.getId(), UUID.randomUUID());
		}

		for (VariableScheme variableSchemeLocal : variableSchemes) {
			VariableSchemeElement variableSchemeElement = new VariableSchemeElement(
					variableSchemeLocal.getUuid(),
					getAgency(),
					ddiLanguage,
					(citationTitle != null && !citationTitle.isEmpty()) ? citationTitle : title,
					variableSchemeLocal.getVariableList(),
					codeListIdToUuidMap
			);

			variableSchemeElementList.add(variableSchemeElement);
		}
		for (VariableSchemeElement variableSchemeElement : variableSchemeElementList) {
			addVariableScheme(variableSchemeElement);
		}
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

	public void addCategoryScheme(String ddiLanguage, String categorySchemeId, String name, List<Category> categoryList) {
		synchronized (categorySchemeList) {
			CategorySchemeElement categorySchemeElement = new CategorySchemeElement(categorySchemeId, getAgency(), ddiLanguage, name, categoryList);
			categorySchemeList.add(categorySchemeElement);
		}
	}

	public void addVariableScheme(VariableSchemeElement variableSchemeElement) {
		synchronized (variableSchemeList) {
			variableSchemeList.add(variableSchemeElement);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element resourcePackage = doc.createElement(NODE_NAME_RESOURCE_PACKAGE);

		super.appendToElement(resourcePackage, doc);

		// Citation
		if (getCitation() != null) {
			getCitation().appendToElement(resourcePackage, doc);
		}

		// Purpose
		getPurpose().appendToElement(resourcePackage, doc);

		// Logical product
		getLogicalProduct().appendToElement(resourcePackage, doc);

		// Physical Data Product
		getPhysicalDataProduct().appendToElement(resourcePackage, doc);

		// Physical Instance
		physicalInstance.appendToElement(resourcePackage, doc);

		// Category Scheme
		for (CategorySchemeElement categorySchemeElement : getCategorySchemeList()) {
			categorySchemeElement.appendToElement(resourcePackage, doc);
		}

		// Code List Scheme
		getCodeListScheme().appendToElement(resourcePackage, doc);

		// Variable Scheme
		for (VariableSchemeElement variableSchemeElement : getVariableSchemeList()) {
			variableSchemeElement.appendToElement(resourcePackage, doc);
		}

		element.appendChild(resourcePackage);
	}

	public List<CategorySchemeElement> getCategorySchemeList() {
		return categorySchemeList;
	}

	public Citation getCitation() {
		return citation;
	}

	public CodeListScheme getCodeListScheme() {
		return codeListScheme;
	}

	public LogicalProductElement getLogicalProduct() {
		return logicalProduct;
	}

	public PhysicalDataProduct getPhysicalDataProduct() {
		return physicalDataProduct;
	}

	public Purpose getPurpose() {
		return purpose;
	}

	public List<VariableSchemeElement> getVariableSchemeList() {
		return variableSchemeList;
	}

	public void setCodeListScheme(List<CodeList> codeListList, List<CategoryScheme> categorySchemeList, Map<String, String> codeSchemeToCategorySchemeMap) {
		CodeListScheme codeListScheme = new CodeListScheme(getAgency());
		for (CodeList codeList : codeListList) {
			CodeListElement codeListElement = new CodeListElement(codeList.getUuid(), getAgency());
			codeListElement.setName(codeList.getLabel());

			String categorySchemeId = codeSchemeToCategorySchemeMap.get(codeList.getId());
			CategoryScheme categoryScheme = categorySchemeList.stream().filter(
					scheme -> scheme.getId().equalsIgnoreCase(categorySchemeId)
			).findFirst().orElse(null);

			for (Code code : codeList.getCodeList()) {
				CodeElement codeElement = new CodeElement(getAgency(), code.getValue());

				if (categoryScheme != null) {
					for (Category category : categoryScheme.getCategoryList()) {
						if (category.getId().equalsIgnoreCase(code.getCategoryId())) {
							codeElement.setCategoryReference(category.getUuid());
							break;
						}
					}
				}
				codeListElement.addCode(codeElement);
			}
			codeListScheme.addCodeList(codeListElement);
		}
		this.codeListScheme = codeListScheme;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}
	public void setLogicalProduct(LogicalProductElement logicalProduct) {
		this.logicalProduct = logicalProduct;
	}

	public void setPhysicalDataProduct(PhysicalDataProduct physicalDataProduct) {
		this.physicalDataProduct = physicalDataProduct;
	}

	public void setPhysicalInstance(PhysicalInstance physicalInstance) {
		this.physicalInstance = physicalInstance;
	}

	public void setPurpose(Purpose purpose) {
		this.purpose = purpose;
	}
}
