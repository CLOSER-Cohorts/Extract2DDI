package edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi33.fragment.AbstractReferenceFragment;

public class CodeListReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:CodeListReference";

	public CodeListReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_VARIABLE_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "CodeList";
	}
}