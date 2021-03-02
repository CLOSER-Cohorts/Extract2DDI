package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

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
		return "PackageReference";
	}
}