package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.relationship;

import edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment.AbstractReferenceFragment;

public class DataRelationshipReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_DATA_RELATIONSHIP_REFERENCE = "r:DataRelationshipReference";

	public DataRelationshipReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
	}

	@Override
	protected String getNodeNameReference() {
		return NODE_NAME_DATA_RELATIONSHIP_REFERENCE;
	}

	@Override
	public String getObjectType() {
		return "DataRelationship";
	}
}