package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

public class PhysicalInstanceReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_TOP_LEVEL_REFERENCE = "r:PhysicalInstanceReference";

	public PhysicalInstanceReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
		setNodeNameReference(NODE_NAME_TOP_LEVEL_REFERENCE);
		setObjectType("PhysicalInstance");
	}
}