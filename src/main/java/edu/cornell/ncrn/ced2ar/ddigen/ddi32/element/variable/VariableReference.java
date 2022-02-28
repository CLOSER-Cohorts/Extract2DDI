package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ReferenceObjectType;

public class VariableReference extends Reference {

	public static final String NODE_NAME_VARIABLE_REFERENCE = "r:VariableReference";

	public VariableReference(String id, String agency) {
		super(id, agency, NODE_NAME_VARIABLE_REFERENCE, ReferenceObjectType.Variable);
	}
}
