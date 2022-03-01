package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class VariablesInRecordElement implements Appendable {

	public static final String NODE_NAME_LOGICAL_PRODUCT = "ddi:VariablesInRecord";

	private List<VariableUsedReferenceElement> variableUsedReferenceList = new ArrayList<>();

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variablesInRecord = doc.createElement(NODE_NAME_LOGICAL_PRODUCT);

		for (VariableUsedReferenceElement variableUsedReference : variableUsedReferenceList) {
			variableUsedReference.appendToElement(variablesInRecord, doc);
		}

		element.appendChild(variablesInRecord);
	}

	public List<VariableUsedReferenceElement> getVariableUsedReferenceList() {
		return variableUsedReferenceList;
	}

	public void setVariableUsedReferenceList(List<VariableUsedReferenceElement> variableUsedReferenceList) {
		this.variableUsedReferenceList = variableUsedReferenceList;
	}
}
