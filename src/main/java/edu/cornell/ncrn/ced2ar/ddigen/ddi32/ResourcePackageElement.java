package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code.CodeListScheme;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical.PhysicalDataProduct;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class ResourcePackageElement extends ElementWithUrn {

	public static final String NODE_NAME_RESOURCE_PACKAGE = "g:ResourcePackage";

	private CodeListScheme codeListScheme;
	private PurposeElement purpose;
	private LogicalProductElement logicalProduct;
	private PhysicalDataProduct physicalDataProduct;
	private List<VariableSchemeElement> variableSchemeList = new ArrayList<>();

	public ResourcePackageElement(String agency) {
		super(agency);
	}

	public void addVariableSchemeList(VariableSchemeElement variableSchemeElement) {
		synchronized (variableSchemeList) {
			variableSchemeList.add(variableSchemeElement);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element resourcePackage = doc.createElement(NODE_NAME_RESOURCE_PACKAGE);

		super.appendToElement(element, doc);

		// Purpose
		getPurpose().appendToElement(resourcePackage, doc);

		// Logical product
		getLogicalProduct().appendToElement(resourcePackage, doc);

		// Physical Data Product
		getPhysicalDataProduct().appendToElement(resourcePackage, doc);

		// Category Scheme

		// Code List Scheme
		getCodeListScheme().appendToElement(resourcePackage, doc);

		// Variable Scheme
		for (VariableSchemeElement variableSchemeElement : getVariableSchemeList()) {
			variableSchemeElement.appendToElement(resourcePackage, doc);
		}

		element.appendChild(resourcePackage);
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

	public PurposeElement getPurpose() {
		return purpose;
	}

	public List<VariableSchemeElement> getVariableSchemeList() {
		return variableSchemeList;
	}

	public void setCodeListScheme(CodeListScheme codeListScheme) {
		this.codeListScheme = codeListScheme;
	}

	public void setLogicalProduct(LogicalProductElement logicalProduct) {
		this.logicalProduct = logicalProduct;
	}

	public void setPhysicalDataProduct(PhysicalDataProduct physicalDataProduct) {
		this.physicalDataProduct = physicalDataProduct;
	}

	public void setPurpose(PurposeElement purpose) {
		this.purpose = purpose;
	}
}
