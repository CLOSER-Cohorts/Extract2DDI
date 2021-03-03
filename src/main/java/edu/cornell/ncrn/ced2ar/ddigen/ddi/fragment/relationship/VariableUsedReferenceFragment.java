package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractReferenceFragment;
import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.StringElement;

public class VariableUsedReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_VARIABLE_USED_REFERENCE = "r:VariableUsedReference";

	private StringElement name;

	public VariableUsedReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_VARIABLE_USED_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "Variable";
	}
}