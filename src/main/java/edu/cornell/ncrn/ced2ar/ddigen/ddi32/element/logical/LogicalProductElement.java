package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.logical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category.CategorySchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListSchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.record.VariableSchemeReference;
import edu.cornell.ncrn.ced2ar.ddigen.variable.VariableScheme;
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

	public void addCategorySchemeReference(String categorySchemeId) {
		synchronized (categorySchemeReferenceList) {
			CategorySchemeReference categorySchemeReference = new CategorySchemeReference(categorySchemeId, getAgency());
			categorySchemeReferenceList.add(categorySchemeReference);
		}
	}

	public void addCodeListSchemeReference(String codeListId) {
		synchronized (categorySchemeReferenceList) {
			CodeListSchemeReference codeListSchemeReference = new CodeListSchemeReference(codeListId, getAgency());
			codeListSchemeReferencesList.add(codeListSchemeReference);
		}
	}

	public void addVariableSchemeReference(String variableSchemeId) {
		synchronized (categorySchemeReferenceList) {
			VariableSchemeReference variableSchemeReference = new VariableSchemeReference(variableSchemeId, getAgency());
			variableSchemeReferenceList.add(variableSchemeReference);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element logicalProduct = doc.createElement(NODE_NAME_LOGICAL_PRODUCT);

		super.appendToElement(logicalProduct, doc);

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

	public void setDataRelationship(String title, List<VariableScheme> variableSchemeList) {
		LogicalRecordElement logicalRecord = new LogicalRecordElement(getAgency(), title, variableSchemeList);


		DataRelationshipElement dataRelationshipElement = new DataRelationshipElement(getAgency());
		dataRelationshipElement.setLogicalRecord(logicalRecord);
		this.dataRelationship = dataRelationshipElement;
	}
}
