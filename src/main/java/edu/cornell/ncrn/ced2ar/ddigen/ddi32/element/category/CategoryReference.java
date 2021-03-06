package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ReferenceObjectType;

public class CategoryReference extends Reference {

	public static final String NODE_NAME_CATEGORY_REFERENCE = "r:CategoryReference";

	public CategoryReference(String id, String agency) {
		super(id, agency, NODE_NAME_CATEGORY_REFERENCE, ReferenceObjectType.Category);
	}
}
