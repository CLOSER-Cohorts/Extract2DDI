package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.ArrayList;
import java.util.List;

public class CategoryScheme {

	private List<Category> categoryList = new ArrayList<>();

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
}
