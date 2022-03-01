package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.resource;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.Citation;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.FragmentWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.category.CategorySchemeReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.instance.PhysicalInstanceReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.variable.VariableSchemeReferenceFragment;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class ResourcePackageFragment extends FragmentWithUrn {

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
	public void appendToElement(Element element, Document doc) {
		Element fragment = createFragment(doc);

		Element resourcePackage = doc.createElement(NODE_NAME_RESOURCE_PACKAGE);
		setUniversallyUniqueAttribute(resourcePackage);
		setVersionDateAttribute(resourcePackage);
		setNamespace(resourcePackage, NAMESPACE_GROUP);

		super.appendToElement(resourcePackage, doc);

		if (getCitation() != null) {
			getCitation().appendToElement(resourcePackage, doc);
		}

		for (PhysicalInstanceReferenceFragment reference : getPhysicalInstanceReferenceList()) {
			reference.appendToElement(resourcePackage, doc);
		}

		for (CategorySchemeReferenceFragment reference : getCategorySchemeReferenceList()) {
			reference.appendToElement(resourcePackage, doc);
		}

		for (VariableSchemeReferenceFragment reference : getVariableSchemeReferenceList()) {
			reference.appendToElement(resourcePackage, doc);
		}

		fragment.appendChild(resourcePackage);
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