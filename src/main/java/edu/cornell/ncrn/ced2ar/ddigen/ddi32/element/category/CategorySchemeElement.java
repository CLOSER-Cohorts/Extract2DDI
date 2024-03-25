package edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.category;

import edu.cornell.ncrn.ced2ar.ddigen.category.Category;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.ElementWithUrn;
import edu.cornell.ncrn.ced2ar.ddigen.ddi32.element.Name;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.List;

public class CategorySchemeElement extends ElementWithUrn {

	public static final String NODE_NAME_CATEGORY_SCHEME = "ddi:CategoryScheme";
	public static final String NODE_NAME_CATEGORY_SCHEME_NAME = "ddi:CategorySchemeName";

	private List<CategoryElement> categoryList = new ArrayList<>();
	private Name name;

	public CategorySchemeElement(String id, String agency, String ddiLanguage, String name, List<Category> categoryList) {
		super(id, agency);
		setName(name);

		for (Category category : categoryList) {
			addCategory(category.getUuid(),category.getLabel(), ddiLanguage);
		}
	}

	public void addCategory(String categoryId, String label, String ddiLanguage) {
		synchronized (categoryList) {
			CategoryElement categoryElement = new CategoryElement(categoryId, getAgency(), label, ddiLanguage);
			categoryList.add(categoryElement);
		}
	}

	@Override
	public void appendToElement(Element element, Document doc) {
		Element categoryScheme = doc.createElement(NODE_NAME_CATEGORY_SCHEME);

		super.appendToElement(categoryScheme, doc);

		getName().appendToElement(categoryScheme, doc);

		// Categories
		for (CategoryElement category : getCategoryList()) {
			category.appendToElement(categoryScheme, doc);
		}

		element.appendChild(categoryScheme);
	}

	public List<CategoryElement> getCategoryList() {
		return categoryList;
	}

	public Name getName() {
		return name;
	}

	public void setName(String content) {
		this.name = new Name(NODE_NAME_CATEGORY_SCHEME_NAME, content);
	}
}
