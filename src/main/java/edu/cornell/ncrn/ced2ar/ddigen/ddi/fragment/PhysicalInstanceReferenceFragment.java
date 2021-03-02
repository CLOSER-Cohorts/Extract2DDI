package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

public class PhysicalInstanceReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_TOP_LEVEL_REFERENCE = "r:PhysicalInstanceReference";

	public PhysicalInstanceReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_TOP_LEVEL_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "PhysicalInstance";
	}
}