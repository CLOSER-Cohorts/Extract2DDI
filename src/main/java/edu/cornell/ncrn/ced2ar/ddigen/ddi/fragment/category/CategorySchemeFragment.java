package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class CategorySchemeFragment extends FragmentWithUrn {
	public static final String NODE_NAME_CATEGORY_SCHEME = "CategoryScheme";

	private List<CategoryReferenceFragment> categoryReferenceList = new ArrayList<>();

	public CategorySchemeFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public void addCategoryReference(CategoryReferenceFragment variable) {
		this.categoryReferenceList.add(variable);
	}

	public List<CategoryReferenceFragment> getVariableList() {
		return categoryReferenceList;
	}

	public void setVariableList(List<CategoryReferenceFragment> categoryReferenceList) {
		this.categoryReferenceList = categoryReferenceList;
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		fragment.setAttribute(ATTRIBUTE_NAME_NAMESPACE_R, ATTRIBUTE_VALUE_NAMESPACE_R);

		Element categoryScheme = doc.createElementNS(namespace, NODE_NAME_CATEGORY_SCHEME);

		super.appendToElement(categoryScheme, doc, namespace);

		fragment.appendChild(categoryScheme);
		element.appendChild(fragment);

		for (CategoryReferenceFragment reference : getVariableList()) {
			reference.appendToElement(categoryScheme, doc, namespace);
		}
	}
}
