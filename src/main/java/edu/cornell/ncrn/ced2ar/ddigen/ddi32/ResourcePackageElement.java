package edu.cornell.ncrn.ced2ar.ddigen.ddi32;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable.VariableSchemeElement;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;

public class ResourcePackageElement extends ElementWithUrn {

	public static final String NODE_NAME_RESOURCE_PACKAGE = "g:ResourcePackage";

	private PurposeElement purpose;
	private LogicalProductElement logicalProduct;
	private List<VariableSchemeElement> variableSchemeList;

	public ResourcePackageElement(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element resourcePackage = doc.createElement(NODE_NAME_RESOURCE_PACKAGE);

		super.appendToElement(element, doc);

		// Purpose
		getPurpose().appendToElement(resourcePackage, doc);

		// Logical product
		getLogicalProduct().appendToElement(resourcePackage, doc);

		// Variable Scheme
		for (VariableSchemeElement variableSchemeElement : getVariableSchemeList()) {
			variableSchemeElement.appendToElement(resourcePackage, doc);
		}

		element.appendChild(resourcePackage);
	}

	public LogicalProductElement getLogicalProduct() {
		return logicalProduct;
	}

	public PurposeElement getPurpose() {
		return purpose;
	}

	public List<VariableSchemeElement> getVariableSchemeList() {
		return variableSchemeList;
	}

	public void setLogicalProduct(LogicalProductElement logicalProduct) {
		this.logicalProduct = logicalProduct;
	}

	public void setPurpose(PurposeElement purpose) {
		this.purpose = purpose;
	}

	public void setVariableSchemeList(List<VariableSchemeElement> variableSchemeList) {
		this.variableSchemeList = variableSchemeList;
	}
}
