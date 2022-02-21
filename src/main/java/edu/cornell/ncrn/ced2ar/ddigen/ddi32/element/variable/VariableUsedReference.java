package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class VariableUsedReference extends Reference {

	public static final String NODE_NAME_VARIABLE_USED_REFERENCE = "ddi:VariableUsedReference";

	public VariableUsedReference(String id, String agency, int version) {
		super(id, agency, version, NODE_NAME_VARIABLE_USED_REFERENCE, ReferenceObjectType.Variable);
	}

}
