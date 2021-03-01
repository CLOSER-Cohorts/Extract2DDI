package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractReferenceFragment;

public class CodeListReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:CodeListReference";

	public CodeListReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
		setNodeNameReference(NODE_NAME_VARIABLE_REFERENCE);
		setObjectType("CodeList");
	}
}