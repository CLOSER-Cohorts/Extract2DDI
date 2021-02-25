package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class VariableSchemeFragment extends Fragment {
	public static final String NODE_NAME_VARIABLE_SCHEME = "VariableScheme";

	private List<VariableReferenceFragment> variableList = new ArrayList<>();

	public VariableSchemeFragment(String id, String agency, int version) {
		super(id, agency, version);
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

		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		fragment.appendChild(variableScheme);
		element.appendChild(fragment);

		for (VariableReferenceFragment variable : getVariableList()) {
			variable.appendToElement(variableScheme, doc, namespace);
		}
	}
}
