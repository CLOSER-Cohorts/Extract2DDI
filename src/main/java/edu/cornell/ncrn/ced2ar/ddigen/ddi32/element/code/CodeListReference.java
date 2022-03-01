package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class CodeListReference extends Reference {

	public static final String NODE_NAME_CODE_LIST_REFERENCE = "r:CodeListSchemeReference";

	public CodeListReference(String id, String agency) {
		super(id, agency, NODE_NAME_CODE_LIST_REFERENCE, ReferenceObjectType.CodeList);
	}
}
