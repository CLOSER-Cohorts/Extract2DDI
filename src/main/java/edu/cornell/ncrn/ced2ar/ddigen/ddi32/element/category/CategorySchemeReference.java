package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category;

import edu.cornell.ncrn.ced2ar.ddigen.ddi32.Reference;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.ReferenceObjectType;

public class CategorySchemeReference extends Reference {

	public static final String NODE_NAME_CATEGORY_LIST_REFERENCE = "r:CategoryListReference";

	public CategorySchemeReference(String id, String agency) {
		super(id, agency, NODE_NAME_CATEGORY_LIST_REFERENCE, ReferenceObjectType.CategoryScheme);
	}
}
