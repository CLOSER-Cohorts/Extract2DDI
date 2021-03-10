package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.resource;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Citation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.Fragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.category.CategorySchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.instance.PhysicalInstanceReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable.VariableSchemeReferenceFragment;
import java.util.ArrayList;
import java.util.List;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ResourcePackageFragment extends FragmentWithUrn {

	public static final String ATTRIBUTE_NAME_NAMESPACE = "xmlns";
	public static final String ATTRIBUTE_VALUE_NAMESPACE = "ddi:group:3_3";

	public static final String NODE_NAME_RESOURCE_PACKAGE = "ResourcePackage";

	private List<CategorySchemeReferenceFragment> categorySchemeReferenceList = new ArrayList<>();
	private Citation citation;
	private List<PhysicalInstanceReferenceFragment> physicalInstanceReferenceList = new ArrayList<>();
	private List<VariableSchemeReferenceFragment> variableSchemeReferenceList = new ArrayList<>();

	public ResourcePackageFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	public void addCategorySchemeReference(CategorySchemeReferenceFragment fragment) {
		this.categorySchemeReferenceList.add(fragment);
	}

	public void addPhysicalInstanceReference(PhysicalInstanceReferenceFragment fragment) {
		this.physicalInstanceReferenceList.add(fragment);
	}

	@Override
	public void appendToElement(Element element, Document doc, String namespace) {
		Element fragment = doc.createElementNS(namespace, NODE_NAME_FRAGMENT);
		fragment.setAttribute(ATTRIBUTE_NAME_NAMESPACE_R, ATTRIBUTE_VALUE_NAMESPACE_R);

		Element resourcePackage = doc.createElementNS(namespace, NODE_NAME_RESOURCE_PACKAGE);
		resourcePackage.setAttribute(ATTRIBUTE_NAME_IS_UNIVERSALLY_UNIQUE, ATTRIBUTE_VALUE_TRUE);
		resourcePackage.setAttribute(ATTRIBUTE_NAME_NAMESPACE, ATTRIBUTE_VALUE_NAMESPACE);
		fragment.appendChild(resourcePackage);

		super.appendToElement(resourcePackage, doc, namespace);

		if (getCitation() != null) {
			getCitation().appendToElement(resourcePackage, doc, namespace);
		}

		for (PhysicalInstanceReferenceFragment physicalInstanceReference : getPhysicalInstanceReferenceList()) {
			physicalInstanceReference.appendToElement(resourcePackage, doc, namespace);
		}

		for (CategorySchemeReferenceFragment categorySchemeReference : getCategorySchemeReferenceList()) {
			categorySchemeReference.appendToElement(resourcePackage, doc, namespace);
		}

		for (VariableSchemeReferenceFragment variableSchemeReference : getVariableSchemeReferenceList()) {
			variableSchemeReference.appendToElement(resourcePackage, doc, namespace);
		}

		element.appendChild(fragment);
	}

	public void addVariableSchemeReference(VariableSchemeReferenceFragment fragment) {
		this.variableSchemeReferenceList.add(fragment);
	}

	public List<CategorySchemeReferenceFragment> getCategorySchemeReferenceList() {
		return categorySchemeReferenceList;
	}

	public Citation getCitation() {
		return citation;
	}

	public List<PhysicalInstanceReferenceFragment> getPhysicalInstanceReferenceList() {
		return physicalInstanceReferenceList;
	}

	public List<VariableSchemeReferenceFragment> getVariableSchemeReferenceList() {
		return variableSchemeReferenceList;
	}

	public void setCitation(Citation citation) {
		this.citation = citation;
	}
}