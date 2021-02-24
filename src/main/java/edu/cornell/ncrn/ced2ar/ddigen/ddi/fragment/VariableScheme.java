package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableScheme extends Fragment {
	public static final String NODE_NAME_VARIABLE_SCHEME = "VariableScheme";

	private List<VariableReferenceFragment> variableList = new ArrayList<>();


	public VariableScheme(String agency, String id, int version) {
		super(agency, id, version);
	}

	public void addVariable(VariableReferenceFragment variable) {
		this.variableList.add(variable);
	}

	public List<VariableReferenceFragment> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<VariableReferenceFragment> variableList) {
		this.variableList = variableList;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element variableScheme = doc.createElementNS(namespace, NODE_NAME_VARIABLE_SCHEME);

		super.appendToElement(variableScheme, doc, namespace);

		{
			Element fragmentElement = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
			fragmentElement.appendChild(variableScheme);
			element.appendChild(fragmentElement);
		}

		for (VariableReferenceFragment variableFragment : variableList) {
			variableFragment.appendToElement(variableScheme, doc, namespace);
		}
	}
}
