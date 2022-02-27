package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class BasedOnReference extends Reference {

	public static final String NODE_NAME_BASED_ON_REFERENCE = "r:BasedOnReference";

	public BasedOnReference(String id, String agency) {
		super(id, agency, NODE_NAME_BASED_ON_REFERENCE, ReferenceObjectType.LogicalProduct);
	}
}
