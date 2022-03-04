package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.Appendable;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class VariablesInRecordElement implements Appendable {

	public static final String NODE_NAME_LOGICAL_PRODUCT = "ddi:VariablesInRecord";

	private List<VariableUsedReference> variableUsedReferenceList = new ArrayList<>();

	public void addReference(VariableUsedReference reference) {
		synchronized (variableUsedReferenceList)  {
			variableUsedReferenceList.add(reference);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element variablesInRecord = doc.createElement(NODE_NAME_LOGICAL_PRODUCT);

		for (Reference variableUsedReference : variableUsedReferenceList) {
			variableUsedReference.appendToElement(variablesInRecord, doc);
		}

		element.appendChild(variablesInRecord);
	}

	public List<VariableUsedReference> getVariableUsedReferenceList() {
		return variableUsedReferenceList;
	}
}
