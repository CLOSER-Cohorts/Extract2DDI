package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.physical;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ReferenceObjectType;

public class LogicalRecordReference extends Reference {

	public static final String NODE_NAME_PHYSICAL_DATA_PRODUCT = "p:LogicalRecordReference";

	public LogicalRecordReference(String id, String agency) {
		super(id, agency, NODE_NAME_PHYSICAL_DATA_PRODUCT, ReferenceObjectType.LogicalProduct);
	}
}
