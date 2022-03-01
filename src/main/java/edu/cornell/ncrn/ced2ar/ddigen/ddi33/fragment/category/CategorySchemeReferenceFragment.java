package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.AbstractReferenceFragment;

public class CategorySchemeReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_CATEGORY_SCHEME_REFERENCE = "r:CategorySchemeReference";

	public CategorySchemeReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_CATEGORY_SCHEME_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "CategoryScheme";
	}
}