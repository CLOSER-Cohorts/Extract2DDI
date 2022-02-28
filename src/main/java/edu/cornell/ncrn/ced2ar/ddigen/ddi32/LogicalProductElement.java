package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategorySchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListSchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.VariableSchemeReference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class LogicalProductElement extends ElementWithUrn {

	public static final String NODE_NAME_LOGICAL_PRODUCT = "ddi:LogicalProduct";
	public static final String NODE_NAME_LOGICAL_PRODUCT_NAME = "ddi:LogicalProductName";

	private DataRelationshipElement dataRelationship;
	private List<CategorySchemeReference> categorySchemeReferenceList = new ArrayList<>();
	private List<CodeListSchemeReference> codeListSchemeReferencesList = new ArrayList<>();
	private List<VariableSchemeReference> variableSchemeReferenceList = new ArrayList<>();

	public LogicalProductElement(String agency) {
		super(agency);
	}

	public void addCategorySchemeReference(CategorySchemeReference categorySchemeReference) {
		synchronized (categorySchemeReferenceList) {
			categorySchemeReferenceList.add(categorySchemeReference);
		}
	}

	public void addCodeListSchemeReference(CodeListSchemeReference codeListSchemeReference) {
		synchronized (categorySchemeReferenceList) {
			codeListSchemeReferencesList.add(codeListSchemeReference);
		}
	}

	public void addVariableSchemeReference(VariableSchemeReference variableSchemeReference) {
		synchronized (categorySchemeReferenceList) {
			variableSchemeReferenceList.add(variableSchemeReference);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalProduct = doc.createElement(NODE_NAME_LOGICAL_PRODUCT);

		super.appendToElement(element, doc);

		// Logical Record
		getDataRelationship().appendToElement(logicalProduct, doc);

		// Category Scheme References
		for (CategorySchemeReference categorySchemeReference : getCategorySchemeReferenceList()) {
			categorySchemeReference.appendToElement(logicalProduct, doc);
		}

		// Code List Scheme Reference
		for (CodeListSchemeReference codeListSchemeReference : getCodeListSchemeReferencesList()) {
			codeListSchemeReference.appendToElement(logicalProduct, doc);
		}

		// Variable Scheme Reference
		for (VariableSchemeReference variableSchemeReference : getVariableSchemeReferenceList()) {
			variableSchemeReference.appendToElement(logicalProduct, doc);
		}

		element.appendChild(logicalProduct);
	}

	public List<CategorySchemeReference> getCategorySchemeReferenceList() {
		return categorySchemeReferenceList;
	}

	public List<CodeListSchemeReference> getCodeListSchemeReferencesList() {
		return codeListSchemeReferencesList;
	}

	public DataRelationshipElement getDataRelationship() {
		return dataRelationship;
	}

	public List<VariableSchemeReference> getVariableSchemeReferenceList() {
		return variableSchemeReferenceList;
	}

	public void setDataRelationship(DataRelationshipElement dataRelationship) {
		this.dataRelationship = dataRelationship;
	}
}
