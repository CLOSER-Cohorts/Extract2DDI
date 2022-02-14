package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.resource;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.AbstractReferenceFragment;

public class TopLevelReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_TOP_LEVEL_REFERENCE = "ddi:TopLevelReference";

	public TopLevelReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_TOP_LEVEL_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "ResourcePackage";
	}
}