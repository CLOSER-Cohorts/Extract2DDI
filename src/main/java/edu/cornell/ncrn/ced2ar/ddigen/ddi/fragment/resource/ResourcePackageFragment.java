package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.resource;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Citation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategorySchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance.PhysicalInstanceReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableSchemeReferenceFragment;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ResourcePackageFragment extends FragmentWithUrn {

	public static final String NODE_NAME_RESOURCE_PACKAGE = "ResourcePackage";

	private List<CategorySchemeReferenceFragment> categorySchemeReferenceFragmentList = new ArrayList<>();
	private Citation citation;
	private List<PhysicalInstanceReferenceFragment> physicalInstanceReferenceFragmentList = new ArrayList<>();
	private List<VariableSchemeReferenceFragment> variableSchemeReferenceFragmentList = new ArrayList<>();

	public ResourcePackageFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public void addCategorySchemeReferenceFragment(CategorySchemeReferenceFragment fragment) {
		this.categorySchemeReferenceFragmentList.add(fragment);
	}

	public void addPhysicalInstanceReferenceFragment(PhysicalInstanceReferenceFragment fragment) {
		this.physicalInstanceReferenceFragmentList.add(fragment);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		Element resourcePackage = doc.createElementNS(namespace, NODE_NAME_RESOURCE_PACKAGE);
		fragment.appendChild(resourcePackage);
		super.appendToElement(resourcePackage, doc, namespace);

		if (getCitation() != null) {
			getCitation().appendToElement(resourcePackage, doc, namespace);
		}

		for (PhysicalInstanceReferenceFragment physicalInstanceReferenceFragment : getPhysicalInstanceReferenceFragmentList()) {
			physicalInstanceReferenceFragment.appendToElement(resourcePackage, doc, namespace);
		}

		for (CategorySchemeReferenceFragment categorySchemeReferenceFragment : getCategorySchemeReferenceFragmentList()) {
			categorySchemeReferenceFragment.appendToElement(resourcePackage, doc, namespace);
		}

		for (VariableSchemeReferenceFragment variableSchemeReferenceFragment : getVariableSchemeReferenceFragmentList()) {
			variableSchemeReferenceFragment.appendToElement(resourcePackage, doc, namespace);
		}

		element.appendChild(fragment);
	}

	public void addVariableSchemeReferenceFragment(VariableSchemeReferenceFragment fragment) {
		this.variableSchemeReferenceFragmentList.add(fragment);
	}

	public List<CategorySchemeReferenceFragment> getCategorySchemeReferenceFragmentList() {
		return categorySchemeReferenceFragmentList;
	}

	public Citation getCitation() {
		return citation;
	}

	public List<PhysicalInstanceReferenceFragment> getPhysicalInstanceReferenceFragmentList() {
		return physicalInstanceReferenceFragmentList;
	}

	public List<VariableSchemeReferenceFragment> getVariableSchemeReferenceFragmentList() {
		return variableSchemeReferenceFragmentList;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}
}