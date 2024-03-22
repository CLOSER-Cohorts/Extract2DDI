package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element;

import edu.cornell.ncrn.ced2ar.ddigen.category.Category;
import edu.cornell.ncrn.ced2ar.ddigen.category.CategoryScheme;
import edu.cornell.ncrn.ced2ar.ddigen.code.Code;
import edu.cornell.ncrn.ced2ar.ddigen.code.CodeList;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategorySchemeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical.LogicalProductElement;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalDataProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalInstance;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

	public ResourcePackageElement(String agency, String ddiLanguage, String citationTitle, String citationAlternateTitle) {
		super(agency);

		Citation citation = new Citation(citationTitle, citationAlternateTitle, ddiLanguage);
		setCitation(citation);
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
