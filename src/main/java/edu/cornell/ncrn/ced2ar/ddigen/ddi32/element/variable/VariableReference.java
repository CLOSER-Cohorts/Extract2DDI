package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class VariableReference extends Reference {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:VariableReference";

	public VariableReference(String id, String agency, int version) {
		super(id, agency, version, NODE_NAME_VARIABLE_REFERENCE, ReferenceObjectType.Variable);
	}
}
