package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractReferenceFragment;

public class VariableSchemeReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_VARIABLE_SCHEME_REFERENCE = "r:VariableSchemeReference";

	public VariableSchemeReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_VARIABLE_SCHEME_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "VariableScheme";
	}
}