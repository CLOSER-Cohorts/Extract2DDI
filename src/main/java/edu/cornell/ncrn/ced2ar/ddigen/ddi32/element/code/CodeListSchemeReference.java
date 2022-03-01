package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.code;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class CodeListSchemeReference extends Reference {

	public static final String NODE_NAME_CODE_LIST_SCHEME_REFERENCE = "r:CodeListSchemeReference";

	public CodeListSchemeReference(String id, String agency) {
		super(id, agency, NODE_NAME_CODE_LIST_SCHEME_REFERENCE, ReferenceObjectType.CodeListScheme);
	}
}
