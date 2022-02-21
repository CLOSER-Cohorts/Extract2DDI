package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.variable;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class CodeListReference extends Reference {

	public static final String NODE_NAME_CODE_LIST_REFERENCE = "r:CodeListReference";

	public CodeListReference(String id, String agency, int version) {
		super(id, agency, version, NODE_NAME_CODE_LIST_REFERENCE, ReferenceObjectType.CodeList);
	}
}
