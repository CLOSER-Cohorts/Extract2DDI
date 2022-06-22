package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ReferenceObjectType;

public class CategorySchemeReference extends Reference {

	public static final String NODE_NAME_CATEGORY_LIST_REFERENCE = "r:CategorySchemeReference";

	public CategorySchemeReference(String id, String agency) {
		super(id, agency, NODE_NAME_CATEGORY_LIST_REFERENCE, ReferenceObjectType.CategoryScheme);
	}
}
