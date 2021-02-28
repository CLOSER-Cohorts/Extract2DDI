package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractReferenceFragment;

public class VariableReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:VariableReference";

	public VariableReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
		setNodeNameReference(NODE_NAME_VARIABLE_REFERENCE);
		setObjectType("Variable");
	}
}