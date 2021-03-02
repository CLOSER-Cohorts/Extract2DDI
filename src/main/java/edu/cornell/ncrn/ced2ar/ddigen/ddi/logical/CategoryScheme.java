package edu.cornell.ncrn.ced2ar.ddigen.ddi.logical;

import java.util.ArrayList;
import java.util.List;

public class CategoryScheme {

	private List<Category> categoryList = new ArrayList<>();
	private String id;

	public List<Category> getCategoryList() {
		return categoryList;
	}

	public String getId() {
		return id;
	}

	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}

	public void setId(String id) {
		this.id = id;
	}
}
