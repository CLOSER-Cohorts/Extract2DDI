package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.StringElement;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class LogicalRecordFragment extends FragmentWithUrn {

	public static final String NODE_NAME_LOGICAL_RECORD = "r:LogicalRecord";
	public static final String NODE_NAME_LOGICAL_RECORD_NAME = "r:LogicalRecordName";
	public static final String NODE_NAME_VARIABLES_IN_RECORD = "VariablesInRecord";

	private StringElement name;
	private List<VariableUsedReferenceFragment> variableUsedReferenceList = new ArrayList<>();

	public LogicalRecordFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public void addVariableUsedReference(VariableUsedReferenceFragment fragment) {
		variableUsedReferenceList.add(fragment);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_LOGICAL_RECORD);
		element.appendChild(fragment);

		super.appendToElement(fragment, doc, namespace);

		if (getName() != null) {
			Element logicalRecordName = doc.createElementNS(namespace, NODE_NAME_LOGICAL_RECORD_NAME);
			getName().appendToElement(logicalRecordName, doc, namespace);
			fragment.appendChild(logicalRecordName);
		}

		Element variablesInRecord = doc.createElementNS(namespace, NODE_NAME_VARIABLES_IN_RECORD);
		fragment.appendChild(variablesInRecord);

		for (VariableUsedReferenceFragment variableUsedReferenceFragment : getVariableUsedReferenceList()) {
			variableUsedReferenceFragment.appendToElement(variablesInRecord, doc, namespace);
		}
	}

	public StringElement getName() {
		return name;
	}

	public List<VariableUsedReferenceFragment> getVariableUsedReferenceList() {
		return variableUsedReferenceList;
	}

	public void setName(StringElement name) {
		this.name = name;
	}

	public void setVariableUsedReferenceList(List<VariableUsedReferenceFragment> variableUsedReferenceList) {
		this.variableUsedReferenceList = variableUsedReferenceList;
	}
}