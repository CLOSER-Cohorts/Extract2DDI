package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CategorySchemeFragment extends Fragment {
	public static final String NODE_NAME_CATEGORY_SCHEME = "CategoryScheme";

	private List<CategoryReferenceFragment> variableList = new ArrayList<>();

	public CategorySchemeFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public void addVariable(CategoryReferenceFragment variable) {
		this.variableList.add(variable);
	}

	public List<CategoryReferenceFragment> getVariableList() {
		return variableList;
	}

	public void setVariableList(List<CategoryReferenceFragment> variableList) {
		this.variableList = variableList;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		Element variableScheme = doc.createElementNS(namespace, NODE_NAME_CATEGORY_SCHEME);

		super.appendToElement(variableScheme, doc, namespace);

		fragment.appendChild(variableScheme);
		element.appendChild(fragment);

		for (CategoryReferenceFragment referenceFragment : variableList) {
			referenceFragment.appendToElement(variableScheme, doc, namespace);
		}
	}
}
