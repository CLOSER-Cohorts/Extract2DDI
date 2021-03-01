package edu.cornell.ncrn.ced2ar.ddigen.ddi.fragment;

public class ResourcePackageReferenceFragment extends AbstractReferenceFragment {

	public static final String NODE_NAME_TOP_LEVEL_REFERENCE = "ddi:TopLevelReference";

	public ResourcePackageReferenceFragment(String id, String agency, int version) {
		super(id, agency, version);
		setNodeNameReference(NODE_NAME_TOP_LEVEL_REFERENCE);
		setObjectType("ResourcePackage");
	}
}